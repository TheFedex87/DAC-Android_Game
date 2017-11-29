package it.thefedex87.dac.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import it.thefedex87.dac.DAC;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.height = (int)DAC.height/3;
		config.width = (int)DAC.width/3;
		new LwjglApplication(new DAC(), config);
	}
}
