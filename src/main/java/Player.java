import java.awt.Font;
import java.awt.Graphics;
import java.util.Random;
import java.util.Scanner;

public abstract class Player {
    protected Monster monster;
    private StatsBar statsBar = null;
    Scanner in = new Scanner(System.in);

    //track stats from last attack for paint
    private boolean hit = false;
    private int totaldmg = 0;
    private Move move = null;

    // calculate and deal damage to enemy monster
    public void attack(Player enemy, int selectedMove) {
        Random rand = new Random();
        int monsterATK = monster.getAttack();
        int enemyDEF = enemy.getMonster().getDefense();
        int enemyHP = enemy.getMonster().getHP();
        float randAccuracy = rand.nextFloat();

        // get the selected move to calculatae accuracy and power of the move
        switch (selectedMove) {
            case 1:
                move = monster.getMove1();
                break;

            case 2:
                move = monster.getMove2();
                break;

            case 3:
                move = monster.getMove3();
                break;

            case 4:
                move = monster.getMove4();
                break;

            default:
                System.out.println("Error! Number isn't between one and 4");
                break;
        }
        float movAccuracy = (move != null) ? move.getAccuracy() : 0;
        System.out.println(monster.getName() + " uses " + move.getName() + ".");

        // deal damage if the attack hits
        if (randAccuracy < movAccuracy) {
            hit = true;
            int power = move.getPower();
            totaldmg = power + monsterATK - enemyDEF;
            System.out.println(monster.getName() + " hit for " + totaldmg + " damage.");
            enemy.getMonster().setHP(enemyHP - totaldmg);
        } else {
            hit = false;
            System.out.println(monster.getName() + " missed.");
        }
    }

    // display attack and value of damage
    public void paintAttackInfo(Graphics g) {
        g.setFont(new Font("TimesRoman", Font.BOLD, 25));
        g.drawString(monster.getName() + " uses " + move.getName() + ".", 35, 300);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        if (hit) {
            g.drawString(monster.getName() + " hit for " + totaldmg + " damage.", 35, 340);
        } else {
            g.drawString(monster.getName() + " missed.", 35, 340);
        }

        //wait until user input to continue
        System.out.print("Press enter to continue...");
        in.nextLine();
    }   
    
    //return true when enemy monster is slower
    public boolean isFasterThan(Player enemy) {
        if (monster.getSpeed() > enemy.getMonster().getSpeed())
            return true;
        return false;       
    }
    
    //return true when monster has less than or equal to 0 health
    public boolean hasLost() {
        if (monster.getHP() <= 0)
            return true; 
        return false;
    }

    //getters and setters
    public Monster getMonster() {
        return monster;
    }

    public void setStatsBar(int HBarXPos, int HBarYPos, int HBarLength, int HBarThickness, Font hpFont, int hpXPos, int hpYPos, Font nameFont, int nameXPos, int nameYPos) {
        statsBar = new StatsBar(this, HBarXPos, HBarYPos, HBarLength, HBarThickness, hpFont, hpXPos, hpYPos, nameFont, nameXPos, nameYPos);
    }

    public StatsBar getStatsBar() {
        return statsBar;
    }
}