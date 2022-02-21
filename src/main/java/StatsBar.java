import java.awt.Color;
import java.awt.Graphics;
import java.awt.Font;

//class for printing the healthbar and stats of monster
public class StatsBar {
    Player player;
    //health bar var
    int HBarXPos;
    int HBarYPos;
    int HBarLength;
    int HBarThickness;
    //hp display var
    Font hpFont;
    int hpXPos, hpYPos;
    //name display var
    Font nameFont;
    int nameXPos, nameYPos;

    
    public StatsBar (Player player, int HBarXPos, int HBarYPos, int HBarLength, int HBarThickness, Font hpFont, int hpXPos, int hpYPos, Font nameFont, int nameXPos, int nameYPos) {
        this.player = player;
        this.HBarXPos = HBarXPos;
        this.HBarYPos = HBarYPos;
        this.HBarLength = HBarLength;
        this.HBarThickness = HBarThickness;
        this.hpFont = hpFont;
        this.hpXPos = hpXPos;
        this.hpYPos = hpYPos;
        this.nameFont = nameFont;
        this.nameXPos = nameXPos;
        this.nameYPos = nameYPos;
    }


    public void paintStatsBar(Graphics g) {
        //select colour based on health
        float totalHP = player.getMonster().getTotalHP();
        if (player.getMonster().getHP() / totalHP > 0.7) 
            g.setColor(new Color(109, 219, 158));
        else if (player.getMonster().getHP() / totalHP > 0.3) 
            g.setColor(Color.yellow);
        else 
            g.setColor(Color.red);
    
        //paint health bar
        if (player.getMonster().getHP() > 0)
            g.fillRect(HBarXPos, HBarYPos, (int)(player.getMonster().getHP() / totalHP * HBarLength), HBarThickness);

        //paint name of monster
        g.setColor(Color.black);
        g.setFont(nameFont);
        g.drawString(player.getMonster().getName(), nameXPos, nameYPos);
        
        //paint current hp of monster
        g.setFont(hpFont);
		g.drawString(player.getMonster().getHP() + "/" + player.getMonster().getTotalHP(), hpXPos, hpYPos);
    }
}
