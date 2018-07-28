package com.bdgrsoft.badgerlang;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;

public class EditorGraphics {
	
	
	private static Cursor editCursor = Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR);

	public static void paint(Graphics g, TextEditor text, Dimension d) {
		Badger.getMainEditor().Cursor(editCursor);
		
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, d.width, d.height);

		for (int i = 0; i < d.height / TextEditor.lineHeight; i++) {
			g.setColor(Color.GRAY);
			g.fillRect(0, i * TextEditor.lineHeight, d.width, 1);
			
			byte[] line = text.getLine(i).getBytes();
			g.setColor(Color.black);
			g.drawBytes(line, 0, line.length, 0, i * TextEditor.lineHeight);
		}
	}

}
