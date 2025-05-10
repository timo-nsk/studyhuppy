package com.studyhub.authentication.db;

import com.studyhub.authentication.model.AppUser;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
public interface AppUserRepository extends CrudRepository<AppUser, Integer> {

	AppUser findByUsername(String username);

	@Query("select semester from users where username = :username")
	Integer findSemesterByUsername(@Param("username") String username);

	@Modifying
	@Query("DELETE FROM users WHERE username = :username")
	void deleteByUsername(@Param("username") String username);

	@Modifying
	@Query("UPDATE users SET notification_subscription = :activate WHERE username = :username")
	void updateNotificationSubscription(@Param("activate") Boolean activate,
	                                    @Param("username") String username);

	@Query("SELECT notification_subscription FROM users WHERE username = :username")
	Boolean getNotificationSubscription(@Param("username") String username);

	@Modifying
	@Query("UPDATE users SET password = :encodedPassword WHERE user_id = :userId")
	void updatePassword(@Param("encodedPassword") String encodedPassword,
	                    @Param("userId") UUID userId);

	@Query("select 1")
	Integer isUserDbHealthy();

	@Query("select * from users where notification_subscription = true")
	List<AppUser> getUsersWithNotificationOn();

	@Modifying
	@Query("update users set mail = :newMail where user_id = :userId")
    void updateMailByUserId(@Param("newMail")String newMail,
							@Param("userId") UUID userId);

	AppUser findByUserId(UUID userId);
}
