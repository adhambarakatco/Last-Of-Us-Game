package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

public class GameOver extends JPanel{
	public GameOver(int panelWidth, int panelHeight) {
		setSize(panelWidth, panelHeight);
        setLayout(new FlowLayout());
        JLabel gameOverLabel = new JLabel("Sorry, you lost!");
        gameOverLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gameOverLabel.setVerticalAlignment(SwingConstants.TOP);
        gameOverLabel.setFont(new Font("Arial", Font.BOLD, 60));
        gameOverLabel.setForeground(new Color(139, 0, 0));
        int padding = 30;
        Border emptyBorder = BorderFactory.createEmptyBorder(padding, 0, 0, 0);
        gameOverLabel.setBorder(emptyBorder);

        add(gameOverLabel, BorderLayout.NORTH);
        
	}
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		BufferedImage bgd = getImage("pics/Lose.jpg");
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
    public static void main(String[] args) {
        JFrame frame = new JFrame("Game Over");
        Dimension size = Toolkit. getDefaultToolkit().getScreenSize();
		frame.setBounds(size.width/2 - 500, size.height/2 - 325, 1000, 600);
        GameOver gameOverPanel = new GameOver(1000, 600);
        frame.add(gameOverPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
