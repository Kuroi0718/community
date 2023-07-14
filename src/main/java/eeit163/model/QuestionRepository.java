package eeit163.model;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
public interface QuestionRepository extends JpaRepository<Question, Integer>{
	
	
	@Query(value="select * from Question where productId = :n order by askDate desc", nativeQuery = true)
	public List<Question> findProductQuestions(@Param("n")Integer productId);
	
	@Query(value="select * from Question where ownerId = :n and answerDate is null ", nativeQuery = true)
	public List<Question> findUnansweredQuestions(@Param("n")Integer ownerId);
	
	@Modifying
	@Query(value="update Question set answer = :n , answerDate = :d where questionId = :m ", nativeQuery = true)
	public void answerByQuestionId(@Param("n")String answer,@Param("d")Date date, @Param("m")Integer questionId);
		
//	@Modifying
//	@Query(value = "update Question set content = :n where memberId = :m and myCartProductId = :p", nativeQuery = true) 
//	public Integer updateQuestion();

}
