package fr.biscuicraft.launcher;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Graphics;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import fr.theshark34.openauth.AuthenticationException;
import fr.theshark34.openlauncherlib.LaunchException;
import fr.theshark34.openlauncherlib.util.Saver;
import fr.theshark34.swinger.Swinger;
import fr.theshark34.swinger.animation.Animator;
import fr.theshark34.swinger.colored.SColoredBar;
import fr.theshark34.swinger.event.SwingerEvent;
import fr.theshark34.swinger.event.SwingerEventListener;
import fr.theshark34.swinger.textured.STexturedButton;

@SuppressWarnings("serial")
public class LauncherPanel extends JPanel implements SwingerEventListener {
	private static final String versionnumber = "V2.0.0.0";
	private Image background = Swinger.getResource("background.png");
	private Saver saver = new Saver(new File(Launcher.BC_DIR,"launcher.properties"));
	private JTextField usernameField = new JTextField(this.saver.get("username"));
	private JTextField passwordField = new JPasswordField(EncryptionPassword.decrypt(saver.get("password"), "Clé de chiffrement"));
	private STexturedButton playbutton = new STexturedButton(Swinger.getResource("play_button.png"));
	private STexturedButton quitbutton = new STexturedButton(Swinger.getResource("quit_button.png"));
	private STexturedButton hidebutton = new STexturedButton(Swinger.getResource("hide_button.png"));
	private STexturedButton facebookButton = new STexturedButton(Swinger.getResource("facebook_logo.png"));
	private STexturedButton discordButton = new STexturedButton(Swinger.getResource("discord_logo.png"));
	private STexturedButton instagramButton = new STexturedButton(Swinger.getResource("instagram_logo.png"));
	private STexturedButton twitterButton = new STexturedButton(Swinger.getResource("twitter_logo.png"));
	private STexturedButton redditButton = new STexturedButton(Swinger.getResource("reddit_logo.png"));
	private STexturedButton websiteButton = new STexturedButton(Swinger.getResource("www_logo.png"));
	private SColoredBar progressBar = new SColoredBar(Swinger.getTransparentWhite(100),Swinger.getTransparentWhite(175));
	private JLabel infoLabel = new JLabel("Clique sur jouer !",SwingConstants.CENTER);
	static int RAM_VALUE;
	static final int RAM_MIN = 3;
	static final int RAM_MAX = 32;
	int RAM_INIT = 4;
	{if(saver.get("ram") == null) {
		saver.set("ram", "3072");
		} 
	else {
		RAM_INIT = (Integer.parseInt(saver.get("ram"))/1024);
		}
	}
	JSlider ramSlider = new JSlider(JSlider.HORIZONTAL, RAM_MIN, RAM_MAX, RAM_INIT);
	private JLabel launcherversionLabel = new JLabel("Launcher " + versionnumber);
	private JLabel ramLabel = new JLabel((Integer.parseInt(saver.get("ram"))/ 1024) + " Go de Ram");
	public static JLabel serverinfosLabel = new JLabel();
	
	public LauncherPanel() {
		this.setLayout(null);
		ramSlider.addChangeListener(new ChangeListener() {
		public void stateChanged(ChangeEvent event) {
		int ramslidervalue = ramSlider.getValue();
		RAM_VALUE = (ramslidervalue * 1024);
		ramLabel.setText((RAM_VALUE / 1024) + " Go de Ram");
		}});
		ramSlider.setMajorTickSpacing(2);
		ramSlider.setMinorTickSpacing(1);
		ramSlider.setPaintTicks(true);
		ramSlider.setPaintLabels(true);
		ramSlider.setOpaque(false);
		ramSlider.setForeground(Color.CYAN);
		ramSlider.setVisible(true);
		ramSlider.setBounds(610, 0, 300, 40);
		add(this.ramSlider);

		usernameField.setForeground(Color.WHITE);
		usernameField.setFont(usernameField.getFont().deriveFont(12F));
		usernameField.setCaretColor(Color.WHITE);
		usernameField.setOpaque(false);
		usernameField.setBorder(null);
		usernameField.setBounds(23, 153, 171, 28);
		add(this.usernameField);

		passwordField.setForeground(Color.WHITE);
		passwordField.setFont(usernameField.getFont());
		passwordField.setCaretColor(Color.WHITE);
		passwordField.setOpaque(false);
		passwordField.setBorder(null);
		passwordField.setBounds(25, 207, 171, 28);
		this.add(passwordField);

		serverinfosLabel.setForeground(Color.WHITE);
		serverinfosLabel.setFont(usernameField.getFont().deriveFont(20F));
		serverinfosLabel.setBounds(12, 15, 951, 40);
		this.add(serverinfosLabel);

		facebookButton.setBounds(150, 540);
		facebookButton.addEventListener(this);
		add(this.facebookButton);

		discordButton.setBounds(10, 480);
		discordButton.addEventListener(this);
		add(this.discordButton);

		instagramButton.setBounds(150, 485);
		instagramButton.addEventListener(this);
		add(this.instagramButton);

		twitterButton.setBounds(80, 485);
		twitterButton.addEventListener(this);
		add(this.twitterButton);

		redditButton.setBounds(80, 540);
		redditButton.addEventListener(this);
		add(this.redditButton);

		websiteButton.setBounds(10, 540);
		websiteButton.addEventListener(this);
		add(this.websiteButton);

		playbutton.setBounds(596, 518);
		playbutton.addEventListener(this);
		add(this.playbutton);

		quitbutton.setBounds(945, 0);
		quitbutton.addEventListener(this);
		add(this.quitbutton);

		hidebutton.setBounds(910, 0);
		hidebutton.addEventListener(this);
		add(this.hidebutton);

		progressBar.setBounds(12, 593, 951, 20);
		add(this.progressBar);

		infoLabel.setForeground(Color.WHITE);
		infoLabel.setFont(usernameField.getFont().deriveFont(20F));
		infoLabel.setBounds(35, 565, 951, 25);
		add(this.infoLabel);
				
		launcherversionLabel.setForeground(Color.ORANGE);
		launcherversionLabel.setFont(usernameField.getFont().deriveFont(20F));
		launcherversionLabel.setBounds(810, 546, 951, 25);
		add(this.launcherversionLabel);
		
		ramLabel.setForeground(Color.ORANGE);
		ramLabel.setFont(usernameField.getFont().deriveFont(20F));
		ramLabel.setBounds(610, 50, 951, 25);
		add(this.ramLabel);
	}
	
	public static boolean hasJava64Bits() {
        if(System.getProperty("sun.arch.data.model").contains("64")) {return true;}
        else{return false;}
    }
	
	@Override
	public void onEvent(SwingerEvent e) {
		if (e.getSource() == playbutton) {
			play();
		} 
		else if (e.getSource() == quitbutton) {Animator.fadeOutFrame(LauncherFrame.getInstance(), 3, new Runnable() {@Override public void run() {System.exit(0);}});} 
		else if (e.getSource() == hidebutton) {LauncherFrame.getInstance().setState(JFrame.ICONIFIED);}
		else if (e.getSource() == facebookButton)
			try {Desktop.getDesktop().browse(new URI("https://url.biscuicraft.fr/facebook"));}
			catch (IOException e1) {e1.printStackTrace();} 
			catch (URISyntaxException e1) {e1.printStackTrace();}
		else if (e.getSource() == discordButton)
			try {Desktop.getDesktop().browse(new URI("https://url.biscuicraft.fr/discord"));}
			catch (IOException e1) {e1.printStackTrace();}
			catch (URISyntaxException e1) {e1.printStackTrace();}
		else if (e.getSource() == instagramButton)
			try {Desktop.getDesktop().browse(new URI("https://url.biscuicraft.fr/instagram"));} 
			catch (IOException e1) {e1.printStackTrace();} 
			catch (URISyntaxException e1) {e1.printStackTrace();}
		else if (e.getSource() == twitterButton)
			try {Desktop.getDesktop().browse(new URI("https://url.biscuicraft.fr/twitter"));}
			catch (IOException e1) {e1.printStackTrace();} 
			catch (URISyntaxException e1) {e1.printStackTrace();}
		else if (e.getSource() == redditButton)
			try {Desktop.getDesktop().browse(new URI("https://url.biscuicraft.fr/reddit"));} 
			catch (IOException e1) {e1.printStackTrace();} 
			catch (URISyntaxException e1) {e1.printStackTrace();}
		else if (e.getSource() == websiteButton)
			try {Desktop.getDesktop().browse(new URI("https://biscuicraft.fr/"));} 
			catch (IOException e1) {e1.printStackTrace();} 
			catch (URISyntaxException e1) {e1.printStackTrace();}
	}
	
	public void play() {
		try {addonelaunch();} 
		catch (Exception e1) {e1.printStackTrace();}
		setFieldsEnabled(false);
		Integer ramobj = new Integer((ramSlider.getValue()*1024));
		RAM_VALUE= (ramSlider.getValue()*1024);
		String RAM_VALUEStr = ramobj.toString();
		saver.set("ram", RAM_VALUEStr);
		if (usernameField.getText().replaceAll(" ", "").length() == 0 || passwordField.getText().length() == 0) {
			JOptionPane.showMessageDialog(this,"Erreur, veuillez entrer un pseudo et un mot de passe valide.","Erreur",JOptionPane.ERROR_MESSAGE);
			setFieldsEnabled(true);
			return;
		}
		Thread t = new Thread() {
			@Override
			public void run() {
				try {
					Launcher.auth(usernameField.getText(), passwordField.getText());
				} catch (AuthenticationException e) {
					JOptionPane.showMessageDialog(LauncherPanel.this,"Erreur, Impossible de se connecter : " + e.getErrorModel().getErrorMessage(), "Erreur",JOptionPane.ERROR_MESSAGE);
					setFieldsEnabled(true);
					return;
				}
				saver.set("username", usernameField.getText());
				saver.set("password", EncryptionPassword.encrypt(passwordField.getText(), "Clé de chiffrement"));
				try {
					Launcher.update();
				} catch (Exception e) {
					Launcher.interruptThread();
					LauncherFrame.getCrashReporter().catchError(e,"Impossible de mettre à jour Biscuicraft !");
					setFieldsEnabled(true);
					return;
				}
				try {
					Launcher.launch();
				} catch (LaunchException e) {
					LauncherFrame.getCrashReporter().catchError(e, "Impossible de lancer Biscuicraft !");
					setFieldsEnabled(true);
				}
			}
		};
		t.start();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(background, 0, 0, this.getWidth(), this.getHeight(), this);
	}
	
	private void setFieldsEnabled(boolean enabled) {
		usernameField.setEnabled(enabled);
		passwordField.setEnabled(enabled);
		playbutton.setEnabled(enabled);
		ramSlider.setEnabled(enabled);
	}
	
	public SColoredBar getProgessBar() {
		return progressBar;
	}
	
	public void setInfoText(String text) {
		infoLabel.setText(text);
	}
	
	public static void setText(JLabel label, String text) {
		label.setText(text);
	}
	
	public static void addonelaunch() throws Exception {
	        URL url = new URL("https://biscuicraft.fr/app/webroot/launchplus.php");
	        URLConnection con = url.openConnection();
	        con.addRequestProperty("User-Agent", "Mozilla");
	        con.setReadTimeout(5000);
	        con.setConnectTimeout(5000);
	        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			@SuppressWarnings("unused")
			String inputLine;
	        while ((inputLine = in.readLine()) != null);
	        in.close();
	}
	
}
