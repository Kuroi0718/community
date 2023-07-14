package eeit163.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MyCartRepository extends JpaRepository<MyCart, Integer> {
	//將某商品加入我的購物車
//	@Query(value="insert into MyCart (memberId,myCartProductId,amount) values(:n,:m,:p)", nativeQuery = true)
//	public void addToMyCart(@Param("n") Integer memberId,
//			@Param("m") Integer myCartProductId,@Param("p") Integer amount);
	
	
	
	//要結帳的商品
	@Query(value="select * from MyCart where cartId in (:n) ", nativeQuery = true)
	public List<MyCart> findCheckoutList(@Param("n")Integer[] cartId);
	
	//我的購物車某個商品
	@Query(value="select * from MyCart where memberId = :n and myCartProductId = :m ", nativeQuery = true)
	public MyCart findAmount(@Param("n")Integer memberId,@Param("m")Integer myCartProductId);
	
	//我的購物車所有商品
	@Query(value="select * from MyCart where memberId = :n ", nativeQuery = true)
	public List<MyCart> findMyCart(@Param("n")Integer memberId);
	
	//更新我的購物車某個商品的數量
	@Modifying
	@Query(value = "update MyCart set amount = :n where memberId = :m and myCartProductId = :p", nativeQuery = true) 
	public Integer updateAmount(@Param("n") Integer amount, 
			@Param("m") Integer memberId,
			@Param("p") Integer myCartProductId
			);
	
	//更新我的購物車某個商品的數量(購物車頁)
	@Modifying
	@Query(value = "update MyCart set amount = :n where cartId = :m ", nativeQuery = true) 
	public Integer updateCartNum(@Param("n") Integer amount, @Param("m") Integer cartId);
	
	//刪除我的購物車某個商品
	@Modifying
	@Query(value="delete from MyCart where cartId = :n ", nativeQuery = true)
	public void deleteCartProduct(@Param("n") Integer cartId);
	
	//刪除我的購物車某個商品
		@Modifying
		@Query(value="delete from MyCart where myCartProductId = :n ", nativeQuery = true)
		public void deleteCartProductByProductId(@Param("n") Integer productId);
	//結帳時，複製一份資料，再刪除我的購物車某店的商品
	@Query(value="select * from MyCart where memberId = :n and ownerId = :m ", nativeQuery = true)
	public List<MyCart> findMyPurchased(@Param("n") Integer memberId,@Param("m") Integer ownerId);
		
	@Modifying
	@Query(value="delete from MyCart where memberId = :n and ownerId = :m", nativeQuery = true)
	public void clearMyCart(@Param("n") Integer memberId,@Param("m") Integer ownerId);
	
	@Query(value="select * from MyCart where cartId = :n ", nativeQuery = true)
	public MyCart findMyCartByCartId(@Param("n") Integer cartId);
}
