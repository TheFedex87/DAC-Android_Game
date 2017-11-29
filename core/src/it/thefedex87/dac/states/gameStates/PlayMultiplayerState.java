package it.thefedex87.dac.states.gameStates;

import it.thefedex87.dac.states.GSM;
import it.thefedex87.dac.states.TransitionState;
import it.thefedex87.dac.states.scoreStates.ScoreMultiPlayerState;
import it.thefedex87.dac.ui.Boubble;
import it.thefedex87.dac.ui.BoubbleStandard;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

/**
 * Classe che gestisce il gioco in modalità multiplayer
 * @author federico.creti
 *
 */
public class PlayMultiplayerState extends PlayState {
	/**
	 * Timer che allo scadere genera una Boubble
	 */
	private Timer timerBoubbleGenerator;
	/**
	 * Indica ogni quanto viene richiamato il timerBoubbleGenerator
	 * @see PlayMultiplayerState#timerBoubbleGenerator
	 */
	private float timerDelay = 0.5f;//1.0f;
	/**
	 * Intervallo di sottrazione di timerDelay. Allo scadere di delayTimer, increaseBoubbleTimerGenerator viene sottratto di questo valore
	 * @see PlayMultiplayerState#timerDelay 
	 * @see PlayMultiplayerState#timerBoubbleGenerator
	 * @see PlayMultiplayerState#increaseBoubbleTimerGenerator
	 */
	private final float decreaseTimerValue = 0.05f;
	/**
	 * Valore minimo di timerDelay
	 * @see PlayMultiplayerState#timerDelay
	 */
	private final float minBubbleGenerationTime = 0.1f;
	
	/**
	 * Timer che allo scadere aumenta la velocità di generazione delle bolle
	 * @see PlayMultiplayerState#timerDelay
	 */
	private Timer increaseBoubbleTimerGenerator;
	
	/**
	 * Valore timer riduzione grandezza Boubble
	 * @see BoubbleStandard
	 */
	private float delayBubbleTimer = 0.005f;
	
	/**
	 * Punteggio giocatore 1
	 */
	private int pointsP1 = 0;
	/**
	 * Punteggio giocatore 2
	 */
	private int pointsP2 = 0;
	/**
	 * Punteggio giocatore 3
	 */
	private int pointsP3 = 0;
	/**
	 * Punteggio giocatore 4
	 */
	private int pointsP4 = 0;
	
	/**
	 * Numero di giocatori
	 */
	private int nPlayer = 0;
	
	/**
	 * Tiene traccia del tempo trascorso dall'inizio del gioco
	 */
	private float timeFromStartGame;
	
	/**
	 * Tempo durata del gioco
	 */
	private final int endGameTime = 90;
	
	/**
	 * 
	 * @param gsm GameStateManager
	 * @param nPlayer Numero dei giocatori
	 */
	public PlayMultiplayerState(GSM gsm, int nPlayer) {
		super(gsm, 80);
		
		this.nPlayer = nPlayer;
		
		timerBoubbleGenerator = new Timer();
		timerBoubbleGenerator.scheduleTask(TimerSchedule, 5, timerDelay);	
		timerBoubbleGenerator.start();
		
		
		increaseBoubbleTimerGenerator = new Timer();
		increaseBoubbleTimerGenerator.scheduleTask(new Task() {
			@Override
			public void run(){
				if (timerDelay - decreaseTimerValue >= minBubbleGenerationTime) {
					timerDelay -= decreaseTimerValue;
					//timerDelay = 0.2f;
					timerBoubbleGenerator.stop();
					timerBoubbleGenerator.clear();
					timerBoubbleGenerator.scheduleTask(TimerSchedule, 0, timerDelay);
					timerBoubbleGenerator.start();
					//Gdx.app.log("DAC", "delayTimer: Decrease generator boubble timer");
				}
			}
		}, 5, 5);
		increaseBoubbleTimerGenerator.start();
	}
	
	
	/**
	 * Metodo richiamato da timerBoubbleGenerator
	 * @see PlayMultiplayerState#timerBoubbleGenerator
	 */
	private Task TimerSchedule = new Task() {
		@Override
		public void run(){
			//Gdx.app.log("DAC", "timer: Generating boubble timer");
			setShowGetReady(false);
			if (gameOver) return;
			float x = MathUtils.random(10, Gdx.graphics.getWidth() - 10);
			float y= MathUtils.random(10, Gdx.graphics.getHeight() - 10);
			boubbleList.add(new BoubbleStandard(x, y, Gdx.graphics.getWidth() / 6, delayBubbleTimer, nPlayer));
		}
	};

	/**
	 * @see PlayState#update(float)
	 */
	@Override
	public void update(float delta) {
		super.update(delta);
		
		if (!inPause) {
			
			timeFromStartGame += delta;
			if (timeFromStartGame >= endGameTime) {
				timerBoubbleGenerator.stop();
				timerBoubbleGenerator.clear();
				explodeAll();
				gameOver=true;
			}
			
			handleInput();
			
			checkCircle();
			
			if (gameOver && boubbleList.size() == 0) {
				closeMultiplayerPlayState();
			}
		}
		
		if (inPause && ed.isShowed() == false) resume();
	}
	
	/**
	 * @see PlayState#handleInput()
	 */
	@Override
	public void handleInput() {
		if (Gdx.input.justTouched()) {
			for (int j = 0; j < MAX_TOUCH; j++) {
				if (Gdx.input.isTouched(j)) {
					Vector3 touchPoint = new Vector3(Gdx.input.getX(j), Gdx.input.getY(j), 0);
					cam.unproject(touchPoint);
					for (int i = boubbleList.size() - 1; i >= 0; i--) {
						if (((BoubbleStandard)boubbleList.get(i)).getRadius() > 0) {
							boolean circleTouched = ((BoubbleStandard)boubbleList.get(i)).isTouchInside(touchPoint.x, touchPoint.y);
							if (circleTouched){
								if (((BoubbleStandard)boubbleList.get(i)).getColor().equals(Color.RED))
									pointsP1++;
								else if (((BoubbleStandard)boubbleList.get(i)).getColor().equals(Color.BLUE)) 
									pointsP2++;
								else if (((BoubbleStandard)boubbleList.get(i)).getColor().equals(Color.GREEN)) 
									pointsP3++;
								else if (((BoubbleStandard)boubbleList.get(i)).getColor().equals(Color.YELLOW)) 
									pointsP4++;
								break;
							}
						}
					}
				}
			}
		}
		if (Gdx.input.isKeyPressed(Keys.BACK) || Gdx.input.isButtonPressed(Keys.A)) {
			pause();
			
			ed.show(stage);
		}
	}
	
	/**
	 * Chiude la schermata di gioco MultiPlayer
	 */
	private void closeMultiplayerPlayState() {
		String[] menu = {"Play Again", "Main Menu"};
		int[] points = new int[nPlayer];

		points[0] = pointsP1;
		points[1] = pointsP2;
		if (nPlayer >= 3) points[2] = pointsP3;
		if (nPlayer == 4) points[3] = pointsP4;
		
		//gsm.set(new ScoreMultiPlayerState(gsm, points, menu, bg));
		gsm.set(new TransitionState(gsm, this, new ScoreMultiPlayerState(gsm, points, menu, bg)));
	}

	/**
	 * @see PlayState#render(SpriteBatch)
	 */
	@Override
	public void render(SpriteBatch sb) {
		super.render(sb);
	}
	
	/**
	 * Controlla la Boubble per vedere se è scomparsa ed è necessario rimuoverla
	 */
	private void checkCircle() {
		for (int i = boubbleList.size() - 1; i >= 0; i--) {
			//Gdx.app.log("DAC", "checkCircle");
			if (((BoubbleStandard)boubbleList.get(i)).getRadius() <= 0 && ((BoubbleStandard)boubbleList.get(i)).renderExplode == false) {
				((BoubbleStandard)boubbleList.get(i)).dispose();
				boubbleList.remove(i);
			}
		}
	}
	
	/**
	 * Mette in pausa il gioco. Richiamato quando si preme il tasto Back del telefono
	 */
	private void pause() {
		increaseBoubbleTimerGenerator.stop();
		timerBoubbleGenerator.stop();
		for (int i = 0; i <= boubbleList.size() - 1; i++) {
			((Boubble)boubbleList.get(i)).pause();
		}
		inPause = true;
	}
	
	/**
	 * Rivvia il gioco che era in modalitò pausa
	 */
	private void resume() {
		increaseBoubbleTimerGenerator.start();
		timerBoubbleGenerator.start();
		for (int i = 0; i <= boubbleList.size() - 1; i++) {
			((Boubble)boubbleList.get(i)).resume();
		}
		inPause = false;
	}
}
