package eeit163.model;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;



public interface MessageRepository extends JpaRepository<Message, Integer> {


	@Query("from Message where sender = :m and target = :n order by creationtime")
	public List<Message> findBySender(@Param(value = "m") Integer senderId,@Param(value = "n") Integer targetId);

	@Query("from Message where sender = :n and target = :m order by creationtime")
	public List<Message> findByTarget(@Param(value = "m") Integer senderId,@Param(value = "n") Integer targetId);
	
	@Query("from Message where (sender = :m and target = :n) or (sender = :n and target = :m) order by creationtime")
	public List<Message> findByChat(@Param(value = "m") Integer senderId,@Param(value = "n") Integer targetId);
	
	@Modifying
	@Query(value = "update Message set sender = :sender ,target = :target ,type = :type ,content = :content ,creationtime = :creationtime where id = :id") 
	public Integer updateById(@Param("id") Integer id, 
			@Param("sender") Integer sender,
			@Param("target") Integer target,
			@Param("type") Integer type,
			@Param("content") String content,
			@Param("creationtime") Date creationtime
			);

}