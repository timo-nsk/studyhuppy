package com.studyhub.track.domain.model;

public class ModulSecondsConverter {

	public static String convertToString(int secondsLearned) {
		int days = secondsLearned / (24 * 3600);
		secondsLearned %= (24 * 3600);
		int hours = secondsLearned / 3600;
		secondsLearned %= 3600;
		int minutes = secondsLearned / 60;
		secondsLearned %= 60;
		int remainingSeconds = secondsLearned;
		return String.format("%02dd %02dh %02dm %02ds", days, hours, minutes, remainingSeconds);
	}
}
