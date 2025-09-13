package com.studyhub.kartei.adapter.db;

import com.studyhub.kartei.adapter.db.dto.StapelDto;
import com.studyhub.kartei.adapter.db.mapper.StapelMapper;
import com.studyhub.kartei.domain.model.Karteikarte;
import com.studyhub.kartei.domain.model.Stapel;
import com.studyhub.kartei.service.application.StapelRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class StapelRepositoryImpl implements StapelRepository {

	private StapelDao dao;

	public StapelRepositoryImpl(StapelDao dao) {
		this.dao = dao;
	}

	@Override
	public Stapel save(Stapel set) {
		Integer existingDbKey =
				dao.findByFachId(set.getFachId()).map(StapelDto::id).orElse(null);
		return StapelMapper.toStapel(dao.save(StapelMapper.toStapelDto(existingDbKey, set)));
	}

	@Override
	public List<Stapel> findAll() {
		return dao.findAll().stream().map(StapelMapper::toStapel).collect(Collectors.toList());
	}

	@Override
	public Stapel findByFachId(UUID fachId) {
		Optional<StapelDto> set = dao.findByFachId(fachId);
		return set.map(StapelMapper::toStapel).orElse(null);
	}

	@Override
	public void deleteKarteikarteByFachid(UUID karteToDelete) {
		dao.deleteKarteikarteByFachId(karteToDelete);
	}

	@Override
	public int countAllByUsername(String username) {
		return dao.countAllByUsername(username);
	}

	@Override
	public void deleteKarteiSet(String karteiSetId) {
		dao.deleteByFachId(UUID.fromString(karteiSetId));
	}

	@Override
	public void changeSetName(String karteiSetId, String newSetName) {
		dao.changeSetName(UUID.fromString(karteiSetId), newSetName);
	}

	@Override
	public void resetAllKarteikarten(String karteiSetId) {
		//TODO: eine resetete karte soll wieder die lernstufe des stapels haben
	}

	@Override
	public void deleteAllKarteikartenOfSet(String karteiSetId) {
		StapelDto set = dao.findByFachId(UUID.fromString(karteiSetId)).get();
		List<Karteikarte> karteikarten = set.karteikarten();
		karteikarten.clear();
		dao.save(set);
	}

	@Override
	public void updateLernIntervalle(String newLernIntervalle, UUID stapelFachId) {
		dao.updateLernIntervalle(newLernIntervalle, stapelFachId);
	}

	@Override
	public int updateSetWithNewKarteikarte(String karteiSetId, Karteikarte karteikarte) {
		Stapel stapel = StapelMapper.toStapel(dao.findByFachId(UUID.fromString(karteiSetId)).get());
		karteikarte.setLernstufen(stapel.getLernIntervalle());
		stapel.addKarteikarte(karteikarte);
		Stapel s = save(stapel);
		if(s == null) return 0;
		return 1;
	}

	@Override
	public List<Stapel> findByUsername(String username) {
		return dao.findByUsername(username).stream().map(StapelMapper::toStapel).toList();
	}

	@Override
	public boolean isStapelDbHealthy() {
		Integer result = dao.isStapelDbHealthy();
		return result != null;
	}

	@Override
	public void deleteStapelByFachId(UUID fachId) {
		dao.deleteByFachId(fachId);
	}
}
