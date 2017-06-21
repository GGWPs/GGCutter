package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.container.impl.bank.BankLocation;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.methods.tabs.Tab;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.script.listener.MessageListener;
import org.dreambot.api.utilities.Timer;
import org.dreambot.api.utilities.impl.Condition;
import org.dreambot.api.wrappers.interactive.GameObject;
import org.dreambot.api.wrappers.items.GroundItem;
import org.dreambot.api.wrappers.widgets.message.Message;

import util.Wood;

@ScriptManifest(author = "GGWP", category = Category.WOODCUTTING, description = "Chops logs/willows/yews", name = "GGCutter", version = 1.6)
public class GGCutter extends AbstractScript implements MessageListener {
	
	private GUI gui;
	private boolean startScript;
	private boolean newNest;
	
	private Wood currentLog;
	private Area BANK_AREA = new Area(3207,3220,3210,3216,2);
	private Area BANK_AREA2 = new Area(3092,3246,3095,3240,0);
	private Area TREE_AREA = new Area(3190,3252,3207,3237);	
	private Area TREE_AREA2 = new Area(3082,3239,3090, 3226,0);
	private Area YEW_AREA = new Area(3052,3273,3055, 3269,0);
	private final Tile LUMBY_STAIRS = new Tile(3205,3209,0);
	private final Tile TOP_STAIRS = new Tile(3205,3209,2);												
	private final Tile OUTSIDE = new Tile(3195,3219,0);
	private String eAxe;
	private String eAxe2;
	
	Timer timer;
	private Area TREEAREA;
	private Area BANK;
	private int logsgained;
	private int logsanhour;
	private int xnests;
	private int logprice;
	private int lvl;
	private double logXP;
	
	String Status = " ";
	static String status = null;

	private GameObject tree;
	
	private State state;



	@Override
	public void onStart() {
		gui = new GUI(this);
		gui.setVisible(true);
		super.onStart();
		currentLog = Wood.NORMAL;
		sleepUntil(() -> getClient().isLoggedIn(),
		Calculations.random(40000, 60000));
		getSkillTracker().start(Skill.WOODCUTTING);
		lvl = getSkills().getRealLevel(Skill.WOODCUTTING);
		eAxe2 = "Bronze axe";
		BANK = BANK_AREA;
	}
	
	private enum State {
		BANK, WALK_TO_TREE, CUT, WAIT;
	}
	
	private State getState() {
		{
	tree = getGameObjects().closest(
			gameObject -> gameObject != null
					&& gameObject.getName().equals(currentLog.getTreeName())
					&& gameObject.distance() < 17
					&& TREEAREA.contains(gameObject));
	
	if (startScript) {
		if (!getInventory().isFull() && !getLocalPlayer().isAnimating()) {
			return State.CUT;
		} else if (getInventory().isFull()) {
				return State.BANK;
			}
		} else if (TREEAREA.contains(getLocalPlayer())
				&& getLocalPlayer().isAnimating()) {
			return State.WAIT;
		}
		else if (BANK.contains(getLocalPlayer())
				&& !getInventory().isFull()
				&& getInventory().contains(eAxe, eAxe2)){
			return State.WALK_TO_TREE;
		} else if (BANK.contains(getLocalPlayer())
				&& getInventory().isFull()){
			bank();
		}
	}
	return null;
	}


	@Override
	public int onLoop() {
		
		if(lvl > 65){
			currentLog = Wood.YEW;
			BANK = BANK_AREA2;
			eAxe = "Rune axe";
			if (gui.getway() == "Member") {
					eAxe = "Dragon axe";
			}
		} else if (lvl > 41) {
			eAxe = "Rune axe";
		} else if (lvl > 31) {
			eAxe = "Adamant axe";
			currentLog = Wood.WILLOW;
			BANK = BANK_AREA2;
		} else if (lvl > 21) {
			eAxe = "Mithril axe";
			currentLog = Wood.WILLOW;
			BANK = BANK_AREA2;
		} else if (lvl > 11) {
			eAxe = "Black axe";
		} else if (lvl > 6 ) {
			eAxe = "Steel axe";
		} else if (lvl > 1) {
			currentLog = Wood.NORMAL;
			eAxe = "Iron axe";
			eAxe2 = "Bronze axe";
			BANK = BANK_AREA;
		}
		
		
		if (currentLog == Wood.NORMAL) {
			logXP = 25;
			logprice = 64;
			TREEAREA = TREE_AREA;
		} else if (currentLog == Wood.OAK) {
			logXP = 37.5;
			logprice = 48;
		} else if (currentLog == Wood.WILLOW) {
			logXP = 67.5;
			logprice = 8;
			TREEAREA = TREE_AREA2;
			BANK = BANK_AREA2;
		} else if (currentLog == Wood.MAPLE) {
			logXP = 100;
			logprice = 7;
		} else if (currentLog == Wood.YEW) {
			logXP = 175;
			logprice = 347;
			TREEAREA = YEW_AREA;
			BANK = BANK_AREA2;
		} else if (currentLog == Wood.MAGIC) {
			logXP = 250;
			logprice = 1168;
		}
		
		tree = getGameObjects().closest(
				gameObject -> gameObject != null
						&& gameObject.getName().equals(currentLog.getTreeName())
						&& gameObject.distance() < 17
						&& TREEAREA.contains(gameObject));
		
		switch (getState()) {

		
		
		case CUT:
			if(lvl > 65){
				currentLog = Wood.YEW;
				eAxe = "Rune axe";
				if (gui.getway() == "Member") {
						eAxe = "Dragon axe";
				}
			} else if (lvl > 41) {
				eAxe = "Rune axe";
				currentLog = Wood.WILLOW;
			} else if (lvl > 31) {
				eAxe = "Adamant axe";
				currentLog = Wood.WILLOW;
				BANK = BANK_AREA2;
			} else if (lvl > 21) {
				eAxe = "Mithril axe";
				currentLog = Wood.WILLOW;
				BANK = BANK_AREA2;
			} else if (lvl > 11) {
				eAxe = "Black axe";
				currentLog = Wood.NORMAL;
			} else if (lvl > 6 ) {
				eAxe = "Steel axe";
				currentLog = Wood.NORMAL;
			} else if (lvl > 1) {
				currentLog = Wood.NORMAL;
				eAxe = "Iron axe";
				eAxe2 = "Bronze axe";
			}
			
			
			if (currentLog == Wood.NORMAL) {
				logXP = 25;
				logprice = 64;
				TREEAREA = TREE_AREA;
				BANK = BANK_AREA;
			} else if (currentLog == Wood.OAK) {
				logXP = 37.5;
				logprice = 48;
			} else if (currentLog == Wood.WILLOW) {
				logXP = 67.5;
				logprice = 8;
				TREEAREA = TREE_AREA2;
				BANK = BANK_AREA2;
			} else if (currentLog == Wood.MAPLE) {
				logXP = 100;
				logprice = 7;
			} else if (currentLog == Wood.YEW) {
				logXP = 175;
				logprice = 347;
				TREEAREA = YEW_AREA;
				BANK = BANK_AREA2;
			} else if (currentLog == Wood.MAGIC) {
				logXP = 250;
				logprice = 1168;
			}
			
			
			if (TREEAREA.contains(getLocalPlayer())) {
				if (!newNest) { // bird nest check
					if (tree != null && TREEAREA.contains(tree)) {
						if (tree.isOnScreen()) {
							chop(); // chop the tree
						} else {
							move(); // move the camera a bit
						}
					} else {
						hopWorlds(); // hop worlds
					}
				} else {
					GroundItem nest = getGroundItems().closest("Bird nest");
					if (nest != null && nest.interact()) {
						sleepUntil(() -> nest == null
								|| !getLocalPlayer().isMoving(),
								Calculations.random(2500, 3800));
						newNest = false;
					}

				}
			} else {
				if (getWalking().walk(TREEAREA.getRandomTile())) {
					log("walking to trees");
					sleepUntil(() -> (getClient().getDestination().distance() < Calculations.random(
									6, 9))
									|| getLocalPlayer().isStandingStill(),
							Calculations.random(4100, 5110));
				}
			}
			break;
		case BANK:
			if (BANK.contains(getLocalPlayer())
					&& getInventory().isFull()){
				bank();
				sleep(Calculations.random(2500, 4500));
			}  else {
				if (getLocalPlayer().distance(
						getBank().getClosestBankLocation().getCenter()) > Calculations
						.random(6, 9)) {
					if (getWalking().walk(
								getBank().getClosestBankLocation().getCenter())) {
							sleepUntil(
									() -> !getLocalPlayer().isMoving()
											|| getLocalPlayer().distance(
													getClient().getDestination()) < 8,
									Calculations.random(3450, 5800));
							bank();
						}
			}
			}
			if (BANK == BANK_AREA 
				&& (getLocalPlayer().getTile().getZ() == 2)
				&& (!BANK.contains(getLocalPlayer()))){
				getWalking().walk(BANK.getRandomTile());
				
			} else if (BANK == BANK_AREA 
					&& (getLocalPlayer().getTile().getZ() == 1)) {
				GameObject stairs = getGameObjects().closest("Staircase");
				if (stairs != null) {
					stairs.interact("Climb-up");
					sleepUntil(new Condition() {
						public boolean verify() {
							return getLocalPlayer().getTile().getZ() == 2;
						}
					}, Calculations.random(2400, 2800));
				}
			} else if (BANK == BANK_AREA 
					&& (getLocalPlayer().getTile().distance(LUMBY_STAIRS) > 5 
					&& (getLocalPlayer().getTile().getZ() == 0))) {
					getWalking().walk(LUMBY_STAIRS);
					sleep(Calculations.random(2500, 4500));
				}  else if (getLocalPlayer().getTile().distance(LUMBY_STAIRS) < 5) {
					GameObject stairs = getGameObjects().closest("Staircase");
					if(stairs != null){
						stairs.interact("Climb-up");
						sleepUntil(new Condition(){
							public boolean verify(){
								return getLocalPlayer().getTile().getZ() == 1;
							}
						},Calculations.random(2400,2800));
					}
				} else {
				bank();
				}
			
			break;
				
			
		case WALK_TO_TREE:
			if (getLocalPlayer().getTile().getZ() == 2) {
				if (getLocalPlayer().getTile().distance(TOP_STAIRS) > 5) {
					getWalking().walk(TOP_STAIRS);
					sleep(Calculations.random(2500, 4500));
				} else {
					GameObject stairs = getGameObjects().closest("Staircase");
					if (stairs != null) {
						stairs.interact("Climb-down");
						sleepUntil(new Condition() {
							public boolean verify() {
								return getLocalPlayer().getTile().getZ() == 1;
							}
						}, Calculations.random(2400, 2800));

					}
				}
			} else if (getLocalPlayer().getTile().getZ() == 1) {
				GameObject stairs = getGameObjects().closest("Staircase");
				if (stairs != null) {
					stairs.interact("Climb-down");
					sleepUntil(new Condition() {
						public boolean verify() {
							return getLocalPlayer().getTile().getZ() == 0;
						}
					}, Calculations.random(2400, 2800));
				}
			} else {
				if (BANK == BANK_AREA
				&& (getLocalPlayer().getTile().distance(OUTSIDE) > 6)) {
					getWalking().walk(OUTSIDE);
				}
			}
			break;
			
		case WAIT:
			antiBan();
			sleepUntil(() -> !getLocalPlayer().isAnimating(),
					Calculations.random(7000, 12000));
			break;
		}

		return Calculations.random(200, 400);
	}

	private void move() {
		log("getting new tree.");
		if (tree.distance() < Calculations.random(5, 7)) {
			Status = "Moving camera";
			getCamera().rotateToEntity(tree);
			getCamera().rotateToPitch(Calculations.random(32, 39));
		} else {
			Status = "Walking to stuff";
			if (tree != null) {
				getWalking().walk(tree);
				sleepUntil(
						() -> (getClient().getDestination().distance() < Calculations.random(
								4, 6))
								|| getLocalPlayer().isStandingStill(),
						Calculations.random(4100, 5110));
			}

		}
	}
	
	
	private int F2PWorld() { 
		int random = Calculations.random(1, 10);
		int world = 0;

		if (random == 1) {
			world = 1;
		} else if (random == 2) {
			world = 8;
		} else if (random == 3) {
			world = 16;
		} else if (random == 4) {
			world = 26;
		} else if (random == 5) {
			world = 35;
		} else if (random == 6) {
			world = 94;
		} else if (random == 7) {
			world = 82;
		} else if (random == 8) {
			world = 83;
		} else if (random == 9) {
			world = 84;
		} else if (random == 10) {
			world = 93;
		} else if (random == 11) {
			world = 85;
		} else if (random == 12) {
			world = 81;
		}

		if (world != getClient().getCurrentWorld()) {
			return world;
		} else {
			return F2PWorld();
		}
	}
	
	
	private int P2PWorld() { 

		int world = getWorlds().getRandomWorld(
				w -> w.isMembers() && w.getID() != (319) && w.getID() != (325)
						&& w.getID() != (337) && w.getID() != (345)
						&& w.getID() != (352) && w.getID() != (357)
						&& w.getID() != (360) && w.getID() != (374)
						&& w.getID() != (373) && w.getID() != (366)
						&& w.getID() != (365) && w.getID() != (361)
						&& w.getID() != (353)
						&& w.getID() != getClient().getCurrentWorld()).getID();
		return world;
	}
	
	
	private void hopWorlds() {
		if (gui.getway() == "Free2Play") {
			if (getWorldHopper().hopWorld(F2PWorld())) {
				sleep(Calculations.random(1000, 2500));
				sleepUntil(() -> getLocalPlayer().exists()
						&& getClient().isLoggedIn(),
						Calculations.random(5000, 8500));
				sleepUntil(() -> TREEAREA.contains(tree),
						Calculations.random(2000, 3500));
			}
		} else {
			if (getWorldHopper().hopWorld(P2PWorld())) {
				sleep(Calculations.random(1000, 2500));
				sleepUntil(() -> getLocalPlayer().exists()
						&& getClient().isLoggedIn(),
						Calculations.random(5000, 8500));
				sleepUntil(() -> TREEAREA.contains(tree),
						Calculations.random(2000, 3500));
			}
		}
	}
	
	private void chop() {
		sleep(Calculations.random(50, 160));
		if (tree != null && tree.interact("Chop down")) {
			log("Chopping new tree.");
			sleep(Calculations.random(600, 1200));
			sleepUntil(() -> !getLocalPlayer().isMoving(),
					Calculations.random(3500, 5000));
			sleep(Calculations.random(150, 311));
			if (getLocalPlayer().isAnimating()) {
				antiBan();
				sleepUntil(() -> !getLocalPlayer().isAnimating(),
						Calculations.random(7000, 12000));
			}
		}
	}

	private void bank() {
		if (BANK.contains(getLocalPlayer())
				&& getInventory().isFull()
				&& getInventory().contains(eAxe, eAxe2, "Bronze axe", "Iron axe",
						"Steel axe", "Black axe", "Mithril axe", "Adamant axe",
						"Rune axe", "Dragon axe", "Infernal axe")){
			getBank().open();
			sleepUntil(() -> getBank().isOpen(),
					Calculations.random(3000, 5000));
		}
		if (getBank().isOpen()) {
			if (!getInventory().onlyContains(eAxe)
					&& getBank().contains(eAxe)) {
				getBank().depositAllExcept(eAxe, eAxe2);
				sleep(Calculations.random(600, 1200));
				getBank().withdraw(eAxe);
					} else if  (!getInventory().onlyContains(eAxe, eAxe2)) {
						getBank().depositAllExcept(eAxe, eAxe2);
					} else {
						if (getBank().close()) {
							sleepUntil(new Condition(){
								public boolean verify(){
									return getBank().isOpen();
								}
							},Calculations.random(4000,6000));
						}
					}
				}
			}
		
		


	private void antiBan() { 
		int random = Calculations.random(1, 250);

		if (random == 1) {
			if (!getTabs().isOpen(Tab.STATS)) {
				getTabs().open(Tab.STATS);
				getSkills().hoverSkill(Skill.WOODCUTTING);
				sleep(Calculations.random(1000, 2000));
				getTabs().open(Tab.INVENTORY);
			}
		} else if (random <= 10) {
			if (!getTabs().isOpen(Tab.INVENTORY)) {
				getTabs().open(Tab.INVENTORY);
			}
		} else if (random <= 15) {
			getCamera().rotateToTile(TREE_AREA2.getRandomTile());
		} else if (random <= 20) {
			getCamera().rotateToEntity(getLocalPlayer());
		} else if (random <= 88) {
			if (getMouse().isMouseInScreen()) {
				if (getMouse().moveMouseOutsideScreen()) {
					sleep(Calculations.random(1500, 3000));
				}
			}
		} else {
			//
		}
	}
	
	private final Color color1 = new Color(100, 100, 51, 147);
	private final Color color2 = new Color(30, 70, 19);
	private final Color color3 = new Color(255, 255, 255);
	private final BasicStroke stroke1 = new BasicStroke(5);
	private final Font font1 = new Font("Arial", Font.BOLD, 13);
	private final Font font2 = new Font("Arial", Font.BOLD, 0);
	private final Font font3 = new Font("Arial", 0, 13);
	private Timer t = new Timer();
	
	public void onPaint(Graphics g1) {
		logsgained = (int) Math.floor(getSkillTracker().getGainedExperience(
				Skill.WOODCUTTING) / logXP); 
		logsanhour = t.getHourlyRate(logsgained);

		if (t == null) {
			t = new Timer(0);
		}
		Graphics2D g = (Graphics2D) g1;
		Stroke stroke = g.getStroke();
		g.setColor(color1);
		g.fillRect(3, 4, 175, 165);
		g.setColor(color2);
		g.setStroke(stroke1);
		g.drawRect(3, 4, 175, 165);
		g.setFont(font1);
		g.setColor(color3);
		g.drawString(getManifest().name() + "         " + "v"
				+ getManifest().version(), 12, 29);
		g.setFont(font2);
		g.setFont(font3);
		g.drawString("State: " + state.toString(), 12, 40);
		g.drawString("Time running: " + Timer.formatTime(t.elapsed()), 12, 59);
		g.drawString(
				"Levels gained: " 
						+ getSkills().getRealLevel(Skill.WOODCUTTING)
						+ "(+"
						+ getSkillTracker().getGainedLevels(Skill.WOODCUTTING)
						+ ")", 12, 79);
		g.drawString(
				"XP gained: " 
						+ getSkillTracker().getGainedExperience(Skill.WOODCUTTING)
						+ "("
						+ getSkillTracker().getGainedExperiencePerHour(
								Skill.WOODCUTTING) + ")", 12, 99);
		g.drawString(
				"XP to level: "
						+ getSkills().getExperienceToLevel(Skill.WOODCUTTING),
				12, 121);
		g.drawString(
				"Chopped [P/H]: " 
						+ logsgained 
						+ " ["
						+ t.getHourlyRate(logsgained) 
						+ "]", 12, 141);
		g.drawString("GP(/h): " + (int) Math.floor(logsgained * logprice) 
				+ " ["
				+ (int) Math.floor(logsanhour * logprice) 
				+ "]", 12, 161);
		if (gui.getway() == "Member") {
			g.drawString("{" + xnests + "}", 150, 161);
		}
		g.setStroke(stroke);
	}

	public void setStartScript(boolean startScript) {
		this.startScript = startScript;
	}

	@Override
	public void onGameMessage(Message message) {
		if (message.getMessage().contains("A bird's nest falls")) {
			xnests++;
			newNest = true;
		}
	}

	@Override
	public void onPlayerMessage(Message arg0) {
	}

	@Override
	public void onPrivateInMessage(Message arg0) {
	}

	@Override
	public void onPrivateOutMessage(Message arg0) {
	}

	@Override
	public void onTradeMessage(Message arg0) {
	}
}

