package com.studyhub.kartei.adapter.db;

import com.studyhub.kartei.adapter.db.dto.KarteikarteDto;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface KarteikarteDao extends CrudRepository<KarteikarteDto, Integer> {
	Optional<KarteikarteDto> findByFachId(UUID fachId);

	@Modifying
	@Query("update karteikarte set lernstufen = :lernstufen where fach_id = :karteId")
	void updateLernstufen(@Param("lernstufen") String lernstufen,
	                      @Param("karteId") UUID karteId);
}
