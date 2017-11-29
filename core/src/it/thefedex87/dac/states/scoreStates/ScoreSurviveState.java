package it.thefedex87.dac.states.scoreStates;

import it.thefedex87.dac.states.GSM;
import it.thefedex87.dac.ui.background.Background;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.HAlignment;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

public class ScoreSurviveState extends ScoreState {
	private String score;
	private Vector3 scorePosition;
	
	private BitmapFont bfScore;
	
	

	public ScoreSurviveState(GSM gsm, int score, String[] menu) {
		super(gsm, menu, 1);
		
		thisState = this;
		
		this.score = String.valueOf(score);
		
		
		bfScore = new BitmapFont(Gdx.files.internal("font/fontBoardBlue.fnt"));
		
		float scale = 1.0f;
		while (Gdx.graphics.getWidth() < bfScore.getBounds("Best: " + dacPrefer.getInteger("surviveBestScore")).width) {
			scale -= 0.1f;
			bfScore.setScale(scale);
		}
		
		scorePosition = new Vector3(0, Gdx.graphics.getHeight() + bfScore.getBounds(score + "\nBest: " + dacPrefer.getInteger("surviveBestScore")).height * 3, 0);
	}
	
	public ScoreSurviveState(GSM gsm, int score, String[] menu, Background bg) {
		this(gsm, score, menu);
		super.bg = bg;
	}

	@Override
	public void update(float delta) {
		super.update(delta);
		
		if (scorePosition.y - 20 >= Gdx.graphics.getHeight() / 2 + bfScore.getBounds(score + "\nBest: " + dacPrefer.getInteger("surviveBestScore")).height * 2) scorePosition.y -= 20;
		scorePosition.x = 0;//Gdx.graphics.getWidth() / 2 - bf.getBounds(score + "\nBest: " + dacPrefer.getInteger("surviveBestScore")).width / 2;
	}

	@Override
	public void render(SpriteBatch sb) {
		super.render(sb);
		sb.setProjectionMatrix(cam.combined);
		
		
		/*ShapeRenderer sr = new ShapeRenderer();
		sr.setColor(1,0,0,1);
		sr.begin(ShapeType.Line);
		sr.rect(playAgainBounds.x, playAgainBounds.y, playAgainBounds.width, playAgainBounds.height);
		sr.setColor(0,1,0,1);
		sr.rect(mainMenuBounds.x, mainMenuBounds.y, mainMenuBounds.width, mainMenuBounds.height);
		sr.end();*/
		
		sb.begin();
		bfScore.drawMultiLine(sb, score + "\nBest: " + dacPrefer.getInteger("surviveBestScore"), 
				scorePosition.x, scorePosition.y, Gdx.graphics.getWidth()*1.0f ,HAlignment.CENTER);
		sb.end();
	}

	@Override
	public void dispose() {
		bfScore.dispose();
		bfScore = null;
		
		stage.dispose();
	}

}
