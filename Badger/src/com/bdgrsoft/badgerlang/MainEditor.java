package com.bdgrsoft.badgerlang;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;

public class MainEditor extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;

	Thread thread;
	JFrame frame;

	boolean running = false;

	public MainEditor() {
		thread = new Thread(this, "Editor Graphics");
		thread.start();
	}

	private void render() {
		//render buttons, etc...
	}

	@Override
	public void run() {
		running = true;

		frame = new JFrame("Badgerlang editor");
		frame.setLocationRelativeTo(null);
		frame.setMinimumSize(new Dimension(300, 200));
		frame.setPreferredSize(new Dimension(1080, 720));
		frame.setIconImage(new ImageIcon("Res/Icon.png").getImage());

		frame.getContentPane().add(new JTextPane(), BorderLayout.CENTER, 0);
		frame.pack();

		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				boolean close = JOptionPane.showConfirmDialog(frame, "Are you sure you want to close this window?",
						"Close confirmation", JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION;

				if (close) {
					running = false;
					frame.remove(0);
					frame.dispose();
				}
			}
		});

		frame.setVisible(true);

		while (running)
			render();

		try {
			thread.join();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
	}

	public void Cursor(Cursor cursor) {
		setCursor(cursor);
	}

}
