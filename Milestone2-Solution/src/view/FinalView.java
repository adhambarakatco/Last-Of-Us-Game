package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import engine.Game;
import exceptions.InvalidTargetException;
import exceptions.MovementException;
import exceptions.NoAvailableResourcesException;
import exceptions.NotEnoughActionsException;
import model.characters.Character;
import model.characters.Direction;
import model.characters.Explorer;
import model.characters.Fighter;
import model.characters.Hero;
import model.characters.Medic;
import model.characters.Zombie;
import model.collectibles.Supply;
import model.collectibles.Vaccine;
import model.world.CharacterCell;
import model.world.CollectibleCell;
import model.world.TrapCell;

public class FinalView extends JFrame implements ActionListener{
	
	private ArrayList<JPanel>panels = new ArrayList<JPanel>();
	private Character selectedTarget;
	private Hero selectedHero;
	private JComboBox<Direction> direction;
	private JComboBox<String> aliveHeros;
	private BufferedImage gridImage;
    private BufferedImage menuImage;
    private JPanel mNm;
    private JButton takeAction;
    private JButton move;
    private JButton EndTurn;
    private JButton info;
	private JButton[][] mapButtons = new JButton[15][15];
	private JButton selectH;
	private JButton ZInfo;
	private JComboBox<String> action;
	private JComboBox<String> selectZ;
	private JButton selectZombie;
	private JButton selectHero;
	private int selectedHeroHP;
	private JComboBox<String> hTarget;
	
	public FinalView(int panelsWidth, int panelsHeight) {
		setTitle("The Last of Us");
		setIconImage(new ImageIcon("pics/TLOU_icon.png").getImage());
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		Dimension size = Toolkit. getDefaultToolkit().getScreenSize();
		setBounds(size.width/2 - 500, size.height/2 - 375, panelsWidth, panelsHeight);
		try {
			Game.loadHeroes("files/Heroes.csv");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			gridImage = ImageIO.read(new File("pics/grid.jpg"));
			menuImage = ImageIO.read(new File("pics/bar.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
        StartGame startGamePanel = new StartGame(panelsWidth,panelsHeight);
		this.add(startGamePanel);
		panels.add(startGamePanel);
		startGamePanel.getStartGameButton().addActionListener(this);
		ChooseHero ChooseHero = new ChooseHero(panelsWidth,panelsHeight);
		ChooseHero.setButtonActionListener(this);
		panels.add(ChooseHero);
		mNm = new JPanel();
        mNm.setLayout(new BorderLayout());
        panels.add(mNm);
        GameOver gameover = new GameOver(panelsWidth, panelsHeight);
		panels.add(gameover);
		win win = new win(panelsWidth, panelsHeight);
		panels.add(win);
		this.setVisible(true);
		this.revalidate();
	}
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Start Game")) {
			playSound("sounds/7abes damohom.wav");
		    this.updateView(this.getPanels().get(1));
		}else if(e.getActionCommand().contains("chose")) {
			int index = -1;
			for (int i = 0; i < 8; i++) {
			    if (e.getActionCommand().equals("chose" + i)) {
			        index = i;
			        break;
			    }
			}

			if (index >= 0 && index < Game.availableHeroes.size()) {
			    selectedHero = Game.availableHeroes.get(index);
			    start(selectedHero);
			    this.updateView(this.getPanels().get(2));
			    selectedHeroHP = selectedHero.getCurrentHp();
			}
		}
		else if(e.getSource() == EndTurn){
			try {
				Game.endTurn();
				updateHeroesComboBox();
				updateZombiesComboBox();
				updateSelectedHComboBox();
			} catch (NotEnoughActionsException | InvalidTargetException e1) {
				new ErrorPopUpWindow(e1.getMessage());
			}
			setIcons();
		}
		else if(e.getSource() == info) {
			new status(this.selectedHero);
		}
		else if(e.getSource() == selectZombie) {
			String z = (String) selectZ.getSelectedItem();
			selectedTarget = getZombie(z);
		}
		else if(e.getSource() == selectHero) {
			String h = (String) hTarget.getSelectedItem();
			selectedTarget = getHero(h);
		}
		else if(e.getSource() == takeAction) {
			String act = (String) action.getSelectedItem();
			if(act == "ATTACK") {
				try {
					selectedHero.setTarget(selectedTarget);
					selectedHero.attack();
					if(selectedHero.getTarget().getCurrentHp() == 0) {
						updateZombiesComboBox();
						updateSelectedHComboBox();
					}
					setIcons();
					
				} catch (NotEnoughActionsException | InvalidTargetException e1) {
					new ErrorPopUpWindow(e1.getMessage());
				}
			}else if(act == "CURE") {
				try {
					selectedHero.setTarget(selectedTarget);
					selectedHero.cure();
					setIcons();
					playSound("sounds/Basyouny w Hazem.wav");
				} catch (NotEnoughActionsException | InvalidTargetException | NoAvailableResourcesException e1) {
					new ErrorPopUpWindow(e1.getMessage());
				}
			}else if(act == "SPECIAL ACTION") {
				try {
					selectedHero.setTarget(selectedTarget);
					selectedHero.useSpecial();
					setIcons();
				} catch (InvalidTargetException | NoAvailableResourcesException e1) {
					new ErrorPopUpWindow(e1.getMessage());
				}
			}
			updateHeroesComboBox();
			updateSelectedHComboBox();
			updateZombiesComboBox();
			/*if(Game.checkGameOver()==true) {
				this.updateView(new GameOver(1000, 700));
				playSound("sounds/Yala nro7 nt8ada.wav");
			}*/
			if(Game.checkWin()==true) {
				this.updateView(new win(1000, 700));
				playSound("sounds/Eh el7alawa dy.wav");
			}
			selectedHeroHP = selectedHero.getCurrentHp();
		}
		else if(e.getSource() == move ) {
			Direction d = (Direction) direction.getSelectedItem();
			selectedHeroHP = selectedHero.getCurrentHp();
			try {
				selectedHero.move(d);
				if(selectedHero.getCurrentHp() != selectedHero.getMaxHp() && selectedHero.getCurrentHp() != selectedHeroHP) {
					playSound("sounds/trap.wav");
					selectedHeroHP = selectedHero.getCurrentHp();
				}
				Game.adjustVisibility(selectedHero);
				setIcons();
			} catch (MovementException | NotEnoughActionsException e2) {
				new ErrorPopUpWindow(e2.getMessage());
			}
		}
		else if(e.getSource() == selectH ) {
			String h = (String) aliveHeros.getSelectedItem();
			selectedHero = getHero(h);
		}
		else if(e.getSource() == ZInfo) {
			if(selectedTarget instanceof Zombie) {
				if(Game.map[selectedTarget.getLocation().x][selectedTarget.getLocation().y].isVisible() == true) {
					new ZInfo((Zombie) selectedTarget);
				}
				else {
					new ErrorPopUpWindow("You can't see that zombie, search for him");
				}
			}
		}
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
	
	public ArrayList<JPanel> getPanels() {
		return panels;
	}
	
	public static void main(String[]args) {
		new FinalView(1100,750);
	}
	public void start(Hero h) {
		Game.startGame(h);
		
		JPanel menu1 = new JPanel(new FlowLayout()){
			protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (menuImage != null) {
                    g.drawImage(menuImage, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };
        takeAction = new JButton("Take Action");
		takeAction.addActionListener(this);
		menu1.add(takeAction);
		
		String actions[] = {"ATTACK","CURE","SPECIAL ACTION"};
		action = new JComboBox<String>(actions); 
        action.addActionListener(this);
        menu1.add(action);
        
        selectZombie = new JButton("Select Zombie");
		selectZombie.addActionListener(this);
		menu1.add(selectZombie);
		
		String Zom[] = new String[Game.zombies.size()];
        Zombielist(Zom);
        selectZ = new JComboBox<String>(Zom);
        selectZ.addActionListener(this);
        menu1.add(selectZ);
        
        selectHero = new JButton("Select Hero AS TARGET");
		selectHero.addActionListener(this);
		menu1.add(selectHero);
		
		String HeroTarget[] = new String[Game.heroes.size()];
        Heroslist(HeroTarget);
        hTarget = new JComboBox<String>(HeroTarget);
        hTarget.addActionListener(this);
        menu1.add(hTarget);
        
        this.getContentPane().add(menu1);
		
		JPanel menu2 = new JPanel(new FlowLayout()){
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (menuImage != null) {
                    g.drawImage(menuImage, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };
		this.getContentPane().add(menu2);
		
		EndTurn = new JButton("END TURN");
		EndTurn.setBackground(Color.RED);
        EndTurn.setFont(new Font("Bookman Old Style",Font.PLAIN,22));
        EndTurn.setForeground(Color.WHITE);
        EndTurn.addActionListener(this);
		menu2.add(EndTurn);
		
		move = new JButton("MOVE");
		move.addActionListener(this);
		menu2.add(move);
		
		Direction[] d = {Direction.UP, Direction.DOWN, Direction.RIGHT, Direction.LEFT};
        direction = new JComboBox<Direction>(d); 
        direction.addActionListener(this);
        menu2.add(direction);
		
		selectH = new JButton("Select Hero");
        selectH.addActionListener(this);
		menu2.add(selectH);
        
        String alive[] = new String[Game.heroes.size()];
        Heroslist(alive);
        aliveHeros = new JComboBox<String>(alive);
        aliveHeros.addActionListener(this);
        menu2.add(aliveHeros);
        
        ZInfo = new JButton("Zombie's info");
        ZInfo.addActionListener(this);
		menu2.add(ZInfo);
		
		info = new JButton("Hero's info");
        info.addActionListener(this);
		menu2.add(info);
        
        
        JPanel map = new JPanel((new GridLayout(15,15))){
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (gridImage != null) {
                    g.drawImage(gridImage, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };
        
        mapButtons = new JButton[15][15];
        for (int i = 0; i < mapButtons.length; i++) {
            for (int j = 0; j < mapButtons.length; j++) {
            	mapButtons[i][j] = new JButton();
            	mapButtons[i][j].setOpaque(false);
            	mapButtons[i][j].setBackground(new Color(0, 0, 0, 0)); 
            	mapButtons[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
            	
                map.add(mapButtons[i][j]);
            }
        }
        setIcons();
        
        
        mNm.add(map, BorderLayout.CENTER);
        mNm.add(menu1,BorderLayout.PAGE_START);
        mNm.add(menu2, BorderLayout.PAGE_END);
	}
	
	public void setIcons() {
		for(int i = 0; i<mapButtons.length; i++) {
			for (int j = 0; j < mapButtons.length; j++) {
				mapButtons[i][j].setIcon(null);
				mapButtons[i][j].setText("");
				if(Game.map[i][j].isVisible() == false) {
					mapButtons[i][j].setBackground(Color.BLACK);
				}else if(Game.map[i][j] instanceof CharacterCell) {
					Character character = ((CharacterCell)Game.map[i][j]).getCharacter();
					if(character instanceof Zombie) {
						String s = character.getName().substring(7);
						mapButtons[i][j].setText(s);
						mapButtons[i][j].setFont(new Font("Bookman Old Style",Font.BOLD,9));
						mapButtons[i][j].setForeground(Color.WHITE);
						mapButtons[i][j].setIcon(getScaledIcon("pics/zombie.png",50,50));
					}else if(character instanceof Fighter) {
						mapButtons[i][j].setIcon(getScaledIcon("pics/po2.png",50,50));
					}else if(character instanceof Explorer) {
						mapButtons[i][j].setIcon(getScaledIcon("pics/hart.png",60,30));
					}else if(character instanceof Medic) {
						mapButtons[i][j].setIcon(getScaledIcon("pics/doctor raby3.png",50,50));
					}
				}else if(Game.map[i][j] instanceof CollectibleCell) {
					CollectibleCell coll = (CollectibleCell)Game.map[i][j];
					if(coll.getCollectible() instanceof Vaccine) {
						mapButtons[i][j].setIcon(getScaledIcon("pics/vaccine.png",50,50));
					}else if(coll.getCollectible() instanceof Supply) {
						mapButtons[i][j].setIcon(getScaledIcon("pics/supply.png",30,30));
					}
				}
			}
		}
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
	public String[] Heroslist(String[]arr){
		for(int i=0; i<Game.heroes.size(); i++) {
        	arr[i]=Game.heroes.get(i).getName();
        }
		return arr;
	}
	public Hero getHero(String name) {
		for(int i=0; i<Game.heroes.size(); i++) {
			if(Game.heroes.get(i).getName() == name) 
				return Game.heroes.get(i);
		}
		return null;
	}
	private void updateHeroesComboBox() {
	    String[] alive = new String[Game.heroes.size()];
	    Heroslist(alive);
	    ComboBoxModel<String> model = new DefaultComboBoxModel<>(alive);
	    aliveHeros.setModel(model);
	}
	public String[] Zombielist(String[]arr){
		for(int i=0; i<Game.zombies.size(); i++) {
        	arr[i]=Game.zombies.get(i).getName();
        }
		return arr;
	}
	public Zombie getZombie(String name) {
		for(int i=0; i<Game.zombies.size(); i++) {
			if(Game.zombies.get(i).getName() == name) 
				return Game.zombies.get(i);
		}
		return null;
	}
	private void updateZombiesComboBox() {
	    String[] zom = new String[Game.zombies.size()];
	    Zombielist(zom);
	    ComboBoxModel<String> model = new DefaultComboBoxModel<>(zom);
	    selectZ.setModel(model);
	}
	private void updateSelectedHComboBox() {
	    String[] SH = new String[Game.heroes.size()];
	    Heroslist(SH);
	    ComboBoxModel<String> model = new DefaultComboBoxModel<>(SH);
	    hTarget.setModel(model);
	}
	public static ImageIcon getScaledIcon(String imagePath, int iconWidth, int iconHeight) {
        ImageIcon originalIcon = new ImageIcon(imagePath);
        Image scaledImage = originalIcon.getImage().getScaledInstance(iconWidth, iconHeight, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }

}