package com.studyhub.track.adapter.db.modul;

import com.studyhub.track.domain.model.modul.ModulGelerntEvent;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface ModulGelerntEventDao extends CrudRepository<ModulGelerntEventDto, Integer> {
	@Query("SELECT COALESCE(SUM(seconds_learned), 0) " +
			"FROM modul_gelernt_event " +
			"WHERE date_gelernt = :date AND username = :username AND modul_id = :modulId")
	int getSumSecondsLearned(@Param("date") LocalDate date,
	                         @Param("username") String username,
	                         @Param("modulId") UUID modulId);

	List<ModulGelerntEvent> findAllByUsername(String username);
}
