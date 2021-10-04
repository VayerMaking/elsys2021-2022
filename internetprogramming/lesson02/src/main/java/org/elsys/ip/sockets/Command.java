package org.elsys.ip.sockets;

import java.util.List;

public interface Command {
    String execute(List<String> args);
}