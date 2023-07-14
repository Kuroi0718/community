package eeit163.model;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface HistoryRepository extends JpaRepository<History, Integer> {
	
	@Query(value="select * from History where productId = :n ", nativeQuery = true)
	public List<History> findHistoryByProductId(@Param("n")Integer productId);
	
	@Query(value="select * from History where serialId = :n ", nativeQuery = true)
	public List<History> findHistoryBySerialId(@Param("n")String serialId);
	
	@Query(value="select * from History where ownerId = :n ", nativeQuery = true)
	public List<History> findHistoryByOwnerId(@Param("n")Integer ownerId);
	
	@Query(value="select * from History where ownerId = :n and orderDate > :m ", nativeQuery = true)
	public List<History> findHistoryByOwnerIdTime(@Param("n")Integer ownerId,@Param("m")Date date);

	@Query(value="select * from History where ownerId = :n and productName = :m", nativeQuery = true)
	public List<History> findHistoryByOwnerIdAndProductName(@Param("n")Integer ownerId,@Param("m")String productName);
	
	@Query(value="select * from History where ownerId = :n and productName = :m and orderDate > :t ", nativeQuery = true)
	public List<History> findHistoryByOwnerIdAndProductNameTime(@Param("n")Integer ownerId,@Param("m")String productName,@Param("t")Date date);
	
	@Query(value="select top 1 productId from History where serialId = :n order by price desc", nativeQuery = true)
	public Integer getHistoryIdsBySerialId(@Param("n")String serialId);
	
	@Modifying
	@Query(value="delete from History where serialId = :n ", nativeQuery = true)
	public void deleteCancelledHistory(@Param("n") String serialId);
}
