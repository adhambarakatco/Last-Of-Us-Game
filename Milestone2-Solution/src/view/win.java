package view;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class win extends JPanel{
	public win(int panelWidth, int panelHeight) {
		setSize(panelWidth, panelHeight);
	}
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		BufferedImage bgd = getImage("pics/win.jpg");
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
}
