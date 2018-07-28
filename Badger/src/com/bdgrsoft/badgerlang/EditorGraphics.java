package com.bdgrsoft.badgerlang;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

public class EditorGraphics {

	private static Cursor editCursor = Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR);

	public static void paint(Graphics g, TextEditor text, Dimension d) {
		Badger.getMainEditor().Cursor(editCursor);

		g.setColor(Color.WHITE);
		g.fillRect(0, 0, d.width, d.height);
		g.setColor(Color.BLACK);
		g.fillRect(TextEditor.textOffset - 2, 0, 1, d.height);
		
		g.setFont(Font.getFont("Courier New"));
		for (int i = 0; i < d.height / TextEditor.lineHeight && i * TextEditor.lineHeight < d.height; i++) {
			g.setColor(Color.GRAY);
			g.fillRect(0, i * TextEditor.lineHeight, d.width, 1);

			byte[] line = text.getLine(i).getBytes();
			byte[] lineNumber = ("" + i).getBytes();
			g.setColor(Color.black);
			g.drawBytes(line, 0, line.length, TextEditor.textOffset + 5, i * TextEditor.lineHeight);
			g.drawBytes(lineNumber, 0, lineNumber.length, 1, i * TextEditor.lineHeight);
		}
		g.fillRect(1 + TextEditor.textOffset, text.line * TextEditor.lineHeight, 2, 20);
	}

}
