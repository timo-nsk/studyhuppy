package com.studyhub.track.adapter.db.modul;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ModulDao extends CrudRepository<ModulDto, Integer> {
	Optional<ModulDto> findByFachId(UUID fachId);
	List<ModulDto> findAll();
	List<ModulDto> findByActiveIsTrue();
	List<ModulDto> findByActiveIsFalse();
	Integer countByActiveIsTrue();
	Integer countByActiveIsFalse();

	@Query("select sum(seconds_learned) from modul")
	Integer sumAllSeconds();

	@Modifying
	@Transactional
	@Query("delete from modul where fach_id = :fachId")
	void deleteByFachId(@Param("fachId") UUID fachId);

	@Modifying
	@Transactional
	@Query("update modul set seconds_learned = :seconds where fach_id = :fachId")
	void updateSecondsByUuid(@Param("fachId") UUID fachId,
	                         @Param("seconds") int seconds);

	@Modifying
	@Transactional
	@Query("update modul set active = :active where fach_id = :fachId")
	void setActive(@Param("fachId") UUID fachId,
	               @Param("active") boolean active);

	@Query("select seconds_learned from modul where fach_id = :fachId")
	Integer findSecondsById(UUID fachId);

	@Query("select name from modul where seconds_learned = (select max(seconds_learned) from modul) order by name asc limit 1")
	Optional<String> findMaxSeconds();

	@Query("select name from modul where seconds_learned = (select min(seconds_learned) from modul) order by name asc limit 1")
	Optional<String> findMinSeconds();


	@Query("select 1")
	Integer isModulDbHealthy();

	List<ModulDto> findByUsername(String username);

	List<ModulDto> findActiveModuleByUsername(boolean active, String username);

	@Modifying
	@Query("update modul set klausur_date = :klausurDate where fach_id = :fachId")
	void addKlausurDate(@Param("fachId") UUID fachId,
	                    @Param("klausurDate") LocalDateTime klausurDate);

	@Query("select klausur_date from modul where fach_id = :fachId")
	String findKlausurDateByFachId(@Param("fachId") UUID fachId);
}
