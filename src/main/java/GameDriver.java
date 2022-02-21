import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class GameDriver extends Canvas implements Runnable {
	/**
	 * GUI DOESN'T work with if run in WSL
	 * 
	 * Info is still inputted in the terminal as 
	 * I don't want to spend the time implementing 
	 * mouse or keyboard controls
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BufferedImage background;
	private Thread thread;

	public boolean running = false;
	public enum State { selectMove, playerAttack, enemyAttack, enemyHasLost, playerHasLost }
	public static State state;
	public static final int WIDTH = 556, HEIGHT = 371;
	public static final String NAME = "Battle";
	HumanPlayer player;
	CPUPlayer enemy;

	// start the thread and set running to true, used when you want to start the battle
	public synchronized void start() {
		if (running)
			return;
		running = true;
		thread = new Thread(this);
		thread.start();
	}

	// join the thread and set running to false, used when the battle is over
	public synchronized void stop() {
		try {
			thread.join();
			running = false;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// initialize variables
	public GameDriver() {
		Move move1 = new Move("Vine Whip", "Grass", 45, 1.0f);
		Move move2 = new Move("Tackle", "Normal", 50, 1.0f);
		Move move3 = new Move("Take Down", "Normal", 90, 0.85f);
		Move move4 = new Move("Razor Leaf", "Grass", 55, 0.95f);
		Monster monster = new Monster("Bulbasaur", "Grass", 240, 45, 49, 49, move1, move2, move3, move4);
		player = new HumanPlayer(monster);
		Font hpFont = new Font("TimesRoman", Font.BOLD, 12);
		Font nameFont = new Font("TimesRoman", Font.BOLD, 20);
		player.setStatsBar(404, 210, 111, 5, hpFont, 455, 235, nameFont, 330, 195);
		
		move1 = new Move("Scratch", "Normal", 40, 1.0f);
		move2 = new Move("Ember", "Fire", 40, 1.0f);
		move3 = new Move("Peck", "Flying", 35, 1.0f);
		move4 = new Move("Fire Spin", "Fire", 35, 0.85f);
		monster = new Monster("Torchic", "Fire", 240, 45, 60, 40, move1, move2, move3, move4);
		enemy = new CPUPlayer(monster);
		nameFont = new Font("TimesRoman", Font.BOLD, 17);
		enemy.setStatsBar(122, 78, 110, 5, hpFont, 170, 70, nameFont, 50, 60);

		//init images
		try {
			//read in images
			background = ImageIO.read(new File("src/main/images/backgound.jpg"));
			BufferedImage playerMonster = ImageIO.read(new File("src/main/images/bulbasaur.png"));
			BufferedImage playerDamagedMonster = ImageIO.read(new File("src/main/images/damaged_bulbasaur.png"));
			BufferedImage enemyMonster = ImageIO.read(new File("src/main/images/torchic.png"));
			BufferedImage enemyDamagedMonster = ImageIO.read(new File("src/main/images/damaged_torchic.png"));
			//set monster images
			player.getMonster().setMonsterImages(playerMonster, playerDamagedMonster);
			enemy.getMonster().setMonsterImages(enemyMonster, enemyDamagedMonster);
		} catch (Exception e) {
			System.out.println("Could not find image");
		}

		//create a window(frame)
		new Window(WIDTH, HEIGHT, NAME, this);
	}

	// game logic
	public void run() {
		while (!player.hasLost() && !enemy.hasLost() && running) {
			// print both monsters' HP
			System.out.println("");
			System.out.printf("%s has %d HP\n", player.getMonster().getName(), player.getMonster().getHP());
			System.out.printf("%s has %d HP\n", enemy.getMonster().getName(), enemy.getMonster().getHP());

			// decide the next move
			state = State.selectMove;
			paint(getGraphics());
			int playerMove = player.chooseMove();
			int enemyMove = enemy.chooseMove();

			// execute the next move
			if (player.isFasterThan(enemy)) {
				state = State.playerAttack;
				player.attack(enemy, playerMove);
				paint(getGraphics());
				if (!enemy.hasLost()) {
					state = State.enemyAttack;
					enemy.attack(player, enemyMove);
					paint(getGraphics());
				}
			} else {
				state = State.enemyAttack;
				enemy.attack(player, enemyMove);
				paint(getGraphics());
				if (!player.hasLost()) {
					state = State.playerAttack;
					player.attack(enemy, playerMove);
					paint(getGraphics());
				}
			}
		}
		
		if (player.hasLost()) {
			System.out.printf("\nYou and %s have lost the battle.\n", player.getMonster().getName());
			state = State.playerHasLost;
			paint(getGraphics());
		} else {
			System.out.printf("\nYou and %s are victorious!\n", player.getMonster().getName());
			state = State.enemyHasLost;
			paint(getGraphics());
		}
		stop();
	}

	//draws all content to frame
	public void paint(Graphics g) {
		//paint background, health bars, names, and health points
		g.drawImage(background, 0, 0, null);
		player.getStatsBar().paintStatsBar(g);
		enemy.getStatsBar().paintStatsBar(g);

		//paint whatever goes in the text box at the bottom of the frame
		g.setFont(new Font("TimesRoman", Font.BOLD, 25));
		g.setColor(Color.black);
		switch (state) {
			case selectMove:
				//paint moves
				g.drawString("1." + player.getMonster().getMove1().getName(), 35, 300);
				g.drawString("3." + player.getMonster().getMove3().getName(), 35, 340);
				g.drawString("2." + player.getMonster().getMove2().getName(), 220, 300);
				g.drawString("4." + player.getMonster().getMove4().getName(), 220, 340);
				player.getMonster().paintMonster(g, 60, 150);
				enemy.getMonster().paintMonster(g, 395, 45);
				break;
		
			case playerAttack:
				enemy.getMonster().paintDamagedMonster(g, 395, 45);
				player.getMonster().paintMonster(g, 60, 150);
				player.paintAttackInfo(g);
				break;
			
			case enemyAttack:
				player.getMonster().paintDamagedMonster(g, 60, 150);
				enemy.getMonster().paintMonster(g, 395, 45);
				enemy.paintAttackInfo(g);
				break;

			case playerHasLost:
				enemy.getMonster().paintMonster(g, 395, 45);
				g.drawString(player.getMonster().getName() + " has feinted.", 35, 300);
				g.drawString("You and " + player.getMonster().getName() + " have lost the battle.", 35, 340);
				break;

			case enemyHasLost:
				player.getMonster().paintMonster(g, 60, 150);
				g.drawString(enemy.getMonster().getName() + " has feinted.", 35, 300);
				g.drawString("You and " + player.getMonster().getName() + " are victorious!", 35, 340);
				break;

			default:
				System.out.println("Invalid state");
				break;
		}
	}


	public static void main(String[] args) {
		//create instance of gameDriver
		new GameDriver();
	}
}
