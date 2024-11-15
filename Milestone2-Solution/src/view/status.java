package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JLabel;

import model.characters.Hero;

public class status extends JFrame {
	public status(Hero hero) {
		setTitle(hero.getName()+" status");
		setSize(200, 300);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;
        int frameWidth = getWidth();
        int frameHeight = getHeight();
        int x = (screenWidth - frameWidth) / 2;
        int y = (screenHeight - frameHeight) / 2;
        setLocation(x, y);
        JLabel nameLabel = new JLabel("Name: " + hero.getName());
        nameLabel.setHorizontalAlignment(JLabel.CENTER);
        add(nameLabel);

        JLabel maxHpLabel = new JLabel("Current HP: " + hero.getCurrentHp());
        maxHpLabel.setHorizontalAlignment(JLabel.CENTER);
        add(maxHpLabel);

        JLabel maxActionsLabel = new JLabel("Actions Available: " + hero.getActionsAvailable());
        maxActionsLabel.setHorizontalAlignment(JLabel.CENTER);
        add(maxActionsLabel);

        JLabel attackDamageLabel = new JLabel("Attack Damage: " + hero.getAttackDmg());
        attackDamageLabel.setHorizontalAlignment(JLabel.CENTER);
        add(attackDamageLabel);
        
        JLabel vaccineLabel = new JLabel("Vaccine inventory: " + hero.getVaccineInventory().size());
        vaccineLabel.setHorizontalAlignment(JLabel.CENTER);
        add(vaccineLabel);
        
        JLabel supplyLabel = new JLabel("Supply inventory: " + hero.getSupplyInventory().size());
        supplyLabel.setHorizontalAlignment(JLabel.CENTER);
        add(supplyLabel);

        setLayout(new GridLayout(6, 1));
        setVisible(true);

		this.repaint();
		this.revalidate();
	}
}
