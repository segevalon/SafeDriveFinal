package com.segeval.safedrive.commands.engine;

import com.segeval.safedrive.commands.PercentageObdCommand;
import com.segeval.safedrive.enums.AvailableCommandNames;


public class AcceleratorEPositionCommand extends PercentageObdCommand{
    public AcceleratorEPositionCommand() {
        super("01 4A");
    }

    @Override
    public String getName() {
        return AvailableCommandNames.ACCELERATOR_E_POS.getValue();
    }
}
