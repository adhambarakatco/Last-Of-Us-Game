/*package controller;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import engine.Game;
import exceptions.InvalidTargetException;
import exceptions.MovementException;
import exceptions.NoAvailableResourcesException;
import exceptions.NotEnoughActionsException;
import model.characters.*;
import model.characters.Character;
import model.collectibles.Supply;
import model.collectibles.Vaccine;
import model.world.CharacterCell;
import model.world.CollectibleCell;
import view.*;

public class Controller implements ActionListener{
	private GameView view;
	private Hero selectedHero;
	private boolean gameover;
	private boolean win;
	private status status;
	private Direction direction;
	private map map;
	private JButton[][] mapButtons = new JButton[15][15];
	private Character selectedTarget;
	
	public Controller() {
		view = new GameView();
		selectedHero = null;
		selectedTarget = null;
		map = new map();
		
		try {
			Game.loadHeroes("files/Heroes.csv");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
		((StartGame)view.getPanels().get(0)).getStartGameButton().addActionListener(this);
		 for (int i = 0; i < 8; i++) {
			 ((ChooseHero)view.getPanels().get(1)).getButton().get(i).addActionListener(this);
		 }
		 ((mapAndmenu)view.getPanels().get(2)).getLost().addActionListener(this);
		 ((mapAndmenu)view.getPanels().get(2)).getStatus().addActionListener(this);
		 ((mapAndmenu)view.getPanels().get(2)).getAttack().addActionListener(this);
		 ((mapAndmenu)view.getPanels().get(2)).getCure().addActionListener(this);
		 ((mapAndmenu)view.getPanels().get(2)).getSpecial().addActionListener(this);
		 mapAndmenu.getDirection().addActionListener(this);
		 
	}
	
	public void actionPerformed(ActionEvent e) {
		
				
		for (int i = 0; i < mapButtons.length; i++) {
		    for (int j = 0; j < mapButtons.length; j++) {
		    	if(e.getSource() == mapButtons[i][j]) {
		    		if (Game.map[i][j] instanceof CharacterCell && Game.map[i][j].isVisible()) {
		    			CharacterCell c = (CharacterCell) Game.map[i][j];
		    			if(c.getCharacter() instanceof Hero) {
		        			selectedHero = (Hero) c.getCharacter();
		        		}else if(c.getCharacter() instanceof Zombie) {
		        			selectedTarget = (Zombie) c.getCharacter();
		        		}
		    		}
		    	}
		    }
		}
		if (e.getActionCommand().equals("Start Game")) {
			playSound("sounds/7abes damohom.wav");
		    view.updateView(view.getPanels().get(1));
		    
		}
		else if(e.getActionCommand().contains("chose")) {
			int index = -1;
			for (int i = 0; i < 8; i++) {
			    if (e.getActionCommand().equals("chose" + i)) {
			        index = i;
			        break;
			    }
			}

			if (index >= 0 && index < Game.availableHeroes.size()) {
			    selectedHero = Game.availableHeroes.get(index);
			    view.updateView(view.getPanels().get(2));
			    Game.startGame(selectedHero);
			    updateMap1();
			    view.updateMapView();
			}
		}
		else if(e.getActionCommand().equals("end")){
			gameover = Game.checkGameOver();
			win = Game.checkWin();
			if(gameover==true) {
				view.updateView(view.getPanels().get(3));
				//playSound("sounds/Yala nro7 nt8ada.wav");
			}
			else if(win == true) {
				view.updateView(view.getPanels().get(4));
			}
			else {
				view.updateView(view.getPanels().get(3));
				playSound("sounds/Yala nro7 nt8ada.wav");
			}
		}
		else if(e.getActionCommand().equals("status")) {
			this.status = new status(this.selectedHero);
			this.status.setVisible(true);
		}
		else if(e.getActionCommand().equals("attack")) {
			try {
				selectedHero.setTarget(selectedTarget);
				selectedHero.attack();
			} catch (NotEnoughActionsException | InvalidTargetException e1) {
				new ErrorPopUpWindow(e1.getMessage());
			}
			updateMap();
			map.updateMapView();
		}
		else if(e.getActionCommand().equals("cure")) {
			try {
				selectedHero.setTarget(selectedTarget);
				selectedHero.cure();
			} catch (NotEnoughActionsException | InvalidTargetException | NoAvailableResourcesException e1) {
				new ErrorPopUpWindow(e1.getMessage());
			}
		}
		else if(e.getActionCommand().equals("special")) {
			try {
				selectedHero.setTarget(selectedTarget);
				selectedHero.useSpecial();
			} catch (InvalidTargetException | NoAvailableResourcesException e1) {
				new ErrorPopUpWindow(e1.getMessage());
			}
		}
		else if(e.getActionCommand().equals("move") ) {
			direction = (Direction) mapAndmenu.getDirection().getSelectedItem();
			try {
				selectedHero.move(direction);
			} catch (MovementException | NotEnoughActionsException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
		}
	}
	
	public GameView getView() {
		return view;
	}
	
	public void playSound(String filePath) {
		try {
		    AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());
		    Clip clip = AudioSystem.getClip();
		    clip.open(audioInputStream);
		    clip.start();

		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
		    e.printStackTrace();
		}
	}
    public void updateMap1() {
        for (int i = 0; i < mapButtons.length; i++) {
            for (int j = 0; j < mapButtons.length; j++) {
                mapButtons[i][j].setIcon(null);
                if(!Game.map[i][j].isVisible()) {
                	mapButtons[i][j].setOpaque(false);
                }
                else if(Game.map[i][j] instanceof CharacterCell) {
                	if(((CharacterCell)Game.map[i][j]).getCharacter() instanceof Zombie) {
                		mapButtons[i][j].setIcon(new ImageIcon("pics/zombie.png"));
                	}else if(((CharacterCell)Game.map[i][j]).getCharacter() instanceof Fighter){
                		mapButtons[i][j].setIcon(new ImageIcon("pics/Po.png"));
                	}else if(((CharacterCell)Game.map[i][j]).getCharacter() instanceof Medic){
                		mapButtons[i][j].setIcon(new ImageIcon("pics/doctor.png"));
                	}else if(((CharacterCell)Game.map[i][j]).getCharacter() instanceof Explorer){
                		mapButtons[i][j].setIcon(new ImageIcon("pics/hart.png"));
                	}
                }
                else if(Game.map[i][j] instanceof CollectibleCell){
                	if(((CollectibleCell)Game.map[i][j]).getCollectible() instanceof Vaccine){
                		mapButtons[i][j].setIcon(new ImageIcon("pics/vaccine.jpg"));
                	}
                	else if(((CollectibleCell)Game.map[i][j]).getCollectible() instanceof Supply){
                		mapButtons[i][j].setIcon(new ImageIcon("pics/supply.jpg"));
                	}
            	}
            }
        }
    }
	public void updateMap() {
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
            	map.getInputMap(i, j).setText("null");
                if(!Game.map[i][j].isVisible()) {
            		map.getInputMap(i, j).setOpaque(false);
                }
                else if(Game.map[i][j] instanceof CharacterCell) {
                	if(((CharacterCell)Game.map[i][j]).getCharacter() instanceof Zombie) {
                		map.mapButtons[i][j].setText("zom");
                	}else if(((CharacterCell)Game.map[i][j]).getCharacter() instanceof Fighter){
                		map.mapButtons[i][j].setText("figh");
                	}else if(((CharacterCell)Game.map[i][j]).getCharacter() instanceof Medic){
                		map.mapButtons[i][j].setText("med");
                	}else if(((CharacterCell)Game.map[i][j]).getCharacter() instanceof Explorer){
                		map.mapButtons[i][j].setText("exp");
                	}
                }
                else if(Game.map[i][j] instanceof CollectibleCell){
                	if(((CollectibleCell)Game.map[i][j]).getCollectible() instanceof Vaccine){
                		map.mapButtons[i][j].setText("vaccine");
                	}
                	else if(((CollectibleCell)Game.map[i][j]).getCollectible() instanceof Supply){
                		map.mapButtons[i][j].setText("supply");
                	}
            	}
            }
        }
    }
	
	public static void main(String[] args) {
		new Controller();
	}

}*/
