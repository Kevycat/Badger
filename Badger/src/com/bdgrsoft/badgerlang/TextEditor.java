package com.bdgrsoft.badgerlang;

public class TextEditor {

	private String[] lines;
	public static final int lineHeight = 20;

	public TextEditor(int numLines) {
		lines = new String[numLines];
		for (int i = 0; i < numLines; i++) {
			lines[i] = "";
		}
	}

	public String[] getLines() {
		return lines;
	}

	public String getLine(int i) {
		return lines[i];
	}

	public char getChar(int line, int character) {
		char[] chars = new char[1];
		lines[line].getChars(character, character, chars, 0);
		return chars[0];
	}

}
