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
@Table(name = "MyOrder")
public class MyOrder {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "orderId")
	private Integer orderId;

	@Column(name = "serialId", columnDefinition = "nvarchar(50)",unique = true)
	private String serialId;
	
	@Column(name = "senderId", columnDefinition = "int")
	private Integer senderId;
	
	@Column(name = "buyerId", columnDefinition = "int")
	private Integer buyerId;
	
	@Column(name = "buyerRating", columnDefinition = "decimal")
	private double buyerRating;
	
	@Column(name = "recipientName", columnDefinition = "nvarchar(20)")
	private String recipientName;
	
	@Column(name = "paymentMethod", columnDefinition = "nvarchar(20)")
	private String paymentMethod;

	@Column(name = "recipientTel", columnDefinition = "nvarchar(50)")
	private String recipientTel = "";
	
	@Column(name = "recipientAddress", columnDefinition = "nvarchar(50)")
	private String recipientAddress = "";
	
	@JsonFormat(pattern = "yyyy/MM/dd")
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "orderDate")
	private Date orderDate = new Date();
	
	@JsonFormat(pattern = "yyyy/MM/dd")
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "cancelledDate")
	private Date cancelledDate;
	
	@Column(name = "products", columnDefinition = "nvarchar(MAX)")
	private String products = "";
	
	@Column(name = "totalPrice", columnDefinition = "int")
	private Integer totalPrice = 0;
	
	@Column(name = "payStatus", columnDefinition = "nvarchar(20)")
	private String payStatus = "未付款";//未付款、已付款
	
	@Column(name = "orderStatus", columnDefinition = "nvarchar(20)")
	private String orderStatus = "訂單成立";//訂單成立、待出貨、已出貨、已送達、訂單完成、已取消
	
	@Column(name = "cancellationApply", columnDefinition = "nvarchar(20)")
	private String cancellationApply = "未申請";//未申請、已申請
	
	@Column(name = "buyerComment", columnDefinition = "nvarchar(20)")
	private String buyerComment="尚未評價";
	
	@Column(name = "senderComment", columnDefinition = "nvarchar(20)")
	private String senderComment="尚未評價";
	
	public String getOrderDateString() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(orderDate);
	}
	
	public String getCancelledDateString() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(cancelledDate);
	}
	
	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public String getSerialId() {
		return serialId;
	}

	public void setSerialId(String serialId) {
		this.serialId = serialId;
	}

	public Integer getSenderId() {
		return senderId;
	}

	public void setSenderId(Integer senderId) {
		this.senderId = senderId;
	}

	public Integer getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(Integer buyerId) {
		this.buyerId = buyerId;
	}

	public String getRecipientName() {
		return recipientName;
	}

	public void setRecipientName(String recipientName) {
		this.recipientName = recipientName;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getRecipientTel() {
		return recipientTel;
	}

	public void setRecipientTel(String recipientTel) {
		this.recipientTel = recipientTel;
	}

	public String getRecipientAddress() {
		return recipientAddress;
	}

	public void setRecipientAddress(String recipientAddress) {
		this.recipientAddress = recipientAddress;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public String getProducts() {
		return products;
	}

	public void setProducts(String products) {
		this.products = products;
	}

	public Integer getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Integer totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getBuyerComment() {
		return buyerComment;
	}

	public void setBuyerComment(String buyerComment) {
		this.buyerComment = buyerComment;
	}

	public String getSenderComment() {
		return senderComment;
	}

	public void setSenderComment(String senderComment) {
		this.senderComment = senderComment;
	}

	public double getBuyerRating() {
		return buyerRating;
	}

	public void setBuyerRating(double buyerRating) {
		this.buyerRating = buyerRating;
	}

	public String getCancellationApply() {
		return cancellationApply;
	}

	public void setCancellationApply(String cancellationApply) {
		this.cancellationApply = cancellationApply;
	}

	
	

	
	
	
	

}