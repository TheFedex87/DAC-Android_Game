package it.thefedex87.dac.handler;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

/**
 * Gestore delle risorse immagini. Memorizza e restituisce le texture usate dal gioco
 * @author federico.creti
 *
 */
public class Content {

	/**
	 * HashMap che memorizza le risorse di tipo TextureAtlas
	 */
	private HashMap<String, TextureAtlas> atlases;
	
	public Content() {
		atlases = new HashMap<String, TextureAtlas>();
	}
	
	/**
	 * Restituisce la risorsa atlas specificata
	 * @param key Chiave di ricerca per la risorsa
	 * @return
	 */
	public TextureAtlas getAtlas(String key) {
		return atlases.get(key);
	}
	
	/**
	 * Salva la risorsa specificata
	 * @param key Chiave per salvare la risorsa
	 * @param path Percorso della risorsa da salvare
	 */
	public void setAtlas(String key, String path) {
		atlases.put(key, new TextureAtlas(Gdx.files.internal(path)));
	}

}
