package eeit163.model;

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
@Table(name = "Question")
public class Question {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "questionId")
	private Integer questionId;
	
	@Column(name = "productId", columnDefinition = "int")
	private Integer productId;
	
	@Column(name = "ownerId", columnDefinition = "int")
	private Integer ownerId;

	@Column(name = "askerId", columnDefinition = "int")
	private Integer askerId;
	
	@Column(name = "content", columnDefinition = "nvarchar(MAX)")
	private String content="";
	
	@Column(name = "answer", columnDefinition = "nvarchar(MAX)")
	private String answer="";
	
	@JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss EEEE", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "askDate")
	private Date askDate;
	
	@JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss EEEE", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "answerDate")
	private Date answerDate;
	
	
	
	
	public Integer getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(Integer ownerId) {
		this.ownerId = ownerId;
	}

	public Integer getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Integer questionId) {
		this.questionId = questionId;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public Integer getAskerId() {
		return askerId;
	}

	public void setAskerId(Integer askerId) {
		this.askerId = askerId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public Date getAskDate() {
		return askDate;
	}

	public void setAskDate(Date askDate) {
		this.askDate = askDate;
	}

	public Date getAnswerDate() {
		return answerDate;
	}

	public void setAnswerDate(Date answerDate) {
		this.answerDate = answerDate;
	}
	
	

}