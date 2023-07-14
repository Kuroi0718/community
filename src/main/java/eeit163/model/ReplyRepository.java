package eeit163.model;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;



public interface ReplyRepository extends JpaRepository<Reply, Integer> {
	
	@Query("from Reply where articleId = :n order by id asc")
	public List<Reply> findByArticleIdOrderByIdAsc(@Param(value = "n") Integer articleId);
	
	@Query("from Reply where status = '2' ")
	public List<Reply> findByLock();

	@Modifying
	@Query(value = "update Reply set authorId = :authorId ,articleId = :articleId ,content = :content ,likes = :likes ,replyDate = :replyDate where id = :id") 
	public Integer updateById(@Param("id") Integer id, 
			@Param("authorId") Integer authorId, 
			@Param("articleId") Integer articleId,
			@Param("content") String content,
			@Param("likes") Integer likes,
			@Param("replyDate") Date replyDate
			);


}