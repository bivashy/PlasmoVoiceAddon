package com.bivashy.plasmovoice.commands.exception;

import revxrsal.commands.command.CommandParameter;
import revxrsal.commands.exception.InvalidValueException;

public class InvalidFIleException extends InvalidValueException {
    public InvalidFIleException(CommandParameter parameter, String input) {
        super(parameter, input);
    }
}
