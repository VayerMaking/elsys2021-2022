package org.elsys.ip.sockets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Server {
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public void start(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        clientSocket = serverSocket.accept();
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        CommandFactory commandFactory = new CommandFactory();
        CommandExecutor commandExecutor = new CommandExecutor(commandFactory);
        while(true) {
            String line = in.readLine();
            //out.println(line);
            List<String> lineSplit =
                    Arrays.stream(line.split(" ")).toList();
            String result = commandExecutor.execute(
                    lineSplit.get(0),
                    lineSplit.stream().skip(1).collect(Collectors.toList()));
            out.println(result);
        }
    }

    public void stop() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
        serverSocket.close();
    }
    public static void main(String[] args) throws IOException {
        Server server=new Server();
        server.start(6666);
    }
}