package com.studyhub.track.adapter.db.lernplan;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface LernplanDao extends CrudRepository<LernplanDto, Long> {

	@Query("select * from lernplan where username = :username and is_active = true")
	LernplanDto findActiveByUsername(@Param("username") String username);
}
