package it.thefedex87.dac.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool.PooledEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

public class ExplosionEffect {
	private ParticleEffectPool bombEffectPool;
	private Array<PooledEffect> effects;
	private ParticleEffect bombEffect;
	private PooledEffect effect;
	
	private boolean effectIsComplete = false;
	
	public ExplosionEffect(float x, float y) {
		
		effects = new Array<PooledEffect>();
		
		bombEffect = new ParticleEffect();
		bombEffect.load(Gdx.files.internal("effects/explosion2.p"), Gdx.files.internal("effects/images"));
		bombEffectPool = new ParticleEffectPool(bombEffect, 1, 2);
		
		// Create effect:
		effect = bombEffectPool.obtain();
		effect.setPosition(x, y);
		effects.add(effect);
	}
	
	public void render(SpriteBatch sb) {
		// Update and draw effects:
		for (int i = effects.size - 1; i >= 0; i--) {
		    PooledEffect effect = effects.get(i);
		    effect.draw(sb, Gdx.graphics.getDeltaTime());
		    if (effect.isComplete()) {
		        effect.free();
		        effects.removeIndex(i);
		        effectIsComplete = true;
		    }
		}
	}
	
	public boolean effectIsComplete() {
		return effectIsComplete;
	}
	
	public void dispose() {
		// Reset all effects:
		for (int i = effects.size - 1; i >= 0; i--)
		    effects.get(i).free();
		effects.clear();
	}
}
