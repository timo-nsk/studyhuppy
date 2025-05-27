package com.studyhub.mail.adapter.db;


import com.studyhub.mail.domain.model.MailGesendetEvent;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

public interface MailGesendetEventRepository extends CrudRepository<MailGesendetEvent, Integer> {
	@Query("select 1")
	Integer isMailDbHealthy();
}
