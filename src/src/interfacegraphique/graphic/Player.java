package interfacegraphique.graphic;

import interfacegraphique.movie.Scenario;
import interfacegraphique.tools.Tools;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class Player extends JPanel implements Observer {

	private JButton beginButton, rewindButton, previousButton, playPauseButton,
			nextButton, fastForwardButton, endButton;

	private ImageIcon playImg, pauseImg;
	private Scenario scenario;
	private boolean plays;
	private Thread thread;

	public Player(Scenario s) {

		super();

		this.plays = false;

		this.beginButton = new JButton();
		this.rewindButton = new JButton();
		this.previousButton = new JButton();
		this.playPauseButton = new JButton();
		this.nextButton = new JButton();
		this.fastForwardButton = new JButton();
		this.endButton = new JButton();

		try {
			this.playImg = Tools.getIcon("resources/play.png");
			this.pauseImg = Tools.getIcon("resources/pause.png");
			this.beginButton.setIcon(Tools.getIcon("resources/first.png"));
			this.rewindButton.setIcon(Tools.getIcon("resources/rewind.png"));
			this.previousButton.setIcon(Tools.getIcon("resources/previous.png"));
			this.playPauseButton.setIcon(this.playImg);
			this.nextButton.setIcon(Tools.getIcon("resources/next.png"));
			this.fastForwardButton.setIcon(Tools.getIcon("resources/forward.png"));
			this.endButton.setIcon(Tools.getIcon("resources/last.png"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		this.beginButton.setPreferredSize(new Dimension(40, 30));
		this.rewindButton.setPreferredSize(new Dimension(40, 30));
		this.previousButton.setPreferredSize(new Dimension(40, 30));
		this.playPauseButton.setPreferredSize(new Dimension(40, 30));
		this.nextButton.setPreferredSize(new Dimension(40, 30));
		this.fastForwardButton.setPreferredSize(new Dimension(40, 30));
		this.endButton.setPreferredSize(new Dimension(40, 30));

		this.add(this.beginButton);
		this.add(this.rewindButton);
		this.add(this.previousButton);
		this.add(this.playPauseButton);
		this.add(this.nextButton);
		this.add(this.fastForwardButton);
		this.add(this.endButton);

		this.initButtons();

		this.setScenario(s);

	}

	private void initButtons() {
		this.beginButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Player.this.scenario.setCurrentTime(0);
			}
		});

		this.rewindButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (!Player.this.isPlaying()) {
					Player.this.setPlays(true);
					Player.this.update(null, null);

					Player.this.thread = new Thread() {
						@Override
						public void run() {
							for (int time = Player.this.scenario.getCurrentTime(); time >= 0 && Player.this.isPlaying(); time--) {
								Player.this.scenario.setCurrentTime(time);
								try {
									Thread.sleep(10);
								} catch (Exception e) {
									e.printStackTrace();
								}
							}

							Player.this.setPlays(false);
							Player.this.update(null, null);
						}
					};

					Player.this.thread.start();

				} else {

					Player.this.setPlays(false);
					Player.this.update(null, null);
				}

			}

		});

		this.previousButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Player.this.scenario.setCurrentTime(Player.this.scenario.getCurrentTime() - 1);
			}
		});

		this.playPauseButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (!Player.this.isPlaying()) {
					Player.this.setPlays(true);
					Player.this.update(null, null);

					Player.this.thread = new Thread() {
						@Override
						public void run() {
							int fps = Player.this.scenario.getFps();
							int end = Player.this.scenario.getCountMaxKeyFrames();
							for (int time = Player.this.scenario.getCurrentTime(); time < end && Player.this.isPlaying(); time++) {
								Player.this.scenario.setCurrentTime(time);
								try {
									Thread.sleep(1000 / fps);
								} catch (Exception e) {
									e.printStackTrace();
								}
							}

							Player.this.setPlays(false);
							Player.this.update(null, null);
						}
					};

					Player.this.thread.start();

				} else {

					Player.this.setPlays(false);
					Player.this.update(null, null);
				}

			}

		});

		this.nextButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Player.this.scenario.setCurrentTime(Player.this.scenario.getCurrentTime() + 1);

			}

		});

		this.fastForwardButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (!Player.this.isPlaying()) {
					Player.this.setPlays(true);
					Player.this.update(null, null);

					Player.this.thread = new Thread() {
						@Override
						public void run() {
							int end = Player.this.scenario.getCountMaxKeyFrames();
							for (int time = Player.this.scenario.getCurrentTime(); time < end && Player.this.isPlaying(); time++) {
								Player.this.scenario.setCurrentTime(time);
								try {
									Thread.sleep(10);
								} catch (Exception e) {
									e.printStackTrace();
								}
							}

							Player.this.setPlays(false);
							Player.this.update(null, null);
						}
					};

					Player.this.thread.start();

				} else {

					Player.this.setPlays(false);
					Player.this.update(null, null);
				}

			}

		});

		this.endButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Player.this.scenario.setCurrentTime(Player.this.scenario.getCountMaxKeyFrames() - 1);

			}
		});

	}

	public void setScenario(Scenario s) {
		if (this.scenario != null) {
			this.scenario.deleteObserver(this);
		}

		this.scenario = s;
		this.scenario.addObserver(this);

		this.update(null, null);
	}

	public synchronized void setPlays(boolean b) {
		this.plays = b;
	}

	public synchronized boolean isPlaying() {
		return this.plays;
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		boolean playing = this.isPlaying();

		if (!playing) {

			int time = this.scenario.getCurrentTime();
			int maxTime = this.scenario.getCountMaxKeyFrames() - 1;

			this.beginButton.setEnabled((time > 0));
			this.previousButton.setEnabled((time > 0));
			this.rewindButton.setEnabled((time > 0));
			this.nextButton.setEnabled((time < maxTime));
			this.fastForwardButton.setEnabled((time < maxTime));
			this.endButton.setEnabled((time < maxTime));
			this.playPauseButton.setEnabled((time < maxTime));
			this.playPauseButton.setIcon(Player.this.playImg);

		} else {

			this.playPauseButton.setIcon(Player.this.pauseImg);
			this.playPauseButton.setEnabled(true);
			this.beginButton.setEnabled(false);
			this.previousButton.setEnabled(false);
			this.rewindButton.setEnabled(false);
			this.nextButton.setEnabled(false);
			this.fastForwardButton.setEnabled(false);
			this.endButton.setEnabled(false);
		}
	}
}
