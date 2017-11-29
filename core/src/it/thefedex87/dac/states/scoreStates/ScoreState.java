package it.thefedex87.dac.states.scoreStates;

import it.thefedex87.dac.states.GSM;
import it.thefedex87.dac.states.State;
import it.thefedex87.dac.states.TransitionState;
import it.thefedex87.dac.states.gameStates.PlayMultiplayerState;
import it.thefedex87.dac.states.gameStates.PlaySurviveState;
import it.thefedex87.dac.states.menuStates.MainMenuState;
import it.thefedex87.dac.ui.background.Background;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

//Questa classe fa da base alle classi ScoreMultiplayerState e ScoreSurviveState
public abstract class ScoreState extends State {
	protected Background bg;
	
	private BitmapFont font;
	protected Stage stage;
	
	protected State thisState;
	
	
	/*private BitmapFont playAgain;
	private Rectangle playAgainBounds = null;
	private int playAgainInd;
	private Vector2 playAgainPos;
	
	private BitmapFont mainMenu;
	private Rectangle mainMenuBounds = null;
	private int mainMenuInd;
	private Vector2 mainMenuPos;*/
	
	
	//private String[] menu;
	
	//private int nPlayer;

	public ScoreState(final GSM gsm, String[] menu, final int nPlayer) {
		super(gsm);
		
		
		Table table;
		TextButton playAgain;
		TextButton mainMenu;
		
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		
		//this.nPlayer = nPlayer;
		//this.menu = menu;
		
		
		font = new BitmapFont(Gdx.files.internal("font/fontBoardBlue.fnt"));
		float scale = 1.0f;
		while (Gdx.graphics.getWidth() * 0.9 < font.getBounds("Play Again").width) {
			scale -= 0.05f;
			font.setScale(scale);
		}
		
		
		TextButtonStyle tbs = new TextButtonStyle();
		tbs.font = font;
		
		table = new Table();
		//table.debug();
		//table.debugCell();
		table.bottom().padBottom(Gdx.graphics.getHeight() / 10);
		table.setFillParent(true);
		
		for (int i = 0; i < menu.length; i++) {
			if (menu[i] == "Play Again") {
				playAgain = new TextButton(menu[i], tbs);
				playAgain.align(Align.center);
				
				playAgain.addListener(new ClickListener() {
				    @Override
				    public void clicked(InputEvent event, float x, float y) {
				    	if (nPlayer == 1) {
				    		//gsm.set(new PlaySurviveState(gsm));
				    		gsm.set(new TransitionState(gsm, thisState, new PlaySurviveState(gsm)));
				    	}
				    	else {
				    		//gsm.set(new PlayMultiplayerState(gsm, nPlayer));
				    		gsm.set(new TransitionState(gsm, thisState, new PlayMultiplayerState(gsm, nPlayer)));
				    	}
		            }
		        });
				
				table.add(playAgain).width(Gdx.graphics.getWidth()).row();
			}
			else if(menu[i] == "Main Menu") {
				mainMenu = new TextButton(menu[i], tbs);
				mainMenu.align(Align.center);
				
				mainMenu.addListener(new ClickListener() {
				    @Override
				    public void clicked(InputEvent event, float x, float y) {
						gsm.set(new MainMenuState(gsm));
		            }
		        });
				
				table.add(mainMenu).width(Gdx.graphics.getWidth()).row();
			}
		}
		
		stage.addActor(table);
		
		/*float scale;
		this.menu = menu;
		for (int i = 0; i < menu.length; i++) {
			if (menu[i] == "Play Again") {
				playAgain = new BitmapFont(Gdx.files.internal("font/fontBoardBlue.fnt"));
				scale = 0.7f;
				playAgain.setScale(scale);
				while (Gdx.graphics.getWidth() < playAgain.getBounds(menu[i]).width) {
					scale -= 0.1f;
					playAgain.setScale(scale);
				}

				playAgainPos = new Vector2(Gdx.graphics.getWidth() / 2 - playAgain.getBounds(menu[i]).width / 2,
						Gdx.graphics.getHeight() / 2 - Gdx.graphics.getHeight() / 6 - playAgain.getBounds(menu[i]).height * i * 3 / 2);
				
				playAgainBounds = new Rectangle(playAgainPos.x,
						playAgainPos.y - playAgain.getBounds(menu[i]).height,
						playAgain.getBounds(menu[i]).width,
						playAgain.getBounds(menu[i]).height);
				playAgainInd = i;
			}
			else if(menu[i] == "Main Menu") {
				mainMenu = new BitmapFont(Gdx.files.internal("font/fontBoardBlue.fnt"));
				scale = 0.7f;
				mainMenu.setScale(scale);
				while (Gdx.graphics.getWidth() < mainMenu.getBounds(menu[i]).width) {
					scale -= 0.1f;
					mainMenu.setScale(scale);
				}
				
				mainMenuPos = new Vector2(Gdx.graphics.getWidth() / 2 - mainMenu.getBounds(menu[i]).width / 2,
						Gdx.graphics.getHeight() / 2 - Gdx.graphics.getHeight() / 6 - mainMenu.getBounds(menu[i]).height * i * 3 / 2);
				
				mainMenuBounds = new Rectangle(mainMenuPos.x,
						mainMenuPos.y - mainMenu.getBounds(menu[i]).height,
						mainMenu.getBounds(menu[i]).width,
						mainMenu.getBounds(menu[i]).height);
				mainMenuInd = i;
			}
		}*/
	}

	@Override
	public void handleInput() {
		/*if (Gdx.input.isTouched()) {
			Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
			cam.unproject(touchPos);
			if (playAgainBounds.contains(touchPos.x, touchPos.y)) {
				if (nPlayer == 1) {
					gsm.set(new PlaySurviveState(gsm));
				}
				else {
					gsm.set(new PlayMultiplayerState(gsm, nPlayer));
				}
			}
			else if (mainMenuBounds.contains(touchPos.x, touchPos.y)) {
				gsm.set(new MainMenuState(gsm));
			}
		}*/
	}

	@Override
	public void update(float delta) {
		//handleInput();
	}

	@Override
	public void render(SpriteBatch sb) {
		sb.setProjectionMatrix(cam.combined);
		
		if (bg != null) bg.render(sb);
		
		sb.begin();
		/*if (playAgain != null) playAgain.draw(sb, menu[playAgainInd], playAgainPos.x, playAgainPos.y);
		if (mainMenu != null) mainMenu.draw(sb, menu[mainMenuInd], mainMenuPos.x, mainMenuPos.y);*/
		stage.draw();
		sb.end();
	}

	@Override
	public void dispose() {
		/*if (playAgain != null) playAgain.dispose();
		if (mainMenu != null) mainMenu.dispose();*/
		if (bg != null) {
			bg.dispose();
			bg = null;
		}
		
		
		font.dispose();
		font = null;
		
		stage.dispose();
	}

}
