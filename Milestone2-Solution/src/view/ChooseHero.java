package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import engine.Game;
import model.characters.*;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ChooseHero extends JPanel {
    private ArrayList<JButton> buttons;
    private ActionListener buttonActionListener;

    public ChooseHero(int panelWidth, int panelHeight) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(panelWidth, panelHeight));
        setOpaque(false);

        JPanel textPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        textPanel.setOpaque(false);
        JLabel text = new JLabel("Choose your first hero:");
        text.setFont(new Font("Times New Roman", Font.BOLD, 26));
        text.setForeground(Color.WHITE);
        textPanel.add(text);
        add(textPanel);

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout());
        buttonsPanel.setOpaque(false);
        buttons = new ArrayList<JButton>();

        for (int i = 0; i < Game.availableHeroes.size(); i++) {
            String heroName = Game.availableHeroes.get(i).getName();
            JButton button = new JButton(heroName);
            button.setOpaque(false);
            button.setBackground(new Color(0, 0, 0, 0));
            button.setFont(new Font("Arial", Font.BOLD, 20));
            button.setForeground(Color.WHITE);

            String tooltipText = "Max HP: " + Game.availableHeroes.get(i).getMaxHp() + "<br>" +
                    "Max Actions: " + Game.availableHeroes.get(i).getMaxActions() + "<br>" +
                    "Attack Damage: " + Game.availableHeroes.get(i).getAttackDmg() + "<br>" +
                    "Type: " + getHeroType(Game.availableHeroes.get(i));

            button.setToolTipText("<html><body style='text-align:center;'>" + tooltipText + "</body></html>");
            buttons.add(button);
            buttons.get(i).setActionCommand("chose" + i);
            buttonsPanel.add(button);
        }

        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        centerPanel.setOpaque(false);
        centerPanel.add(buttonsPanel);
        add(centerPanel);
    }

    private String getHeroType(Hero hero) {
        if (hero instanceof Fighter) {
            return "Fighter";
        } else if (hero instanceof Medic) {
            return "Medic";
        } else {
            return "Explorer";
        }
    }


	public void setButtonActionListener(ActionListener listener) {
        this.buttonActionListener = listener;
        for (JButton button : buttons) {
            button.addActionListener(listener);
        }
    }
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		BufferedImage bgd = getImage("pics/Choosehero.jpg");
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

	public ArrayList<JButton> getButton() {
		// TODO Auto-generated method stub
		return buttons;
	}
	public void paintComponent(Graphics graphics, String string) {
		// TODO Auto-generated method stub
		
	}

}
