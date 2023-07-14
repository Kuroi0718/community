package eeit163.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import eeit163.model.MyCart;
import eeit163.model.MyCartRepository;

@Service
public class MyCartService {
	@Autowired
	private MyCartRepository cDao;
	
	
	public void addToMyCart(MyCart myCart) {
		cDao.save(myCart);
	}
	
	@Transactional
	public void updateAmount(Integer amount,Integer memberId,Integer myCartProductId) {
		cDao.updateAmount(amount,memberId,myCartProductId);
	}
	
	@Transactional
	public void updateCartNum(Integer amount,Integer cartId) {
		cDao.updateCartNum(amount, cartId);
	}
	
	@Transactional
	public void deleteCartProduct(Integer cartId) {
		cDao.deleteCartProduct(cartId);
	}
	
	@Transactional
	public void clearMyCart(Integer memberId, Integer ownerId) {
		cDao.clearMyCart(memberId, ownerId);
	}
	
	@Transactional
	public void deleteCartProductByProductId(Integer productId) {
		cDao.deleteCartProductByProductId(productId);
	}
	
	public MyCart findAmount(Integer memberId,Integer myCartProductId){
		return cDao.findAmount(memberId,myCartProductId);
	}
	
	public List<MyCart> findMyCart(Integer memberId) {
		return cDao.findMyCart(memberId);
	}
	
	public MyCart findMyCartByCartId(Integer cartId) {
		return cDao.findMyCartByCartId(cartId);
	}
	
	public List<MyCart> findCheckoutList(Integer[] cartId){
		return cDao.findCheckoutList(cartId);
	}
	
	public List<MyCart> findMyPurchased(Integer memberId, Integer ownerId) {
		return cDao.findMyPurchased(memberId, ownerId);
	}
	

	

}


