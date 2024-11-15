package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import view.GameView;

@SuppressWarnings("serial")
public class GameView extends JFrame implements ActionListener{
	private ArrayList<JPanel>panels;
	public GameView() {
		setTitle("The Last of Us");
		setIconImage(new ImageIcon("pics/TLOU_icon.png").getImage());
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		Dimension size = Toolkit. getDefaultToolkit().getScreenSize();
		setBounds(size.width/2 - 500, size.height/2 - 325, 1000, 600);
		panels = new ArrayList<JPanel>();
		StartGame startGamePanel = new StartGame(1000,600);
		this.add(startGamePanel);
		ChooseHero chooseHeroPanel = new ChooseHero(1000,600);
		//mapAndmenu mapPanel = new mapAndmenu(1000,600);
		//mapPanel.updateMapView();
		GameOver lost = new GameOver(1000,600);
		win win = new win(1000,600);
		panels.add(startGamePanel);
		panels.add(chooseHeroPanel);
		//panels.add(mapPanel);
		panels.add(lost);
		panels.add(win);
		this.setVisible(true);
		this.revalidate();
		this.repaint();
	}
	
	public void updateView(JPanel newPanel) {
		for(int i = 0 ; i < panels.size() ; i++) {
			this.remove(panels.get(i));
		}
		this.add(newPanel, BorderLayout.CENTER);
		
		this.revalidate();
		this.repaint();
	}

	public void updateView(JPanel newPanel, JPanel exceptionPanel) {
		for(int i=0; i < panels.size(); i++) {
			if(panels.get(i) == exceptionPanel)
				i++;
			this.remove(panels.get(i));
		}
	}
	
	public void updateMapView() {
		for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
            	JButton button = map.mapButtons[i][j];
            	if(button.getText().equals("zom")) {
            		button.setIcon(new ImageIcon("pics/zombie.png"));
            	}else if(button.getText().equals("figh")) {
            		button.setIcon(new ImageIcon("pics/po2.png"));
            	}else if(button.getText().equals("med")) {
            		button.setIcon(new ImageIcon("pics/doctor.png"));
            	}else if(button.getText().equals("exp")) {
            		button.setIcon(new ImageIcon("pics/hart.png"));
            	}else if(button.getText().equals("vaccine")) {
            		button.setIcon(new ImageIcon("pics/vaccine.png"));
            	}else if(button.getText().equals("supply")) {
            		button.setIcon(new ImageIcon("pics/supply.png"));
            	}
            }
        }
	}
	
	public ArrayList<JPanel> getPanels() {
		return panels;
	}
	public void error(Exception e) {
		JOptionPane.showMessageDialog(this, e.getMessage());
	}
	public void win(String s){
		JOptionPane.showMessageDialog(this, s + " YOU WON!!!");
	}
	
	public static void main(String[] args) {
		GameView v = new GameView();
		v.updateMapView();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		
	}

}