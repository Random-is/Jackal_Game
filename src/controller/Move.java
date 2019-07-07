package controller;

import model.*;
import view.GameView;

import java.io.IOException;
import java.util.ArrayList;

public class Move {
    private Game game;
    private GameView gameView;
    private Pirate selectedPirate;
    private Ship selectedShip;
    private Rules rules;
    private boolean startMoving = false;

    public Move(Game game, GameView gameView) {
        this.game = game;
        this.gameView = gameView;
        rules = new Rules(game);
    }

    public void createShipListenerForPlayer(Player player) {
        Ship ship = player.getShip();
        ship.setOnMouseReleased(event -> {
            System.out.println("You select ship " + ship.getColor());
            if (!startMoving && game.isThisPlayerTurn(player)) {
                selectedShip = ship;
                selectedPirate = null;
            } else
                System.out.println("Now turn of player " + game.getCurrentTurnPlayer().getNickname());
        });
    }

    public void createShipListenerForSingle() {
        createShipListenerForPlayer(game.getMainPlayer());
    }

    public void createShipListenerForHotSeat() {
        game.getPlayers().forEach(this::createShipListenerForPlayer);
    }

    public void createPirateListenerForPlayer(Player player) {
        ArrayList<Pirate> pirates = player.getPirates();
        for (Pirate pirate : pirates) {
            pirate.setOnMouseReleased(event -> {
                if (!startMoving && game.isThisPlayerTurn(player)) {
                    selectedPirate = pirate;
                    selectedShip = null;
                } else
                    System.out.println("Now turn of player " + game.getCurrentTurnPlayer().getNickname());
            });
        }
    }

    public void createPiratesListenerForSingle() {
        createPirateListenerForPlayer(game.getMainPlayer());
    }

    public void createPiratesListenerForHotSeat() {
        game.getPlayers().forEach(this::createPirateListenerForPlayer);
    }

    public void createCardListener() {
        Card[][] matrix = game.getMatrix();
        for (int i = 0; i < 13; i++) {
            for (int j = 0; j < 13; j++) {
                Card card = matrix[i][j];
                card.setOnMouseReleased(event -> {
                    if (selectedPirate != null && rules.isValidPirateTurn(selectedPirate, card)) {
                        startMoving = true;
                        addPirateToCard(selectedPirate, card);
                        if (game.getGameType() == GameType.MULTI) {
                            try {
                                game.getClient().commandHandler("send GAME MOVE "
                                        + game.getPlayers().indexOf(game.getCurrentTurnPlayer()) + " "
                                        + game.getCurrentTurnPlayer().getPirates().indexOf(selectedPirate) + " "
                                        + card.getmX() + " " + card.getmY());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        if (rules.isCompleteTurn(card)) {
                            selectedPirate = null;
                            startMoving = false;
                            if (game.getGameType() == GameType.MULTI) {
                                try {
                                    game.getClient().commandHandler("send GAME END_TURN");
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                            game.endTurn();
                        }
                        gameView.updateData(game);
                    } else if (selectedShip != null && rules.isValidShipTurn(selectedShip, card)) {
                        ArrayList<Pirate> pirates = selectedShip.getCard().getPirates();
                        int size = pirates.size();
                        for (int k = 0; k < size; k++) {
                            addPirateToCard(pirates.get(0), card);
                        }
                        card.setShip(selectedShip);
                        if (game.getGameType() == GameType.MULTI) {
                            try {
                                game.getClient().commandHandler("send GAME SHIP "
                                        + game.getPlayers().indexOf(game.getCurrentTurnPlayer()) + " "
                                        + card.getmX() + " " + card.getmY());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        selectedShip = null;
                        if (game.getGameType() == GameType.MULTI) {
                            try {
                                game.getClient().commandHandler("send GAME END_TURN");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        game.endTurn();
                        gameView.updateData(game);
                    } else {
                        System.out.println("YOU DON'T SELECT ANYONE OR FAIL MOVE");
                    }
                });
            }
        }
    }

    public void addPirateToCard(Pirate pirate, Card card) {
        if (!card.getPirates().isEmpty()) {
            if (!card.getPirates().get(0).getColor().equals(pirate.getColor())) {
                Card cardWithShip = game.getPlayers().get(game.getPlayers().indexOfByColor(card.getPirates().get(0).getColor())).getShip().getCard();
                int size = card.getPirates().size();
                for (int i = 0; i < size; i++) {
                    if (card.getPirates().get(0).isHaveMoney()) {
                        card.getPirates().get(0).setHaveMoney(false);
                        card.getPirates().get(0).getCard().setMoney(card.getPirates().get(0).getCard().getMoney() + 1);
                    }
                    addPirateToCard(card.getPirates().get(0), cardWithShip);
                }
            }
        }
        card.addPirate(pirate);
        if (card.hasShip()) {
            if (!card.getShip().getColor().equals(pirate.getColor()))
                pirate.kill();
            else if (pirate.isHaveMoney()) {
                pirate.setHaveMoney(false);
                game.getCurrentTurnPlayer().setMoney(game.getCurrentTurnPlayer().getMoney() + 1);
                game.setMoney(game.getMoney() - 1);
            }
        }
        cardAction(pirate, card);
    }

    private void cardAction(Pirate pirate, Card card) {
        switch (card.getCardType()) {
            case JUNGLE:
                pirate.setSleep(pirate.getSleep() > 0 ? pirate.getSleep() - 1 : 1);
                break;
            case DESERT:
                pirate.setSleep(pirate.getSleep() > 0 ? pirate.getSleep() - 1 : 2);
                break;
            case BOG:
                pirate.setSleep(pirate.getSleep() > 0 ? pirate.getSleep() - 1 : 3);
                break;
            case MOUNTAINS:
                pirate.setSleep(pirate.getSleep() > 0 ? pirate.getSleep() - 1 : 4);
                break;
            case GUN:
                addPirateToCard(pirate, game.getMatrix()[card.getmX()][0]);
                break;
            case ROM:
                game.getCurrentTurnPlayer().setRom(game.getCurrentTurnPlayer().getRom() + 1);
                game.setRom(game.getRom() - 1);
                break;
            case BALLOON:
                addPirateToCard(pirate, game.getCurrentTurnPlayer().getShip().getCard());
                break;
            case KILLER:
                pirate.kill();
                break;
            case SEA:
                if (pirate.isHaveMoney())
                    pirate.setHaveMoney(false);
                break;
            case CHEST:
                if (!card.isUsed()) {
                    card.setUsed(true);
                    card.setMoney(1);
                }
                break;
            case HOLE:
                System.out.println("hole size " + card.getPirates().size());
                if (card.getPirates().size() == 1) {
                    pirate.setInHole(true);
                } else {
                    for (int i = 0; i < card.getPirates().size(); i++) {
                        card.getPirates().get(i).setInHole(false);
                    }
                }
                break;
            case CROCODILE:
                addPirateToCard(pirate, pirate.getPreviousCard());
                break;
            case ICE:
                addPirateToCard(pirate, game.getMatrix()[card.getmX() + card.getmX() - pirate.getPreviousCard().getmX()][card.getmY() + card.getmY() - pirate.getPreviousCard().getmY()]);
                break;
        }
    }
}
