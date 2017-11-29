package it.thefedex87.dac.states.menuStates;

import it.thefedex87.dac.states.GSM;
import it.thefedex87.dac.states.State;
import it.thefedex87.dac.states.TransitionState;
import it.thefedex87.dac.states.gameStates.PlaySurviveState;
import it.thefedex87.dac.ui.ExitDialog;
import it.thefedex87.dac.ui.background.Background;
import it.thefedex87.dac.ui.background.PopBoubbleBackground;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
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
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class MainMenuState extends State {
	private Stage stage;
	private BitmapFont titleFnt;
	private BitmapFont menuFnt;

	/*private BitmapFont title;
	private final String TITLE = "DAC";
	
	private BitmapFont play;
	private final String PLAY = "Play Survive";
	private Vector3 playPos;
	Rectangle playBounds;
	
	private BitmapFont multiPlayer;
	private final String MULTIPLAYER = "Multiplayer";
	private Vector2 multiPlayerPos;
	Rectangle multiPlayerBounds;
	
	private BitmapFont exit;
	private final String EXIT = "Exit";
	private Vector2 exitPos;
	Rectangle exitBounds;*/
	
	private Background bg;
	private boolean disposeBg = true;
	
	State thisState;
	

	public MainMenuState(final GSM gsm) {
		super(gsm);
		
		
		thisState = this;
		
		
		Table table;

		final String TITLE = "DAC";
		Label title;
		
		final TextButton play;
		final String PLAY = "Play Survive";
		
		final TextButton multiplayer;
		final String MULTIPLAYER = "Multiplayer";
		
		final TextButton settings;
		final String SETTINGS = "Settings";
		
		TextButton exit;
		final String EXIT = "Exit";
		
		
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		
		titleFnt = new BitmapFont(Gdx.files.internal("font/titleB.fnt"));
		float scale = 1.0f;
		while (Gdx.graphics.getWidth() * 0.9 < titleFnt.getBounds(TITLE).width) {
			scale -= 0.05f;
			titleFnt.setScale(scale);
		}
		
		LabelStyle ls = new LabelStyle();
		ls.font = titleFnt;
		
		title = new Label(TITLE, ls);
		title.setPosition(Gdx.graphics.getWidth() / 2 - title.getTextBounds().width / 2,
				Gdx.graphics.getHeight() * 9 / 10);
		
		
		
		
		menuFnt = new BitmapFont(Gdx.files.internal("font/fontBoardYellow.fnt"));
		scale = 1.0f;
		while (Gdx.graphics.getWidth() < menuFnt.getBounds(PLAY).width) {
			scale -= 0.05f;
			menuFnt.setScale(scale);
		}
		
		TextButtonStyle tbs = new TextButtonStyle();
		tbs.font = menuFnt;
		
		play = new TextButton(PLAY, tbs);
		multiplayer = new TextButton(MULTIPLAYER, tbs);
		settings = new TextButton(SETTINGS, tbs);
		exit = new TextButton(EXIT, tbs);
		
		
		play.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				//gsm.set(new PlaySurviveState(gsm));
				gsm.set(new TransitionState(gsm, thisState, new PlaySurviveState(gsm)));
				play.clearListeners();
				multiplayer.clearListeners();
			}
		});
		
		
		multiplayer.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				stage.addAction(Actions.sequence(Actions.fadeOut(0.5f), completeActionMultiplayer));
				multiplayer.clearListeners();
			}
		});
		
		
		settings.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				stage.addAction(Actions.sequence(Actions.fadeOut(0.5f), completeActionSettings));
				settings.clearListeners();
			}
		});
		
		
		exit.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				gsm.pop();
				System.exit(0);
			}
		});
		
		table = new Table();
		/*table.debug();
		table.debugTable();
		table.debugCell();*/
		
		table.padBottom(Gdx.graphics.getHeight() / 20);
		
		table.add(title).expandY().top().row();
		
		table.add(play).bottom().width(Gdx.graphics.getWidth()).height(play.getHeight() + 20).row();
		table.add(multiplayer).bottom().width(Gdx.graphics.getWidth()).height(multiplayer.getHeight() + 20).row();
		table.add(settings).bottom().width(Gdx.graphics.getWidth()).height(settings.getHeight() + 20).row();
		table.add(exit).bottom().width(Gdx.graphics.getWidth()).height(exit.getHeight() + 20).row();
		
		table.setFillParent(true);
		
		stage.addActor(table);
		stage.addAction(Actions.sequence(Actions.alpha(0), Actions.fadeIn(0.5f)));
		
		
		
		/*title = new BitmapFont(Gdx.files.internal("font/titleB.fnt"));
		float scale = 1.0f;
		while (Gdx.graphics.getWidth() < title.getBounds(TITLE).width) {
			scale -= 0.1f;
			title.setScale(scale);
		}
		
		
		play = new BitmapFont(Gdx.files.internal("font/fontBoardYellow.fnt"));
		play.setScale(0.8f);
		
		scale = 0.8f;
		while (Gdx.graphics.getWidth() < play.getBounds(PLAY).width) {
			scale -= 0.1f;
			play.setScale(scale);
		}
		playPos = new Vector3(Gdx.graphics.getWidth() / 2 - play.getBounds(PLAY).width / 2,
				Gdx.graphics.getHeight() / 3, 0);
		playBounds = new Rectangle(playPos.x, playPos.y - play.getBounds(PLAY).height, play.getBounds(PLAY).width, play.getBounds(PLAY).height);
	
		
		
		
		multiPlayer = new BitmapFont(Gdx.files.internal("font/fontBoardYellow.fnt"));
		multiPlayer.setScale(scale);
		//scale = 0.8f;
		while (Gdx.graphics.getWidth() < multiPlayer.getBounds(MULTIPLAYER).width) {
			scale -= 0.1f;
			multiPlayer.setScale(scale);
		}
		multiPlayerPos = new Vector2(Gdx.graphics.getWidth() / 2 - play.getBounds(MULTIPLAYER).width / 2,
				 playPos.y - play.getBounds(PLAY).height * 3 / 2);
		multiPlayerBounds = new Rectangle(multiPlayerPos.x, multiPlayerPos.y - multiPlayer.getBounds(MULTIPLAYER).height, 
				multiPlayer.getBounds(MULTIPLAYER).width, multiPlayer.getBounds(MULTIPLAYER).height);
		
		
				
		
		exit = new BitmapFont(Gdx.files.internal("font/fontBoardYellow.fnt"));
		exit.setScale(scale);
		exitPos = new Vector2(Gdx.graphics.getWidth() / 2 - play.getBounds(EXIT).width / 2,
				 playPos.y - play.getBounds(PLAY).height * 3);
		exitBounds = new Rectangle(exitPos.x, exitPos.y - exit.getBounds(EXIT).height,
				exit.getBounds(EXIT).width, exit.getBounds(EXIT).height);*/
		
		

		bg = new PopBoubbleBackground();
		ed = new ExitDialog();
	}
	
	public MainMenuState(final GSM gsm, Background bg) {
		this(gsm);
		this.bg.dispose();
		this.bg = bg;
	}
	
	
	//Azione da eseguire alla fine del fadeOut del menù dopo click MultiPlayer
	Action completeActionMultiplayer = new Action(){
	    public boolean act( float delta ) {
	    	disposeBg = false;
	    	gsm.set(new MultiPlayerMenuState(gsm, bg));
	        return true;
	    }
	};
	
	
	//Azione da eseguire alla fine del fadeOut del menù dopo click Settings
	Action completeActionSettings = new Action(){
	    public boolean act( float delta ) {
	    	disposeBg = false;
	    	stage.addAction(Actions.alpha(1));
	    	gsm.set(new SettingsMenuState(gsm, bg));
	        return true;
	    }
	};
	

	@Override
	public void handleInput() {
		/*if (Gdx.input.isTouched()) {
			Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
			cam.unproject(touchPos);
			if (playBounds.contains(touchPos.x, touchPos.y)) {
				gsm.set(new PlaySurviveState(gsm));
			}
			else if (multiPlayerBounds.contains(touchPos.x, touchPos.y)) {
				gsm.set(new MultiPlayerMenuState(gsm));
			}
			else if (exitBounds.contains(touchPos.x, touchPos.y)) {
				gsm.pop();
				System.exit(0);
			}
		}*/
		if (Gdx.input.isKeyPressed(Keys.BACK) || Gdx.input.isButtonPressed(Keys.A)) {
			ed.show(stage);
		}
	}

	@Override
	public void update(float delta) {
		handleInput();
		bg.update(delta);
	}

	@Override
	public void render(SpriteBatch sb) {
		sb.setProjectionMatrix(cam.combined);
		
		bg.render(sb);
		
		/*ShapeRenderer sr = new ShapeRenderer();
		sr.setColor(1,0,0,1);
		sr.begin(ShapeType.Line);
		sr.rect(playBounds.x, playBounds.y, playBounds.width, playBounds.height);
		sr.setColor(0,1,0,1);
		sr.rect(exitBounds.x, exitBounds.y, exitBounds.width, exitBounds.height);
		sr.rect(title.getX(), title.getY(), title.getWidth(), title.getHeight());
		sr.rect(table.getX(), table.getY(),table.getWidth(), table.getHeight());
		sr.end();*/
	
		sb.begin();
		/*title.draw(sb, TITLE, Gdx.graphics.getWidth() / 2 - title.getBounds(TITLE).width / 2,
				Gdx.graphics.getHeight() * 9 / 10);
		
		play.draw(sb, PLAY, playPos.x, playPos.y);
		
		multiPlayer.draw(sb, MULTIPLAYER, multiPlayerPos.x, multiPlayerPos.y);
		
		exit.draw(sb, EXIT, exitPos.x, exitPos.y);*/
		stage.act();
		stage.draw();
		sb.end();
		
		
		
	}

	@Override
	public void dispose() {
		/*play.dispose();
		title.dispose();*/
		if (disposeBg) {
			bg.dispose();
			bg = null;
		}
		
		menuFnt.dispose();
		menuFnt = null;
		
		titleFnt.dispose();
		titleFnt = null;
		
		ed.dispose();
		ed = null;
		
		stage.dispose();
	}
}
