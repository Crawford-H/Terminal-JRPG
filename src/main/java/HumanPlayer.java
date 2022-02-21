import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


public class HumanPlayer extends Player {
    Scanner in = new Scanner(System.in);

    //constructor
    public HumanPlayer(Monster monster) {
        this.monster = monster;
    }
    
    //promts user for choice in the console
    public int chooseMove() {
        int choice = 0;
        List<Move> moves = new ArrayList<Move>(Arrays.asList(monster.getMove1(), monster.getMove2(), monster.getMove3(), monster.getMove4()));
        
        //loop until valid choice is made
        while (choice < 1 || choice > 4) {
            for (Move move : moves) 
                System.out.printf ("%d. %s\n", moves.indexOf(move) + 1, move.getName());
            System.out.print("Select a move: ");
            choice = in.nextInt();

            //check if number entered is valid
            if (choice < 1 || choice > 4) {
                System.out.println ("Unvalid number entered! Please pick a number between 1 and 4");
            }     
        } 
        return choice;
    }
}