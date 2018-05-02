package com.souschef.sork.sous_chef;

public class CommandMonitor {

    private volatile String command;

    public CommandMonitor(){

    }
    public synchronized String getCommand(){
        return command;
    }

    public synchronized void setCommand(String newCommand){
        command = newCommand;
    }

}
