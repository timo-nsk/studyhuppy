package com.studyhub.track.adapter.web.controller.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
public class GeneralStatisticsDto {
	private Integer totalStudyTime;
	private Map<Integer, Integer> totalStudyTimePerSemester;
	private Integer durchschnittlicheLernzeitProTag;
	private Integer numberActiveModules;
	private Integer numberNotActiveModules;
	private String maxStudiedModul;
	private String minStudiedModul;
}
