package com.whippyteam.shared.command;

public interface CommandContent {

    String[] getArgs();

    String getParam(int index);

    String getParam(int index, String def);

    String getParams(int from, int to);

    default int getParamsLength() {
        return this.getArgs().length;
    }

    String getLabel();

    Command getCommand();
}
