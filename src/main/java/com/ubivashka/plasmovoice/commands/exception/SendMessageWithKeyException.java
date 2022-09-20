package com.ubivashka.plasmovoice.commands.exception;

import com.ubivashka.plasmovoice.PlasmoVoiceAddon;

import revxrsal.commands.command.CommandActor;

public class SendMessageWithKeyException extends RuntimeException {
    private static final PlasmoVoiceAddon PLUGIN = PlasmoVoiceAddon.getPlugin(PlasmoVoiceAddon.class);
    private final String key;

    public SendMessageWithKeyException(String key) {
        this.key = key;
    }

    public void sendTo(CommandActor commandActor) {
        commandActor.reply(PLUGIN.getPluginConfig().getMessages().getMessage(key));
    }
}
