package com.studyhub.track.adapter.web.controller.request.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AddTimeRequest {
	private String fachId;
	private String time;
}
