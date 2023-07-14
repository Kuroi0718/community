package eeit163.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CancelRepository extends JpaRepository<Cancel, Integer> {
	
	@Query(value="select * from Cancel ", nativeQuery = true)
	public List<Cancel> findCancelledTable();
	
	@Query(value="select * from Cancel where serialId = :n ", nativeQuery = true)
	public List<Cancel> findCancelBySerialId(@Param("n")String serialId);
}
