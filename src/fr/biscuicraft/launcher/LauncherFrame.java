package fr.biscuicraft.launcher;

import javax.swing.JFrame;
import com.sun.awt.AWTUtilities;
import fr.theshark34.openlauncherlib.util.CrashReporter;
import fr.theshark34.swinger.Swinger;
import fr.theshark34.swinger.animation.Animator;
import fr.theshark34.swinger.util.WindowMover;

@SuppressWarnings("serial")
public class LauncherFrame extends JFrame {
	private static LauncherFrame instance;
	private LauncherPanel launcherPanel;
	private static CrashReporter crashReporter;
	public LauncherFrame() {
		this.setTitle("Biscuicraft Launcher");
		this.setSize(975, 625);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setUndecorated(true);
		this.setIconImage(Swinger.getResource("rounded_icon.png"));
		this.setContentPane(launcherPanel = new LauncherPanel());
		AWTUtilities.setWindowOpacity(this,  0.0F);
		WindowMover mover = new WindowMover(this);
		this.addMouseListener(mover);
		this.addMouseMotionListener(mover);
		this.setVisible(true);
		Animator.fadeInFrame(this, 3);
	}
	public static void main(String[] args) {
		Swinger.setSystemLookNFeel();
		Swinger.setResourcePath("/fr/biscuicraft/launcher/resources");
		Launcher.BC_CRASHES_DIR.mkdirs();
		crashReporter = new CrashReporter("Biscuicraft Launcher", Launcher.BC_CRASHES_DIR);
		Launcher.reload();
		instance = new LauncherFrame();
	}
	public static LauncherFrame getInstance() {return instance;}
	public LauncherPanel getLauncherPanel() {return launcherPanel;}
	public static CrashReporter getCrashReporter() {return crashReporter;}
}
