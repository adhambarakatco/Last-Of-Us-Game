package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class ErrorPopUpWindow extends JFrame{
	public ErrorPopUpWindow(String errorMessage) {
		setTitle("Error Pop-up Window");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		Dimension size = Toolkit. getDefaultToolkit().getScreenSize();
		setBounds(size.width/2,size.height/2 - 325,700,100);
		JLabel errorLabel = new JLabel (errorMessage);
		errorLabel.setFont(new Font("Bookman Old Style",Font.BOLD,12));
		this.add(errorLabel);
		this.setVisible(true);
		this.revalidate();
		this.repaint();
	}
	
	public ErrorPopUpWindow(String title, String message) {
		this(message);
		setTitle(title);
	}
}
