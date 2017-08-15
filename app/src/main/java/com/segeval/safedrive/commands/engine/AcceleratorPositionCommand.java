package com.segeval.safedrive.commands.engine;

import com.segeval.safedrive.commands.PercentageObdCommand;
import com.segeval.safedrive.enums.AvailableCommandNames;


public class AcceleratorPositionCommand extends PercentageObdCommand {

    public AcceleratorPositionCommand() {
        super("01 5A");
    }

    @Override
    public String getName() {
        return AvailableCommandNames.ACCELERATOR_POS.getValue();
    }
}
