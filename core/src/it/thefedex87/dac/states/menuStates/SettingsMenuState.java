package it.thefedex87.dac.states.menuStates;

import it.thefedex87.dac.states.GSM;
import it.thefedex87.dac.states.State;
import it.thefedex87.dac.ui.background.Background;
import it.thefedex87.dac.ui.background.PopBoubbleBackground;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
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

public class SettingsMenuState extends State {
	Background bg;
	
	private Stage stage;
	private BitmapFont menuFnt;
	
	private final Preferences dacPrefer = Gdx.app.getPreferences("DAC_PREFERENCES");
	
	public SettingsMenuState(GSM gsm) {
		super(gsm);
		
		Table table = new Table();
		table.setFillParent(true);
		
		Label settings;
		final String SETTINGS = "Settings";
		
		final TextButton sound;
		final String SOUND = "Sound: ";
		
		final TextButton mainMenu;
		final String MAINMENU = "Main Menu";
		
		menuFnt = new BitmapFont(Gdx.files.internal("font/fontBoardYellow.fnt"));
		float scale = 1.0f;
		while (Gdx.graphics.getWidth() * 0.95 < menuFnt.getBounds(SETTINGS).width) {
			scale -= 0.05f;
			menuFnt.setScale(scale);
		}
		
		LabelStyle ls = new LabelStyle();
		ls.font = menuFnt;
		
		settings = new Label(SETTINGS, ls);
		settings.setAlignment(Align.top);
		
		
			
		menuFnt = new BitmapFont(Gdx.files.internal("font/fontBoardYellow.fnt"));
		scale = 1.0f;
		while (Gdx.graphics.getHeight() / 2 < menuFnt.getBounds(SOUND + "off").width) {
			scale -= 0.05f;
			menuFnt.setScale(scale);
		}
		
		TextButtonStyle tbs = new TextButtonStyle();
		tbs.font = menuFnt;
		
		if (!dacPrefer.getBoolean("excludeSound")) 
			sound = new TextButton(SOUND + "on", tbs);
		else
			sound = new TextButton(SOUND + "off", tbs);
		
		sound.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y){
				if (dacPrefer.getBoolean("excludeSound")) {
					sound.setText(SOUND + "on");
					dacPrefer.putBoolean("excludeSound", false);
				}
				else {
					sound.setText(SOUND + "off");
					dacPrefer.putBoolean("excludeSound", true);
				}
				dacPrefer.flush();
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
				stage.addAction(Actions.sequence(Actions.fadeOut(0.5f), completeActionMainMenu));
				mainMenu.clearListeners();
			}
		});
		
		table.top().padTop(Gdx.graphics.getHeight() / 10);
		table.add(settings).top().height(Gdx.graphics.getHeight() / 3).row();
		table.add(sound).row();
		table.add(mainMenu).padTop(Gdx.graphics.getHeight() / 10).row();
		//table.debugCell();
		
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		stage.addActor(table);
		stage.addAction(Actions.sequence(Actions.alpha(0), Actions.fadeIn(0.5f)));

		bg = new PopBoubbleBackground();
	}
	
	//Azione da eseguire alla fine del fadeOut del menù
	Action completeActionMainMenu = new Action(){
	    public boolean act( float delta ) {
	    	gsm.set(new MainMenuState(gsm, bg));
	        return true;
	    }
	};
	
	public SettingsMenuState(GSM gsm, Background bg) {
		this(gsm);
		
		this.bg.dispose();
		this.bg = bg;
	}

	@Override
	public void handleInput() {

	}

	@Override
	public void update(float delta) {
		bg.update(delta);
	}

	@Override
	public void render(SpriteBatch sb) {
		sb.setProjectionMatrix(cam.combined);
		
		bg.render(sb);
		
		sb.begin();
		stage.act();
		stage.draw();
		sb.end();
	}

	@Override
	public void dispose() {
		stage.dispose();
		
		menuFnt.dispose();
		menuFnt = null;
	}

}
