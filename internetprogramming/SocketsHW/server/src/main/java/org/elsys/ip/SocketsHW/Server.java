package org.elsys.ip.SocketsHW;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Server {
    private ServerSocket serverSocket;
    private final Map<String, ClientHandler> clients = new HashMap<>();

    public void start(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        while (true) {
            ClientHandler client = new ClientHandler(serverSocket.accept(), this);
            client.start();
        }
    }

    private static class ClientHandler extends Thread {
        private final Socket clientSocket;
        private PrintWriter out;
        private BufferedReader in;
        private final Server server;
        private String name;

        public ClientHandler(Socket socket, Server server) {
            this.clientSocket = socket;
            this.server = server;
        }

        public void run() {
            try {
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                ArrayList<String> validTimezones = new ArrayList<>(Arrays.asList("+00:00", "+01:00", "+02:00", "+03:00", "+04:00", "-10:00", "-09:00", "-04:00", "-03:00", "-05:00", "-06:00", "-07:00", "-08:00", "-02:00", "+00:00", "-01:00", "+11:00", "+12:00", "+05:00", "+07:00", "+08:00", "+09:00", "+10:00", "+14:00"));
//                name = in.readLine();
//                server.addToMap(name, this);

                while (true) {
                    String line = in.readLine().toLowerCase(Locale.ROOT);
                    System.out.println(line);
                    if (line == null) {
                        break;
                    }

                    if (line.startsWith("time")){
                        if(line.equals("time")){
                            ZoneId timeZone = ZoneId.systemDefault();

                            LocalDateTime localDateTime = LocalDateTime.now();
                            ZonedDateTime zonedDateTime =  localDateTime.atZone(timeZone);
                            //LocalTime rawTime = LocalTime.now();
                            out.println(zonedDateTime.format(DateTimeFormatter.ofPattern("HH:mm")));
                        }else{
                            try {
                                LocalTime rawTime = LocalTime.now();
                                String timezone = line.substring(5).split(" ")[0];
                                //out.println(timezone.length());
                                if(timezone.length() < 6){
                                    out.println("invalid input");
                                }else {
                                    Integer hours = Integer.parseInt(timezone.substring(1, 3));
                                    String minutes = timezone.substring(4, 6);
                                    //out.println(minutes);
                                    String flag = timezone.substring(0, 1);
                                    LocalTime ajustedTime;

//                                if (!minutes.equals("00")) {
//                                    out.println("invalid time zone");
//                                }
                                    if (!validTimezones.contains(timezone)) {
                                        out.println("invalid time zone");
                                    } else {

                                        if (flag.equals("+")) {
                                            ajustedTime = rawTime.plusHours(hours);
                                        } else if (flag.equals("-")) {
                                            ajustedTime = rawTime.minusHours(hours);
                                        } else {
                                            ajustedTime = rawTime;
                                        }
                                        String time = ajustedTime.format(DateTimeFormatter.ofPattern("HH:mm"));

                                        out.println(time);
                                    }
                                }
                            } catch (NumberFormatException e) {
                                out.println("invalid input");
                            }
                        }
                    }else if (line.equals("exit") || line.equals("quit")){
                        clientSocket.close();
                        dispose();
                    }else{
                        out.println("invalid input");
                    }
                    //server.printlnAll(line);
                }
            } catch (Throwable t) {
                System.out.println(t.getMessage());
            } finally {
                dispose();
            }
        }

        private void dispose() {
            try {
                server.removeClient(name);
                if (clientSocket != null) clientSocket.close();
                if (in != null) in.close();
                if (out != null) out.close();
            } catch (Throwable t) {
                System.out.println(t.getMessage());
            }
        }
    }

    private void addToMap(String name, ClientHandler client) {
        clients.put(name, client);
    }

    private void removeClient(String name) {
        clients.remove(name);
    }

//    private void printlnAll(String line) {
//            clients.values().forEach(c -> c.out.println(" > " + line));
//    }

    public static void main(String[] args) throws IOException {
        Server server = new Server();

        try{
            Integer.parseInt(args[0]);
            if(args.length == 0 || Integer.parseInt(args[0]) < 0 || Integer.parseInt(args[0]) >= 65536){
                System.err.println("invalid arguments");
                System.exit(1);
            }
        }catch(NumberFormatException e){
            System.err.println("invalid arguments");
            System.exit(1);
        }
        try {
            server.start(Integer.parseInt(args[0]));
        }catch (BindException e){
            System.err.println("port is already in use");
            System.exit(2);
        }
    }
}