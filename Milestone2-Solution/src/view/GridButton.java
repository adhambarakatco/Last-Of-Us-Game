package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class GridButton extends JButton implements ActionListener {
    public GridButton() {
        super();
        this.getActionListeners();
        this.setOpaque(false);
        this.setBackground(new Color(0, 0, 0, 0)); 
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        BufferedImage bgd = getImage("pics/rifle.png");
        bgd = scale(bgd, this.getWidth(), this.getHeight());
        g.drawImage(bgd, 0, 0, this);
    }

    public static BufferedImage getImage(String path) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File(path));
        } catch (IOException e) {
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

    @Override
    public void actionPerformed(ActionEvent e) {
    	
    }
}