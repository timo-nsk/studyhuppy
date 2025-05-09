package com.studyhub.track.domain.model.modul;

import com.studyhub.track.domain.model.semester.SemesterPhase;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Lerntage {
	private boolean mondays;
	private boolean tuesdays;
	private boolean wednesdays;
	private boolean thursdays;
	private boolean fridays;
	private boolean saturdays;
	private boolean sundays;
	private SemesterPhase semesterPhase;

	public boolean[] getAllLerntage() {
		return new boolean[] {mondays, tuesdays, wednesdays, thursdays, fridays, saturdays, sundays};
	}
}
