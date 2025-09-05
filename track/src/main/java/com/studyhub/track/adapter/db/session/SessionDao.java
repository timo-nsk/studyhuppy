package com.studyhub.track.adapter.db.session;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SessionDao extends CrudRepository<SessionDto, Long> {
	List<SessionDto> findAllByUsername(String username);
	Optional<SessionDto> findByFachId(UUID fachId);

    @Modifying
    @Query("delete from session where fach_id = :fachId")
    long deleteByFachId(@Param("fachId") UUID fachId);
}
