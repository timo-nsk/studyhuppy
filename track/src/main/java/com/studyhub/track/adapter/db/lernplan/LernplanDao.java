package com.studyhub.track.adapter.db.lernplan;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface LernplanDao extends CrudRepository<LernplanDto, Long> {
	List<LernplanDto> findAllByUsername(String username);

	@Modifying
	@Query("delete from lernplan where fach_id = :fachId")
	void deleteByFachId(UUID fachId);

	@Query("select * from lernplan where username = :username and is_active = true")
	LernplanDto findActiveByUsername(@Param("username") String username);
}
