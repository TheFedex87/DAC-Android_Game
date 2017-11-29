package it.thefedex87.dac.handler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

/**
 * Gestione del suono
 * @author federico.creti
 *
 */
public class MySound {
	/**
	 * Istanza del suono corrente
	 */
	private Sound sound;
	
	/**
	 * 
	 * @param path Percorso dove si trova il suono da impostare
	 */
	public MySound(String path) {
		sound = Gdx.audio.newSound(Gdx.files.internal(path));
	}
	
	/**
	 * Riproduce il suono impostato
	 */
	public void play() {
		if (Gdx.app.getPreferences("DAC_PREFERENCES").getBoolean("excludeSound")) return;
		sound.play();
	}
	
	/**
	 * Dispose del suono
	 */
	public void dispose() {
		sound.dispose();
		sound = null;
	}
}
