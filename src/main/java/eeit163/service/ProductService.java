package eeit163.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import eeit163.model.Product;
import eeit163.model.ProductRepository;

@Service
public class ProductService {
	@Autowired
	private ProductRepository pDao;
	
	public List<Product> findWinning(Integer id,Date date){
		return pDao.findWinning(id,date);
	}
	
	public List<Product> searchNoLimit(String[] strs){
		List<Product> searchNoLimitList = pDao.searchNoLimit(strs);
		return searchNoLimitList;
	}
	
	public List<Integer> findAllProductId(){
		List<Integer> allProductIdList = pDao.findAllProductId();
		return allProductIdList;
	}
	
	public List<Integer> searchCategory(String category){
		List<Integer> searchCategoryList = pDao.searchCategory(category);
		return searchCategoryList;
	}
	public List<Integer> searchDetail(String detail){
		List<Integer> searchDetailList = pDao.searchDetail(detail);
		return searchDetailList;
	}
	public List<Integer> searchPrice(Integer price1, Integer price2){
		List<Integer> searchPriceList = pDao.searchPrice(price1, price2);
		return searchPriceList;
	}
	public List<Integer> searchStatus(Integer status){
		List<Integer> searchStatusList = pDao.searchStatus(status);
		return searchStatusList;
	}
	public List<Integer> searchBargain(Integer bargain){
		List<Integer> searchBargainList = pDao.searchBargain(bargain);
		return searchBargainList;
	}
	//*************************************************************************
	public List<Product> findMyLikes(Integer[] myLikes){
		List<Product> likeList = pDao.findMyLikes(myLikes);
		return likeList;
	}
	
	public List<Product> findMyRecord(Integer[] myRecord){
		List<Product> recordList = pDao.findMyRecord(myRecord);
		return recordList;
	}
	
	
	public List<Product> getAllProducts(String timeLimit){
		List<Product> productList = pDao.findAllProducts(timeLimit);
		return productList;
	};
	
	public List<Product> findAllProductsForAdmin(){
		List<Product> productList = pDao.findAllProductsForAdmin();
		return productList;
	};
	
	public List<Product> getAllMyProducts(Integer ownerId){
		List<Product> myProductList = pDao.findAllMyProducts(ownerId);
		return myProductList;
	};
	
	public List<Product> getAllProductsByCategory(String category){
		List<Product> categoryList = pDao.findAllProductsByCategory(category);
		return categoryList;
	};
	
	public List<Product> getAllProductsByCategoryAndDetail(String detail,String category){
		List<Product> DetailList = pDao.findAllProductsByCategoryAndDetail(detail,category);
		return DetailList;
	};
	
	public List<Product> getAllMyDelistedProducts(Integer ownerId){
		List<Product> myDelistedProducts = pDao.findAllMyDelistedProducts(ownerId);
		return myDelistedProducts;
	};
	
	public byte[] getProductPhotoById(Integer productId) {
		byte[] photo = pDao.findProductPhotoById(productId);
		return photo;
	};
	
	public Product getProductById(Integer productId) {
		return pDao.findProductById(productId);
	};
	
	public List<Product> findProductsByIdArray(Integer[] productId){
		return pDao.findProductsByIdArray(productId);
	}
	
	public Product getDelistedProductById(Integer productId) {
		Product delistedProduct = pDao.findDelistedProductById(productId);
		return delistedProduct;
	};
	
	@Transactional
	public void deleteMyProduct(Integer productId) {
		pDao.deleteMyProduct(productId);
	}

	
	@Transactional
	public void delistMyProduct(Integer productId) {
		pDao.delistMyProduct(productId);
	}
	
	@Transactional
	public void banAndDelistMyProduct(Integer productId) {
		pDao.banAndDelistMyProduct(productId);
	}
	
	@Transactional
	public void publishMyProduct(Integer productId) {
		pDao.publishMyProduct(productId);
	}
	
	@Transactional
	public Integer updateProductQuantityById(Integer quantity,Integer productId) {
		Integer n = pDao.updateProductQuantityById(quantity,productId);
		return n;
	};
	
	@Transactional
	public void updateSoldById(Integer sold, Integer productId) {
		pDao.updateSoldById(sold,productId);
	}
	
	@Transactional
	public Integer updateLikes(String whoLikes,Integer productId) {
		Integer n = pDao.updateWhoLikes(whoLikes,productId);
		return n;
	};
	
	@Transactional
	public Integer updateWinnerTypeMaxPlus(String winnerType,Integer max,Integer plus,Integer productId) {
		Integer n = pDao.updateWinnerTypeMaxPlus(winnerType,max,plus,productId);
		return n;
	};
	
	@Transactional
	public void updateLatestPrice(Integer latestPrice,Integer productId) {
		pDao.updateLatestPrice(latestPrice, productId);
	};
	
	@Transactional
	public void updateBuyerId(String buyerId,Integer productId) {
		pDao.updateBuyerId(buyerId, productId);
	};
	
	@Transactional
	public void updateWinner(Integer winnerId,Integer productId) {
		pDao.updateWinner(winnerId, productId);
	};
	
	@Transactional
	public Integer updateRating(Integer productRating,Integer productId) {
		Integer n = pDao.updateProductRating(productRating,productId);
		return n;
	};
	
	@Transactional
	public Integer updateProductReleaseDate(Date releaseDate,Integer productId) {
		Integer n = pDao.updateProductReleaseDate(releaseDate,productId);
		return n;
	};
	
	@Transactional
	public Integer updateProductExpirationDate(Date ExpirationDate,Integer productId) {
		Integer n = pDao.updateProductExpirationDate(ExpirationDate,productId);
		return n;
	};
	
	@Transactional
	public Integer updateExpirationDateEmptyString(Integer productId) {
		Integer n = pDao.updateExpirationDateEmptyString(productId);
		return n;
	};
	
	public void addProduct(Product product) {
		pDao.save(product);
	}
	
	@Transactional
	public void deleteProduct(Integer productId) {
		pDao.deleteById(productId);
	}
	
	public Product findProductById(Integer productId) {
		return pDao.findProductById(productId);
	}
	
	@Transactional
	public void updateWhoLikes(String str,Integer productId) {
		pDao.updateWhoLikes(str, productId);
	}
 
	public String findWhoLikes(Integer productId) {
		return pDao.findWhoLikes(productId);
	}
	//********************
	@Transactional
	public void updateProductPhoto(byte[] productPhoto, Integer productId) {
		pDao.updateProductPhoto(productPhoto, productId);
	};
	
	@Transactional
	public void updateProductName(String productName, Integer productId) {
		pDao.updateProductName(productName, productId);
	};
	
	@Transactional
	public void updateCategory(String category, Integer productId) {
		pDao.updateCategory(category, productId);
	};
	
	@Transactional
	public void updatePrice(Integer price, Integer productId) {
		pDao.updatePrice(price, productId);
	};
	
	@Transactional
	public void updateQuantity(Integer quantity, Integer productId) {
		pDao.updateQuantity(quantity, productId);
	};
	
	@Transactional
	public void updateStatus(Integer status, Integer productId) {
		pDao.updateStatus(status, productId);
	};
	
	@Transactional
	public void updateInfo(String info, Integer productId) {
		pDao.updateInfo(info, productId);
	};
	
	@Transactional
	public void updateBargain(Integer bargain, Integer productId) {
		pDao.updateBargain(bargain, productId);
	};
	
	@Transactional
	public void updateTimeLimit(String timeLimit, Integer productId) {
		pDao.updateTimeLimit(timeLimit, productId);
	};
}
