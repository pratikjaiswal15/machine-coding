package battleship;

/*

BattleShipGame
BattleField
Ship
strategy interface
Players

*/

/*
Database schema
BattleShipGame - Id, player1Id, player2Id, status, size
Player - id, name, email
BattleField - Id, gameId, playerId, width, height
Ship - Id, playerId, BattleField, startX, startY , status, size
Attack - Id, gameId, attackerPlayerId, receiverPlayerId, status, x, y, timestamp
*/

/*
post - initGame()
post - addship()
post - startGame()
post - hitShip()

get - BattleField()
get - battleShipGame(Id)
get - battleShipGameStatus(Id)

get post - player()

get - ship()
get - attack(Id)
get - attacks()
*/

import java.util.*;

public class BattleshipGame {

    private int battleShipSize;
    BattleField playerABattleField;
    BattleField PlayerBBattleField;
    AttackStrategy attackStrategy;
    Set<String> firedCoordinates;

    public void initGame(int battleShipSize) {
        this.battleShipSize = battleShipSize;
        playerABattleField = new BattleField(battleShipSize/ 2, battleShipSize,"Player A");
        PlayerBBattleField = new BattleField(battleShipSize/ 2, battleShipSize,"Player B");
        firedCoordinates = new HashSet<>();
    }

    private void setAttackStrategy (AttackStrategy attackStrategy) {
        this.attackStrategy = attackStrategy;
    }

    private boolean addShip(int size, int xA, int yA, int xB, int yB) {
        if(playerABattleField.addShip(new Ship(size, xA, yA)) && playerABattleField.adShip(new Ship(size, xB, yB))) {
            return true;
        }

        return false;
    }

    private void startGame()  {
        boolean playerATurn = false;
        while (!playerABattleField.allShipsDestryoyed() && !playerABattleField.allShipsDEstryoyted()) {
            if(playerATurn) {
                hitShip(playerABattleField, PlayerBBattleField);
            } else  {
                hitShip(PlayerBBattleField,playerABattleField);
            }

            playerATurn = !playerATurn;
        }

        if(playerABattleField.allShipsDestryoyed()) {
            System.out.println("Player B wins");
        } else  {
            System.out.println("Player A wins");
        }
    }

    private void hitShip(BattleField attacker, BattleField receiver) {
        String coordinate = attackStrategy.getNextCoordinates(firedCoordinates, battleShipSize);


        int x = Integer.parseInt(coordinate.split(",")[0]);
        int y = Integer.parseInt(coordinate.split(",")[1]);

        if(x >= receiver.getWidth() && y >= receiver.getHeight()) {
            throw new IllegalArgumentException("Invalid cordinates");
        }

        firedCoordinates.add(coordinate);


        receiver.receiveAttack(x, y);
    }
}



class BattleField {
    private int width;
    private int height;
    private String player;


    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    Set<String> shipCordinates;
    Map<Integer, Ship> ships;

    public BattleField(int width, int height, String player) {
        this.width = width;
        this.height = height;
        this.player = player;
        shipCordinates = new HashSet<>();
        ships = new HashMap<>();
    }

    public boolean addShip(Ship ship) {
        if(!isValidPlacement(ship)) return false;

        ships.put(ship.getId(), ship);
        markcordinates(ship);
        return true;
    }

    private boolean isValidPlacement(Ship ship) {

        int x = ship.getX();
        int y = ship.getY();

        String coordinate = x + "," + y;

        if(shipCordinates.contains(coordinate) && x >= width && y >= height) return false;
        return true;
    }

    private void markcordinates(Ship ship) {
        int x = ship.getX();
        int y = ship.getY();

        String coordinate = x + "," + y;
        shipCordinates.add(coordinate);
    }
}

class Ship {

    private int Id;
    private int size;
    private int x;
    private int y;


    public Ship(int id, int size, int x, int y) {
        this.size = size;
        this.x = x;
        this.y = y;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}

interface AttackStrategy {

    // 2,3 2,3
    String getNextCoordinates(Set<String> firedCoordinates, int size);
}

class RandomAttackStrategy implements AttackStrategy {


    private static Random random;
    @Override
    public String getNextCoordinates(Set<String> firedCoordinates,  int size) {

        int x = random.nextInt(size);
        int y = random.nextInt(size);

        return null;
    }
}

// class