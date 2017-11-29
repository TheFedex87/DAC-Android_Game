package it.thefedex87.dac.states.menuStates;

import it.thefedex87.dac.states.GSM;
import it.thefedex87.dac.states.State;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class TestTableState extends State{
	Stage stage;
	Skin skin;
	Label label;
	Label label2;
	Table table;
	BitmapFont font;
	
	TextButton textButton;

	public TestTableState(GSM gsm) {
		super(gsm);
		
		
		
		font = new BitmapFont(Gdx.files.internal("font/titleB.fnt"));
		float scale = 1.0f;
		while (Gdx.graphics.getWidth() * 0.95 < font.getBounds("DAC").width) {
			scale -= 0.05f;
			font.setScale(scale);
		}
		
		
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		
		LabelStyle ls = new LabelStyle();
		ls.font = font;
		
		label = new Label("DAC", ls);
		label.setFontScale(scale);
		label.pack();
		label.setHeight(font.getBounds("DAB").height);
		
		label2 = new Label("DAB", ls);
		label2.setHeight(font.getBounds("DAB").height);
		
		TextButtonStyle tbs = new TextButtonStyle();
		tbs.font = font;
		
		textButton = new TextButton("PDDD", tbs);
		Gdx.app.log("DAC", "Created textButton");
		
		textButton.addListener(new ClickListener() {
		    @Override
		    public void clicked(InputEvent event, float x, float y) {
		    	Gdx.app.log("DAC", "Pressed textButton");
		    }
		});
		
		
		table = new Table();
		table.top().padTop(-30);
		table.debug();
		table.debugTable();
		table.debugCell();
		table.add(label).row().height(font.getBounds("DAC").height);
		//table.add(label2).row();
		//table.add(textButton).row();
		table.setFillParent(true);
		
		stage.addActor(table);
		stage.addActor(textButton);
		
	}

	@Override
	public void handleInput() {
		
	}

	@Override
	public void update(float delta) {
		
	}

	@Override
	public void render(SpriteBatch sb) {
		sb.setProjectionMatrix(cam.combined);
		
		sb.begin();
		stage.draw();
		sb.end();
		
		
		ShapeRenderer sr = new ShapeRenderer();
		sr.begin(ShapeType.Line);
		sr.rect(label.getX(), label.getY(), label.getTextBounds().width, label.getTextBounds().height);
		sr.setColor(Color.GREEN);
		//sr.rect(label.getX(), label.getY(), font.getBounds("DAC").width, font.getBounds("DAC").height);
		sr.end();
	}

	@Override
	public void dispose() {

	}

}
