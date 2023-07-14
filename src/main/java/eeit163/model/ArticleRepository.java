package eeit163.model;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;



public interface ArticleRepository extends JpaRepository<Article, Integer> {
	
	@Query("from Article where title = :n ")
	public List<Article> findByTitle(@Param(value = "n") String title);

	@Query("from Article where title like %:n% order by id asc")
	public List<Article> findByTitleLikeOrderByIdAsc(@Param(value = "n") String title);
	@Query("from Article where title like %:n% order by authorId asc")
	public List<Article> findByTitleLikeOrderByAuthorIdAsc(@Param(value = "n") String title);
	@Query("from Article where title like %:n% order by authorName asc")
	public List<Article> findByTitleLikeOrderByAuthorNameAsc(@Param(value = "n") String title);
	@Query("from Article where title like %:n% order by title asc")
	public List<Article> findByTitleLikeOrderByTitleAsc(@Param(value = "n") String title);
	@Query("from Article where title like %:n% order by content asc")
	public List<Article> findByTitleLikeOrderByContentAsc(@Param(value = "n") String title);
	@Query("from Article where title like %:n% order by likes asc")
	public List<Article> findByTitleLikeOrderByLikesAsc(@Param(value = "n") String title);
	@Query("from Article where title like %:n% order by postDate asc")
	public List<Article> findByTitleLikeOrderByPostDateAsc(@Param(value = "n") String title);
	
	@Query("from Article where title like %:n% order by id desc")
	public List<Article> findByTitleLikeOrderByIdDesc(@Param(value = "n") String title);
	@Query("from Article where title like %:n% order by authorId desc")
	public List<Article> findByTitleLikeOrderByAuthorIdDesc(@Param(value = "n") String title);
	@Query("from Article where title like %:n% order by authorName desc")
	public List<Article> findByTitleLikeOrderByAuthorNameDesc(@Param(value = "n") String title);
	@Query("from Article where title like %:n% order by title desc")
	public List<Article> findByTitleLikeOrderByTitleDesc(@Param(value = "n") String title);
	@Query("from Article where title like %:n% order by content desc")
	public List<Article> findByTitleLikeOrderByContentDesc(@Param(value = "n") String title);
	@Query("from Article where title like %:n% order by likes desc")
	public List<Article> findByTitleLikeOrderByLikesDesc(@Param(value = "n") String title);
	@Query("from Article where title like %:n% order by postDate desc")
	public List<Article> findByTitleLikeOrderByPostDateDesc(@Param(value = "n") String title);
	
	@Query("from Article where content like %:n% order by id asc")
	public List<Article> findByContentLikeOrderByIdAsc(@Param(value = "n") String content);
	@Query("from Article where content like %:n% order by authorId asc")
	public List<Article> findByContentLikeOrderByAuthorIdAsc(@Param(value = "n") String content);
	@Query("from Article where content like %:n% order by authorName asc")
	public List<Article> findByContentLikeOrderByAuthorNameAsc(@Param(value = "n") String content);
	@Query("from Article where content like %:n% order by title asc")
	public List<Article> findByContentLikeOrderByTitleAsc(@Param(value = "n") String content);
	@Query("from Article where content like %:n% order by content asc")
	public List<Article> findByContentLikeOrderByContentAsc(@Param(value = "n") String content);
	@Query("from Article where content like %:n% order by likes asc")
	public List<Article> findByContentLikeOrderByLikesAsc(@Param(value = "n") String content);
	@Query("from Article where content like %:n% order by postDate asc")
	public List<Article> findByContentLikeOrderByPostDateAsc(@Param(value = "n") String content);
	
	@Query("from Article where content like %:n% order by id desc")
	public List<Article> findByContentLikeOrderByIdDesc(@Param(value = "n") String content);
	@Query("from Article where content like %:n% order by authorId desc")
	public List<Article> findByContentLikeOrderByAuthorIdDesc(@Param(value = "n") String content);
	@Query("from Article where content like %:n% order by authorName desc")
	public List<Article> findByContentLikeOrderByAuthorNameDesc(@Param(value = "n") String content);
	@Query("from Article where content like %:n% order by title desc")
	public List<Article> findByContentLikeOrderByTitleDesc(@Param(value = "n") String content);
	@Query("from Article where content like %:n% order by content desc")
	public List<Article> findByContentLikeOrderByContentDesc(@Param(value = "n") String content);
	@Query("from Article where content like %:n% order by likes desc")
	public List<Article> findByContentLikeOrderByLikesDesc(@Param(value = "n") String content);
	@Query("from Article where content like %:n% order by postDate desc")
	public List<Article> findByContentLikeOrderByPostDateDesc(@Param(value = "n") String content);

	
	@Query("from Article where authorName like %:n% order by id asc")
	public List<Article> findByAuthorNameLikeOrderByIdAsc(@Param(value = "n") String authorName);
	@Query("from Article where authorName like %:n% order by authorId asc")
	public List<Article> findByAuthorNameLikeOrderByAuthorIdAsc(@Param(value = "n") String authorName);
	@Query("from Article where authorName like %:n% order by authorName asc")
	public List<Article> findByAuthorNameLikeOrderByAuthorNameAsc(@Param(value = "n") String authorName);
	@Query("from Article where authorName like %:n% order by title asc")
	public List<Article> findByAuthorNameLikeOrderByTitleAsc(@Param(value = "n") String authorName);
	@Query("from Article where authorName like %:n% order by content asc")
	public List<Article> findByAuthorNameLikeOrderByContentAsc(@Param(value = "n") String authorName);
	@Query("from Article where authorName like %:n% order by likes asc")
	public List<Article> findByAuthorNameLikeOrderByLikesAsc(@Param(value = "n") String authorName);
	@Query("from Article where authorName like %:n% order by postDate asc")
	public List<Article> findByAuthorNameLikeOrderByPostDateAsc(@Param(value = "n") String authorName);
	
	@Query("from Article where authorName like %:n% order by id desc")
	public List<Article> findByAuthorNameLikeOrderByIdDesc(@Param(value = "n") String authorName);
	@Query("from Article where authorName like %:n% order by authorId desc")
	public List<Article> findByAuthorNameLikeOrderByAuthorIdDesc(@Param(value = "n") String authorName);
	@Query("from Article where authorName like %:n% order by authorName desc")
	public List<Article> findByAuthorNameLikeOrderByAuthorNameDesc(@Param(value = "n") String authorName);
	@Query("from Article where authorName like %:n% order by title desc")
	public List<Article> findByAuthorNameLikeOrderByTitleDesc(@Param(value = "n") String authorName);
	@Query("from Article where authorName like %:n% order by content desc")
	public List<Article> findByAuthorNameLikeOrderByContentDesc(@Param(value = "n") String authorName);
	@Query("from Article where authorName like %:n% order by likes desc")
	public List<Article> findByAuthorNameLikeOrderByLikesDesc(@Param(value = "n") String authorName);
	@Query("from Article where authorName like %:n% order by postDate desc")
	public List<Article> findByAuthorNameLikeOrderByPostDateDesc(@Param(value = "n") String authorName);

	
	@Query("from Article where authorId = :n ")
	public List<Article> findByAuthorId(@Param(value = "n") Integer authorId);
	
	@Query("from Article where postDate = :l ")
	public List<Article> findByPostDate(@Param(value = "l") Date postDate);
	
	@Query("from Article where status = '2' ")
	public List<Article> findByLock();
	
	@Modifying
	@Query(value = "update Article set authorId = :authorId ,title = :title ,authorName = :authorName ,content = :content ,likes = :likes ,postDate = :postDate where id = :id") 
	public Integer updateById(@Param("id") Integer id, 
			@Param("authorId") Integer authorId, 
			@Param("authorName") String authorName, 
			@Param("title") String title,
			@Param("content") String content,
			@Param("likes") Integer likes,
			@Param("postDate") Date postDate
			);


}