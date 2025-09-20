package com.studyhub.track.adapter.db.lernplan;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.relational.core.sql.LockMode;
import org.springframework.data.relational.repository.Lock;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LernplanDao extends CrudRepository<LernplanDto, Long> {
	List<LernplanDto> findAllByUsername(String username);
	Optional<LernplanDto> findByFachId(UUID fachId);

	@Modifying
	@Query("delete from lernplan where fach_id = :fachId")
	int deleteByFachId(UUID fachId);

	@Lock(LockMode.PESSIMISTIC_WRITE)
	@Query("select * from lernplan where username = :username and is_active = true")
	LernplanDto findActiveByUsername(@Param("username") String username);

	@Modifying
	@Query("update lernplan set is_active = :isActive where fach_id = :fachId")
	void setIsActiveOfLernplan(@Param("fachId") UUID fachId,
	                           @Param("isActive") boolean isActive);

	@Modifying
	@Query("update lernplan set is_active = false where username = :username")
	void deactivateAllByUsername(@Param("username") String username);
}
