package com.segeval.safedrive.commands.engine;

import com.segeval.safedrive.commands.PercentageObdCommand;
import com.segeval.safedrive.enums.AvailableCommandNames;


public class AcceleratorDPositionCommand extends PercentageObdCommand{
    public AcceleratorDPositionCommand() {
        super("01 49");
    }

    @Override
    public String getName() {
        return AvailableCommandNames.ACCELERATOR_D_POS.getValue();
    }
}
