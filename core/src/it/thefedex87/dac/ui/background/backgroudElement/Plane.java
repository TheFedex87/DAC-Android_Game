package it.thefedex87.dac.ui.background.backgroudElement;

import it.thefedex87.dac.DAC;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Plane extends BackgroundElementGeneral {
	private float elementStart;
	private float timer;
	private float speed = 500;
	
	
	private float width;
	private float height;
	
	private Vector2 startPoint;
	private Vector2 endPoint;
	private short nArcs = 0; //numero di traiettorie arco eseguite
	private float tRadius;  //raggio della transizione ad arco (piroetta aereo)
	private float centroX;
	private float centroY;
	private float tAngle; //angolo della traiettoria corrente
	private double sAngle; // angolo iniziale curve
	private short tType = 0; //tipo traiettoria: 0 = retta, 1, circonferenza
	private float tStart; //timer avvio singola transizione
	private float tDirection; //verso della rotazione: 0=CCW, 1=CW
	
	public Plane(float elementStart) {
		this.elementStart = elementStart;
		tStart = elementStart;
	}

	@Override
	public void update(float dt) {
		timer += dt;
		
		if (timer > elementStart) {
			if (texture == null) {
				texture = DAC.res.getAtlas("pack").findRegion("plane");
				float rappHW = texture.getRegionWidth() / texture.getRegionHeight();
				width = Gdx.graphics.getWidth() / 3;
				height = width / rappHW;
				
				x = -width;
				y = MathUtils.random(0, Gdx.graphics.getHeight());
				
				startPoint = new Vector2(x, y);
				endPoint = new Vector2(Gdx.graphics.getWidth() / 2,
						Gdx.graphics.getHeight() / 2);
				//endPoint = new Vector2(MathUtils.random(Gdx.graphics.getWidth() / 4, Gdx.graphics.getWidth() / 4 * 3),
				//		MathUtils.random(0, Gdx.graphics.getHeight()));
				
				tAngle = MathUtils.atan2(endPoint.y - startPoint.y, endPoint.x - startPoint.x);
			}
			
			
			if (tType == 0) {
				double len = Math.sqrt(Math.pow((endPoint.x - startPoint.x),2)+Math.pow((endPoint.y-startPoint.y),2));
				
				double totTime = len / speed;
				
				x = (float)(startPoint.x + ((timer - tStart) / totTime * (endPoint.x - startPoint.x)));
				y = (float)(startPoint.y + ((timer - tStart) / totTime * (endPoint.y - startPoint.y)));
				
				if (x > Gdx.graphics.getWidth() + 20) transitionFinish = true;
				
				double distPercorsa = Math.sqrt(Math.pow((x - startPoint.x),2)+Math.pow((y-startPoint.y),2));
				
				if (distPercorsa > len) { //retta finita ora eseguo la traiettoria arco
					startPoint.x = endPoint.x;
					startPoint.y = endPoint.y;
					
					tRadius = MathUtils.random(100, 200);
					
					if (MathUtils.randomBoolean())
						tDirection = 0;
					else
						tDirection = 1;
					
					if (tDirection == 0) {
						centroX = startPoint.x + tRadius * MathUtils.cos(tAngle + MathUtils.PI / 2);
						centroY = startPoint.y + tRadius * MathUtils.sin(tAngle + MathUtils.PI / 2);
						
						endPoint.x = startPoint.x + tRadius * 2 * MathUtils.cos(tAngle + MathUtils.PI / 2);
						endPoint.y = startPoint.y + tRadius * 2 * MathUtils.sin(tAngle + MathUtils.PI / 2);	
					}
					else {
						centroX = startPoint.x + tRadius * MathUtils.cos(tAngle - MathUtils.PI / 2);
						centroY = startPoint.y + tRadius * MathUtils.sin(tAngle - MathUtils.PI / 2);
						
						endPoint.x = startPoint.x + tRadius * 2 * MathUtils.cos(tAngle - MathUtils.PI / 2);
						endPoint.y = startPoint.y + tRadius * 2 * MathUtils.sin(tAngle - MathUtils.PI / 2);
					}
					
					tType = 1; //Setto la traiettoria a tipo arco
					
					tStart = timer;
					
					sAngle = tAngle;
				}
			}
			else if (tType == 1) {
				double len = MathUtils.PI * tRadius;
				
				double totTime = len / speed;
				
				if (tDirection == 0)
					tAngle += (MathUtils.PI * dt / totTime);
				else
					tAngle -= (MathUtils.PI * dt / totTime);
				

				if (tDirection == 0) {
					x = centroX + tRadius * MathUtils.cos(tAngle - MathUtils.PI / 2);
					y = centroY + tRadius * MathUtils.sin(tAngle - MathUtils.PI / 2);
				}
				else {
					x = centroX + tRadius * MathUtils.cos(tAngle + MathUtils.PI / 2);
					y = centroY + tRadius * MathUtils.sin(tAngle + MathUtils.PI / 2);
				}
				
				double endAngle;
				if (tDirection == 0)
					endAngle = sAngle + MathUtils.PI;
				else
					endAngle = sAngle - MathUtils.PI;
				
				
				if ((tAngle > endAngle && tDirection == 0) || (tAngle < endAngle && tDirection == 1)) {
					startPoint.x = endPoint.x;
					startPoint.y = endPoint.y;
					
					nArcs++;
					if (nArcs == 1) { // eseguio il secondo semicerchio
						sAngle = tAngle;
						
						if (MathUtils.randomBoolean())
							tDirection = 0;
						else
							tDirection = 1;
						
						if (tDirection == 0) {
							centroX = startPoint.x + tRadius * MathUtils.cos(tAngle + MathUtils.PI / 2);
							centroY = startPoint.y + tRadius * MathUtils.sin(tAngle + MathUtils.PI / 2);
							
							endPoint.x = startPoint.x + tRadius * 2 * MathUtils.cos(tAngle + MathUtils.PI / 2);
							endPoint.y = startPoint.y + tRadius * 2 * MathUtils.sin(tAngle + MathUtils.PI / 2);	
						}
						else {
							centroX = startPoint.x + tRadius * MathUtils.cos(tAngle - MathUtils.PI / 2);
							centroY = startPoint.y + tRadius * MathUtils.sin(tAngle - MathUtils.PI / 2);
							
							endPoint.x = startPoint.x + tRadius * 2 * MathUtils.cos(tAngle - MathUtils.PI / 2);
							endPoint.y = startPoint.y + tRadius * 2 * MathUtils.sin(tAngle - MathUtils.PI / 2);
						}
					}
					else {
						//float rndLun = MathUtils.random(50, 250);
						float rndLun = (Gdx.graphics.getWidth() + 20) / MathUtils.cos(tAngle);
						endPoint = new Vector2(startPoint.x + MathUtils.cos(tAngle) * rndLun, startPoint.y + MathUtils.sin(tAngle) * rndLun);
						//endPoint = new Vector2((float)(Gdx.graphics.getWidth() + 20), (float)(Math.tan(tAngle) * Gdx.graphics.getWidth() + 20));
						tType = 0;	
					}
					
					tStart = timer;
				}
					
			}
		}
	}
	
	@Override
	public void render(SpriteBatch sb) {
		if (texture != null) {
			//sb.draw(texture, x, y, width, height);
			
			//double xSprite = 
			sb.draw(texture, x, y, texture.getRegionWidth() / 2, texture.getRegionHeight() / 2, width, height, 1.0f, 1.0f, tAngle * MathUtils.radiansToDegrees);
			
			/*sb.end();
			ShapeRenderer sr = new ShapeRenderer();
			sr.setColor(Color.RED);
			sr.begin(ShapeType.Filled);
			sr.circle(x, y, 30);
			sr.end();
			sb.begin();*/
		}
	}
	
	@Override
	public boolean isTransitionFinish() {
		return transitionFinish;
	}
	
	@Override 
	public void dispose() {
		texture = null;
		startPoint = null;
		endPoint = null;
	}
}
