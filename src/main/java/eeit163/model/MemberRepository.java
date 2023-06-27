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

	@Query("from Member where username like %:n% order by id asc")
	public List<Member> findByUsernameLikeOrderByIdAsc(@Param(value = "n") String username);
	@Query("from Member where username like %:n% order by username asc")
	public List<Member> findByUsernameLikeOrderByUsernameAsc(@Param(value = "n") String username);
	@Query("from Member where username like %:n% order by level asc")
	public List<Member> findByUsernameLikeOrderByLevelAsc(@Param(value = "n") String username);
	@Query("from Member where username like %:n% order by creationdate asc")
	public List<Member> findByUsernameLikeOrderByCreationdateAsc(@Param(value = "n") String username);
	@Query("from Member where username like %:n% order by birthday asc")
	public List<Member> findByUsernameLikeOrderByBirthdayAsc(@Param(value = "n") String username);
	@Query("from Member where username like %:n% order by gender asc")
	public List<Member> findByUsernameLikeOrderByGenderAsc(@Param(value = "n") String username);
	@Query("from Member where username like %:n% order by tel asc")
	public List<Member> findByUsernameLikeOrderByTelAsc(@Param(value = "n") String username);
	@Query("from Member where username like %:n% order by email asc")
	public List<Member> findByUsernameLikeOrderByEmailAsc(@Param(value = "n") String username);

	@Query("from Member where username like %:n% order by id desc")
	public List<Member> findByUsernameLikeOrderByIdDesc(@Param(value = "n") String username);
	@Query("from Member where username like %:n% order by username desc")
	public List<Member> findByUsernameLikeOrderByUsernameDesc(@Param(value = "n") String username);
	@Query("from Member where username like %:n% order by level desc")
	public List<Member> findByUsernameLikeOrderByLevelDesc(@Param(value = "n") String username);
	@Query("from Member where username like %:n% order by creationdate desc")
	public List<Member> findByUsernameLikeOrderByCreationdateDesc(@Param(value = "n") String username);
	@Query("from Member where username like %:n% order by birthday desc")
	public List<Member> findByUsernameLikeOrderByBirthdayDesc(@Param(value = "n") String username);
	@Query("from Member where username like %:n% order by gender desc")
	public List<Member> findByUsernameLikeOrderByGenderDesc(@Param(value = "n") String username);
	@Query("from Member where username like %:n% order by tel desc")
	public List<Member> findByUsernameLikeOrderByTelDesc(@Param(value = "n") String username);
	@Query("from Member where username like %:n% order by email desc")
	public List<Member> findByUsernameLikeOrderByEmailDesc(@Param(value = "n") String username);

	
	
	@Query("from Member where creationdate = :l ")
	public List<Member> findByCreationdate(@Param(value = "l") Date creationdate);
	
	@Query("from Member where level = :n ")
	public List<Member> findByLevel(@Param(value = "n") String level);

	@Modifying
	@Query(value = "update Member set username = :username ,password = :password ,level = :level ,email = :email ,photo = :photo ,tel = :tel ,gender = :gender ,friend = :friend ,invitation = :invitation ,invite = :invite ,birthday = :birthday ,creationdate = :creationdate where id = :id") 
	public Integer updateById(@Param("id") Integer id, 
			@Param("username") String username,
			@Param("password") String password,
			@Param("level") String level,
			@Param("email") String email,
			@Param("photo") byte[] photo,
			@Param("tel") String tel,
			@Param("gender") String gender,
			@Param("friend") String friend,
			@Param("invitation") String invitation,
			@Param("invite") String invite,
			@Param("birthday") Date birthday,
			@Param("creationdate") Date creationdate
			);

	public List<Member> findByCreationdateOrderByIdDesc(Date creationdate);

	@Query(value = "select top(1) * from member order by creationdate Desc", nativeQuery = true)
	public Member findFirst();

}
