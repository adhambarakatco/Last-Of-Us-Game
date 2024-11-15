package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

import engine.Game;
import model.characters.*;
import model.collectibles.*;
import model.world.*;


public class map extends JPanel{
	public static  JButton[][] mapButtons = new JButton[15][15];;
	public map() {
		this.setLayout(new GridLayout(15,15));
		
        for (int i = 0; i < mapButtons.length; i++) {
            for (int j = 0; j < mapButtons.length; j++) {
            	mapButtons[i][j] = new JButton();
            	mapButtons[i][j].setOpaque(false);
            	mapButtons[i][j].setBackground(new Color(0, 0, 0, 0)); 
            	mapButtons[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
                add(mapButtons[i][j]);
            }
        }
	}
        

    public JButton getButtonAtCell(int row, int col) {
        Component[] components = getComponents();
        int index = (row - 1) * 15 + (col - 1);
        if (index >= 0 && index < components.length && components[index] instanceof JButton) {
            return (JButton) components[index];
        }
        return null;
    }
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		BufferedImage bgd = getImage("pics/grid.jpg");
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
	public static JButton getButton(int i, int j){
		return mapButtons[i][j];
	}
	public static JButton[][] getMAttribute(){
		return mapButtons;
	}

	public static ImageIcon getScaledIcon(String imagePath, int iconWidth, int iconHeight) {
        ImageIcon originalIcon = new ImageIcon(imagePath);
        Image scaledImage = originalIcon.getImage().getScaledInstance(iconWidth, iconHeight, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }
	public static void main(String[]args) {
		JFrame frame = new JFrame();
		map map =new map();
		frame.add(map);
		frame.setVisible(true);
	}
}
