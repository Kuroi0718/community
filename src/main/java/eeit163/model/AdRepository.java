package eeit163.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AdRepository extends JpaRepository<Ad, Integer> {

	@Query("from Ad where status = :n ")
	public List<Ad> findByStatus(@Param(value = "n") Integer status);

	@Modifying
	@Query(value = "update Ad set status = :status where id = :id")
	public Integer updateById(@Param("id") Integer id, @Param("status") Integer status);

}