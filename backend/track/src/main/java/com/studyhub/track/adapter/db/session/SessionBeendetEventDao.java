package com.studyhub.track.adapter.db.session;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.UUID;

public interface SessionBeendetEventDao extends CrudRepository<SessionBeendetEventDto, UUID> {
	List<SessionBeendetEventDto> findAllByUsername(String username);
	SessionBeendetEventDto findByEventId(UUID eventId);

	@Modifying
	@Query("DELETE FROM session_beendet_event WHERE username = :username")
	int deleteAllByUsername(@Param("username") String username);
}
