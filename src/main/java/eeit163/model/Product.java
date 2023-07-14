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
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "Product")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "productId")
	private Integer productId;
	
	@Column(name = "ownerId", columnDefinition = "int")
	private Integer ownerId;

	@Column(name = "productName", columnDefinition = "nvarchar(50)")
	private String productName;
	
	@Column(name = "price", columnDefinition = "int")
	private Integer price;
	
	@Column(name = "info", columnDefinition = "nvarchar(MAX)")
	private String info;
	
	@Column(name = "quantity", columnDefinition = "int")
	private Integer quantity;
	
	@Column(name = "category", columnDefinition = "nvarchar(50)")
	private String category;
	
	@Column(name = "detail", columnDefinition = "nvarchar(50)")
	private String detail;
	
	@Column(name = "productRating", columnDefinition = "int")
	private Integer productRating = 0;
	
	
	@Column(name = "whoLikes", columnDefinition = "nvarchar(MAX)")
	private String whoLikes = "";
	
	@Column(name = "isDelisted", columnDefinition = "int")
	private Integer isDelisted = 0;
	
	@Column(name = "status", columnDefinition = "int")
	private Integer status;
	
	@Column(name = "sold", columnDefinition = "int")
	private Integer sold = 0;
	
	
		
	@Lob 
	@Column(name = "productPhoto", columnDefinition = "varbinary(MAX)")
	private byte[] productPhoto;
	
	@JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss EEEE", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "releaseDate")
	private Date releaseDate;
	
	@JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss EEEE", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "expirationDate")
	private Date expirationDate;

	@Column(name = "buyerId", columnDefinition = "nvarchar(MAX)")
	private String buyerId = "";
	
	@Column(name = "timeLimit", columnDefinition = "nvarchar(50)")
	private String timeLimit = "";
	
	@Column(name = "bargain", columnDefinition = "int")
	private Integer bargain = 0;
	
	@Column(name = "latestPrice", columnDefinition = "int")
	private Integer latestPrice = this.price;
	
	@Column(name = "winnerId", columnDefinition = "int")
	private Integer winnerId = 0;
	
	@Column(name = "winnerType", columnDefinition = "nvarchar(20)")
	private String winnerType = "";
	
	@Column(name = "winnerMax", columnDefinition = "int")
	private Integer winnerMax = 0;
	
	@Column(name = "winnerPlus", columnDefinition = "int")
	private Integer winnerPlus = 0;
	
	@Column(name = "ban", columnDefinition = "nvarchar(20)")
	private String ban = "";
	
	public String getExpirationDateString() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		return sdf.format(expirationDate);
	}
	public String getreleaseDateString() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		return sdf.format(releaseDate);
	}
	

	public Integer getWinnerPlus() {
		return winnerPlus;
	}

	public void setWinnerPlus(Integer winnerPlus) {
		this.winnerPlus = winnerPlus;
	}

	public Integer getWinnerMax() {
		return winnerMax;
	}

	public void setWinnerMax(Integer winnerMax) {
		this.winnerMax = winnerMax;
	}

	public String getWinnerType() {
		return winnerType;
	}

	public void setWinnerType(String winnerType) {
		this.winnerType = winnerType;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public Date getExpirationDate() {
		return expirationDate;
	}
	
	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public Integer getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(Integer ownerId) {
		this.ownerId = ownerId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Integer getProductRating() {
		return productRating;
	}

	public void setProductRating(Integer productRating) {
		this.productRating = productRating;
	}

	public String getWhoLikes() {
		return whoLikes;
	}

	public void setWhoLikes(String whoLikes) {
		this.whoLikes = whoLikes;
	}

	public Integer getIsDelisted() {
		return isDelisted;
	}

	public void setIsDelisted(Integer isDelisted) {
		this.isDelisted = isDelisted;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public byte[] getProductPhoto() {
		return productPhoto;
	}

	public void setProductPhoto(byte[] productPhoto) {
		this.productPhoto = productPhoto;
	}

	public Date getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

	public String getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(String buyerId) {
		this.buyerId = buyerId;
	}

	public String getTimeLimit() {
		return timeLimit;
	}

	public void setTimeLimit(String timeLimit) {
		this.timeLimit = timeLimit;
	}

	public Integer getBargain() {
		return bargain;
	}

	public void setBargain(Integer bargain) {
		this.bargain = bargain;
	}

	public Integer getLatestPrice() {
		return latestPrice;
	}

	public void setLatestPrice(Integer latestPrice) {
		this.latestPrice = latestPrice;
	}

	public Integer getWinnerId() {
		return winnerId;
	}

	public void setWinnerId(Integer winnerId) {
		this.winnerId = winnerId;
	}


	public Integer getSold() {
		return sold;
	}


	public void setSold(Integer sold) {
		this.sold = sold;
	}


	public String getBan() {
		return ban;
	}


	public void setBan(String ban) {
		this.ban = ban;
	}


	


	

}