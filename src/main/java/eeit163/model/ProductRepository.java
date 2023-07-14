package eeit163.model;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;




public interface ProductRepository extends JpaRepository<Product, Integer> {
	
	@Modifying
	@Query(value="update Product set expirationDate = '' where productId = :n", nativeQuery = true)
	public Integer updateExpirationDateEmptyString(@Param("n")Integer productId);
	
	@Query(value="select * from Product where winnerId = :n and expirationDate > '1900/01/02 11:11:11' and expirationDate <= :m ", nativeQuery = true)
	public List<Product> findWinning(@Param("n")Integer id,@Param("m")Date date);
	
	@Query(value="select productPhoto from Product where productId = :n", nativeQuery = true)
	public byte[] findProductPhotoById(@Param("n")Integer productId);
	
	@Query(value="select * from Product where productId in (:n) ", nativeQuery = true)
	public List<Product> findMyLikes(@Param("n")Integer[] myLikes);
	
	@Query(value="select * from Product where productId in (:n) ", nativeQuery = true)
	public List<Product> findMyRecord(@Param("n")Integer[] myRecord);
	
	@Query(value="select * from Product where isDelisted = 0 and timeLimit = :n", nativeQuery = true)
	public List<Product> findAllProducts(@Param("n")String timeLimit);
	
	@Query(value="select * from Product where isDelisted = 0", nativeQuery = true)
	public List<Product> findAllProductsForAdmin();
	
	@Query(value="select * from Product where isDelisted = 0 and ownerId = :n ", nativeQuery = true)
	public List<Product> findAllMyProducts(@Param("n")Integer ownerId);
	
	@Query(value="select * from Product where isDelisted = 0 and category = :n ", nativeQuery = true)
	public List<Product> findAllProductsByCategory(@Param("n")String category);
	
	@Query(value="select * from Product where isDelisted = 0 and detail = :n and category = :m ", nativeQuery = true)
	public List<Product> findAllProductsByCategoryAndDetail(@Param("n")String detail, @Param("m")String category);
	
	@Query(value="select * from Product where isDelisted = 1 and ownerId = :n", nativeQuery = true)
	public List<Product> findAllMyDelistedProducts(@Param("n")Integer ownerId);
 
	@Query(value="select * from Product where productId = :n ", nativeQuery = true)
	public Product findProductById(@Param("n")Integer productId);
	
	@Query(value="select * from Product where productId in (:n) ", nativeQuery = true)
	public List<Product> findProductsByIdArray(@Param("n")Integer[] productId);
	
	@Query(value="select * from Product where isDelisted = true and productId = :n ", nativeQuery = true)
	public Product findDelistedProductById(@Param("n")Integer productId);
	
	@Query(value="from Product where productName like %:n% ")
	public Product findSearchProducts(@Param("n")String input);
	
	@Modifying
	@Query(value="update Product set productRating = :n where productId = :m", nativeQuery = true)
	public Integer updateProductRating(@Param("n")Integer productRating, @Param("m")Integer productId);
	
	@Modifying
	@Query(value="update Product set winnerType = :n, winnerMax=:m, winnerPlus=:p where productId = :q", nativeQuery = true)
	public Integer updateWinnerTypeMaxPlus(@Param("n")String winnerType,
			@Param("m")Integer max,
			@Param("p")Integer plus,
			@Param("q")Integer productId);
	
	@Modifying
	@Query(value="update Product set releaseDate = :n where productId = :m", nativeQuery = true)
	public Integer updateProductReleaseDate(@Param("n")Date releaseDate, @Param("m")Integer productId);
	
	@Modifying
	@Query(value="update Product set ExpirationDate = :n where productId = :m", nativeQuery = true)
	public Integer updateProductExpirationDate(@Param("n")Date ExpirationDate, @Param("m")Integer productId);
	
	@Modifying
	@Query(value="update Product set quantity = :n where productId = :m", nativeQuery = true)
	public Integer updateProductQuantityById(@Param("n")Integer quantity, @Param("m")Integer productId);
	
	@Modifying
	@Query(value="update Product set sold = :n where productId = :m", nativeQuery = true)
	public Integer updateSoldById(@Param("n")Integer sold, @Param("m")Integer productId);
	
	@Modifying
	@Query(value="update Product set buyerId = :n where productId = :m", nativeQuery = true)
	public Integer updateBuyerId(@Param("n")String buyerId, @Param("m")Integer productId);
	
	@Query(value="select whoLikes from Product where productId = :n ", nativeQuery = true)
	public String findWhoLikes(@Param("n")Integer productId);
	
	
	@Modifying
	@Query(value="update Product set whoLikes = :n where productId = :m ")
	public Integer updateWhoLikes(@Param("n")String str,@Param("m")Integer productId);
	
	
	
	//刪除我的賣場某個商品
	@Modifying
	@Query(value="delete from Product where productId = :n ", nativeQuery = true)
	public void deleteMyProduct(@Param("n") Integer productId);
		
	@Modifying
	@Query(value="update Product set isDelisted = 1 where productId = :n", nativeQuery = true)
	public void delistMyProduct(@Param("n") Integer productId);
	
	@Modifying
	@Query(value="update Product set isDelisted = 1 , ban = '已禁賣' where productId = :n", nativeQuery = true)
	public void banAndDelistMyProduct(@Param("n") Integer productId);
	
	@Modifying
	@Query(value="update Product set isDelisted = 0 where productId = :n", nativeQuery = true)
	public void publishMyProduct(@Param("n") Integer productId);
	
	//************************編輯我的商品
	@Modifying
	@Query(value="update Product set productPhoto = :n where productId = :m", nativeQuery = true)
	public void updateProductPhoto(@Param("n") byte[] productPhoto, @Param("m") Integer productId);
	
	@Modifying
	@Query(value="update Product set productName = :n where productId = :m", nativeQuery = true)
	public void updateProductName(@Param("n") String productName, @Param("m") Integer productId);
	
	@Modifying
	@Query(value="update Product set category = :n where productId = :m", nativeQuery = true)
	public void updateCategory(@Param("n") String category, @Param("m") Integer productId);
	
	@Modifying
	@Query(value="update Product set price = :n where productId = :m", nativeQuery = true)
	public void updatePrice(@Param("n") Integer price, @Param("m") Integer productId);
	
	@Modifying
	@Query(value="update Product set latestPrice = :n where productId = :m", nativeQuery = true)
	public void updateLatestPrice(@Param("n") Integer latestPrice, @Param("m") Integer productId);
	
	@Modifying
	@Query(value="update Product set winnerId = :n where productId = :m", nativeQuery = true)
	public void updateWinner(@Param("n") Integer winnerId, @Param("m") Integer productId);
	
	@Modifying
	@Query(value="update Product set quantity = :n where productId = :m", nativeQuery = true)
	public void updateQuantity(@Param("n") Integer quantity, @Param("m") Integer productId);
	
	@Modifying
	@Query(value="update Product set status = :n where productId = :m", nativeQuery = true)
	public void updateStatus(@Param("n") Integer status, @Param("m") Integer productId);
	
	@Modifying
	@Query(value="update Product set info = :n where productId = :m", nativeQuery = true)
	public void updateInfo(@Param("n") String info, @Param("m") Integer productId);
	
	@Modifying
	@Query(value="update Product set bargain = :n where productId = :m", nativeQuery = true)
	public void updateBargain(@Param("n") Integer bargain, @Param("m") Integer productId);
	
	@Modifying
	@Query(value="update Product set timeLimit = :n where productId = :m", nativeQuery = true)
	public void updateTimeLimit(@Param("n") String timeLimit, @Param("m") Integer productId);
	
	@Modifying
	@Query(value="update Product set sold = :n where productId = :m", nativeQuery = true)
	public void updateSold(@Param("n") Integer sold, @Param("m") Integer productId);
	
	//************************************************
	@Query(value="select productId from Product where isDelisted = 0", nativeQuery = true)
	public List<Integer> findAllProductId();
	
	@Query(value="select productId from Product where category = :n and isDelisted = 0", nativeQuery = true)
	public List<Integer> searchCategory(@Param("n")String category);
	
	@Query(value="select productId from Product where detail = :n and isDelisted = 0", nativeQuery = true)
	public List<Integer> searchDetail(@Param("n")String detail);
	
	@Query(value="select productId from Product where price >= :n and price<= :m and isDelisted = 0", nativeQuery = true)
	public List<Integer> searchPrice(@Param("n")Integer price1, @Param("m")Integer price2);
	
	@Query(value="select productId from Product where status = :n and isDelisted = 0", nativeQuery = true)
	public List<Integer> searchStatus(@Param("n")Integer status);
	
	@Query(value="select productId from Product where bargain = :n and isDelisted = 0", nativeQuery = true)
	public List<Integer> searchBargain(@Param("n")Integer bargain);
	
	@Query(value="select * from Product where productId in (:n) ", nativeQuery = true)
	public List<Product> searchNoLimit(@Param("n")String[] strs);
}
/*
照片更新
findSearchProducts(String input) 

*/

/*

"select * from Product where () "
"select * from Product where category=:c "
"select * from Product where detail=:d "
"select * from Product where price>=:p1 and price<=:p2"
"select * from Product where status=:s "
"select * from Product where bargain=:b "

*/










