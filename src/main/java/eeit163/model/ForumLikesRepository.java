package eeit163.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ForumLikesRepository extends JpaRepository<ForumLikes, Integer> {

	@Query("from ForumLikes where postId = :n and type = :m")
	public List<ForumLikes> findByPost(@Param(value = "n") Integer postId, @Param(value = "m") String type);
	
	@Query("from ForumLikes where memberId = :n and type = :m")
	public List<ForumLikes> findByMember(@Param(value = "n") Integer memberId, @Param(value = "m") String type);
}