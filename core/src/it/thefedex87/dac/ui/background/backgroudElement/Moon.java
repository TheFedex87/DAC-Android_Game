package it.thefedex87.dac.ui.background.backgroudElement;

import it.thefedex87.dac.DAC;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Moon extends BackgroundElementGeneral {
	
	private float elementStart;
	private int maxMoonTime = 30;
	private Vector2 moonPositionStart;
	private Vector2 moonPositionEnd;
	
	private float timer;

	public Moon(float elementStart) {
		timer = 0;
		this.elementStart = elementStart;
		transitionFinish = false;
	}
	
	
	@Override
	public void update(float dt) {
		//Moon
		timer += dt;
		
		if (timer >= elementStart) {
			if (texture == null) {
				texture = DAC.res.getAtlas("pack").findRegion("moon");;
				
				height = Gdx.graphics.getHeight() / 3;
				width = height;
				
				//moonPosition = new Vector2(-texture.getWidth() - 10, Gdx.graphics.getHeight() + 10);
				moonPositionStart = new Vector2(-texture.getRegionWidth() - 10, Gdx.graphics.getHeight() + 10);
				moonPositionEnd = new Vector2(Gdx.graphics.getWidth() + 10, -texture.getRegionHeight() - 10);
				
				this.x = -texture.getRegionWidth() - 10;
				this.y = Gdx.graphics.getHeight() + 10;
			}
			if (timer <= maxMoonTime + elementStart) {
				x = moonPositionStart.x + ((timer - elementStart) / (maxMoonTime) * (moonPositionEnd.x - moonPositionStart.x));
				y = moonPositionStart.y + ((timer - elementStart) / (maxMoonTime) * (moonPositionEnd.y - moonPositionStart.y));
			}
			else {
				transitionFinish = true;
			}
		}
		///////
	}
	
	@Override
	public void render(SpriteBatch sb) {
		if (texture != null)
			sb.draw(texture, x, y);
	}
	
	@Override
	public boolean isTransitionFinish() {
		return transitionFinish;
	}
	
	@Override
	public void dispose() {
		moonPositionStart = null;
		moonPositionEnd = null;
		
		texture = null;
	}

}
