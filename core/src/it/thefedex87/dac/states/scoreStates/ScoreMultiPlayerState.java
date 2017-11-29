package it.thefedex87.dac.states.scoreStates;

import it.thefedex87.dac.states.GSM;
import it.thefedex87.dac.ui.background.Background;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;

public class ScoreMultiPlayerState extends ScoreState {
	private BitmapFont scoreFont;
	//Stage stage;

	/*private BitmapFont scoreP1;
	private Vector2 scoreP1Pos;
	//private Rectangle scoreP1Bounds;
	
	//private BitmapFont scoreP2;
	private Vector2 scoreP2Pos;
	
	//private BitmapFont scoreP3;
	private Vector2 scoreP3Pos;
	
	//private BitmapFont scoreP4;
	private Vector2 scoreP4Pos;*/
	
	int[] score;
	
	public ScoreMultiPlayerState(GSM gsm, int score[], String[] menu) {
		super(gsm, menu, score.length);
		
		thisState = this;
		
		Table table;
		Label scoreP1lbl;
		Label scoreP2lbl;
		Label scoreP3lbl;
		Label scoreP4lbl;
		
		this.score = score;
		
		
		scoreFont = new BitmapFont(Gdx.files.internal("font/fontBoardBlue.fnt"));
		float scale = 1.0f;
		while (Gdx.graphics.getWidth() / 2 * 0.9 < scoreFont.getBounds("Yellow:").width) {
			scale -= 0.05f;
			scoreFont.setScale(scale);
		}
		
		
		table = new Table();
		/*table.debug();
		table.debugTable();
		table.debugCell();*/
		table.top().padTop(Gdx.graphics.getHeight() / 10);
		table.setFillParent(true);
		
		
		LabelStyle ls = new LabelStyle();
		ls.font = scoreFont;
		
		scoreP1lbl = new Label("Red:\n" + score[0], ls);
		scoreP1lbl.setAlignment(Align.center);
		table.add(scoreP1lbl).width(Gdx.graphics.getWidth() / 2);
		
		scoreP2lbl = new Label("Blue:\n" + score[1], ls);
		scoreP2lbl.setAlignment(Align.center);
		table.add(scoreP2lbl).width(Gdx.graphics.getWidth() / 2).row();
		
		if (score.length >= 3) {
			scoreP3lbl = new Label("Green:\n" + score[2], ls);
			scoreP3lbl.setAlignment(Align.center);
			table.add(scoreP3lbl).width(Gdx.graphics.getWidth() / 2);
		}
		if (score.length == 4) {
			scoreP4lbl = new Label("Yellow:\n" + score[3], ls);
			scoreP4lbl.setAlignment(Align.center);
			table.add(scoreP4lbl).width(Gdx.graphics.getWidth() / 2).row();
		}
		
		
		//stage = new Stage();
		super.stage.addActor(table);
		
		
		/*String referenceForScale = "";
		referenceForScale = "Red: " + String.valueOf(score[0]);
		
		scoreP1 = new BitmapFont(Gdx.files.internal("font/fontBoardBlue.fnt"));
		if (scoreP1.getBounds(referenceForScale).width < 
				scoreP1.getBounds("Blue: " + String.valueOf(score[1])).width) {
			referenceForScale = "Blue: " + String.valueOf(score[1]);
		}
		if (score.length >= 3)
			if (scoreP1.getBounds(referenceForScale).width < 
					scoreP1.getBounds("Green: " + String.valueOf(score[2])).width) {
				referenceForScale = "Green: " + String.valueOf(score[2]);
			}
		if (score.length == 4)
			if (scoreP1.getBounds(referenceForScale).width < 
					scoreP1.getBounds("Yellow: " + String.valueOf(score[3])).width) {
				referenceForScale = "Yellow: " + String.valueOf(score[3]);
			}
		
		
		
		
		float scale = 1.0f;
		scoreP1.setScale(scale);
		while (Gdx.graphics.getWidth() / 2 < scoreP1.getBounds(referenceForScale).width) {
			scale -= 0.1f;
			scoreP1.setScale(scale);
		}
		scoreP1Pos = new Vector2(Gdx.graphics.getWidth() / 4 - scoreP1.getBounds("Red:").width / 2, 
				Gdx.graphics.getHeight() * 9 / 10);
		
		scoreP2Pos = new Vector2(Gdx.graphics.getWidth() / 4 * 3 - scoreP1.getBounds("Blue:").width / 2, 
				Gdx.graphics.getHeight() * 9 / 10);
		
		
		if (score.length >= 3)
			scoreP3Pos = new Vector2(Gdx.graphics.getWidth() / 4 - scoreP1.getBounds("Green:").width / 2, 
					Gdx.graphics.getHeight() * 9 / 10 - scoreP1.getBounds("Red").height * 3);
		
		
		if (score.length == 4) 
			scoreP4Pos = new Vector2(Gdx.graphics.getWidth() / 4 * 3 - scoreP1.getBounds("Yellow:").width / 2, 
					Gdx.graphics.getHeight() * 9 / 10 - scoreP1.getBounds("Red").height * 3);*/
		
		
		
	}
	
	public ScoreMultiPlayerState(GSM gsm, int score[], String[] menu, Background bg) {
		this(gsm, score, menu);
		super.bg = bg;
	}

	@Override
	public void update(float delta) {
		super.update(delta);
		
		
	}

	@Override
	public void render(SpriteBatch sb) {
		super.render(sb);
		
		
		/*ShapeRenderer sr = new ShapeRenderer();
		sr.setColor(1,0,0,1);
		sr.begin(ShapeType.Line);
		sr.rect(scoreP1Bounds.x, scoreP1Bounds.y, scoreP1Bounds.width, scoreP1Bounds.height);
		sr.end();*/
		
		sb.begin();
		//scoreP1.draw(sb, "Red: " + String.valueOf(score[0]), scoreP1Pos.x, scoreP1Pos.y);
		//scoreP1.draw(sb, "Blue: " + String.valueOf(score[1]), scoreP2Pos.x, scoreP2Pos.y);
		
		/*scoreP1.drawMultiLine(sb, "Red:\n" + String.valueOf(score[0]), scoreP1Pos.x, scoreP1Pos.y, 
				scoreP1.getBounds("Red:").width, HAlignment.CENTER);
		scoreP1.drawMultiLine(sb, "Blue:\n" + String.valueOf(score[1]), scoreP2Pos.x, scoreP2Pos.y, 
				scoreP1.getBounds("Blue:").width, HAlignment.CENTER);
		if (score.length >= 3)
			scoreP1.drawMultiLine(sb, "Green:\n" + String.valueOf(score[2]), scoreP3Pos.x, scoreP3Pos.y, 
					scoreP1.getBounds("Green:").width, HAlignment.CENTER);
		if (score.length == 4)
			scoreP1.drawMultiLine(sb, "Yellow:\n" + String.valueOf(score[3]), scoreP4Pos.x, scoreP4Pos.y, 
					scoreP1.getBounds("Yellow:").width, HAlignment.CENTER);*/
		//stage.draw();
		
		sb.end();

	}

	@Override
	public void dispose() {
		scoreFont.dispose();
		scoreFont = null;
		
		stage.dispose();
	}

}
