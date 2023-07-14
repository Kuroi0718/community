package eeit163.model;


import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "Comment")
public class Comment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "commentId")
	private Integer commentId;
	
	@Column(name = "ratingTargetId", columnDefinition = "int")
	private Integer ratingTargetId;
	
	@Column(name = "content", columnDefinition = "nvarchar(MAX)")
	private String content;
	
	@Column(name = "serialId", columnDefinition = "nvarchar(50)")
	private String serialId;
	
	@Column(name = "type", columnDefinition = "nvarchar(20)")
	private String type;
	
	@Column(name = "authorId", columnDefinition = "int")
	private Integer authorId;
	
	@Column(name = "score", columnDefinition = "int")
	private Integer score;
	
	@JsonFormat(pattern = "yyyy/MM/dd")
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "commentDate")
	private Date commentDate;

	public String getCommentDateString() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(commentDate);
	}
	
	
	
	public String getType() {
		return type;
	}



	public void setType(String type) {
		this.type = type;
	}



	public Integer getCommentId() {
		return commentId;
	}

	public void setCommentId(Integer commentId) {
		this.commentId = commentId;
	}

	public Integer getRatingTargetId() {
		return ratingTargetId;
	}

	public void setRatingTargetId(Integer ratingTargetId) {
		this.ratingTargetId = ratingTargetId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getAuthorId() {
		return authorId;
	}

	public void setAuthorId(Integer authorId) {
		this.authorId = authorId;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public Date getCommentDate() {
		return commentDate;
	}

	public void setCommentDate(Date commentDate) {
		this.commentDate = commentDate;
	}

	public String getSerialId() {
		return serialId;
	}

	public void setSerialId(String serialId) {
		this.serialId = serialId;
	}

	
	
	
	

}