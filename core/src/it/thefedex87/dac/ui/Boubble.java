package it.thefedex87.dac.ui;

import it.thefedex87.dac.handler.MySound;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public abstract class Boubble extends com.badlogic.gdx.math.Circle {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ExplosionEffect explosion;
	public boolean renderExplode = false;
	
	protected boolean ballLost = true;
	
	
	protected MySound pop;
	
	
	protected Boubble(float x, float y, float radius) {
		this.x = x;
		this.y = y;
		this.radius = radius;
		
		pop = new MySound("sound/pop.mp3");
	}

	public void render(SpriteBatch sb) {
		if (renderExplode) {
			sb.begin();
			explosion.render(sb);
			sb.end();
			if (explosion.effectIsComplete()) {
				renderExplode = false;
				explosion.dispose();
			}
		}
	}
	
	public boolean isTouchInside(float x, float y) {
		if (radius <= 0) return false;
		
		Vector2 vector = new Vector2(x, y);
		if (this.contains(vector)) {
			ballLost = false;
			explosion = new ExplosionEffect(this.x, this.y);
			pop.play();
			radius = 0;
			renderExplode = true;
			return true;
		}
		else {
			return false;
		}
	}
	
	protected void dispose() {
		if (explosion != null) {
			explosion.dispose();
			explosion = null;
		}
		
		pop.dispose();
		pop = null;
	}
	
	public void pause() {}
	public void resume() {}
}
