package it.thefedex87.dac.ui.background.backgroudElement;

import it.thefedex87.dac.DAC;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Palaces extends BackgroundElementGeneral {
	private int y;
	private int width;
	private int height;
	
	private float delay; //ritardo prima di attivare lo spostamento dei palazzi
	
	
	public Palaces() {
		texture = DAC.res.getAtlas("pack").findRegion("palaces");
		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight() * 2;
		y = 0;
	}
	
	@Override
	public void update(float dt) {
		delay += dt;
		
		if (delay > 3.5)
			y -= 1;
		
		if (y + height < -10)
			transitionFinish = true;
	}
	
	@Override
	public void render(SpriteBatch sb) {
		if (texture != null)
			sb.draw(texture, 0, y, width, height);
	}
	
	@Override
	public boolean isTransitionFinish() {
		return transitionFinish;
	}
	
	@Override
	public void dispose() {
		texture = null;
	}
}
