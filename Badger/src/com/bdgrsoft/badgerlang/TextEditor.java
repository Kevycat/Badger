package com.bdgrsoft.badgerlang;

import java.util.ArrayList;
import java.util.List;

public class TextEditor {

	private List<String> lines;
	public int line = -1;
	public static final int lineHeight = 20;
	public static final int textOffset = 18;

	public TextEditor(int numLines) {
		lines = new ArrayList<String>();
		for (int i = 0; i < numLines; i++) {
			lines.add("");
		}
	}
	
	private String[] getLines(int length) {
		while(length >= lines.size())
			lines.add("");
		String[] stringLines = new String[lines.size()];
		for(int i = 0; i < lines.size(); i++)
			stringLines[i] = lines.get(i);
		return stringLines;
	}

	public String[] getLines() {
		return (String[]) lines.toArray();
	}

	public String getLine(int i) {
		return getLines(i)[i];
	}

	public char getChar(int line, int character) {
		char[] chars = new char[1];
		getLines(line)[line].getChars(character, character, chars, 0);
		return chars[0];
	}

}
