# PlasmoVoiceAddon

![Java](https://img.shields.io/badge/Java-8%2B-brightgreen)
[![Jitpack](https://jitpack.io/v/U61vashka/PlasmoVoiceAddon.svg)](https://jitpack.io/#U61vashka/PlasmoVoiceAddon) 
![Lines of code](https://img.shields.io/tokei/lines/github/U61vashka/PlasmoVoiceAddon?label=Lines%20of%20code) 

[![Spigot Downloads](https://img.shields.io/spiget/downloads/99253?label=[Spigot]%20Downloads)](https://www.spigotmc.org/resources/plasmovoiceaddon.99253/)
[![Github Releases Downloads](https://img.shields.io/github/downloads/U61vashka/PlasmoVoiceAddon/total?label=[Github]%20Releases%20downloads)](https://github.com/U61vashka/PlasmoVoiceAddon/releases)

![Tested Spigot version](https://img.shields.io/badge/Tested%20on-Spigot%201.16.4-informational)
[![Spigot Rating](https://img.shields.io/spiget/rating/99253?label=[Spigot]%20Rating)](https://www.spigotmc.org/resources/plasmovoiceaddon.99253/) 


![Plugin logo](https://user-images.githubusercontent.com/85439143/150570814-1e3f3e00-7ec7-4972-a888-cfbb32b8ea6f.png)


PlasmoVoiceAddon is a music player that supports both MP3 and WAV formats. It allows you to play music from URLs or local files and provides an API for developers to incorporate music playback functionality.

> Important: This plugin depends on the PlasmoVoice plugin. You can only play music for players who have the PlasmoVoice mod downloaded.

**Installation**:
1. Download the JAR file from Spigot or GitHub releases.
2. Place the JAR file in your Spigot _plugins_ folder.
3. Launch server

**Plugin Commands**:
1. `/musicfile <file name>` - Play music from a file. The music file should be located in the PlasmoVoiceAddon folder
2. `/musicurl <URL>` - Play music from a URL

**Command Examples**:
1. `/musicfile piano2.wav` - This command will play the piano2.wav file from the PlasmoVoiceAddon folder
2. `/musicfile piano2.mp3` - This command will play the piano2.mp3 file from the PlasmoVoiceAddon folder
3. `/musicurl https://audio.jukehost.co.uk/L3HgZZAWd0GO38Ghw4xxreITjDk1krXL` - This command will play music from the provided URL

**Developer API**:
  
**How to play sound?**
1. Get `InputStream` from our source:
```java    
import java.io.File;
import java.nio.file.Files;
import java.net.URL;
import java.io.InputStream;

//  From file:
File file = //Your music file
InputStream inputStream = new BufferedInputStream(Files.newInputStream(file.toPath()));


// From URL:
URL url = //Your music url
InputStream inputStream = new BufferedInputStream(url.openStream());
```
2. Find right `ISoundFormat` for `InputStream`

```java
import com.bivashy.plasmovoice.PlasmoVoiceAddon;
import com.bivashy.plasmovoice.sound.ISoundFormat;

PlasmoVoiceAddon addon = PlasmoVoiceAddon.getPlugin(PlasmoVoiceAddon.class);
Optional<ISoundFormat> soundFormat = addon.getSoundFormatHolder().findFirstByPredicate(sound->sound.isSupported(bufferedInputStream));
```
3. Play `ISound` with `AudioSource`, `SoundController` (currently only PlayerAudioSource available)

```java
import com.bivashy.plasmovoice.PlasmoVoiceAddon;
import org.bukkit.entity.Player;
import com.bivashy.plasmovoice.sound.ISound;
import com.bivashy.plasmovoice.audio.player.PlasmoVoiceSoundPlayer;

if(!soundFormat.isPresent())
        // handle unknown sound format
PlasmoVoiceAddon addon=PlasmoVoiceAddon.getPlugin(PlasmoVoiceAddon.class);
Player player= //Your player
int soundDistance=100; //Maximum distance that will be music played

PlasmoVoiceSoundPlayer soundPlayer=addon.getPlasmoVoiceSoundPlayer(player.getUniqueId());
ISound sound = soundFormat.get().newSoundFactory().createSound(inputStream); // This line will automatically close InputStream, after you cannot use stream!
IPlasmoVoiceSoundController soundController = IPlasmoVoiceSoundController.of(soundFormat.get(),soundDistance));
soundPlayer.playSound(sound,soundController);
```
**How to add custom sound format (For example Mp3)**
1. Create implementation of `ISoundFormat`

```java            
import com.bivashy.plasmovoice.sound.ISoundFormat;
import com.bivashy.plasmovoice.settings.config.MusicPlayerSettings;

public class Mp3SoundFormat implements ISoundFormat {

    @Override
    public boolean isSupported(InputStream audioStream) {
        //Check if audio is supported
    }

    @Override
    public MusicPlayerSettings getSettings() {
        return new MusicPlayerSettings(sleepDelay, volume, encoderBitrate, soundDistance, shouldUseCaching);
    }

    @Override
    public ISoundFactory newSoundFactory() {
        return new Mp3SoundFactory(this);
    }

}
```
2. Create implementation of `ISoundFactory`, that will convert your format into Opus audio format

```java
import java.util.List;
import java.util.ArrayList;
import java.io.InputStream;

import com.bivashy.plasmovoice.sound.ISoundFactory;
import com.bivashy.plasmovoice.sound.ISoundFormat;
import com.bivashy.plasmovoice.sound.ISound;
import com.bivashy.plasmovoice.audio.codecs.OpusCodecHolder;

public class Mp3SoundFactory implements ISoundFactory {

    protected final OpusCodecHolder opusCodecHolder = OpusCodecHolder.newCodecHolder();
    protected final ISoundFormat soundFormat;

    public Mp3SoundFactory(ISoundFormat soundFormat) {
        this.soundFormar = soundFormat;
    }

    @Override
    public ISound createSound(InputStream audioStream) {
        byte[] yourSoundData = new byte[0];// Create sound data from audioStream, for example with library
        List<byte[]> splittedSoundData = // Split your byte[] opusCodecHolder.getFrameSize()
        List<byte[]> encodedSoundData = new ArrayList<>();
        for (byte[] chunkData : splittedSoundData) {
            encodedSoundData.add(opusCodecHolder.encode(chunkData));
        }
        audioStream.close(); // Close stream
        opusCodecHolder.closeEncoder(); // Close encoder, because it uses native library
        return ISound.of(encodedSoundData, soundFormat);
    }

}   
```    
3. Register your `ISoundFormat`

```java   

PlasmoVoiceAddon addon = PlasmoVoiceAddon.getPlugin(PlasmoVoiceAddon.class);
addon.getSoundFormatHolder().add(new Mp3SoundFormat());
```