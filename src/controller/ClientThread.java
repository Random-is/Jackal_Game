package controller;

import javafx.application.Platform;
import model.*;
import view.GameView;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Scanner;

public class ClientThread extends Thread {
    private Client main;
    private Player player = new Player();
    private String roomName;
    private int newRoomPlayersCount;
    private String presetIP = "";
    private Socket client;
    private DataInputStream in;
    private DataOutputStream out;
    private ArrayList<CardType> gameField = new ArrayList<>();
    Move move;

    public ClientThread(Client main, String nickname, String roomName, int newRoomPlayersCount) {
        this.main = main;
        player.setNickname(nickname);
        this.roomName = roomName;
        this.newRoomPlayersCount = newRoomPlayersCount;
    }

    public ClientThread(Client main, String nickname, String roomName, int newRoomPlayersCount, String presetIP) {
        this.main = main;
        player.setNickname(nickname);
        this.roomName = roomName;
        this.newRoomPlayersCount = newRoomPlayersCount;
        this.presetIP = presetIP;
    }

    @Override
    public void run() {
        try {
            searchServerIP();
            in = new DataInputStream(client.getInputStream());
            out = new DataOutputStream(client.getOutputStream());
//            connect();
            while (true) commandHandler(in.readUTF());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("disconnect from server");
        }
    }

    private void commandHandler(String message) throws IOException {
        System.out.println("message = [" + message + "]");
        Scanner command = new Scanner(message);
        switch (command.next()) {
            case "ROOM":
                switch (command.next()) {
                    case "ADD":
                        main.getPlayers().get(command.nextInt()).set(command.next(), command.next().equals("READY"), command.nextInt());
                        break;
                    case "REMOVE":
                        main.getPlayers().get(command.nextInt()).reset();
                        break;
                    case "SET":
                        switch (command.next()) {
                            case "STATUS":
                                main.getPlayers().get(command.nextInt()).setReady(command.next().equals("READY"));
                                //Обработка кнопки старт
                                break;
                            case "LEADER":
                                main.getPlayers().setLeader(main.getPlayers().get(command.nextInt()));
                                break;
                            case "ROLES":
                                for (Player player : main.getPlayers())
                                    player.setRole(command.nextInt());
                                break;
                            case "ROOM":
                                roomName = command.next();
                                main.getPlayers().createPlayers(command.nextInt());
                                send("ROOMS QUIT");
                                break;
                            case "PLAYER":
                                player = main.getPlayers().get(command.nextInt());
                                break;
                        }
                        break;
                }
                break;
            case "GAME":
                switch (command.next()) {
                    case "END_TURN":
                        main.getGame().endTurn();
                        break;
                    case "SHIP":
                        Player playerShip = main.getGame().getPlayers().get(command.nextInt());
                        Card cardShip = main.getGame().getMatrix()[command.nextInt()][command.nextInt()];
                        ArrayList<Pirate> pirates1 = playerShip.getShip().getCard().getPirates();
                        int size = pirates1.size();
                        for (int k = 0; k < size; k++) {
                            move.addPirateToCard(pirates1.get(0), cardShip);
                        }
                        cardShip.setShip(playerShip.getShip());
                        Platform.runLater(() -> main.getGameView().updateData(main.getGame()));
                        break;
                    case "TAKE":
                        Game game1 = main.getGame();
                        Player player = game1.getPlayers().get(command.nextInt());
                        Pirate pirate = player.getPirates().get(command.nextInt());

                        if (pirate.isHaveMoney()) {
                            pirate.setHaveMoney(false);
                            pirate.getCard().setMoney(pirate.getCard().getMoney() + 1);
                            Platform.runLater(() -> main.getGameView().updateData(game1));
                        } else {
                            if (pirate.getCard().getMoney() > 0) {
                                pirate.setHaveMoney(true);
                                pirate.getCard().setMoney(pirate.getCard().getMoney() - 1);
                                Platform.runLater(() -> main.getGameView().updateData(game1));
                            }
                        }
                        break;
                    case "MOVE":
                        move.addPirateToCard(main.getGame().getPlayers().get(command.nextInt()).getPirates().get(command.nextInt()), main.getGame().getMatrix()[command.nextInt()][command.nextInt()]);
                        Platform.runLater(() -> main.getGameView().updateData(main.getGame()));
                        break;
                    case "FIELD":
                        while (command.hasNext()) {
                            gameField.add(CardType.valueOf(command.next()));
                        }
                        break;
                    case "LOAD":
                        //Включить загрузочный экран
                        //Настроить интерфейс
                        main.getPlayers().roleToColor();
                        main.getGame().setPlayers(main.getPlayers());
                        PlayerList players = main.getGame().getPlayers();
                        Game game = main.getGame();
                        GameView gameView = main.getGameView();
                        for (int i = 0; i < players.size(); i++) {
                            players.get(i).createCrew(players.get(i).getColor());
                        }
                        game.setMainPlayer(this.player);
                        game.setCurrentTurnPlayer(players.get(0));
                        game.generateField(gameField);
                        game.placeCrews();
//
                        move = new Move(game, gameView);
                        move.createPiratesListenerForSingle();
                        move.createShipListenerForSingle();
                        move.createCardListener();
//
                        gameView.createImages(game);
                        gameView.setPlayers(game);
                        gameView.setMap(game.getMatrix());
                        gameView.setCrews(game);
                        gameView.updateData(game);
                        Platform.runLater(() -> gameView.getStage().show());
                        break;
                    case "START":
                        //Выключить загрузочный экран
                        //Включить игровое поле
                        break;
                }
                break;
            default:
                System.out.println("NOT_COMMAND");
                break;
        }
    }

    public void send(String message) throws IOException {
        out.writeUTF(message);
        out.flush();
    }

    private void connect() throws IOException {
        if (newRoomPlayersCount != 0)
            send("CONNECT " + player.getNickname() + " CREATE " + roomName + " " + newRoomPlayersCount);
        else
            send("CONNECT " + player.getNickname() + " " + roomName);
        switch (in.readUTF()) {
            case "ACCEPT":
                System.out.println("Подключилось к комнате");
                break;
            case "BUSY":
                System.out.println("Комната занята");
                close();
                break;
            case "NOT_FOUND":
                System.out.println("Комната не найдена");
                close();
                break;
        }
    }

    public void firstCommand() throws IOException {

    }

    public void secondCommand() throws IOException {

    }

    public void thirdCommand() throws IOException {

    }

    private void searchServerIP() throws UnknownHostException {
        boolean preset = !presetIP.equals("");
        ArrayList<Thread> searchThreads = new ArrayList<>();
        final ArrayList<Socket> sockets = new ArrayList<>();
        String localIP;
        if (preset)
            localIP = presetIP;
        else {
            localIP = InetAddress.getLocalHost().getHostAddress();
            if (localIP.equals("127.0.0.1"))
                preset = true;
            else
                localIP = getIpMask(localIP);
        }
        for (int i = (preset ? 255 : 1); i < 256; i++) {
            final String iIPv4 = preset ? localIP : localIP + i;
            Socket socket = new Socket();
            sockets.add(socket);
            searchThreads.add(createSearchThread(iIPv4, socket, preset, sockets));
        }
        for (Thread thread : searchThreads)
            try {
                if (thread.isAlive())
                    thread.join();
            } catch (InterruptedException ignored) {
            }
    }

    private String getIpMask(String ip) {
        int l = 0;
        int pCount = 0;
        while (pCount < 3)
            if (String.valueOf(ip.charAt(l + pCount)).equals("."))
                pCount++;
            else
                l++;
        return ip.substring(0, l + pCount);
    }

    private Thread createSearchThread(String iIPv4, Socket socket, Boolean preset, ArrayList<Socket> sockets) {
        Thread thread = new Thread(() -> {
            try {
                InetAddress ip = InetAddress.getByName(iIPv4);
                socket.connect(new InetSocketAddress(ip, main.getServerPort()));
                DataInputStream in = new DataInputStream(socket.getInputStream());
                String fromServer = in.readUTF();
                if (fromServer.equals("OMEGA_WELCOME")) {
                    while ((sockets.size() != 255) && !preset)
                        Thread.sleep(100);
                    sockets.remove(socket);
                    for (Socket threadSocket : sockets)
                        threadSocket.close();
                    client = socket;
                    System.out.println("OmegaServer found: " + iIPv4);
                }
            } catch (IOException | InterruptedException ignored) {
            }
        });
        thread.start();
        return thread;
    }

    public void close() throws IOException {
        client.close();
    }

    public Player getPlayer() {
        return player;
    }
}