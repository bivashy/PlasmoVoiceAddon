package com.ubivashka.plasmovoice.commands.exception;

import com.ubivashka.plasmovoice.config.PluginConfig;
import revxrsal.commands.command.CommandActor;
import revxrsal.commands.exception.*;

import java.text.MessageFormat;

public class CommandExceptionHandler extends DefaultExceptionHandler {
    private final PluginConfig config;

    public CommandExceptionHandler(PluginConfig config) {
        this.config = config;
    }

    @Override
    public void missingArgument(CommandActor actor, MissingArgumentException exception) {
        actor.reply(MessageFormat.format(config.getMessages().getMessage("missing-argument"), exception.getParameter().getName()));
    }

    @Override
    public void invalidEnumValue(CommandActor actor, EnumNotFoundException exception) {
    }

    @Override
    public void invalidNumber(CommandActor actor, InvalidNumberException exception) {
        actor.reply(MessageFormat.format(config.getMessages().getMessage("invalid-number"), exception.getInput()));
    }

    @Override
    public void invalidUUID(CommandActor actor, InvalidUUIDException exception) {
    }

    @Override
    public void invalidURL(CommandActor actor, InvalidURLException exception) {
        actor.reply(MessageFormat.format(config.getMessages().getMessage("invalid-url"), exception.getInput()));
    }

    public void invalidFile(CommandActor actor, InvalidFIleException exception) {
        actor.reply(MessageFormat.format(config.getMessages().getMessage("invalid-file"), exception.getInput()));
    }

    @Override
    public void invalidBoolean(CommandActor actor, InvalidBooleanException exception) {
    }

    @Override
    public void noPermission(CommandActor actor, NoPermissionException exception) {
        actor.reply(config.getMessages().getMessage("not-enough-permission"));
    }

    @Override
    public void argumentParse(CommandActor actor, ArgumentParseException exception) {
    }

    @Override
    public void commandInvocation(CommandActor actor, CommandInvocationException exception) {
        actor.reply(config.getMessages().getMessage("error-occurred"));
        exception.getCause().printStackTrace();
    }

    @Override
    public void tooManyArguments(CommandActor actor, TooManyArgumentsException exception) {
    }

    @Override
    public void invalidCommand(CommandActor actor, InvalidCommandException exception) {
    }

    @Override
    public void invalidSubcommand(CommandActor actor, InvalidSubcommandException exception) {
    }

    @Override
    public void noSubcommandSpecified(CommandActor actor, NoSubcommandSpecifiedException exception) {
    }

    @Override
    public void cooldown(CommandActor actor, CooldownException exception) {
    }

    @Override
    public void invalidHelpPage(CommandActor actor, InvalidHelpPageException exception) {
    }

    @Override
    public void sendableException(CommandActor actor, SendableException exception) {
    }

    @Override
    public void numberNotInRange(CommandActor actor, NumberNotInRangeException exception) {
    }

    @Override
    public void onUnhandledException(CommandActor actor, Throwable throwable) {
        actor.reply(config.getMessages().getMessage("error-occurred"));
        throwable.printStackTrace();
    }
}
