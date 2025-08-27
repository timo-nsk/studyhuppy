package com.studyhub.track.adapter.db.lernplan;

import com.studyhub.track.application.service.LernplanRepository;

public class LernplanRepositoryImpl implements LernplanRepository {

	private final LernplanDao lernplanDao;

	public LernplanRepositoryImpl(LernplanDao lernplanDao) {
		this.lernplanDao = lernplanDao;
	}
}
