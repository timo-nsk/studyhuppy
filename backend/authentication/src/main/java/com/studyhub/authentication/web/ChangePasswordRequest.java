package com.studyhub.authentication.web;

import lombok.Data;

@Data
public class ChangePasswordRequest {
		private String userId;
		private String oldPw;
		private String newPw;
		private String username;
		private String mail;

		public void clearPasswords() {
			oldPw = null;
			newPw = null;
		}
}
