package com.segeval.safedrive.model.Managers;


import com.segeval.safedrive.commands.ObdCommand;

import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;


public interface Manager extends Readable{


    void addCommands(String string, ObdCommand obdCommand);

    HashMap<String, ObdCommand> commandsFactory = new HashMap<>();

    boolean isConnected();

    void connect(String deviceAddress) throws IOException, InterruptedException;

    void stop();

    UUID uuid = UUID.fromString("667d60d3-981e-41c8-befc-ba931ebaa385");


}
