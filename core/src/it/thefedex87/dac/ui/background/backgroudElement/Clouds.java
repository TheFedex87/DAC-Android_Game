package it.thefedex87.dac.ui.background.backgroudElement;

import it.thefedex87.dac.DAC;
import it.thefedex87.dac.ui.Box;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;

public class Clouds extends BackgroundElementGeneral {
	private TextureRegion cloudTexture;
	private Array<Box> clouds;
	
	private float delay; //ritardo prima di attivare lo spostamento delle nuvole
	
	public Clouds() {
		clouds = new Array<Box>(8);
		cloudTexture = DAC.res.getAtlas("pack").findRegion("cloud2");
		
		//Generate 8 random clouds
		for (int i = 0; i < 8; i++) {
			float width = MathUtils.random(50, Gdx.graphics.getWidth() * 2 / 3);
			//float height = (int)(width * 0.67);
			float height = (int)(width * (MathUtils.random(40, 75) / 100));
			clouds.add(new Box(MathUtils.random(-50, Gdx.graphics.getWidth() - 50), MathUtils.random(-50, Gdx.graphics.getHeight()) - 50,
					width, height));
		}
	}
	
	@Override
	public void update(float dt) {
		delay += dt;
		if (delay > 7)
		{
			for (int i = 0; i < clouds.size; i++) {
				clouds.get(i).setY(2);
				if (clouds.get(i).getY() < -clouds.get(i).getHeight() - 10) {
					float width = MathUtils.random(50, Gdx.graphics.getWidth() * 2 / 3);
					float height = (int)(width * 0.67);
					clouds.get(i).setBox((float)(MathUtils.random(-50, Gdx.graphics.getWidth() - 50)), Gdx.graphics.getHeight() + 20,
							width, height);
				}
			}
		}	
	}
	
	@Override
	public void render(SpriteBatch sb) {
		for (int i = 0; i < clouds.size; i++) {
			sb.draw(cloudTexture, clouds.get(i).getX(), clouds.get(i).getY(), clouds.get(i).getWidth(), clouds.get(i).getHeight());
		}
	}
	
	@Override
	public void dispose() {
		cloudTexture = null;
		clouds.clear();
		clouds = null;
		
	}
}
