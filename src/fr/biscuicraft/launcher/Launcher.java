package fr.biscuicraft.launcher;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import fr.theshark34.openauth.AuthPoints;
import fr.theshark34.openauth.AuthenticationException;
import fr.theshark34.openauth.Authenticator;
import fr.theshark34.openauth.model.AuthAgent;
import fr.theshark34.openauth.model.response.AuthResponse;
import fr.theshark34.openlauncherlib.LaunchException;
import fr.theshark34.openlauncherlib.external.ExternalLaunchProfile;
import fr.theshark34.openlauncherlib.external.ExternalLauncher;
import fr.theshark34.openlauncherlib.minecraft.AuthInfos;
import fr.theshark34.openlauncherlib.minecraft.GameFolder;
import fr.theshark34.openlauncherlib.minecraft.GameInfos;
import fr.theshark34.openlauncherlib.minecraft.GameTweak;
import fr.theshark34.openlauncherlib.minecraft.GameType;
import fr.theshark34.openlauncherlib.minecraft.GameVersion;
import fr.theshark34.openlauncherlib.minecraft.MinecraftLauncher;
import fr.theshark34.openlauncherlib.util.ProcessLogManager;
import fr.theshark34.supdate.BarAPI;
import fr.theshark34.supdate.SUpdate;
import fr.theshark34.supdate.application.integrated.FileDeleter;
import fr.theshark34.swinger.Swinger;
import fr.theshark34.swinger.animation.Animator;
import re.alwyn974.minecraftserverping.MinecraftServerPing;
import re.alwyn974.minecraftserverping.MinecraftServerPingInfos;
import re.alwyn974.minecraftserverping.MinecraftServerPingOptions;

public class Launcher {
	public static final GameVersion BC_VERSION = new GameVersion("1.12.2", GameType.V1_8_HIGHER);
	public static final GameInfos BC_INFOS = new GameInfos("Biscuicraft", BC_VERSION,new GameTweak[] { GameTweak.FORGE });
	public static final File BC_DIR = BC_INFOS.getGameDir();
	public static final File BC_CRASHES_DIR = new File(BC_DIR, "crashes");
	public static final GameFolder BC_FOLDER = new GameFolder("launcher/resources/assets","launcher/resources/libraries", "launcher/resources/natives", "launcher/resources/biscuicraft.jar");
	private static AuthInfos authInfos;
	private static Thread updateThread;
	private static Thread reloadThread;
	public static void auth(String username, String password) throws AuthenticationException {
		Authenticator authenticator = new Authenticator(Authenticator.MOJANG_AUTH_URL, AuthPoints.NORMAL_AUTH_POINTS);
		AuthResponse response = authenticator.authenticate(AuthAgent.MINECRAFT, username, password, "");
		authInfos = new AuthInfos(response.getSelectedProfile().getName(), response.getAccessToken(),
				response.getSelectedProfile().getId());
	}
	public static void update() throws Exception {
		SUpdate su = new SUpdate("url du serveur de S-Update", BC_DIR);
		su.addApplication(new FileDeleter());
		updateThread = new Thread() {
			private int val;
			private int max;
			@Override
			public void run() {
				while (!this.isInterrupted()) {
					if (BarAPI.getNumberOfFileToDownload() == 0) {
						LauncherFrame.getInstance().getLauncherPanel().setInfoText("Verification des fichiers...");
						continue;
					}
					val = (int) (BarAPI.getNumberOfTotalDownloadedBytes() / 1000);
					max = (int) (BarAPI.getNumberOfTotalBytesToDownload() / 1000);
					LauncherFrame.getInstance().getLauncherPanel().getProgessBar().setMaximum(max);
					LauncherFrame.getInstance().getLauncherPanel().getProgessBar().setValue(val);
					LauncherFrame.getInstance().getLauncherPanel().setInfoText("Telechargement des fichiers du jeu " + BarAPI.getNumberOfDownloadedFiles()
					+ "/" + BarAPI.getNumberOfFileToDownload() + " " + Swinger.percentage(val, max)+ " %");
				}
			}
		};
		updateThread.start();
		su.start();
		updateThread.interrupt();
	}
	public static void launch() throws LaunchException {
		ExternalLaunchProfile profile = MinecraftLauncher.createExternalProfile(BC_INFOS, BC_FOLDER, authInfos);
		if(LauncherPanel.hasJava64Bits() == true) {
			profile.getVmArgs().addAll(Arrays.asList("-Xms512M", "-Xmx"+LauncherPanel.RAM_VALUE+"M"));
		} else {
			profile.getVmArgs().addAll(Arrays.asList("-Xms512M","-Xmx1024M"));
		}
		ExternalLauncher launcher = new ExternalLauncher(profile);
		reloadThread.interrupt();
		Animator.fadeOutFrame(LauncherFrame.getInstance(), 3, new Runnable() {@Override public void run() {LauncherFrame.getInstance().setVisible(false);}});
		Process p = launcher.launch();
		ProcessLogManager manager = new ProcessLogManager(p.getInputStream(), new File(BC_DIR, "logs.txt"));
		manager.start();
		try {p.waitFor();} 
		catch (InterruptedException e) {e.printStackTrace();}
		System.exit(0);
	}
	public static void interruptThread() {
		updateThread.interrupt();
		reloadThread.interrupt();
	}
	public static void reload() {
		reloadThread = new Thread(() -> {
			while (!Thread.interrupted()) {

				try {
					Serverinfos();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				try {
					Thread.sleep(5000);
					} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		reloadThread.start();
	}
	public static void Serverinfos() throws IOException {
		MinecraftServerPingInfos.V1_9_HIGHER data = null;
		try {
			data = new MinecraftServerPing().getPing_V1_9_HIGHER(
					new MinecraftServerPingOptions().setHostname("ip du serveur minecraft").setPort(25565));
		} catch (IOException e) {
			e.printStackTrace();
			LauncherPanel.setText(LauncherPanel.serverinfosLabel,"Serveur éteint ou indisponible !");
		}
		LauncherPanel.setText(LauncherPanel.serverinfosLabel, 
		"<html> <font color=\"white\"> Ping: <font color=\"red\">"+
		data.getLatency()+
		"<font color=\"white\"> ms" 
		+ "<br/>" 
		+ "<font color=\"white\"> Il y a <font color=\"yellow\">"+data.getPlayers().getOnline()+"<font color=\"white\"> Joueur(s) connecté(s) / <font color=\"yellow\">"
		+data.getPlayers().getMax()+"<font color=\"white\"> Joueurs maximum</html>");
	}
	
}
.
