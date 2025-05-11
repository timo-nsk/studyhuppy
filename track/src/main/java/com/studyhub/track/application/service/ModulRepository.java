package com.studyhub.track.application.service;

import com.studyhub.track.domain.model.modul.Modul;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface ModulRepository {
	Modul save(Modul modul);
	Modul findByUuid(UUID fachId);
	List<Modul> findAll();
	List<Modul> findByActiveIsTrue();
	List<Modul> findByActiveIsFalse();
	List<Modul> findByUsername(String username);
	List<Modul> saveAll(List<Modul> modulList);
	Integer getTotalStudyTime(String username);
	Integer countActiveModules();
	Integer findSecondsById(UUID fachId);
	Integer countNotActiveModules();
	String findByMinSeconds();
	String findByMaxSeconds();
	boolean isModulDbHealthy();
	void deleteByUuid(UUID fachId);
	void updateSecondsByUuid(UUID fachid, int seconds);
	void setActive(UUID fachId, boolean active);
	void addKlausurDate(UUID fachId, LocalDateTime klausurDate);

	List<Modul> findActiveModuleByUsername(boolean active, String username);

	String findKlausurDateByFachId(UUID fachId);
}
