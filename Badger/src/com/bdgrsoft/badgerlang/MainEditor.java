package com.bdgrsoft.badgerlang;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

public class MainEditor extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;

	private Thread thread;
	private JFrame frame;
	private JTextPane pane;
	private ImageIcon icon;
	private File file = null;

	boolean running = false;

	public MainEditor() {
		thread = new Thread(this, "Editor Graphics");
		thread.start();
	}

	@Override
	public void run() {
		running = true;
		icon = new ImageIcon("Res/Icon.png");
		
		frame = new JFrame("Untitled | Badgerlang editor");
		frame.setLocationRelativeTo(null);
		frame.setMinimumSize(new Dimension(300, 200));
		frame.setPreferredSize(new Dimension(1080, 720));
		frame.setIconImage(icon.getImage());
		frame.getContentPane().add(buildGUI(new JPanel(new GridBagLayout())), 0);
		frame.pack();

		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				String fileName = "Untitled";
				if (file != null)
					fileName = file.getName();

				int save = JOptionPane.showConfirmDialog(frame, "Save file " + fileName + "?", "Save",
						JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, icon);
				if (save == JOptionPane.YES_OPTION) {
					if(save()) {
						running = false;
						frame.remove(0);
						frame.dispose();
						System.exit(0);
					}
				} else if (save == JOptionPane.NO_OPTION) {
					running = false;
					frame.remove(0);
					frame.dispose();
					System.exit(0);
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
		System.exit(0);
	}

	private boolean saveAs() {
		JFileChooser chooser = new JFileChooser();
		int option = chooser.showOpenDialog(frame);
		if(option == JFileChooser.APPROVE_OPTION) {
			file = chooser.getSelectedFile();
			if (file == null)
				return false;
			save();
			return true;
		}
		return false;
	}

	private boolean save() {
		String fileName = "Untitled";
		if (file != null)
			fileName = file.getName();
		frame.setTitle(fileName + " | Badgerlang editor");
		
		if (file == null)
			return saveAs();
		else {
			if (!file.exists()) {
				try {
					file.createNewFile();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}

			FileOutputStream output = null;
			try {
				output = new FileOutputStream(file);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}

			try {
				output.write(pane.getText().getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			try {
				output.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return true;
		}
	}
	
	private void open() {
		JFileChooser chooser = new JFileChooser();
		int option = chooser.showOpenDialog(frame);
		if(option == JFileChooser.APPROVE_OPTION) {
			if (file == null)
				return;
			
			file = chooser.getSelectedFile();
			
			FileInputStream stream = null;
			
			try {
				stream = new FileInputStream(file);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			
			byte[] bytes = new byte[(int) file.length()];
			try {
				stream.read(bytes);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			String data = "";
			for(Byte b: bytes)
				data += b;
			pane.setText(data);
		}
	}

	private JPanel buildGUI(JPanel panel) {
		GridBagConstraints textConstraints = new GridBagConstraints(0, 1, 3, 1, 1.0, 1.0, GridBagConstraints.LINE_START,
				GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0);
		JTextPane textComponent = new JTextPane();
		JScrollPane textScrollComponent = new JScrollPane(textComponent);
		panel.add(textScrollComponent, textConstraints, 0);

		GridBagConstraints saveButtonConstraints = new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.FIRST_LINE_START, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0);
		JButton saveButtonComponent = new JButton("Save");
		saveButtonComponent.setBackground(new Color(240, 240, 240));
		panel.add(saveButtonComponent, saveButtonConstraints, 1);
		saveButtonComponent.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				save();
			}
		});

		GridBagConstraints saveAsButtonConstraints = new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.FIRST_LINE_START, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0);
		JButton saveAsButtonComponent = new JButton("Save As");
		saveAsButtonComponent.setBackground(new Color(240, 240, 240));
		panel.add(saveAsButtonComponent, saveAsButtonConstraints, 1);
		saveAsButtonComponent.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				saveAs();
			}
		});
		
		//set VERTICAL to BOTH when new button is added
		GridBagConstraints openButtonConstraints = new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.FIRST_LINE_START, GridBagConstraints.VERTICAL, new Insets(0, 0, 0, 0), 0, 0);
		JButton openButtonComponent = new JButton("Open");
		openButtonComponent.setBackground(new Color(240, 240, 240));
		panel.add(openButtonComponent, openButtonConstraints, 2);
		openButtonComponent.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				open();
			}
		});
		pane = textComponent;
		return panel;
	}

	private void render() {
		// render buttons, etc...
	}

	public void Cursor(Cursor cursor) {
		setCursor(cursor);
	}

}