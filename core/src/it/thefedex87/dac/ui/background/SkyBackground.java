package it.thefedex87.dac.ui.background;


import it.thefedex87.dac.ui.background.backgroudElement.BackgroundElementGeneral;
import it.thefedex87.dac.ui.background.backgroudElement.Clouds;
import it.thefedex87.dac.ui.background.backgroudElement.Moon;
import it.thefedex87.dac.ui.background.backgroudElement.Palaces;
import it.thefedex87.dac.ui.background.backgroudElement.Plane;
import it.thefedex87.dac.ui.background.backgroudElement.Stars;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

/**
 * @author federico.creti
 *
 */
/**
 * @author federico.creti
 *
 */
/**
 * @author federico.creti
 *
 */
/**
 * @author federico.creti
 *
 */
public class SkyBackground extends Background {
	private ShapeRenderer sr;
	
	
	private Color startSky;
	private Color endSky;
	private float startSkyRDay; 
	private float startSkyGDay; 
	private float startSkyBDay;
	private float endSkyRDay; 
	private float endSkyGDay; 
	private float endSkyBDay;
	private float startSkyRNight;
	private float startSkyGNight;
	private float startSkyBNight;
	private float endSkyRNight;
	private float endSkyGNight;
	private float endSkyBNight;
	private float startSkyRAct;
	private float startSkyGAct;
	private float startSkyBAct;
	private float endSkyRAct;
	private float endSkyGAct;
	private float endSkyBAct;
	
	private float timer; //tiene traccia del tempo passato per far calare la notte
	private final float nightIn; //indica il tempo massimo in s entro quanto arriva notte
	
	/**
	 * BackgorundElementManager: gestisce i vari elementi che vengono visualizzato sul background.
	 */
	private List<BackgroundElementGeneral> BEM; 
	
	
	/**
	 * Classe che gestisce lo sfondo "Cielo", dove il gioco si sviluppa e dove vengono disegnati i vari elementi di background
	 * @param nightIn: indica dopo quanto tempo il cielo si scurisce simulando un effetto di transizione da giorno a notte
	 */
	public SkyBackground(float nightIn) {
		sr = new ShapeRenderer();
		sr.setColor(0.5f, 0.5f, 0.5f, 1);
		
		startSkyRDay = 0.87f;
		startSkyGDay = 0.94f;
		startSkyBDay = 1.0f;
		startSkyRNight = 0.0078f;
		startSkyGNight = 0.0078f;
		startSkyBNight = 0.2f;
		startSkyRAct = startSkyRDay;
		startSkyGAct = startSkyGDay;
		startSkyBAct = startSkyBDay;
		
		startSky = new Color(startSkyRAct, startSkyGAct, startSkyBAct, 1.0f);
		
		endSkyRDay = 0.37f;
		endSkyGDay = 0.54f;
		endSkyBDay = 0.70f;
		endSkyRNight = 0.0f;
		endSkyGNight = 0.0f;
		endSkyBNight = 0.05f;
		endSkyRAct = endSkyRDay;
		endSkyGAct = endSkyGDay;
		endSkyBAct = endSkyBDay;
		
		endSky = new Color(endSkyRAct, endSkyGAct, endSkyBAct, 1.0f);
		
		this.nightIn = nightIn;
		
		//stars = new ArrayList(100);
		
		BEM = new ArrayList<BackgroundElementGeneral>();
		
		BEM.add(new Moon(nightIn - 10));
		BEM.add(new Stars(nightIn - 20));
		BEM.add(new Clouds());
		BEM.add(new Palaces());
		BEM.add(new Plane((int)nightIn / 2));
	}
	
	public void update(float delta) {
		timer += delta;
		if (timer <= nightIn) {
			startSkyRAct = startSkyRDay - ((startSkyRDay - startSkyRNight) / nightIn * timer);
			startSkyGAct = startSkyGDay - ((startSkyGDay - startSkyGNight) / nightIn * timer);
			startSkyBAct = startSkyBDay - ((startSkyBDay - startSkyBNight) / nightIn * timer);
			
			endSkyRAct = endSkyRDay - ((endSkyRDay - endSkyRNight) / nightIn * timer);
			endSkyGAct = endSkyGDay - ((endSkyGDay - endSkyGNight) / nightIn * timer);
			endSkyBAct = endSkyBDay - ((endSkyBDay - endSkyBNight) / nightIn * timer);
		}

		for (int i = 0; i < BEM.size(); i++) {
			if (((BackgroundElementGeneral)(BEM.get(i))).isTransitionFinish()) {
				
				if (BEM.get(i) instanceof Palaces)
					Gdx.app.log("DAC", "Palaces destroied");
				
				((BackgroundElementGeneral)(BEM.get(i))).dispose();
				BEM.remove(i);
			}
			else {
				((BackgroundElementGeneral)(BEM.get(i))).update(delta);
			}
		}
	}
	
	@Override
	public void render(SpriteBatch sb) {
		
		startSky.r = startSkyRAct;
		startSky.g = startSkyGAct;
		startSky.b = startSkyBAct;
		
		endSky.r = endSkyRAct;
		endSky.g = endSkyGAct;
		endSky.b = endSkyBAct;
		

		sr.begin(ShapeType.Filled);
		sr.rect(0.0f, 0.0f, Gdx.graphics.getWidth() * 1.0f, Gdx.graphics.getHeight() * 1.0f, 
				startSky, 
				startSky,
				endSky, 
				endSky);
		sr.end();
		
		
		
		sb.begin();
		for (int i = 0; i < BEM.size(); i++) {
			((BackgroundElementGeneral)(BEM.get(i))).render(sb);
		}
		
		sb.end();
	}
	
	public void dispose() {
		for (int i = 0; i < BEM.size(); i++) {
			((BackgroundElementGeneral)(BEM.get(i))).dispose();
		}
		BEM.clear();
		BEM = null;
		
		sr.dispose();
		sr = null;
	}
}
