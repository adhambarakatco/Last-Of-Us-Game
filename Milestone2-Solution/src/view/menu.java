package view;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

import model.characters.Direction;

public class menu extends JPanel {
	private static JButton attack;
	private static JButton cure;
	private static JButton special;
	private static JButton Herostatus;
	private static JButton EndTurn;
    private static  JComboBox<Direction> direction;
	
	public menu(int panelWidth, int panelHeight) {
		Herostatus = new JButton("Hero's Status");
        EndTurn = new JButton("END TURN");
        attack = new JButton("ATTACK");
        cure = new JButton("Cure");
        special = new JButton("Special action");
        Direction[] d = {Direction.UP, Direction.DOWN, Direction.RIGHT, Direction.LEFT};
        direction = new JComboBox<Direction>(d);
        EndTurn.setBackground(Color.RED);
        EndTurn.setFont(new Font("Bookman Old Style",Font.PLAIN,22));
        EndTurn.setForeground(Color.WHITE);
        setSize(panelWidth, panelHeight);
        setLayout(new FlowLayout());
        add(direction);
        add(attack);
        add(cure);
        add(special);
        add(Herostatus);
        add(EndTurn);
        attack.setActionCommand("attack");
        cure.setActionCommand("cure");
        special.setActionCommand("special");
        Herostatus.setActionCommand("status");
        EndTurn.setActionCommand("end");
	}
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		BufferedImage bgd = getImage("pics/bar.jpg");
		bgd = scale(bgd,this.getWidth(),this.getHeight());
		g.drawImage(bgd, 0,0, this);
	}
	
	public static BufferedImage getImage(String path) {
		BufferedImage image = null;
		try {
			image = ImageIO.read(new File (path));
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		return image;
	}
	
	public static BufferedImage scale(BufferedImage imageToScale, int dWidth, int dHeight) {
        BufferedImage scaledImage = null;
        if (imageToScale != null) {
            scaledImage = new BufferedImage(dWidth, dHeight, imageToScale.getType());
            Graphics2D graphics2D = scaledImage.createGraphics();
            graphics2D.drawImage(imageToScale, 0, 0, dWidth, dHeight, null);
            graphics2D.dispose();
        }
        return scaledImage;
    }
	
	public AbstractButton getLost() {
		return EndTurn;
	}
	public  AbstractButton getStatus() {
		return Herostatus;
	}
	public  AbstractButton getAttack() {
		return attack;
	}
	public  AbstractButton getCure() {
		return cure;
	}
	public  AbstractButton getSpecial() {
		return special;
	}
	public  JComboBox<Direction> getDirection() {
		return direction;
	}
}
