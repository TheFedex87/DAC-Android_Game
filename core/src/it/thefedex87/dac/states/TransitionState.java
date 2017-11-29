package it.thefedex87.dac.states;

import it.thefedex87.dac.ui.TransitionBoubble;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class TransitionState extends State {

	private State prev;
	private State next;
	
	private TransitionBoubble[][] circles;
	
	
	//private final float maxTime = 0.5f;
	
	//private float timer = 0;
	
	private ShapeRenderer sr;
	
	private boolean expandingFinish;
	
	
	public TransitionState(GSM gsm, State prev, State next) {
		super(gsm);
		
		this.prev = prev;
		this.next = next;
		
		int nCirclesX = Gdx.graphics.getWidth() / 72 + 2;
		int nCirclesY = Gdx.graphics.getHeight() / 72 + 2;
		
		circles = new TransitionBoubble[nCirclesX][nCirclesY];
		
		for (int i = 0; i < circles.length; i++)
			for (int j = 0; j < circles[0].length; j++) {
				circles[i][j] = new TransitionBoubble(72 * i - 36, 72 * j - 36, 55);
				circles[i][j].setTimer((-(circles.length + i) + j));
				//circles[i][j].setTimer((float)((1.0f * (circles.length-i) / circles.length * 1.0) + (1.0f * (circles[0].length - j) / circles[0].length * 1.0)) / 2);
			}
		
		sr = new ShapeRenderer();
	}

	@Override
	public void handleInput() {
		
	}

	@Override
	public void update(float delta) {
		boolean finish=true;
		for (int i = 0; i < circles.length; i++)
			for (int j = 0; j < circles[0].length; j++) {
				if (!circles[i][j].isFinish()) 
					finish = false;
			}
		if (finish==true) {
			gsm.set(next);
			return;
		}
			
		boolean expandingDone = true;
		for (int i = 0; i < circles.length; i++)
			for (int j = 0; j < circles[0].length; j++) {
				if (!circles[i][j].isExpandingDone()) 
					expandingDone = false;
			}
		

		for (int i = 0; i < circles.length; i++)
			for (int j = 0; j < circles[0].length; j++) {
				circles[i][j].update(delta);
			}
		
		if (expandingDone) {
			expandingFinish = true;
			for (int i = 0; i < circles.length; i++)
				for (int j = 0; j < circles[0].length; j++) 
					circles[i][j].setContracting((-(circles.length + i) + j));
		}
	}

	@Override
	public void render(SpriteBatch sb) {
		if (!expandingFinish) 
			prev.render(sb);
		else {
			if (prev != null) {
				prev.dispose();
				prev = null;
			}
			next.render(sb);
		}
		
		sr.setProjectionMatrix(cam.combined);
		for (int i = 0; i < circles.length; i++)
			for (int j = 0; j < circles[0].length; j++)
				circles[i][j].render(sb, sr);
	}

	@Override
	public void dispose() {
		sr.dispose();
		sr = null;
		
		circles = null;
	}
}
