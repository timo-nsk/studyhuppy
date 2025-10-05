package com.studyhub.authentication.web.controller;

import com.studyhub.authentication.service.ProfilbildDecoder;

public record ProfilbildRequest(
		String profilbildBase64
) {

	public String decode(String userId) {
		return ProfilbildDecoder.decode(profilbildBase64, userId);
	}
}
