import java.util.Random;

public class CPUPlayer extends Player {
    Random rand = new Random();

    //constructor
    public CPUPlayer(Monster monster) {
        this.monster = monster;
    }

    //return random number from 1-4
    public int chooseMove() {
        return rand.nextInt(4)+1;
    }
}