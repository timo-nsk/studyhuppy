package com.studyhub.track.adapter.web;

public record ModulRequest(
		String modulName,
		Integer creditPoints,
		Integer kontaktzeitStunden,
		Integer selbststudiumStunden
) {
}
