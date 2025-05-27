package com.studyhub.track.adapter.web;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AddTimeRequest {
	private String fachId;
	private String time;
}
