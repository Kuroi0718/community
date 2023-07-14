package eeit163.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "MyCart")
public class MyCart {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cartId")
	private Integer cartId;

	@Column(name = "memberId", columnDefinition = "int")
	private Integer memberId;
	
	@Column(name = "ownerId", columnDefinition = "int")
	private Integer ownerId;
	
	@Column(name = "myCartProductId", columnDefinition = "int")
	private Integer myCartProductId;

	@Column(name = "price", columnDefinition = "int")
	private Integer price;
	
	@Column(name = "productName", columnDefinition = "nvarchar(50)")
	private String productName;

	@Column(name = "amount", columnDefinition = "int")
	private Integer amount;
	
	@Column(name = "type", columnDefinition = "nvarchar(20)")
	private String type = "直購";

	public Integer getCartId() {
		return cartId;
	}

	public void setCartId(Integer cartId) {
		this.cartId = cartId;
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

	public Integer getMyCartProductId() {
		return myCartProductId;
	}

	public void setMyCartProductId(Integer myCartProductId) {
		this.myCartProductId = myCartProductId;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	
	
	
	
}