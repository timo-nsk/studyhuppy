package com.studyhub.track.adapter.db.modul;

import com.studyhub.track.application.service.ModulRepository;
import com.studyhub.track.application.service.NoModulPresentException;
import com.studyhub.track.domain.model.modul.Modul;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.studyhub.track.adapter.db.modul.ModulMapper.toModul;

@Repository
public class ModulRepositoryImpl implements ModulRepository {

	private final ModulDao modulDao;

	public ModulRepositoryImpl(ModulDao modulDao) {
		this.modulDao = modulDao;
	}

	@Override
	public Integer findSecondsById(UUID fachId) {
		return modulDao.findSecondsById(fachId);
	}

	@Override
	public Modul save(Modul modul) {
		return toModul(modulDao.save(ModulMapper.toModulDto(modul)));
	}

	@Override
	public List<Modul> findAll() {
		return modulDao.findAll().stream().map(e -> toModul(e)).toList();
	}

	@Override
	public void deleteByUuid(UUID fachId) {
		modulDao.deleteByFachId(fachId);
	}

	@Override
	public Modul findByUuid(UUID fachId) {
		Optional<ModulDto> dto = modulDao.findByFachId(fachId).stream().findAny();
		if(dto.isEmpty()) throw new NoModulPresentException("no modul present with id: %s".formatted(fachId.toString()));
		return toModul(dto.get());
	}

	@Override
	public String findByMaxSeconds() {
		Optional<String> modulName = modulDao.findMaxSeconds();
		return modulName.orElse(null);
	}

	@Override
	public String findByMinSeconds() {
		Optional<String> modulName = modulDao.findMinSeconds();
		return modulName.orElse(null);
	}

	@Override
	public boolean isModulDbHealthy() {
		Integer result = modulDao.isModulDbHealthy();
		return result != null;
	}

	@Override
	public List<Modul> saveAll(List<Modul> modulList) {
		List<ModulDto> saved = (List<ModulDto>) modulDao.saveAll(modulList.stream().map(ModulMapper::toModulDto).toList());
		return saved.stream().map(ModulMapper::toModul).toList();
	}

	@Override
	public void updateSecondsByUuid(UUID fachid, int seconds) {
		modulDao.updateSecondsByUuid(fachid, seconds);
	}

	@Override
	public List<Modul> findByActiveIsTrue() {
		return modulDao.findByActiveIsTrue().stream().map(ModulMapper::toModul).toList();
	}

	@Override
	public List<Modul> findByActiveIsFalse() {
		return modulDao.findByActiveIsFalse().stream().map(ModulMapper::toModul).toList();
	}

	@Override
	public List<Modul> findByUsername(String username) {
		return modulDao.findByUsername(username).stream().map(ModulMapper::toModul).toList();
	}

	@Override
	public Integer getTotalStudyTime(String username) {
		return modulDao.sumAllSeconds(username);
	}

	@Override
	public Integer countActiveModules(String username) {
		return modulDao.countByActiveIsTrue(username);
	}

	@Override
	public Integer countNotActiveModules(String username) {
		return modulDao.countByActiveIsFalse(username);
	}

	@Override
	public void setActive(UUID fachId, boolean active) {
		modulDao.setActive(fachId, active);
	}

	@Override
	public void addKlausurDate(UUID fachId, LocalDateTime klausurDate) {
		modulDao.addKlausurDate(fachId, klausurDate);
	}

	@Override
	public List<Modul> findActiveModuleByUsername(boolean active, String username) {
		return modulDao.findAll().stream()
				.filter(m -> m.active() == active)
				.filter(m -> m.username().equals(username))
				.map(ModulMapper::toModul)
				.toList();
	}

	@Override
	public String findKlausurDateByFachId(UUID fachId) {
		return modulDao.findKlausurDateByFachId(fachId);
	}
}