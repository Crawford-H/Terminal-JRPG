import java.awt.image.BufferedImage;
import java.awt.Graphics;

public class Monster {
    private String name;
    private String type;
    private int hp;
    private int totalHP;
    private int attack;
    private int defense;
    private int speed;
    private Move move1;
    private Move move2;
    private Move move3;
    private Move move4;
    private BufferedImage monsterImage = null, damagedMonsterImage = null;

    //initialize variables
    public Monster(String name, String type, int hp, int speed, int attack, int defense, Move move1, Move move2, Move move3, Move move4) {
        this.name = name;
        this.type = type;
        this.hp = hp;
        totalHP = hp;
        this.speed = speed;
        this.attack = attack;
        this.defense = defense;
        this.move1 = move1;
        this.move2 = move2;
        this.move3 = move3;
        this.move4 = move4;
    }

    //paint image of monster
    public void paintMonster (Graphics g, int xPos, int yPos) {
        g.drawImage(monsterImage, xPos, yPos, null);
    }

    //paint image of monster getting damaged
    public void paintDamagedMonster(Graphics g, int xPos, int yPos) {
        g.drawImage(damagedMonsterImage, xPos, yPos, null);       
    }

    //getters and setters
    public String getName() { return name; }
    public String getType() { return type; }
    public int getTotalHP() { return totalHP; }
    public int getHP() { return hp; }
    public void setHP(int hp) { this.hp = hp; }
    public int getAttack() {  return attack; }
    public int getDefense() { return defense; }
    public int getSpeed() { return speed; }
    public Move getMove1() { return move1; }
    public Move getMove2() { return move2; }
    public Move getMove3() { return move3; }
    public Move getMove4() { return move4; }

    //getters and setters for images
    public void setMonsterImages(BufferedImage monsterImage, BufferedImage damagedMonsterImage) { 
        this.monsterImage = monsterImage;
        this.damagedMonsterImage = damagedMonsterImage;
    }
    public BufferedImage getMonsterImage() {return monsterImage;}
    public BufferedImage getDamagedMonsterImage() {return damagedMonsterImage;}
}