package view;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;

public class StartGameButton extends JButton {
	public StartGameButton() {
		this.setName("START GAME");
        this.setFocusPainted(false);
        this.setFont(new Font("Arial", Font.BOLD, 35));
        this.setBorderPainted(false);
        this.setOpaque(false);
        this.setBackground(new Color(0, 0, 0, 0));
        this.setActionCommand("start");
	}
}
