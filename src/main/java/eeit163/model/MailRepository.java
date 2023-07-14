package eeit163.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MailRepository extends JpaRepository<Mail, Integer> {

	@Query("from Mail where email = :n ")
	public List<Mail> findByEmail(@Param(value = "n") String email);

}
