package com.studyhub.kartei.adapter.db;

import com.studyhub.kartei.adapter.db.dto.KarteikarteGelerntEventDto;
import com.studyhub.kartei.domain.model.KarteikarteGelerntEvent;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface KarteikarteGelerntEventDao extends CrudRepository<KarteikarteGelerntEventDto, Integer> {
	List<KarteikarteGelerntEventDto> findByKarteikarteId(UUID karteikarteId);
	List<KarteikarteGelerntEvent> findByStapelId(UUID stapelId);

	@Query("select sum(seconds_needed) from karteikarte_gelernt_event where karteikarte_id = :karteikarteId")
	int sumOfSecondsNeededForKarteikarte(@Param("karteikarteId") UUID karteikarteId);

	@Query("select count(*) from karteikarte_gelernt_event where karteikarte_id = :karteikarteId")
	int coundByKarteikarteId(@Param("karteikarteId") UUID karteikarteId);
}
