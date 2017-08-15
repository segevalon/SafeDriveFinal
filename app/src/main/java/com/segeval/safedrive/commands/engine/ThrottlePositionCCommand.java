package com.segeval.safedrive.commands.engine;

import com.segeval.safedrive.commands.PercentageObdCommand;


public class ThrottlePositionCCommand extends PercentageObdCommand {
    public ThrottlePositionCCommand() {
        super("01 48");
    }

    @Override
    public String getName() {
        return null;
    }
}
