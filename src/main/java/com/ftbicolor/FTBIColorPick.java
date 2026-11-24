package com.ftbicolor;

public final class FTBIColorPick {
	private static boolean ctrlPick;

	private FTBIColorPick() {
	}

	public static boolean isCtrlPick() {
		return ctrlPick;
	}

	public static void setCtrlPick(boolean value) {
		ctrlPick = value;
	}
}
