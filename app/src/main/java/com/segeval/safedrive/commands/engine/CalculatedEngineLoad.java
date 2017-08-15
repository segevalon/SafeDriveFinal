package com.segeval.safedrive.commands.engine;

import com.segeval.safedrive.commands.PercentageObdCommand;
import com.segeval.safedrive.enums.AvailableCommandNames;


public class CalculatedEngineLoad extends PercentageObdCommand {
    public CalculatedEngineLoad() {
        super("01 04");
    }

    @Override
    public String getName() {
        return AvailableCommandNames.CALCULATED_ENGINE_LOAD.getValue();
    }
}
