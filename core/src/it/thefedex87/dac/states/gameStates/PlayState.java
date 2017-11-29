package it.thefedex87.dac.states.gameStates;


import it.thefedex87.dac.states.GSM;
import it.thefedex87.dac.states.State;
import it.thefedex87.dac.ui.Boubble;
import it.thefedex87.dac.ui.ExitDialog;
import it.thefedex87.dac.ui.background.SkyBackground;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * Classe base astratta per i vari stati di gioco. Ogni stato di gioco eredita da questa classe
 * @author federico.creti
 *
 */
public abstract class PlayState extends State {
	/*static enum gameType {
		SURVIVE,
		MULTIPLAYER,
		LEVELS
	}*/
	
	/**
	 * ArrayList che memorizza tutte le istanze di Boubble
	 */
	protected ArrayList<Boubble> boubbleList;
	
	/**
	 * Istanza della classe SkyBackGround usata per disegnare lo sfondo di gioco
	 */
	protected SkyBackground bg;
	
	/**
	 * Numero massimo di tocchi per il multitouch
	 */
	protected final short MAX_TOUCH = 4;
	
	/**
	 * Memorizza lo stato della partita, se settato a True il gioco è finito
	 */
	protected boolean gameOver = false;
	
	
	/**
	 * BitmaoFont GetReady
	 */
	private BitmapFont bfGetReady;
	/**
	 * String GetReady
	 */
	private final String getReady = "Get Ready...";
	/**
	 * Stato visualizzazione messaggio GetReady
	 */
	private boolean showGetReady = true;
	
	/**
	 * Istanza di stage per la visualizzazione della ExitDialog
	 */
	protected Stage stage = new Stage();
	
	/**
	 * Settata a True se il gioco è in pausa
	 */
	protected boolean inPause = false;
	
	
	protected PlayState(GSM gsm, float nightIn) {
		super(gsm);
		boubbleList = new ArrayList<Boubble>();
		
		bg = new SkyBackground(nightIn);
		//bg.setWrap(TextureWrap.Repeat, TextureWrap.Repeat);
		
		bfGetReady = new BitmapFont(Gdx.files.internal("font/getReady.fnt"));
		
		
		float scale = 1.0f;
		while (Gdx.graphics.getWidth() < bfGetReady.getBounds(getReady).width) {
			scale -= 0.1f;
			bfGetReady.setScale(scale);
		}
		
		ed = new ExitDialog();
		Gdx.input.setInputProcessor(stage);
	}
	
	/**
	 * Gestore di input. Controlla se è attualmente stato premuto qualcosa
	 */
	@Override
	public abstract void handleInput();

	/**
	 * Update della logica del gioco
	 * @param delta Tempo trascorso dall'ultimo update effettuato
	 */
	@Override
	public void update(float delta) {
		if (!inPause) {
			if (!gameOver) {
				bg.update(delta);
			}
		}
	}

	/**
	 * Disegno a video dei componenti grafici
	 * @param sb SpriteBatch sul quale viene disegnato
	 */
	@Override
	public void render(SpriteBatch sb) {
		
		sb.setProjectionMatrix(cam.combined);
		
		bg.render(sb);
		
		
		
		if (showGetReady) {
			sb.begin();
			bfGetReady.draw(sb, getReady, 
					Gdx.graphics.getWidth() / 2 - bfGetReady.getBounds(getReady).width / 2, 
					Gdx.graphics.getHeight() / 2 + bfGetReady.getBounds(getReady).height / 2);
			sb.end();
		}
		
		for (int i = 0; i <= boubbleList.size() - 1; i++) {
			((Boubble)boubbleList.get(i)).render(sb);
		}
		
		sb.begin();
		stage.act();
		stage.draw();
		sb.end();
	}

	/**
	 * Dispose dei componenti che vengono istanziati all'interno della classe
	 */
	@Override
	public void dispose() {
		//bg.dispose();
		boubbleList.clear();
		boubbleList = null;
		
		stage.dispose();
	}
	
	/**
	 * Metodo richiamato per esplodere tutte le istanze della classe Boubble correntmente a video
	 */
	protected void explodeAll() {
		for (int i = 0; i <= boubbleList.size() - 1; i++) {
			((Boubble)boubbleList.get(i)).isTouchInside(((Boubble)boubbleList.get(i)).x, ((Boubble)boubbleList.get(i)).y);
		}
	
	}
	
	/**
	 * Setta la visualizzazione del messaggio "Get Ready" presente a inizio livello
	 * @param value True per visualizzare il messaggio "GetReady", False per nasconderlo
	 */
	protected void setShowGetReady(boolean value) {
		showGetReady = value;
	}
}
