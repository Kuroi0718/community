package eeit163.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Mail")
public class Mail {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "email", columnDefinition = "nvarchar(50)")
	private String email;
	
	@Column(name = "first")
	private Integer first;
	
	@Column(name = "second")
	private Integer second;
	
	@Column(name = "third")
	private Integer third;
	
	@Column(name = "forth")
	private Integer forth;
	
	@Column(name = "fifth")
	private Integer fifth;
	
	@Column(name = "sixth")
	private Integer sixth;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getFirst() {
		return first;
	}

	public void setFirst(Integer first) {
		this.first = first;
	}

	public Integer getSecond() {
		return second;
	}

	public void setSecond(Integer second) {
		this.second = second;
	}

	public Integer getThird() {
		return third;
	}

	public void setThird(Integer third) {
		this.third = third;
	}

	public Integer getForth() {
		return forth;
	}

	public void setForth(Integer forth) {
		this.forth = forth;
	}

	public Integer getFifth() {
		return fifth;
	}

	public void setFifth(Integer fifth) {
		this.fifth = fifth;
	}

	public Integer getSixth() {
		return sixth;
	}

	public void setSixth(Integer sixth) {
		this.sixth = sixth;
	}
	
}