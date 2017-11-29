package it.thefedex87.dac.states.menuStates;

import it.thefedex87.dac.states.GSM;
import it.thefedex87.dac.states.State;
import it.thefedex87.dac.states.TransitionState;
import it.thefedex87.dac.states.gameStates.PlayMultiplayerState;
import it.thefedex87.dac.ui.background.Background;
import it.thefedex87.dac.ui.background.PopBoubbleBackground;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;



public class MultiPlayerMenuState extends State {
	private Background bg;
	private boolean disposeBg = true;
	
	private Stage stage;
	private BitmapFont menuFnt;
	
	State thisState;

	/*private BitmapFont nOfPlayerTitle;
	private final String NOFPLAYERTITLE = "NUMBER OF PLAYER";
	private Vector3 nOfPlayerTitlePos;
	Rectangle nOfPlayerTitleBounds;
	
	
	private BitmapFont nPlayer;
	
	private Vector3 nPlayer2Pos;
	Rectangle nPlayer2Bounds;
	private Vector3 nPlayer3Pos;
	Rectangle nPlayer3Bounds;
	private Vector3 nPlayer4Pos;
	Rectangle nPlayer4Bounds;
	
	
	private BitmapFont mainMenu;
	private Rectangle mainMenuBounds = null;
	private final String MAINMENU = "Main Menu";
	private Vector2 mainMenuPos;*/
	
	
	public MultiPlayerMenuState(final GSM gsm) {
		super(gsm);
		
		thisState = this;
		
		Table table;
		
		Label nOfPlayer;
		final String NOFPLAYER = "NUMBER OF PLAYER";
		
		final TextButton nPlayer2;
		final TextButton nPlayer3;
		final TextButton nPlayer4;
		
		final TextButton mainMenu;
		final String MAINMENU = "Main Menu";
		
		
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		
		table = new Table();
		table.debug();
		table.debugTable();
		table.debugCell();
		
		menuFnt = new BitmapFont(Gdx.files.internal("font/fontBoardYellow.fnt"));
		float scale = 1.0f;
		while (Gdx.graphics.getWidth() * 0.95 < menuFnt.getBounds(NOFPLAYER).width) {
			scale -= 0.05f;
			menuFnt.setScale(scale);
		}
		
		LabelStyle ls = new LabelStyle();
		ls.font = menuFnt;
		
		nOfPlayer = new Label(NOFPLAYER, ls);
		
		
		menuFnt = new BitmapFont(Gdx.files.internal("font/fontBoardYellow.fnt"));
		scale = 1.0f;
		while (Gdx.graphics.getHeight() / 2 < menuFnt.getBounds("2").height * 8) {
			scale -= 0.05f;
			menuFnt.setScale(scale);
		}
		
		TextButtonStyle tbs = new TextButtonStyle();
		tbs.font = menuFnt;
		
		nPlayer2 = new TextButton("2", tbs);
		nPlayer3 = new TextButton("3", tbs);
		nPlayer4 = new TextButton("4", tbs);
		
		
		nPlayer2.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y){
				//gsm.set(new PlayMultiplayerState(gsm, 2));
				gsm.set(new TransitionState(gsm, thisState, new PlayMultiplayerState(gsm, 2)));
				nPlayer2.clearListeners();
				nPlayer3.clearListeners();
				nPlayer4.clearListeners();
			}
		});
		
		
		nPlayer3.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y){
				//gsm.set(new PlayMultiplayerState(gsm, 3));
				gsm.set(new TransitionState(gsm, thisState, new PlayMultiplayerState(gsm, 3)));
				nPlayer2.clearListeners();
				nPlayer3.clearListeners();
				nPlayer4.clearListeners();
			}
		});
		
		
		nPlayer4.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y){
				//gsm.set(new PlayMultiplayerState(gsm, 4));
				gsm.set(new TransitionState(gsm, thisState, new PlayMultiplayerState(gsm, 4)));
				nPlayer2.clearListeners();
				nPlayer3.clearListeners();
				nPlayer4.clearListeners();
			}
		});
		
		
		menuFnt = new BitmapFont(Gdx.files.internal("font/fontBoardYellow.fnt"));
		scale = 1.0f;
		while (Gdx.graphics.getWidth() * 0.9 < menuFnt.getBounds(MAINMENU).width) {
			scale -= 0.05f;
			menuFnt.setScale(scale);
		}
		
		tbs = new TextButtonStyle();
		tbs.font = menuFnt;
		
		mainMenu = new TextButton(MAINMENU, tbs);
		mainMenu.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y){
				stage.addAction(Actions.sequence(Actions.fadeOut(0.5f), completeAction));
				mainMenu.clearListeners();
			}
		});
		
		
		table = new Table();
		/*table.debug();
		table.debugCell();*/
		
		table.top();
		
		table.add(nOfPlayer).minHeight(Gdx.graphics.getHeight() / 10).row();
		
		table.add(nPlayer2).row();
		table.add(nPlayer3).row();
		table.add(nPlayer4).row();
		
		table.add(mainMenu).expandY().align(Align.center).row();
		
		table.setFillParent(true);
		
		stage.addActor(table);
		stage.addAction(Actions.sequence(Actions.alpha(0), Actions.fadeIn(0.5f)));
		
		///////
		/*nOfPlayerTitle = new BitmapFont(Gdx.files.internal("font/fontBoardYellow.fnt"));
		float scale = 1.0f;
		nOfPlayerTitle.setScale(scale);
		while (Gdx.graphics.getWidth() < nOfPlayerTitle.getBounds(NOFPLAYERTITLE).width) {
			scale -= 0.1f;
			nOfPlayerTitle.setScale(scale);
		}
		nOfPlayerTitlePos = new Vector3(Gdx.graphics.getWidth() / 2 - nOfPlayerTitle.getBounds(NOFPLAYERTITLE).width / 2,
				Gdx.graphics.getHeight() / 8 * 7, 0);
		nOfPlayerTitleBounds = new Rectangle(nOfPlayerTitlePos.x, nOfPlayerTitlePos.y - nOfPlayerTitle.getBounds(NOFPLAYERTITLE).height, 
				nOfPlayerTitle.getBounds(NOFPLAYERTITLE).width, nOfPlayerTitle.getBounds(NOFPLAYERTITLE).height);
		////////
		
		
		////////
		nPlayer = new BitmapFont(Gdx.files.internal("font/fontBoardYellow.fnt"));
		scale = 1.0f;
		nPlayer.setScale(scale);
		while (Gdx.graphics.getHeight() / 2 < nPlayer.getBounds("2").height * 6) {
			scale -= 0.1f;
			nPlayer.setScale(scale);
		}
		
		nPlayer2Pos = new Vector3(Gdx.graphics.getWidth() / 2 - nPlayer.getBounds("2").width / 2,
				nOfPlayerTitleBounds.y - nOfPlayerTitleBounds.height * 3 / 2, 0);
		nPlayer2Bounds = new Rectangle(nPlayer2Pos.x, nPlayer2Pos.y - nPlayer.getBounds("2").height, 
				nPlayer.getBounds("2").width, nPlayer.getBounds("2").height);
		
		nPlayer3Pos = new Vector3(Gdx.graphics.getWidth() / 2 - nPlayer.getBounds("3").width / 2,
				nPlayer2Bounds.y - nPlayer2Bounds.height / 2, 0);
		nPlayer3Bounds = new Rectangle(nPlayer3Pos.x, nPlayer3Pos.y - nPlayer.getBounds("3").height, 
				nPlayer.getBounds("3").width, nPlayer.getBounds("3").height);
		
		nPlayer4Pos = new Vector3(Gdx.graphics.getWidth() / 2 - nPlayer.getBounds("4").width / 2,
				nPlayer3Bounds.y - nPlayer3Bounds.height / 2, 0);
		nPlayer4Bounds = new Rectangle(nPlayer4Pos.x, nPlayer4Pos.y - nPlayer.getBounds("4").height, 
				nPlayer.getBounds("4").width, nPlayer.getBounds("4").height);
		////////
		
		
		/////////////
		mainMenu = new BitmapFont(Gdx.files.internal("font/fontBoardYellow.fnt"));
		scale = 0.7f;
		mainMenu.setScale(scale);
		while (Gdx.graphics.getWidth() < mainMenu.getBounds(MAINMENU).width) {
			scale -= 0.1f;
			mainMenu.setScale(scale);
		}
		
		mainMenuPos = new Vector2(Gdx.graphics.getWidth() / 2 - mainMenu.getBounds(MAINMENU).width / 2,
				Gdx.graphics.getHeight() / 2 - Gdx.graphics.getHeight() / 6 - mainMenu.getBounds(MAINMENU).height * 3 / 2);
		
		mainMenuBounds = new Rectangle(mainMenuPos.x,
				mainMenuPos.y - mainMenu.getBounds(MAINMENU).height,
				mainMenu.getBounds(MAINMENU).width,
				mainMenu.getBounds(MAINMENU).height);
		/////////////*/
		
		
		bg = new PopBoubbleBackground();
		
	}
	
	
	public MultiPlayerMenuState(final GSM gsm, Background bg) {
		this(gsm);
		this.bg.dispose();
		this.bg = bg;
	}
	
	
	//Azione da eseguire alla fine del fadeOut del menù
	Action completeAction = new Action(){
	    public boolean act( float delta ) {
	    	disposeBg = false;
	    	gsm.set(new MainMenuState(gsm, bg));
	        return true;
	    }
	};
	
	

	@Override
	public void handleInput() {
		/*if (Gdx.input.isTouched()) {
			Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
			cam.unproject(touchPos);
			if (nPlayer2Bounds.contains(touchPos.x, touchPos.y)) {
				gsm.set(new PlayMultiplayerState(gsm, 2));
			}
			else if (nPlayer3Bounds.contains(touchPos.x, touchPos.y)) {
				gsm.set(new PlayMultiplayerState(gsm, 3));
			}
			else if (nPlayer4Bounds.contains(touchPos.x, touchPos.y)) {
				gsm.set(new PlayMultiplayerState(gsm, 4));
			}
			else if (mainMenuBounds.contains(touchPos.x, touchPos.y)) {
				gsm.set(new MainMenuState(gsm));
			}
		}*/
	}

	@Override
	public void update(float delta) {
		bg.update(delta);
		//handleInput();
	}

	@Override
	public void render(SpriteBatch sb) {
		sb.setProjectionMatrix(cam.combined);
		
		
		bg.render(sb);
		
		sb.begin();
		/*nOfPlayerTitle.draw(sb, NOFPLAYERTITLE, nOfPlayerTitlePos.x, nOfPlayerTitlePos.y);
		nPlayer.draw(sb,"2", nPlayer2Pos.x, nPlayer2Pos.y);
		nPlayer.draw(sb,"3", nPlayer3Pos.x, nPlayer3Pos.y);
		nPlayer.draw(sb,"4", nPlayer4Pos.x, nPlayer4Pos.y);
		mainMenu.draw(sb, MAINMENU, mainMenuPos.x, mainMenuPos.y);*/
		stage.act();
		stage.draw();
		sb.end();
	}

	@Override
	public void dispose() {
		if (disposeBg) {
			bg.dispose();
			bg = null;
		}
		
		stage.dispose();
		
		menuFnt.dispose();
		menuFnt = null;
		/*nOfPlayerTitle.dispose();
		nPlayer.dispose();
		mainMenu.dispose();*/
	}

}
