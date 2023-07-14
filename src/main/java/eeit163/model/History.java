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
@Table(name = "History")
public class History {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "historyId")
	private Integer historyId;
	
	@Column(name = "serialId", columnDefinition = "nvarchar(50)")
	private String serialId;
	
	@Column(name = "memberId", columnDefinition = "int")
	private Integer memberId;
	
	@Column(name = "ownerId", columnDefinition = "int")
	private Integer ownerId;
	
	@Column(name = "productId", columnDefinition = "int")
	private Integer ProductId;

	@Column(name = "price", columnDefinition = "int")
	private Integer price;
	
	@Column(name = "productName", columnDefinition = "nvarchar(50)")
	private String productName;

	@Column(name = "amount", columnDefinition = "int")
	private Integer amount;
	
	@JsonFormat(pattern = "yyyy/MM/dd")
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "orderDate")
	private Date orderDate;

	public Integer getHistoryId() {
		return historyId;
	}

	public void setHistoryId(Integer historyId) {
		this.historyId = historyId;
	}

	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	public Integer getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(Integer ownerId) {
		this.ownerId = ownerId;
	}

	public Integer getProductId() {
		return ProductId;
	}

	public void setProductId(Integer productId) {
		ProductId = productId;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public String getSerialId() {
		return serialId;
	}

	public void setSerialId(String serialId) {
		this.serialId = serialId;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	
	
	
	
	
}