package org.elsys.ip.springmvc;

public class Form {
    String command;
    String firstArg;
    String secondArg;
    String result;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getFirstArg() {
        return firstArg;
    }

    public void setFirstArg(String firstArg) {
        this.firstArg = firstArg;
    }

    public String getSecondArg() {
        return secondArg;
    }

    public void setSecondArg(String secondArg) {
        this.secondArg = secondArg;
    }
}
