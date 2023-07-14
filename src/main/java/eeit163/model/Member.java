package eeit163.model;

import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "Member")
public class Member {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "username", columnDefinition = "nvarchar(50)",unique = true)
	private String username;

	@Column(name = "password", columnDefinition = "nvarchar(20)")
	private String password;
	
	@Column(name = "level", columnDefinition = "nvarchar(20)")
	private String level;
	
	@Column(name = "tel")
	private String tel;
	
	@Column(name = "gender", columnDefinition = "nvarchar(10)")
	private String gender;
	
	@Column(name = "friend", columnDefinition = "nvarchar(max)")       //好友名單
	private String friend="";
	
	@Column(name = "invitation", columnDefinition = "nvarchar(max)")   //受邀
	private String invitation="";
	
	@Column(name = "invite", columnDefinition = "nvarchar(max)")       //邀請中
	private String invite="";
	
	 @JSONField(format = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "birthday")
	private Date birthday;
	
	@Column(name = "email", columnDefinition = "nvarchar(50)")
	private String email;
	
	@Lob 
	@Column(name = "photo")
	private byte[] photo;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss EEEE", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "creationdate")
	private Date creationdate;
	@PrePersist
	public void onCreate() {
		if(creationdate == null) {
			creationdate = new Date();
		}
	}
	
//////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Column(name = "record", columnDefinition = "nvarchar(MAX)")
	private String record = "";
	
	@Column(name = "myLikes", columnDefinition = "nvarchar(MAX)")
	private String myLikes = "";
	
	@Column(name = "purchaseHistory", columnDefinition = "nvarchar(MAX)")
	private String purchaseHistory = "";
	
	@Column(name = "buyScore", columnDefinition = "nvarchar(MAX)")
	private Integer buyScore = 0;
	
	@Column(name = "buyScoreTimes", columnDefinition = "nvarchar(MAX)")
	private Integer buyScoreTimes = 0;
	
	@Column(name = "sellScore", columnDefinition = "nvarchar(MAX)")
	private Integer sellScore = 0;
	
	@Column(name = "sellScoreTimes", columnDefinition = "nvarchar(MAX)")
	private Integer sellScoreTimes = 0;
	
	public String getBirthdayString() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(birthday);
	}
	
	public Member() {
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Date getCreationdate() {
		return creationdate;
	}
	public void setCreationdate(Date creationdate) {
		this.creationdate = creationdate;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public byte[] getPhoto() {
		return photo;
	}
	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}
	
	public String getFriend() {
		return friend;
	}
	public void setFriend(String friend) {
		this.friend = friend;
	}
	
	public String getInvite() {
		return invite;
	}
	public void setInvite(String invite) {
		this.invite = invite;
	}
	public String getInvitation() {
		return invitation;
	}
	public void setInvitation(String invitation) {
		this.invitation = invitation;
	}
	public String generateBase64Image()
	{
	    return Base64.getEncoder().encodeToString(this.photo);
	}
	
	public String simpleBirthday() {
		SimpleDateFormat df = new SimpleDateFormat ("yyyy-MM-dd");
		return df.format(birthday);
	}
	public String simpleCreationdate() {
		SimpleDateFormat df = new SimpleDateFormat ("yyyy-MM-dd");
		return df.format(creationdate);
	}
	public String getRecord() {
		return record;
	}
	public void setRecord(String record) {
		this.record = record;
	}
	
	public String getMyLikes() {
		return myLikes;
	}
	public void setMyLikes(String myLikes) {
		this.myLikes = myLikes;
	}
	public String getPurchaseHistory() {
		return purchaseHistory;
	}
	public void setPurchaseHistory(String purchaseHistory) {
		this.purchaseHistory = purchaseHistory;
	}
	public Integer getBuyScore() {
		return buyScore;
	}
	public void setBuyScore(Integer buyScore) {
		this.buyScore = buyScore;
	}
	public Integer getBuyScoreTimes() {
		return buyScoreTimes;
	}
	public void setBuyScoreTimes(Integer buyScoreTimes) {
		this.buyScoreTimes = buyScoreTimes;
	}
	public Integer getSellScore() {
		return sellScore;
	}
	public void setSellScore(Integer sellScore) {
		this.sellScore = sellScore;
	}
	public Integer getSellScoreTimes() {
		return sellScoreTimes;
	}
	public void setSellScoreTimes(Integer sellScoreTimes) {
		this.sellScoreTimes = sellScoreTimes;
	}
	
	
	
	

}