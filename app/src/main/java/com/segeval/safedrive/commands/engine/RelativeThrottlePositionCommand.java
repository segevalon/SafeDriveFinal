package com.segeval.safedrive.commands.engine;

import com.segeval.safedrive.commands.PercentageObdCommand;
import com.segeval.safedrive.enums.AvailableCommandNames;


public class RelativeThrottlePositionCommand extends PercentageObdCommand {

    public RelativeThrottlePositionCommand() {
        super("01 45");
    }

    @Override
    public String getName() {
        return AvailableCommandNames.RELATIVE_THROTTLE_POS.getValue();
    }
}
