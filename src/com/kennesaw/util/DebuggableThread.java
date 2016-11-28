package com.kennesaw.util;

/**
 * Created by Thomas on 11/28/2016.
 */
public class DebuggableThread extends Thread {
    private String moduleName;
    private boolean debugMode = false;

    public void setModuleName(String name) {
        moduleName = name;
    }

    public void setDebugMode(boolean isDebug) {
        debugMode = isDebug;
    }

    public void logMessage(String message) {
        if (debugMode) System.out.println("DEBUG | "+moduleName+" | "+message);
    }
}
