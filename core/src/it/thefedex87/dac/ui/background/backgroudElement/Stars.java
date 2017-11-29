package it.thefedex87.dac.ui.background.backgroudElement;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;

public class Stars extends BackgroundElementGeneral {
	//private float x;
	//private float y;
	
	private ShapeRenderer sr;
	
	private List<Vector3> starsList; //Uso un Vector3 perchè uso la proprietà 'z' per impostare il raggio della stella
	
	private float timer;
	
	private float elementStart;
	private float lastStarGeneration;
	
	public Stars(float elementStart) {
		sr = new ShapeRenderer();
		timer = 0;
		starsList = new ArrayList<Vector3>(100);
		this.elementStart = elementStart;
	}

	
	@Override
	public void update(float delta) {
		timer += delta;
		if (timer >= elementStart  && starsList.size() <= 99) {
			float rndStarGeneration = MathUtils.random(2, 8);
			if (timer - lastStarGeneration > rndStarGeneration || lastStarGeneration == 0) {
				lastStarGeneration = timer;
				starsList.add(new Vector3(MathUtils.random(0, Gdx.graphics.getWidth()), MathUtils.random(0, Gdx.graphics.getHeight()), MathUtils.random(0.5f, 7.0f)));
			}
		}
	}
	
	@Override
	public void render(SpriteBatch sb) {
		if (starsList.size() > 0){
			sb.end();
			sr.begin(ShapeType.Filled);
			for (int i = 0; i < starsList.size(); i++) {
				sr.setColor(1, (float)MathUtils.random(60, 100) / 100.0f, (float)MathUtils.random(40, 80) / 100.0f, 1);
				float rnd = MathUtils.random(starsList.get(i).z * 100 / 2, starsList.get(i).z * 100);
				float radiusRed = rnd / 100.0f;
				sr.circle(starsList.get(i).x, starsList.get(i).y, radiusRed);
			}
			sr.end();
			sb.begin();
		}
	}

}
