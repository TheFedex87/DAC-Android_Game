package it.thefedex87.dac.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Circle;

public class TransitionBoubble extends Circle {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private float x;
	private float y;
	private float maxRadius;
	private float radius;
	
	private float timer;
	private float maxTimer;
	
	
	private boolean expanding;
	private boolean contracting;
	
	
	
	public TransitionBoubble(float x, float y, float maxRadius) {
		this.x = x;
		this.y = y;
		this.maxRadius = maxRadius;
		expanding = true;
		radius = 0;
		maxTimer = 1.0f;
	}
	
	public void setContracting(float timer) {
		contracting = true;
		expanding = false;
		this.timer = (float)(timer * (0.02 * maxTimer));
	}
	
	public void setTimer(float t) {
		timer = (float)(t * (0.02 * maxTimer));
	}
	
	public void update(float delta) {
		timer += delta;
		if (expanding) {
			if (radius <= maxRadius) radius = timer / (maxTimer / 2) * maxRadius;
		}
		else if(contracting) {
			if (radius >= 0) 
				radius = (1 - (timer / (maxTimer / 2))) * maxRadius;
			else
				contracting = false;
				//radius = maxRadius + (timer / (maxTimer / 2) * maxRadius);

		}
	}
	
	public boolean isFinish() {
		return !expanding && !contracting;
	}
	
	public void render(SpriteBatch sb, ShapeRenderer sr) {
		sr.setColor(1, 1, 1, 1);
		sr.begin(ShapeType.Filled);
		sr.circle(x,  y,  radius);
		sr.end();
	}

	public boolean isExpandingDone() {
		return radius >= maxRadius;
	}
}
