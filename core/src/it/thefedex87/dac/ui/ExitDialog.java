package it.thefedex87.dac.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Window.WindowStyle;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;



public class ExitDialog {

	private Dialog dialog;
	private BitmapFont dialogFnt;
	private Skin skin;
	
	private boolean showed;
	
	

	public ExitDialog() {
		skin = new Skin();
		dialogFnt = new BitmapFont(Gdx.files.internal("font/dialogTitle.fnt"));
		float scale = 1.0f;
		while (Gdx.graphics.getWidth() * 8 / 10 < dialogFnt.getBounds("Do you really want exit?").width) {
			scale -= 0.05f;
			dialogFnt.setScale(scale);
		}
		
		TextButtonStyle tbs = new TextButtonStyle();
		tbs.font = dialogFnt;

		
		LabelStyle ls = new LabelStyle();
		dialogFnt.setColor(Color.rgb888(0.19f, 0.62f, 0.78f));
		ls.font = dialogFnt;
		ls.fontColor = new Color(Color.rgb888(0.39f, 0.82f, 0.98f));
		
		skin.add("dialog", ls);
		skin.add("default", ls);
		
		skin.add("dialog", tbs);
		skin.add("default", ls);
		
		
		///////
		Pixmap pixmap = new Pixmap(100, 100, Format.RGBA8888);
        pixmap.setColor(Color.rgba8888(.157f, .157f, .157f, 1));
        pixmap.fill();
		
		WindowStyle ws = new WindowStyle();
		ws.titleFont = dialogFnt;
		ws.titleFontColor=new Color(Color.WHITE);
		ws.background = new NinePatchDrawable(new NinePatch(new Texture(pixmap)));
		
		pixmap = new Pixmap(100, 100, Format.RGBA8888);
		//pixmap.setBlending(Pixmap.Blending.None);
		pixmap.setColor(new Color(.7f, .7f, .7f, 0.8f));
		pixmap.fill();
		
		ws.stageBackground = new NinePatchDrawable(new NinePatch(new Texture(pixmap)));
		skin.add("dialog", ws);
		//////
		
		/*ExitDialog exitDialog;
		exitDialog = new ExitDialog("Confirm", skin);
		stage.addActor(exitDialog);*/
		
		Label label = new Label("Do you really want exit?", skin);
		//label.setWrap(true);
		//label.setFontScale(.3f);
		//label.setAlignment(Align.center);

		dialog = 
		new Dialog("", skin, "dialog") {
		    protected void result (Object object) {
		    	if ((Boolean)object == true) System.exit(0);
		    	showed = false;
		    }
		};

		dialog.getContentTable().defaults().padLeft(20);
		dialog.getButtonTable().defaults().width(Gdx.graphics.getWidth() * 9 / 20).height(Gdx.graphics.getHeight() / 10);
		
		//dialog.debugAll();
		//dialog.padTop(50).padBottom(50);
		dialog.getContentTable().add(label).width(Gdx.graphics.getWidth() * 9 / 10).height(Gdx.graphics.getHeight() / 10).row();
		//dialog.getButtonTable().padTop(50);

		TextButton dbutton = new TextButton("Yes", skin, "dialog");
		dbutton.setWidth(50);
		dialog.button(dbutton, true);
		//dialog.getButtonTable().add(dbutton).width(200).height(Gdx.graphics.getHeight() / 10);

		dbutton = new TextButton("No", skin, "dialog");
		dialog.button(dbutton, false);
		//dialog.getButtonTable().add(dbutton).width(200);
		
		//dialog.key(Keys.ENTER, true).key(Keys.ESCAPE, false);
		dialog.invalidateHierarchy();
		dialog.invalidate();
		dialog.layout();
		
		showed = false;
	}
	
	public void show(Stage stage) {
		dialog.show(stage);
		showed = true;
	}
	
	public void dispose() {
		dialogFnt.dispose();
		dialogFnt = null;
		
		skin.dispose();
		skin = null;
	}
	
	public boolean isShowed() {
		return showed;
	}
}
