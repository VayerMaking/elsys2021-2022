package org.elsys.ip.sockets;

import java.util.List;

public class ExitCommand implements Command{
    @Override
    public String execute(List<String> args) {
        System.exit(0);
        throw new IllegalStateException("Unreachable");
    }
}