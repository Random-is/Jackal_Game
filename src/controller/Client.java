package controller;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.Game;
import model.PlayerList;
import view.GameView;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.InetAddress;
import java.util.Scanner;

public class Client extends Thread {
    private int serverPort = 4444;
    private ClientThread clientThread;
    private String nickname;
    private PlayerList players = new PlayerList();
    private String ip = "10.193.27.108";
    private Game game;
    private GameView gameView;

    public Client(Game game, GameView gameView) {
        this.game = game;
        this.gameView = gameView;
    }

    @Override
    public void run() {
        startCommands();
//        showConsole(new Stage());
//        Scanner console = new Scanner(System.in);
//        while (true) commandHandler(console.nextLine());
    }

    private void startCommands() {
        System.out.println("Client started");
        System.out.println("--------------");
    }

    public void commandHandler(String message) throws IOException {
        Scanner command = new Scanner(message);
        switch (command.next()) {
            case "ip":
                System.out.println("| your ip = " + InetAddress.getLocalHost().getHostAddress());
                break;
            case "nick":
                if (clientThread == null || !clientThread.isAlive())
                    if (command.hasNext()) {
                        nickname = command.next();
                        System.out.println("New nickname: " + nickname);
//                        commandHandler("connect");
                    }
//                    else {
//                        System.out.print("Enter nickname: ");
//                        nickname = new Scanner(System.in).next();
//                    }
                    else
                        System.out.println("disconnect before change nickname");
                break;
            case "connect":
                String roomName = command.next();
                if (command.hasNext()) {
                    clientThread = new ClientThread(this, nickname, roomName, command.nextInt(), ip);
                    clientThread.start();
                } else {
                    clientThread = new ClientThread(this, nickname, roomName, 0, ip);
                    clientThread.start();
                }
                break;
            case "close":
                clientThread.close();
                break;
            case "status":
                System.out.println(status());
                break;
            case "ready":
                clientThread.firstCommand();
                System.out.println("send ready");
                break;
            case "nready":
                clientThread.secondCommand();
                System.out.println("send not ready");
                break;
            case "start":
//                if (players.readyCount() == players.size()) {
                if (players.getLeader() == clientThread.getPlayer())
                    clientThread.thirdCommand();
                else
                    System.out.println("you not leader");
//                } else
//                    System.out.println("all not ready");
                break;
            case "con":
                clientThread = new ClientThread(this, "Ya", "test", 0, "127.0.0.1");
                clientThread.start();
                break;
            case "send":
                String m = command.nextLine().substring(1);
                System.out.println("send " + m);
                clientThread.send(m);
                break;
            case "exit":
                System.out.println("--------------");
                System.out.print("Client stopped");
                System.exit(0);
                break;
            default:
                System.out.println("| command not found");
                break;
        }
    }

    public void showConsole(Stage stage) {
        TextArea text = new TextArea();
        TextField textField = new TextField();
        BorderPane root = new BorderPane(text, null, null, textField, null);
        text.setEditable(false);
        textField.requestFocus();
        textField.setOnAction(event -> {
            text.appendText(textField.getText() + "\n");
            if (textField.getText().equals("clear"))
                text.clear();
            else
                try {
                    commandHandler(textField.getText());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            textField.clear();
        });
        OutputStream out = new OutputStream() {
            @Override
            public void write(int b) {
                Platform.runLater(() -> text.appendText(String.valueOf((char) b)));
            }
        };
        System.setOut(new PrintStream(out, true));
        Scene scene = new Scene(root, 500, 300);
        scene.addEventFilter(KeyEvent.KEY_PRESSED, event -> textField.requestFocus());
//        scene.getStylesheets().add("Game/Server/Default.css");
        stage.setScene(scene);
        stage.setTitle("Active - Omega Server");
        stage.setOnCloseRequest(event -> System.exit(0));
        stage.show();
    }

    public String status() {
        return "Player status: \n" + players.toString() +
                "\nLEADER " + players.getLeader().getNickname() +
                "\nSTART " + (players.readyCount() == players.size() ? "READY" : "NOT_READY");
    }

    public PlayerList getPlayers() {
        return players;
    }

    public int getServerPort() {
        return serverPort;
    }

    public ClientThread getClientThread() {
        return clientThread;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public GameView getGameView() {
        return gameView;
    }

    public void setGameView(GameView gameView) {
        this.gameView = gameView;
    }
}