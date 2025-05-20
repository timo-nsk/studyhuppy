package com.studyhub.kartei.adapter.db;

import com.studyhub.kartei.adapter.db.dto.StapelDto;
import com.studyhub.kartei.domain.model.Stapel;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StapelDao extends CrudRepository<StapelDto, Integer> {
	List<StapelDto> findAll();
	Optional<StapelDto> findByFachId(UUID fachId);
	List<StapelDto> findByUsername(String username);

	@Query("select count(*) from stapel")
	int countAll();

	@Modifying
	@Query("DELETE FROM stapel WHERE id IN (SELECT ks.id FROM stapel ks JOIN karteikarte k ON ks.id = k.karteikarte_set WHERE k.fach_id = :karteToDelete)")
	void deleteKarteikarteByFachId(@Param("karteToDelete") UUID karteToDelete);


	@Modifying
	@Query("delete from stapel where fach_id = :fachId")
	void deleteByFachId(@Param("fachId") UUID fachId);

	@Modifying
	@Query("update stapel set name = :newSetName where fach_id = :karteiSetId")
	void changeSetName(@Param("karteiSetId") UUID karteiSetId,
	                   @Param("newSetName") String newSetName);

	@Modifying
	@Query("update stapel set lern_intervalle = :newLernIntervalle where fach_id = :stapelFachId")
	void updateLernIntervalle(@Param("newLernIntervalle") String newLernIntervalle,
	                          @Param("stapelFachId") UUID stapelFachId);

	@Query("select 1")
	Integer isStapelDbHealthy();

	@Query("select * from stapel where (select * from karteikarte where faellig_am < :dateToday)")
    Stapel findByFachIdWithFaelligeKarten(@Param("stapelfachId") UUID uuid,
										  @Param("dateToday") LocalDateTime now);
}
