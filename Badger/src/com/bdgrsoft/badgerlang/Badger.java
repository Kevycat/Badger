package com.bdgrsoft.badgerlang;

public class Badger {
	
	private static MainEditor mainEditor;

	public static void main(String[] args) {
		mainEditor = new MainEditor();
	}
	
	public static MainEditor getMainEditor() {
		return mainEditor;
	}

}
