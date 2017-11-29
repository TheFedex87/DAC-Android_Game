package it.thefedex87.dac.states.gameStates;

import it.thefedex87.dac.states.GSM;
import it.thefedex87.dac.states.TransitionState;
import it.thefedex87.dac.states.scoreStates.ScoreSurviveState;
import it.thefedex87.dac.ui.Boubble;
import it.thefedex87.dac.ui.BoubbleStandard;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

/**
 * Classe che gestisce il gioco in modalità single player
 * @author federico.creti
 *
 */
public class PlaySurviveState extends PlayState {
	
	/**
	 * Timer che allo scadere genera una Boubble
	 */
	private Timer timerBoubbleGenerator;
	/**
	 * Indica ogni quanto viene richiamato il timerBoubbleGenerator
	 * @see PlaySurviveState#timerBoubbleGenerator
	 */
	private float timerDelay = 0.5f;//1.0f;
	/**
	 * Intervallo di sottrazione di timerDelay. Allo scadere di delayTimer, increaseBoubbleTimerGenerator viene sottratto di questo valore
	 * @see PlaySurviveState#timerDelay 
	 * @see PlaySurviveState#timerBoubbleGenerator
	 * @see PlaySurviveState#increaseBoubbleTimerGenerator
	 */
	private final float decreaseTimerValue = 0.05f;
	/**
	 * Valore minimo di timerDelay
	 * @see PlaySurviveState#timerDelay
	 */
	private final float minBubbleGenerationTime = 0.2f;
	
	
	/**
	 * Timer che allo scadere aumenta la velocità di generazione delle bolle
	 * @see PlaySurviveState#timerDelay
	 */
	private Timer increaseBoubbleTimerGenerator;
	
	/**
	 * Valore timer riduzione grandezza Boubble
	 * @see BoubbleStandard
	 */
	private float delayBubbleTimer = 0.005f;
	
	/**
	 * Numero massimo di Boubble perse prima del game over
	 */
	private final int maxBoubbleLost = 5;
	/**
	 * Numero di Boubble perse in fase di gioco
	 */
	private int boubbleLost = 0;
	
	/**
	 * Punteggio, numero di Boubble esplose
	 */
	private int points; //tiene traccia dei palloni esplosi
	
	/**
	 * BitmapFont del punteggio corrente per la visualizzazione a video
	 */
	private BitmapFont pointsFnt;
	/**
	 * Label del punteggio corrente per la visualizzazione a video
	 */
	Label lblPoints;
	
	/**
	 * 
	 * @param gsm GameStateManager
	 */
	public PlaySurviveState(GSM gsm) {
		super(gsm, 150);
		
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
		}, 10, 10);
		increaseBoubbleTimerGenerator.start();
		
		
		pointsFnt = new BitmapFont(Gdx.files.internal("font/fontBoardYellow.fnt"));
		float scale = 1.0f;
		while (Gdx.graphics.getWidth() * 0.1 < pointsFnt.getBounds("0").width) {
			scale -= 0.05f;
			pointsFnt.setScale(scale);
		}
		LabelStyle ls = new LabelStyle();
		ls.font = pointsFnt;
		lblPoints = new Label("0", ls);
		lblPoints.setPosition(Gdx.graphics.getWidth()/2 - pointsFnt.getBounds(lblPoints.getText()).width / 2,
				Gdx.graphics.getHeight()/2 - pointsFnt.getBounds(lblPoints.getText()).height / 2);
		
	}
	
	/**
	 * Metodo richiamato da timerBoubbleGenerator
	 * @see PlaySurviveState#timerBoubbleGenerator
	 */
	private Task TimerSchedule = new Task() {
		@Override
		public void run(){
			//Gdx.app.log("DAC", "timer: Generating boubble timer");
			setShowGetReady(false);
			if (gameOver) return;
			float x = MathUtils.random(10, Gdx.graphics.getWidth() - 10);
			float y= MathUtils.random(10, Gdx.graphics.getHeight() - 10);
			boubbleList.add(new BoubbleStandard(x, y, Gdx.graphics.getWidth() / 6, delayBubbleTimer, 1));
			stage.addActor(lblPoints);
		}
	};

	/**
	 * @see PlayState#update(float)
	 */
	public void update(float delta) {
		super.update(delta);
		
		if (!inPause) {
			handleInput();
			
			
			checkCircle();
			
			if (gameOver && boubbleList.size() == 0) {
				closeSurvivePlayState();
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
								points++;
								lblPoints.setText(String.valueOf(points));
								lblPoints.setPosition(Gdx.graphics.getWidth()/2 - pointsFnt.getBounds(lblPoints.getText()).width / 2,
										Gdx.graphics.getHeight()/2 - pointsFnt.getBounds(lblPoints.getText()).height / 2);
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
	 * Chiude la schermata di gioco SinglePlayer
	 */
	private void closeSurvivePlayState() {
		String[] menu = {"Play Again", "Main Menu"};
		//gsm.set(new ScoreSurviveState(gsm, points, menu, bg));
		gsm.set(new TransitionState(gsm, this, new ScoreSurviveState(gsm, points, menu, bg)));
	}
	
	/**
	 * @see PlayState#render(SpriteBatch)
	 */
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
				if (((BoubbleStandard)boubbleList.get(i)).getRadius() <= 0 && ((BoubbleStandard)boubbleList.get(i)).getBallLost()) {
					boubbleLost++;
					if (boubbleLost >= maxBoubbleLost && !gameOver) {
						timerBoubbleGenerator.stop();
						timerBoubbleGenerator.clear();
						explodeAll();
						gameOver=true;
						((BoubbleStandard)boubbleList.get(i)).dispose();
						boubbleList.remove(i);
						if (dacPrefer.getInteger("surviveBestScore") < points) {
							dacPrefer.putInteger("surviveBestScore", points);
							dacPrefer.flush();
						}
						break;
					}
				}
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
