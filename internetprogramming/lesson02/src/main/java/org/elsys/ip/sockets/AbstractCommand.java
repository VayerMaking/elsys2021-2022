package org.elsys.ip.sockets;

public abstract class AbstractCommand implements Command {
    protected double parse(String arg) {
        try {
            return Double.parseDouble(arg);
        } catch (Throwable t) {
            return Memory.getInstance().
                    getValue(arg);
        }
    }
}