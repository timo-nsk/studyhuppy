package com.studyhub.authentication.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

public class ProfilbildDecoder {

	public static String decode(String profilbildBase64, String userId) {
		try {
			if(profilbildBase64.contains(",")) {
				profilbildBase64 = profilbildBase64.split(",")[1];
			}

			byte[] imageBytes = Base64.getDecoder().decode(profilbildBase64);

			String pathString = "authentication/user-data/uploads/%s.png".formatted(userId);
			Path path = Paths.get(pathString);
			Files.createDirectories(path.getParent());
			Files.write(path, imageBytes);

			return pathString;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
