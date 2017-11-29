package it.thefedex87.dac.states;

import it.thefedex87.dac.DAC;
import it.thefedex87.dac.states.menuStates.MainMenuState;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

public class SplashState extends State {
	private Stage stage;
	private BitmapFont bfont;
	//Skin skin;

	public SplashState(GSM gsm) {
		super(gsm);
		Gdx.input.setInputProcessor(stage);
		
		Image logo;
		Label developer;
		Table table;
		
		table = new Table();
		//table.debug();
		//table.debugCell();
		
		/*skin = new Skin();
		
		
		Pixmap pixmap = new Pixmap(100, 100, Format.RGBA8888);
        pixmap.setColor(Color.alpha(0));
        pixmap.fill();
 
        skin.add("white", new Texture(pixmap));*/
		
		
		
		bfont = new BitmapFont(Gdx.files.internal("font/courier.fnt"));
		float scale = 1.0f;
		while (Gdx.graphics.getWidth() / 3 < bfont.getBounds("Developed by TheFedex87").width) {
			scale -= 0.05f;
			bfont.setScale(scale);
		}
        //skin.add("default",bfont);
		
		logo = new Image(DAC.res.getAtlas("pack").findRegion("logo"));
		logo.setSize(logo.getWidth() / 3 * 2, logo.getHeight() / 3 * 2);
		//logo.setPosition(Gdx.graphics.getWidth() / 2 - logo.getWidth() / 2, Gdx.graphics.getHeight() / 2 - logo.getHeight() / 2);
		scale = 1.0f;
		while (Gdx.graphics.getWidth() < logo.getWidth()) {
			scale -= 0.1f;
			logo.setSize(logo.getWidth() * scale, logo.getHeight() * scale);
		}
		
		
		
		LabelStyle ls = new LabelStyle();
		ls.font = bfont;
		developer = new Label("Developed by TheFedex87", ls);
		developer.setAlignment(Align.center);
		
		table.add(logo).row();
		table.add(developer).row();
		table.setFillParent(true);
		
		stage = new Stage();
		stage.addActor(table);
		
		stage.addAction(Actions.sequence(Actions.alpha(0.0f),Actions.fadeIn(1)));
		
		Timer timer = new Timer();
		timer.scheduleTask(new Task(){
		    @Override
		    public void run() {
		        // Do your work
		    	Action action = Actions.sequence(Actions.fadeOut(1), completeAction);
		    	stage.addAction(action);
		    }
		}, 3);
		timer.start();
	}
	
	Action completeAction = new Action(){
	    public boolean act( float delta ) {
	    	gsm.set(new MainMenuState(gsm));
	        return true;
	    }
	};

	@Override
	public void handleInput() {
		
	}

	@Override
	public void update(float delta) {
		
	}

	@Override
	public void render(SpriteBatch sb) {
		sb.begin();
		stage.act();
		stage.draw();
		sb.end();
	}

	@Override
	public void dispose() {
		bfont.dispose();
		bfont = null;
		
		stage.dispose();
	}

}
