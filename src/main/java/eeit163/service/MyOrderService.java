package eeit163.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import eeit163.model.MyOrder;
import eeit163.model.MyOrderRepository;

@Service
public class MyOrderService {
	@Autowired
	private MyOrderRepository oDao;
	
	public void makeAnOrder(MyOrder order) {
		oDao.save(order);
	}
	
	public List<MyOrder> findOrderList(){
		return oDao.findOrderList();
	}
	

	
	public MyOrder findOrderById(Integer orderId) {
		return oDao.findOrderById(orderId);
	}
	
	@Transactional
	public void updateBuyerCommentBySerialId(String serialId) {
		oDao.updateBuyerCommentBySerialId(serialId);
	}
	@Transactional
	public void updateSenderCommentBySerialId(String serialId) {
		oDao.updateSenderCommentBySerialId(serialId);
	}
	//////賣家管理訂單//////
	public List<MyOrder> findToPayList(Integer id){
		return oDao.findToPayList(id);
	}
	public List<MyOrder> findShippedList(Integer id){
		return oDao.findShippedList(id);
	}
	
	public List<MyOrder> findToShipList(Integer id){
		return oDao.findToShipList(id);
	}
	public List<MyOrder> findReceivedList(Integer id){
		return oDao.findReceivedList(id);
	}
	
	public List<MyOrder> findCompletedList(Integer id){
		return oDao.findCompletedList(id);
	}
	
	public List<MyOrder> findCancelledList(Integer id){
		return oDao.findCancelledList(id);
	}
	
	@Transactional
	public void updateOrderStatusShipped(Integer orderId){
		oDao.updateOrderStatusShipped(orderId);
	}
	
	@Transactional
	public void updateOrderStatusCancelled(Date date, String serialId){
		oDao.updateOrderStatusCancelled(date, serialId);
	}
	//////賣家管理訂單//////
	
	//////買家查詢訂單//////
	public List<MyOrder> findBuyerToPayList(Integer id){
		return oDao.findBuyerToPayList(id);
	}
	public List<MyOrder> findBuyerShippedList(Integer id){
		return oDao.findBuyerShippedList(id);
	}
	
	public List<MyOrder> findBuyerToShipList(Integer id){
		return oDao.findBuyerToShipList(id);
	}
	
	public List<MyOrder> findBuyerReceivedList(Integer id){
		return oDao.findBuyerReceivedList(id);
	}
	
	public List<MyOrder> findBuyerCompletedList(Integer id){
		return oDao.findBuyerCompletedList(id);
	}
	
	public List<MyOrder> findBuyerCancelledList(Integer id){
		return oDao.findBuyerCancelledList(id);
	}
	
	@Transactional
	public void updateOrderStatusReceived(Integer orderId){
		oDao.updateOrderStatusReceived(orderId);
	}
	
	@Transactional
	public void updatePayStatus(Integer orderId){
		oDao.updatePayStatus(orderId);
	}
	
	@Transactional
	public void updateCancellationApply(String serialId){
		oDao.updateCancellationApply(serialId);
	}
	//////買家查詢訂單//////
	
	@Transactional
	public void updateOrderStatus(Integer orderId){
		oDao.updateOrderStatus(orderId);
	}
	
}
