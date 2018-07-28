package com.bdgrsoft.badgerlang;

import java.awt.AWTEvent;
import java.awt.Canvas;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.AWTEventListener;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class MainEditor extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;

	Thread thread;
	JFrame frame;
	TextEditor textEditor;

	boolean running = false;

	public MainEditor() {
		thread = new Thread(this, "Editor Graphics");
		thread.start();
	}

	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		EditorGraphics.paint(g, textEditor, frame.getSize());
		g.dispose();
		bs.show();
	}

	@Override
	public void run() {
		running = true;

		frame = new JFrame("Badgerlang editor");
		frame.setLocationRelativeTo(null);
		frame.setMinimumSize(new Dimension(300, 200));
		frame.setPreferredSize(new Dimension(1080, 720));
		frame.setIconImage(new ImageIcon("Res/Icon.png").getImage());

		frame.add(this);
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
				}
			}
		});

		textEditor = new TextEditor(frame.getHeight() / TextEditor.lineHeight);
		frame.getToolkit().addAWTEventListener(new AWTEventListener() {

			@Override
			public void eventDispatched(AWTEvent event) {
				MouseEvent mEvent = (MouseEvent) event;
				if(mEvent.getID() == MouseEvent.MOUSE_RELEASED)
					textEditor.line = mEvent.getY() / TextEditor.lineHeight;
			}
		}, AWTEvent.MOUSE_EVENT_MASK);

		frame.setVisible(true);
		this.createBufferStrategy(3);

		while (running)
			render();

		frame.dispose();

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
