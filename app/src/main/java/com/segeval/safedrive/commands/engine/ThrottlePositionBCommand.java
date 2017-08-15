package com.segeval.safedrive.commands.engine;

import com.segeval.safedrive.commands.PercentageObdCommand;
import com.segeval.safedrive.enums.AvailableCommandNames;


public class ThrottlePositionBCommand extends PercentageObdCommand {

    public ThrottlePositionBCommand() {
        super("01 47");
    }

    @Override
    public String getName() {
        return AvailableCommandNames.THROTTLE_B_POS.getValue();
    }
}
