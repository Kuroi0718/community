package eeit163.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;

import jakarta.persistence.Table;

@Entity
@Table(name = "Ad")
public class Ad {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "Ad_content", columnDefinition = "nvarchar(50)")
	private String adContent;

	@Lob
	@Column(name = "Ad_file")
	private byte[] adFile;

	@ManyToOne
	@JoinColumn(name = "mem_id", referencedColumnName = "id")
	@JsonIgnoreProperties({"adContent", "adFile", "member"})
	private Member member;
	
	@Column(name = "status", columnDefinition = "int")
	private Integer status = 0;

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public Ad() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAdContent() {
		return adContent;
	}

	public void setAdContent(String adContent) {
		this.adContent = adContent;
	}

	public byte[] getAdFile() {
		return adFile;
	}

	public void setAdFile(byte[] adFile) {
		this.adFile = adFile;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	

}
