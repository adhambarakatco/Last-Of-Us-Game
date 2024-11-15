package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;

public class StartGame extends JPanel {
    private JButton StartGameButton;

    public StartGame(int panelWidth, int panelHeight) {
        setLayout(new GridBagLayout());
        StartGameButton = new JButton("Start Game");
        StartGameButton.setFocusPainted(false);
        StartGameButton.setFont(new Font("Arial", Font.BOLD, 35));
        StartGameButton.setBorderPainted(false);
        StartGameButton.setOpaque(false);
        StartGameButton.setBackground(new Color(0, 0, 0, 0));
        add(StartGameButton);
    }

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		BufferedImage bgd = getImage("pics/wp1811287.png");
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
	public JButton getStartGameButton() {
		return StartGameButton;
	}
}
