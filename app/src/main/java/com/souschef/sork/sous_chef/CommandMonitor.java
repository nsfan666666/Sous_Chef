package com.souschef.sork.sous_chef;

public class CommandMonitor {

    static CommandMonitor commandMonitor;

    private volatile String command = "";

    public static CommandMonitor getMonitor() {
        if(commandMonitor == null) {
            commandMonitor = new CommandMonitor();
        }
        return commandMonitor;
    }

    public synchronized String getCommand(){
        return command;
    }

    public synchronized void setCommand(String newCommand){
        command = newCommand;
    }

}
