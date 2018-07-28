package com.bdgrsoft.badgerlang;

public class TextEditor {
	
	private String[] lines;
	public static final int lineHeight = 20;
	
	public TextEditor(int numLines) {
		lines = new String[numLines];
		for(int i = 0; i < numLines; i++)
			lines[i] = "";
	}

}
