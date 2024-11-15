package view;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JLabel;

import model.characters.Hero;
import model.characters.Zombie;

public class ZInfo extends JFrame {
	public ZInfo(Zombie zombie) {
		setTitle("Hero Stats");
		setSize(300, 200);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;
        int frameWidth = getWidth();
        int frameHeight = getHeight();
        int x = (screenWidth - frameWidth) / 2;
        int y = (screenHeight - frameHeight) / 2;
        setLocation(x, y);
        JLabel nameLabel = new JLabel("Name: " + zombie.getName());
        nameLabel.setHorizontalAlignment(JLabel.CENTER);
        add(nameLabel);

        JLabel maxHpLabel = new JLabel("Current HP: " + zombie.getCurrentHp());
        maxHpLabel.setHorizontalAlignment(JLabel.CENTER);
        add(maxHpLabel);

        JLabel attackDamageLabel = new JLabel("Attack Damage: " + zombie.getAttackDmg());
        attackDamageLabel.setHorizontalAlignment(JLabel.CENTER);
        add(attackDamageLabel);
        
        setLayout(new GridLayout(3, 1));
        setVisible(true);

		this.repaint();
		this.revalidate();
	}
}
