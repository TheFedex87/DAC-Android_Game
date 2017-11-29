package it.thefedex87.dac.ui.background.backgroudElement;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public abstract class BackgroundElementGeneral {

	protected float x;
	protected float y;
	protected float width;
	protected float height;
	protected boolean transitionFinish;
	
	protected TextureRegion texture;
	
	public void update(float delta){}
	public void render(SpriteBatch sb){}
	public boolean isTransitionFinish() {return false;}
	public void dispose() {}
}
