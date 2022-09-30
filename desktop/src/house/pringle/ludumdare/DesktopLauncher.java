package house.pringle.ludumdare;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import house.pringle.ludumdare.main;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {

	// full: 1920x1080
	// 75%: 1440x810
	// 60%: 115xx648
	// 50% 960x540
	public static final int WIDTH = 1152;
	public static final int HEIGHT = 648;

	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.setWindowedMode(WIDTH, HEIGHT);
		config.setTitle("LD51");
		config.useVsync(true);
		new Lwjgl3Application(new main(), config);
	}
}
