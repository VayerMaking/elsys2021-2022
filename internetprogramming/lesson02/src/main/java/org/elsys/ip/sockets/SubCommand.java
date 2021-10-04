package org.elsys.ip.sockets;

import java.util.List;

public class SubCommand extends AbstractCommand implements Command {
    @Override
    public String execute(List<String> args) {
        return String.valueOf(args.stream().
                mapToDouble(x -> parse(x)).
                reduce((x, y) -> x - y).orElseGet(() -> 0d));
    }
}