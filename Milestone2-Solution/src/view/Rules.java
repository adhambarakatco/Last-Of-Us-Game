package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Rules extends JFrame {
	public Rules() {
		this.setTitle("RULES");
		this.setSize(400, 400);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;
        int frameWidth = getWidth();
        int frameHeight = getHeight();
        int x = (screenWidth - frameWidth) / 2;
        int y = (screenHeight - frameHeight) / 2;
        this.setLocation(x, y);
		JPanel p = new JPanel();
		p.setBackground(Color.yellow);
		p.setPreferredSize(new Dimension(this.getWidth(), this.getHeight()));
		p.setLayout(new BorderLayout());
		String [] labels = {"The rules are simple." + "\n" + "Find the Zombies, Vaccines and 							Supplies, you will need the vaccines to cure the zombies and the 							the supplies to use your special actions. If you don't have any 							Collectibles you will have to fight the zombies instead. Be 							carfull of the actions and movments you take, you have limited 							action points."};
		for(String text :labels) {
			JLabel label = new JLabel(text); 
			label.setFont(new Font("Bookman Old Style",Font.PLAIN,14));
			p.add(label);
		}
		add(p, BorderLayout.CENTER);
		setVisible(true);
		repaint();
		revalidate();
	}
}
