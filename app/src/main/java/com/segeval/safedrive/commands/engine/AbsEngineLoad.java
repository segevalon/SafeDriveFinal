package com.segeval.safedrive.commands.engine;

import com.segeval.safedrive.commands.PercentageObdCommand;
import com.segeval.safedrive.enums.AvailableCommandNames;


public class AbsEngineLoad extends PercentageObdCommand {
    public AbsEngineLoad() {
        super("01 43");
    }

    @Override
    public String getName() {
        return AvailableCommandNames.ABS_ENGINE_LOAD.getValue();
    }
}
