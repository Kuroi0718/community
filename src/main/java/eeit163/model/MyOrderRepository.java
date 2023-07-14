package eeit163.model;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MyOrderRepository extends JpaRepository<MyOrder, Integer> {
	
	@Query(value="select * from MyOrder ", nativeQuery = true)
	public List<MyOrder> findOrderList();
	
	@Query(value="select * from MyOrder where orderId = :n", nativeQuery = true)
	public MyOrder findOrderById(@Param("n") Integer orderId);
	

	
	@Modifying
	@Query(value="update MyOrder set buyerComment = '已評價'  where serialId = :n ", nativeQuery = true)
	public void updateBuyerCommentBySerialId(@Param("n") String serialId);
	
	@Modifying
	@Query(value="update MyOrder set senderComment = '已評價' where serialId = :n ", nativeQuery = true)
	public void updateSenderCommentBySerialId(@Param("n") String serialId);
	
	//////賣家管理訂單//////
	@Query(value="select * from MyOrder where senderId = :n and payStatus = '未付款' and paymentMethod = '銀行轉帳' and orderStatus = '訂單成立' ", nativeQuery = true)
	public List<MyOrder> findToPayList(@Param("n")Integer id);
	
	@Query(value="select * from MyOrder where senderId = :n and orderStatus = '待出貨' ", nativeQuery = true)
	public List<MyOrder> findToShipList(@Param("n")Integer id);
	
	@Query(value="select * from MyOrder where senderId = :n and orderStatus = '已出貨' ", nativeQuery = true)
	public List<MyOrder> findShippedList(@Param("n")Integer id);
	
	@Query(value="select * from MyOrder where senderId = :n and orderStatus = '已送達' ", nativeQuery = true)
	public List<MyOrder> findReceivedList(@Param("n")Integer id);
	
	@Query(value="select * from MyOrder where senderId = :n and orderStatus = '訂單完成' ", nativeQuery = true)
	public List<MyOrder> findCompletedList(@Param("n")Integer id);
	
	@Query(value="select * from MyOrder where senderId = :n and orderStatus = '已取消' ", nativeQuery = true)
	public List<MyOrder> findCancelledList(@Param("n")Integer id);
	
	@Modifying
	@Query(value = "update MyOrder set orderStatus = '已出貨' where orderId = :n ", nativeQuery = true) 
	public void updateOrderStatusShipped(@Param("n") Integer orderId);
	
	@Modifying
	@Query(value = "update MyOrder set orderStatus = '已取消', cancelledDate = :n where serialId = :m ", nativeQuery = true) 
	public void updateOrderStatusCancelled(@Param("n")Date date, @Param("m") String serialId);
	//////賣家管理訂單//////
	
	//////買家查詢訂單//////
	@Query(value="select * from MyOrder where buyerId = :n and payStatus = '未付款' and paymentMethod = '銀行轉帳' and orderStatus = '訂單成立' ", nativeQuery = true)
	public List<MyOrder> findBuyerToPayList(@Param("n")Integer id);
	
	@Query(value="select * from MyOrder where buyerId = :n and orderStatus = '待出貨' ", nativeQuery = true)
	public List<MyOrder> findBuyerToShipList(@Param("n")Integer id);
	
	@Query(value="select * from MyOrder where buyerId = :n and orderStatus = '已出貨' ", nativeQuery = true)
	public List<MyOrder> findBuyerShippedList(@Param("n")Integer id);
	
	@Query(value="select * from MyOrder where buyerId = :n and orderStatus = '已送達' ", nativeQuery = true)
	public List<MyOrder> findBuyerReceivedList(@Param("n")Integer id);
	
	@Query(value="select * from MyOrder where buyerId = :n and orderStatus = '訂單完成' ", nativeQuery = true)
	public List<MyOrder> findBuyerCompletedList(@Param("n")Integer id);
	
	@Query(value="select * from MyOrder where buyerId = :n and orderStatus = '已取消' ", nativeQuery = true)
	public List<MyOrder> findBuyerCancelledList(@Param("n")Integer id);
	
	@Modifying
	@Query(value = "update MyOrder set orderStatus = '已送達' where orderId = :n ", nativeQuery = true) 
	public void updateOrderStatusReceived(@Param("n") Integer orderId);
	
	@Modifying
	@Query(value = "update MyOrder set payStatus = '已付款', orderStatus = '待出貨' where orderId = :n ", nativeQuery = true) 
	public void updatePayStatus(@Param("n") Integer orderId);
	
	@Modifying
	@Query(value = "update MyOrder set cancellationApply = '已申請' where serialId = :n ", nativeQuery = true) 
	public void updateCancellationApply(@Param("n") String serialId);
	//////買家查詢訂單//////
	
	//////後台管理訂單//////
	@Modifying
	@Query(value = "update MyOrder set orderStatus = '訂單完成' where orderId = :n ", nativeQuery = true) 
	public void updateOrderStatus(@Param("n") Integer orderId);
	//////後台管理訂單//////
	
	
}
