package it.thefedex87.dac;

import it.thefedex87.dac.handler.Content;
import it.thefedex87.dac.states.GSM;
import it.thefedex87.dac.states.SplashState;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Classe principale del gioco che carica tutti i componenti e le classi che compongono il gioco.
 * @author federico.creti
 *
 */
public class DAC extends ApplicationAdapter {
	/**
	 * SpriteBatch principale su cui verrà disegnato tutto
	 */
	SpriteBatch batch;
	
	/**
	 * Dichiarazione GameStateManager
	 */
	GSM gsm; //GameStateManager
	
	/**
	 * Larghezza della schermata di gioco
	 */
	public static final float width = 450;
	/**
	 * Altezza della schermata di gioco
	 */
	public static final float height = 800;
	
	/**
	 * Gestore di risorse
	 */
	public static Content res; 
	
	
	/**
	 * Metodo principale chiamato in fase di creazione del gioco
	 */
	@Override
	public void create () {
		Gdx.input.setCatchBackKey(true);
		
		res = new Content();
		res.setAtlas("pack", "img/packed/pack.pack");
		
		batch = new SpriteBatch();
		
		//Gdx.gl.glClearColor(0.5f, 0.8f, 0.9f, 1);
		Gdx.gl.glClearColor(0f, 0f, 0f, 1);
		
		gsm = new GSM();
		gsm.push(new SplashState(gsm));
		//gsm.push(new CameraTestState(gsm));
	}

	/**
	 * Metodo che disegna e aggiorna la grafica
	 */
	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		gsm.update(Gdx.graphics.getDeltaTime());
		gsm.render(batch);
	}
	
	
	 @Override
	 public void resume() {
	 }
}
