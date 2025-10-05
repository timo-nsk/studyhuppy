package com.studyhub.authentication.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * Class checks on specific dates and updates the current semester = semester + 1 when the date is met.
 */
@Service
public class UserSemesterService {

	// TODO: What if sever is not up on this date?
	@Scheduled(cron = "0 0 0 1 4 *")
	public void summerSemesterUpdater() {

	}

	@Scheduled(cron = "0 0 0 1 10 *")
	public void winterSemesterUpdater() {

	}
}
