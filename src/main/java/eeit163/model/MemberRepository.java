package eeit163.model;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;



public interface MemberRepository extends JpaRepository<Member, Integer> {


	@Query("from Member where username = :n ")
	public List<Member> findByUsername(@Param(value = "n") String username);

	@Query("from Member where username like %:n% ")
	public List<Member> findByUsernameLike(@Param(value = "n") String username);

	@Query("from Member where creationdate = :l ")
	public List<Member> findByCreationdate(@Param(value = "l") Date creationdate);
	
	@Query("from Member where level = :n ")
	public List<Member> findByLevel(@Param(value = "n") String level);

	@Modifying
	@Query(value = "update Member set username = :username ,password = :password ,level = :level ,email = :email ,photo = :photo where id = :id") 
	public Integer updateById(@Param("id") Integer id, 
			@Param("username") String username,
			@Param("password") String password,
			@Param("level") String level,
			@Param("email") String email,
			@Param("photo") byte[] photo
			);

	public List<Member> findByCreationdateOrderByIdDesc(Date creationdate);

	@Query(value = "select top(1) * from member order by creationdate Desc", nativeQuery = true)
	public Member findFirst();

}
