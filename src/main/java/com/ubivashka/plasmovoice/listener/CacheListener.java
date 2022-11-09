package com.ubivashka.plasmovoice.listener;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.ubivashka.plasmovoice.PlasmoVoiceAddon;
import com.ubivashka.plasmovoice.event.url.URLSoundFormatPreCreateEvent;
import com.ubivashka.plasmovoice.event.url.URLSoundPlayEvent;
import com.ubivashka.plasmovoice.event.url.URLSoundPreCreateEvent;
import com.ubivashka.plasmovoice.sound.ISound;
import com.ubivashka.plasmovoice.sound.ISoundFormat;
import com.ubivashka.plasmovoice.sound.cache.CachedSound;
import com.ubivashka.plasmovoice.sound.frame.ISoundFrameProvider;

public class CacheListener implements Listener {
    private static final String FILE_URL_PROTOCOL = "file";
    private final PlasmoVoiceAddon plugin;

    public CacheListener(PlasmoVoiceAddon plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onSoundPlay(URLSoundPlayEvent e) {
        Optional<CachedSound> cachedSoundOptional = plugin.getCachedSoundHolder().findCachedSound(e.getSoundEventModel().getSource());
        if (cachedSoundOptional.isPresent())
            return;
        ISoundFrameProvider frameProvider = e.getSoundPlaySession().getSound().getFrameProvider();
        if (frameProvider.getFramesCount() <= 0)
            return;
        try {
            URL url = e.getSoundEventModel().getSource();
            if (url.getProtocol().equals(FILE_URL_PROTOCOL))
                return;
            long contentLength = url.openConnection().getContentLengthLong();
            if (plugin.getPluginConfig().getCachingSettings().getSizeLimit() > 0 &&
                    contentLength >= plugin.getPluginConfig().getCachingSettings().getSizeLimit())
                return;
            if (contentLength <= 0 && plugin.getPluginConfig().getCachingSettings().isBlockUndefinedSize())
                return;
            frameProvider.getAllFrames().thenAccept(frames -> {
                if (frames.isEmpty())
                    return;
                try {
                    Path cacheFolder = plugin.getPluginConfig().getCachingSettings().getCacheFolder().toPath();
                    Path cachedSoundPath = Files.createTempFile(cacheFolder, "", "");
                    plugin.getCachedSoundHolder()
                            .add(new CachedSound(url.toString(), e.getSoundFormat().getName(),
                                    cachedSoundPath.getFileName().toString()));

                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
                    dataOutputStream.writeUTF(e.getSoundFormat().getName());
                    dataOutputStream.writeInt(frames.size());
                    frames.forEach(data -> {
                        try {
                            dataOutputStream.writeInt(data.length);
                            dataOutputStream.write(data);
                        } catch(IOException ex) {
                            ex.printStackTrace();
                        }
                    });
                    Files.write(cachedSoundPath, outputStream.toByteArray());
                } catch(IOException ex) {
                    ex.printStackTrace();
                }
            });
        } catch(IOException ex) {
            ex.printStackTrace();
        }
    }

    @EventHandler
    public void onUrlSoundPreCreate(URLSoundPreCreateEvent e) throws IOException {
        Optional<CachedSound> cachedSoundOptional = plugin.getCachedSoundHolder().findCachedSound(e.getSoundEventModel().getSource());
        if (!cachedSoundOptional.isPresent())
            return;
        Path cachedSoundPath = plugin.getPluginConfig().getCachingSettings().getCacheFolder().toPath().resolve(cachedSoundOptional.get().getCachedFile());
        if (!Files.exists(cachedSoundPath)) {
            plugin.getCachedSoundHolder().remove(cachedSoundOptional.get());
            return;
        }
        DataInputStream dataInputStream = new DataInputStream(Files.newInputStream(cachedSoundPath));
        String formatName = dataInputStream.readUTF();
        Optional<ISoundFormat> foundSoundFormat = plugin.getSoundFormatHolder().findFirstByPredicate(format -> format.getName().equals(formatName));
        if (!foundSoundFormat.isPresent())
            return;
        int framesCount = dataInputStream.readInt();
        List<byte[]> frameList = new ArrayList<>();
        for (int i = 0; i < framesCount; i++) {
            int frameSize = dataInputStream.readInt();
            byte[] newFrame = new byte[frameSize];
            dataInputStream.read(newFrame);
            frameList.add(newFrame);
        }
        e.getSoundEventModel().setSource(cachedSoundPath.toUri().toURL());
        e.setSound(ISound.of(frameList, foundSoundFormat.get()));
    }

    @EventHandler
    public void onUrlSoundFormatPreCreate(URLSoundFormatPreCreateEvent e) {
        Optional<CachedSound> cachedSoundOptional = plugin.getCachedSoundHolder().findCachedSound(e.getSoundEventModel().getSource());
        if (!cachedSoundOptional.isPresent())
            return;
        e.setSoundFormat(plugin.getSoundFormatHolder()
                .findFirstByPredicate(soundFormat -> soundFormat.getName().equals(cachedSoundOptional.get().getSoundFormat()))
                .orElse(null));
    }
}
