package gr.ntua.ece.medialab.minesweeper.game;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import gr.ntua.ece.medialab.minesweeper.types.Minefield;

import java.text.SimpleDateFormat;

import javafx.scene.control.Label;

import javafx.application.Platform;

public class CountdownTimer extends Label {
	
	private Timer timer;
	private static SimpleDateFormat displayFormat = new SimpleDateFormat("mm:ss");
	
	private boolean isRunning;
	private int secondsLeft = -1;
	
	private TimerTask updateTask;

	public CountdownTimer() {
		super();

		setText("00:00");
		setStyle("-fx-font-family: monospace; -fx-font-size: 20px; -fx-text-fill: red; -fx-background-color: black; -fx-border-color: black;");
	}
	
	public void set(int sec) {
		stop();
		timer = new Timer();
		
		updateTask = new TimerTask() {
			@Override
			public void run() {
				if(secondsLeft > 0) {
					secondsLeft--;
					Platform.runLater(() -> CountdownTimer.this.setText(displayFormat.format(new Date(secondsLeft * 1000))));
				} else {
					Minefield.blowUp();
					stop();
				}
			}
		};

		secondsLeft = sec + 1;

		updateTask.run();
	}
	
	public void start() {
		if(timer != null) {
			timer.schedule(updateTask, 0, 1000);
			isRunning = true;
		}
	}

	public void stop() {
		if(timer != null) {
			timer.cancel();
			timer.purge();
		}
		
		timer = null;
		isRunning = false;
	}
	
	public void setTextColor(String color) {
		setStyle("-fx-font-family: monospace; -fx-font-size: 20px; -fx-text-fill: " + color + "; -fx-background-color: black; -fx-border-color: black;");
	}

	public boolean isRunning() {
		return isRunning;
	}
	
}