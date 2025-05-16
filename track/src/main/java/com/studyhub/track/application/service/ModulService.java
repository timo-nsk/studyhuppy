package com.studyhub.track.application.service;

import com.studyhub.jwt.JWTService;
import com.studyhub.track.adapter.db.modul.ModulDto;
import com.studyhub.track.adapter.db.modul.ModulMapper;
import com.studyhub.track.adapter.mail.KlausurReminderDto;
import com.studyhub.track.domain.model.modul.Modul;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.IntStream;

@Service
public class ModulService {
	private final Logger log = LoggerFactory.getLogger(ModulService.class);
	private final ModulRepository repo;
	private final JWTService jwtService;

	public ModulService(ModulRepository modulRepository, JWTService jwtService) {
		this.repo = modulRepository;
		this.jwtService = jwtService;
	}

	public void saveNewModul(Modul modul) {
		repo.save(modul);
		log.info("Saved new modul '%s'".formatted(modul.getName()));
	}

	public void saveAllNewModule(List<Modul> modulList) {
		repo.saveAll(modulList);
	}

	public List<Modul> findAll() {
		return repo.findAll();
	}

	public void updateSeconds(UUID fachId, int seconds) {
		repo.updateSecondsByUuid(fachId, seconds);
		log.info("updated modul with id:%s to seconds=%s".formatted(fachId.toString(), String.valueOf(seconds)));
	}

	public void deleteModul(UUID fachId) {
		repo.deleteByUuid(fachId);
		log.warn("deleted modul with id:%s".formatted(fachId.toString()));
	}

	public void resetModulTime(UUID fachId) {
		updateSeconds(fachId, 0);
		log.info("reseted modul time to 0 with id:%s".formatted(fachId.toString()));
	}

	public List<ModulDto> findActiveModuleByUsername(boolean active, String username) {
		return repo.findActiveModuleByUsername(active, username)
				.stream().map(ModulMapper::toModulDto)
				.sorted(Comparator.comparing(ModulDto::secondsLearned).reversed())
				.toList();
	}

	public void activateModul(UUID fachId) {
		repo.setActive(fachId, true);
		log.info("activated modul id:%s".formatted(fachId.toString()));
	}

	public void deactivateModul(UUID fachId) {
		repo.setActive(fachId, false);
		log.info("deactivated modul id:%s".formatted(fachId.toString()));
	}

	public void addTime(UUID fachId, String time) {
		TimeConverter tc = new TimeConverter();
		int addSeconds = tc.timeToSeconds(time);
		int alreadyLearned = repo.findSecondsById(fachId);
		int newSeconds = addSeconds + alreadyLearned;
		updateSeconds(fachId, newSeconds);
		log.info("added seconds=%s to modul with id:%s".formatted(addSeconds, fachId.toString()));
	}

	public int getSecondsForId(UUID fachId) {
		return repo.findSecondsById(fachId);
	}

	public String findModulNameByFachid(UUID fachId) {
		return repo.findByUuid(fachId).getName();
	}

	public Integer getTotalStudyTimeForUser(String username) {
		Integer totalStudyTime = repo.getTotalStudyTime(username);

		if(totalStudyTime == null) {
			return null;
		}
		return totalStudyTime;
	}

	public Map<Integer, Integer> getTotalStudyTimePerFachSemester(String username) {
		List<Modul> allModule = repo.findByUsername(username);
		Set<Integer> fachsemesterMap = new HashSet<>();
		Map<Integer, Integer> res = new HashMap<>();

		for (Modul modul : allModule) {
			int fachsemester = modul.getSemesterstufe();
			fachsemesterMap.add(fachsemester);
		}

		for (Integer thisFachsemester : fachsemesterMap) {
			List<Modul> moduleWithThisFachsemester = allModule.stream()
					.filter(e -> Objects.equals(e.getSemesterstufe(), thisFachsemester))
					.toList();

			int sumSecondsLearned = moduleWithThisFachsemester.stream()
					.flatMapToInt(e -> IntStream.of(e.getSecondsLearned()))
					.sum();

			res.put(thisFachsemester, sumSecondsLearned);
		}

		return res;
	}

	public Integer countActiveModules(String username) {
		return repo.countActiveModules(username);
	}

	public Object countNotActiveModules(String username) {
		return repo.countNotActiveModules(username);
	}

	public String findModulWithMaxSeconds(String username) {
		return repo.findByMaxSeconds(username);
	}

	public String findModulWithMinSeconds(String username) {
		return repo.findByMinSeconds(username);
	}

	public Map<UUID, String> getModuleMap() {
		List<Modul> all = repo.findAll();
		Map<UUID, String> map = new HashMap<>();

		for (Modul m: all) {
			map.put(m.getFachId(), m.getName());
		}

		return map;
	}

	public boolean isModulDbHealthy() {
		log.info("fetching database health status");
		return repo.isModulDbHealthy();
	}

	public void addKlausurDate(UUID uuid, LocalDate date, String time) {
		repo.addKlausurDate(uuid, LocalDateTime.of(date, TimeConverter.getLocalTimeFromString(time)));
	}

	public String dateStringGer(String zeitString) {
		try {
			DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			LocalDateTime dateTime = LocalDateTime.parse(zeitString, inputFormatter);

			DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm")
					.withLocale(Locale.GERMANY);
			return dateTime.format(outputFormatter);
		} catch (Exception e) {
			return "Ungültiges Datums-/Zeitformat";
		}
	}

	public int daysLeftToKlausur(UUID fachId) {
		String dateString = repo.findKlausurDateByFachId(fachId);
		DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime end = LocalDateTime.parse(dateString, inputFormatter);

		LocalDateTime start = LocalDateTime.now();

		return computeDayDifference(start, end);
	}

	public int computeDayDifference(LocalDateTime start, LocalDateTime end) {
		return (int) ChronoUnit.DAYS.between(start, end);
	}

	public String findKlausurDateByFachId(UUID fachId) {
		String dateString = repo.findKlausurDateByFachId(fachId);

		if(dateString != null) {
			return dateStringGer(dateString);
		} else {
			return null;
		}
	}

	public Modul findByFachId(UUID uuid) {
		return repo.findByUuid(uuid);
	}

	public List<KlausurReminderDto> findModuleWithoutKlausurDate(List<String> users) {
		List<KlausurReminderDto> res = new LinkedList<>();

		for (String user : users) {
			List<Modul> userModule = repo.findActiveModuleByUsername(true, user);

			if(userModule.isEmpty()) continue;

			for (Modul modul : userModule) {
				if (!modul.klausurDatumEingetragen()) {
					KlausurReminderDto dto = new KlausurReminderDto(modul.getName(), user);
					res.add(dto);
				}
			}
		}
		return res;
	}

	public List<Modul> findAllByUsername(String username) {
		return repo.findByUsername(username);
	}

	public boolean modulCanBeCreated(String username, int limit) {
		List<Modul> module = repo.findByUsername(username);
        return module.size() < limit;
    }
}
