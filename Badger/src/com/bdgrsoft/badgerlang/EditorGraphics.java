package com.bdgrsoft.badgerlang;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

public class EditorGraphics {

	public static void paint(Graphics g, Dimension d) {
		g.setColor(new Color(230, 230, 230));
		g.fillRect(0, 0, d.width, d.height);
	}

}
