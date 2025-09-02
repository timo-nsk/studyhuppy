package com.studyhub.track.adapter.web.controller.api;

import java.util.Map;

public class GeneralStatisticsDtoBuilder {
	private GeneralStatisticsDto obj;

	public GeneralStatisticsDtoBuilder builder() {
		obj = new GeneralStatisticsDto();
		return this;
	}

	public GeneralStatisticsDtoBuilder withTotalStudyTime(Integer totalStudyTime) {
		obj.setTotalStudyTime(totalStudyTime);
		return this;
	}

	public GeneralStatisticsDtoBuilder withTotalStudyTimePerSemester(Map<Integer, Integer> totalStudyTimePerSemester) {
		obj.setTotalStudyTimePerSemester(totalStudyTimePerSemester);
		return this;
	}

	public GeneralStatisticsDtoBuilder withDurchschnittlicheLernzeitProTag(Integer durchschnittlicheLernzeitProTag) {
		obj.setDurchschnittlicheLernzeitProTag(durchschnittlicheLernzeitProTag);
		return this;
	}

	public GeneralStatisticsDtoBuilder withNumberActiveModules(Integer numberActiveModules) {
		obj.setNumberActiveModules(numberActiveModules);
		return this;
	}

	public GeneralStatisticsDtoBuilder withNumberNotActiveModules(Integer numberNotActiveModules) {
		obj.setNumberNotActiveModules(numberNotActiveModules);
		return this;
	}

	public GeneralStatisticsDtoBuilder withMaxStudiedModul(String maxStudiedModul) {
		obj.setMaxStudiedModul(maxStudiedModul);
		return this;
	}

	public GeneralStatisticsDtoBuilder withMinStudiedModul(String minStudiedModul) {
		obj.setMinStudiedModul(minStudiedModul);
		return this;
	}

	public GeneralStatisticsDto build() {
		return obj;
	}


}
