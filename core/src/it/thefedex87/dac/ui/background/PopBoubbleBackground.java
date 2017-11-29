package it.thefedex87.dac.ui.background;


import it.thefedex87.dac.ui.BoubbleStandard;

import java.util.ArrayList;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

public class PopBoubbleBackground extends Background {

	private ArrayList<BoubbleStandard> boubbleList;
	private Timer timer;
	private float delayBubbleTimer;
	
	public PopBoubbleBackground() {
		boubbleList = new ArrayList<BoubbleStandard>();
		delayBubbleTimer = 0.002f;
		
		timer = new Timer();
		timer.scheduleTask(TimerSchedule, 0, 0.3f);
		timer.start();
	}
	
	private Task TimerSchedule = new Task() {
		@Override
		public void run(){
			float x = MathUtils.random(10, Gdx.graphics.getWidth() - 10);
			float y= MathUtils.random(10, Gdx.graphics.getHeight() - 10);
			
			boubbleList.add(new BoubbleStandard(x, y, Gdx.graphics.getWidth() / (MathUtils.random(6, 12)), delayBubbleTimer, 1));
		}
	};
	
	public void render(SpriteBatch sb) {
		for (int i = 0; i <= boubbleList.size() - 1; i++) {
			((BoubbleStandard)boubbleList.get(i)).render(sb);
		}
	}
	
	public void update(float delta) {
		checkCircle();
	}
	
	private void checkCircle() {
		for (int i = boubbleList.size() - 1; i >= 0; i--) {
			if (((BoubbleStandard)boubbleList.get(i)).getRadius() <=0 && ((BoubbleStandard)boubbleList.get(i)).renderExplode == false) {
				((BoubbleStandard)boubbleList.get(i)).dispose();
				boubbleList.remove(i);
			}
		}
	}
	
	public void dispose() {
		timer.stop();
		timer.clear();
		
		for (int i = boubbleList.size() - 1; i >= 0; i--) {
			((BoubbleStandard)boubbleList.get(i)).dispose();
		}
		boubbleList.clear();
	}

}
