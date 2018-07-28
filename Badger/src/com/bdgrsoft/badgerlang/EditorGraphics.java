package com.bdgrsoft.badgerlang;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

public class EditorGraphics {

	public static void paint(Graphics g, TextEditor text, Dimension d) {
		g.setColor(new Color(230, 230, 230));
		g.fillRect(0, 0, d.width, d.height);
		
		g.setColor(new Color(120, 120, 120));
		for(int i = 0; i < d.height / TextEditor.lineHeight; i++) {
			g.fillRect(0, i * TextEditor.lineHeight, d.width, 1);
		}
	}

}
