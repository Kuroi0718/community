package eeit163.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommentRepository  extends JpaRepository<Comment, Integer> {
	
	@Query(value="select * from Comment ", nativeQuery = true)
	public List<Comment> findAllComments();
	
	@Query(value="select * from Comment where authorId = :n and type = :m ", nativeQuery = true)
	public List<Comment> findMyComments(@Param("n")Integer id,@Param("m")String type);
	
	@Query(value="select * from Comment where ratingTargetId = :n and type = :m ", nativeQuery = true)
	public List<Comment> findComments(@Param("n")Integer id,@Param("m")String type);
	
	@Query(value="select * from Comment where serialId in (:n) and type = '買家評價' ", nativeQuery = true)
	public List<Comment> findCommentsBySerialIdArray(@Param("n")String[] serialIdArray);
	
}
