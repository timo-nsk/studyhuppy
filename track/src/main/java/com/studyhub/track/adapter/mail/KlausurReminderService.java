package com.studyhub.track.adapter.mail;

import com.studyhub.track.application.service.ModulRepository;
import com.studyhub.track.domain.model.modul.Modul;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class KlausurReminderService {

	private final ModulRepository repo;

	public KlausurReminderService(ModulRepository repo) {
		this.repo = repo;
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
}
