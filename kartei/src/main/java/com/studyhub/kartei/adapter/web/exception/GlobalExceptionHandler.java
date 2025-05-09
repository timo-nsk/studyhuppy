package com.studyhub.kartei.adapter.web.exception;

import com.studyhub.kartei.adapter.web.form.InvalidAntwortenListFormatException;
import com.studyhub.kartei.service.application.KarteikarteUpdateException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(StapelDoesNotExistException.class)
	public String setDoesNotExistExceptionHandler(StapelDoesNotExistException ex, Model model, HttpServletResponse resp) {
		resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
		model.addAttribute("errorMessage", ex.getMessage());
		model.addAttribute("httpStatus", resp.getStatus());
		model.addAttribute("reason", HttpStatus.valueOf(resp.getStatus()).getReasonPhrase());
		return "error";
	}

	@ExceptionHandler(KarteikarteDoesNotExistException.class)
	public String karteikarteDoesNotExistExceptionHandler(KarteikarteDoesNotExistException ex, Model model, HttpServletResponse resp) {
		resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
		model.addAttribute("errorMessage", ex.getMessage());
		model.addAttribute("httpStatus", resp.getStatus());
		model.addAttribute("reason", HttpStatus.valueOf(resp.getStatus()).getReasonPhrase());
		return "error";
	}

	@ExceptionHandler(InvalidSetFragetypRequestException.class)
	public String invalidSetFragetypRequestExceptionHandler(InvalidSetFragetypRequestException ex, Model model, HttpServletResponse resp) {
		System.out.println("blub");

		resp.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
		model.addAttribute("errorMessage", ex.getMessage());
		model.addAttribute("httpStatus", resp.getStatus());
		model.addAttribute("reason", HttpStatus.valueOf(resp.getStatus()).getReasonPhrase());
		return "error";
	}

	@ExceptionHandler(KarteikarteUpdateException.class)
	public String karteikarteUpdateExceptionHandler(KarteikarteUpdateException ex, Model model, HttpServletResponse resp) {
		resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		model.addAttribute("errorMessage", ex.getMessage());
		model.addAttribute("httpStatus", resp.getStatus());
		model.addAttribute("reason", HttpStatus.valueOf(resp.getStatus()).getReasonPhrase());
		return "error";
	}

	@ExceptionHandler(KarteikarteEditierenFehlerException.class)
	public String karteikarteEditierenFehlerExceptionHandler(KarteikarteEditierenFehlerException ex, Model model, HttpServletResponse resp) {
		resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		model.addAttribute("errorMessage", ex.getMessage());
		model.addAttribute("httpStatus", resp.getStatus());
		model.addAttribute("reason", HttpStatus.valueOf(resp.getStatus()).getReasonPhrase());
		return "error";
	}

	@ExceptionHandler(InvalidAntwortenListFormatException.class)
	public String invalidAntwortenListFormatExceptionHandler(InvalidAntwortenListFormatException ex, Model model, HttpServletResponse resp) {
		resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		model.addAttribute("errorMessage", ex.getMessage());
		model.addAttribute("httpStatus", resp.getStatus());
		model.addAttribute("reason", HttpStatus.valueOf(resp.getStatus()).getReasonPhrase());
		return "error";
	}
}