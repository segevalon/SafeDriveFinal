package com.segeval.safedrive.commands.engine;

import com.segeval.safedrive.commands.PercentageObdCommand;
import com.segeval.safedrive.enums.AvailableCommandNames;


public class AcceleratorFPositionCommand extends PercentageObdCommand{
    public AcceleratorFPositionCommand() {
        super("01 4B");
    }

    @Override
    public String getName() {
        return AvailableCommandNames.ACCELERATOR_F_POS.getValue();
    }
}
