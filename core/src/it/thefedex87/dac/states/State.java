package it.thefedex87.dac.states;


import it.thefedex87.dac.DAC;
import it.thefedex87.dac.ui.ExitDialog;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class State implements Cloneable {
	protected GSM gsm;
	public OrthographicCamera cam;
	
	protected Preferences dacPrefer = Gdx.app.getPreferences("DAC_PREFERENCES");
	
	protected ExitDialog ed;
	
	public State(GSM gsm) {
		this.gsm = gsm;
		cam = new OrthographicCamera(DAC.width, DAC.height);
		cam.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		//cam.setToOrtho(false, DAC.width, DAC.height);
		
	}
	
	public abstract void handleInput();
	public abstract void update(float delta);
	public abstract void render(SpriteBatch sb);
	public abstract void dispose();
}
