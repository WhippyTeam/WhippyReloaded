package main.java.com.whippyteam.shared.bukkit.command;

import com.whippyteam.shared.command.Command;
import com.whippyteam.shared.command.CommandContent;
import java.util.List;

public class CommandContentImpl implements com.whippyteam.shared.command.CommandContent {

    private final List<String> params;
    private final String label;

    private final Command command;

    public CommandContentImpl(List<String> params, String label, Command command) {
        this.params = params;
        this.label = label;
        this.command = command;
    }

    @Override
    public String[] getArgs() {
        return params.toArray(new String[0]);
    }

    @Override
    public String getParam(int index) {
        return this.getParam(index, null);
    }

    @Override
    public String getParam(int index, String def) {
        if (this.params.size() <= index) {
            return def;
        }

        return this.params.get(index);
    }

    @Override
    public String getParams(int from, int to) {
        StringBuilder builder = new StringBuilder();
        for (int i = from; i < to; i++) {
            if (i != 0) {
                builder.append(" ");
            }

            builder.append(getParam(i));
        }

        return builder.toString();
    }

    public String getParams(int from) {
        return this.getParams(from, this.params.size());
    }

    public double getParamDouble(int index) {
        return this.getParamDouble(index, 0.0);
    }

    public double getParamDouble(int index, double def) {
        return Double.parseDouble(this.getParam(index, String.valueOf(def)));
    }

    public int getParamInt(int index) {
        return this.getParamInt(index, 0);
    }

    public int getParamInt(int index, int def) {
        return Integer.parseInt(this.getParam(index, String.valueOf(def)));
    }

    public int getParamsLength() {
        return this.params.size();
    }

    @Override
    public String getLabel() {
        return this.label;
    }

    @Override
    public Command getCommand() {
        return this.command;
    }

    protected Boolean parseBoolean(String bool, Boolean def) {
        switch (bool.toLowerCase()) {
            case "true":
            case "1":
            case "yes":
            case "on":
            case "enable":
            case "enabled":
                return Boolean.TRUE;
            case "false":
            case "0":
            case "no":
            case "off":
            case "disable":
            case "disabled:":
                return Boolean.FALSE;
            default:
                return def;
        }
    }
}
