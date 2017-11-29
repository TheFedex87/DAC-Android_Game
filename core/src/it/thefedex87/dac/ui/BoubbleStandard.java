package it.thefedex87.dac.ui;

import it.thefedex87.dac.DAC;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

public class BoubbleStandard extends Boubble {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//private ShapeRenderer sr;
	private Timer timer;
	
	private TextureRegion texture;
	
	
	private BoubbleStandard boubble;
	
	private float r,g,b,a;
	

	public BoubbleStandard(float x, float y, float radius, float delayTimer, int nPlayer) {
		super(x, y, radius);
		
		boubble = this;
		//sr = new ShapeRenderer();
		
		texture = DAC.res.getAtlas("pack").findRegion("bubble2");
		
		
		timer = new Timer();
		timer.scheduleTask(new Task() {
			@Override
			public void run(){
				//Gdx.app.log("DAC", "timer: Decrease dimension boubble timer");
				if (boubble.radius < 0) {
					boubble.radius = 0;
					timer.stop();
					timer.clear();
					return;
				}
				boubble.radius -= 0.2f;
			}
		}, 0.5f, delayTimer);
		timer.start();
		
		a = MathUtils.random(55, 100) / 100.0f;
		
		switch (nPlayer) {
			case 1:
				r = MathUtils.random(50, 230) / 255.0f;
				g = MathUtils.random(50, 230) / 255.0f;
				b = MathUtils.random(50, 230) / 255.0f;
				
				
				//sr.setColor(r, g, b, 1);
				
				
				break;
			case 2:
				float rnd = MathUtils.random(0,1);
				if (rnd == 0) {
					//sr.setColor(Color.RED);
					r = 1;
					g = 0;
					b = 0;
				}
				else {
					//sr.setColor(Color.BLUE);
					r = 0;
					g = 0;
					b = 1;
				}
					
				break;
			
			case 3:
				rnd = MathUtils.random(0,2);
				if (rnd == 0) {
					//sr.setColor(Color.RED);
					r = 1;
					g = 0;
					b = 0;
				}
				else if(rnd == 1) {
					//sr.setColor(Color.BLUE);
					r = 0;
					g = 0;
					b = 1;
				}
				else {
					//sr.setColor(Color.GREEN);
					r = 0;
					g = 1;
					b = 0;
				}
				break;
			case 4:
				rnd = MathUtils.random(0,3);
				if (rnd == 0) {
					//sr.setColor(Color.RED);
					r = 1;
					g = 0;
					b = 0;
				}
				else if(rnd == 1) {
					//sr.setColor(Color.BLUE);
					r = 0;
					g = 0;
					b = 1;
				}
				else if(rnd == 2) {
					//sr.setColor(Color.GREEN);
					r = 0;
					g = 1;
					b = 0;
				}
				else {
					//sr.setColor(Color.YELLOW);
					r = 1;
					g = 1;
					b = 0;
				}
				break;
		}
		
		
		
	}
	
	public void setCircle(float x, float y, float radius) {
		this.x = x;
		this.y = y;
		this.radius = radius;
	}
	
	public float getRadius() {
		return radius;
	}
	
	public void render(SpriteBatch sb) {
		super.render(sb);
		if (radius > 0) {
			//sr.setProjectionMatrix(it.thefedex87.dac.states.State.cam);
			/*sr.begin(ShapeType.Filled);
			sr.circle(x, y, radius);
			sr.end();*/
			sb.begin();
			/*String vertexShader = "attribute vec4 " + ShaderProgram.POSITION_ATTRIBUTE + ";\n" //
		            + "attribute vec4 " + ShaderProgram.COLOR_ATTRIBUTE + ";\n" //
		            + "attribute vec2 " + ShaderProgram.TEXCOORD_ATTRIBUTE + "0;\n" //
		            + "uniform mat4 u_projTrans;\n" //
		            + "varying vec4 v_color;\n" //
		            + "varying vec2 v_texCoords;\n" //
		            + "\n" //
		            + "void main()\n" //
		            + "{\n" //
		            + "   v_color = " + ShaderProgram.COLOR_ATTRIBUTE + ";\n" //
		            + "   v_texCoords = " + ShaderProgram.TEXCOORD_ATTRIBUTE + "0;\n" //
		            + "   gl_Position =  u_projTrans * " + ShaderProgram.POSITION_ATTRIBUTE + ";\n" //
		            + "}\n";
		        String fragmentShader = "#ifdef GL_ES\n" //
		            + "#define LOWP lowp\n" //
		            + "precision mediump float;\n" //
		            + "#else\n" //
		            + "#define LOWP \n" //
		            + "#endif\n" //
		            + "varying LOWP vec4 v_color;\n" //
		            + "varying vec2 v_texCoords;\n" //
		            + "uniform sampler2D u_texture;\n" //
		            + "void main()\n"//
		            + "{\n" //
		            + "  gl_FragColor = v_color * texture2D(u_texture, v_texCoords).a;\n" //
		            + "}";

		        ShaderProgram shader = new ShaderProgram(vertexShader, fragmentShader);
		        
		    sb.setShader(shader);  */
		    sb.setColor(r, g, b, a);
			sb.draw(texture, x - radius, y - radius, radius * 2, radius * 2);
			sb.setColor(Color.WHITE);
			sb.end();
		}
	}
	
	public void dispose() {
		super.dispose();
		//sr.dispose();
		timer.stop();
		timer.clear();
	}
	
	public boolean getBallLost() {
		return ballLost;
	}
	
	public Color getColor() {
		//return sr.getColor();
		return new Color(r, g ,b, 1);
	}
	
	public void pause() {
		timer.stop();
	}
	
	public void resume() {
		timer.start();
	}
}
