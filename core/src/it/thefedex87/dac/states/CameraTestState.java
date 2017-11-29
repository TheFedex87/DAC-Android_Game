package it.thefedex87.dac.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;



public class CameraTestState extends State {
	private ShapeRenderer sr;
	//private Texture texture;
	OrthographicCamera camera;

	private float posX;
	public CameraTestState(GSM gsm) {
		super(gsm);
		sr = new ShapeRenderer();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 450, 800);
		
		//texture = new Texture("img/cloud.png");
		
		posX = 50.0f;
	}

	@Override
	public void handleInput() {

	}

	@Override
	public void update(float delta) {
		if (Gdx.input.isTouched()) {
			int x = Gdx.input.getX();
			if (x > Gdx.graphics.getWidth() / 2) {
				//VERSO DX
				if (posX - 50 + 2 < 1500)
					posX += 2.0f;
				if (camera.position.x + camera.viewportWidth / 2 < 1600 && posX >= camera.position.x){
					camera.position.set(posX, camera.position.y ,camera.position.z);
					camera.update();
				}
			}
			else {
				if (posX - 2 > 50)
					posX -= 2.0f;
				if (camera.position.x - camera.viewportWidth / 2 > 0 && posX <= camera.position.x){
					camera.position.set(posX, camera.position.y ,camera.position.z);
					camera.update();
				}
			}
			
		}
	}

	@Override
	public void render(SpriteBatch sb) {
		
		
		sr.setProjectionMatrix(camera.combined);
		sr.begin(ShapeType.Filled);
		sr.setColor(1, 0, 0, 1);
		sr.rect(50, 50, 1500, 700);
		sr.setColor(0, 1, 0, 1);
		sr.rect(posX, 70, 50, 50);
		sr.setColor(0, 0, 1, 1);
		sr.rect(camera.position.x, camera.position.y, 20, 20);
		sr.setColor(0, 1, 1, 1);
		sr.rect(200, 500, 100, 100);
		//sr.setColor(0, 0.5f, 1, 1);
		//sr.rect(200, 600, 100, 100);
		sr.end();
		
		
		
		
	}

	@Override
	public void dispose() {

	}

}
