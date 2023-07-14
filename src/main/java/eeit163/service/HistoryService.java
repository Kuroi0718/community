package eeit163.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import eeit163.model.History;
import eeit163.model.HistoryRepository;

@Service
public class HistoryService {
	@Autowired
	private HistoryRepository hDao;
	
	
	public void insertHistory(History history) {
		hDao.save(history);
	}
	
	public List<History> findHistoryByProductId(Integer productId){
		return hDao.findHistoryByProductId(productId);
	}
	
	public List<History> findHistoryBySerialId(String serialId) {
		return hDao.findHistoryBySerialId(serialId);
	}
	
	public List<History> findHistoryByOwnerId(Integer ownerId) {
		return hDao.findHistoryByOwnerId(ownerId);
	}
	
	public List<History> findHistoryByOwnerIdTime(Integer ownerId,Date date) {
		return hDao.findHistoryByOwnerIdTime(ownerId,date);
	}
	
	public List<History> findHistoryByOwnerIdAndProductName(Integer ownerId,String productName){
		return hDao.findHistoryByOwnerIdAndProductName(ownerId,productName);
	}
	
	public List<History> findHistoryByOwnerIdAndProductNameTime(Integer ownerId,String productName,Date date){
		return hDao.findHistoryByOwnerIdAndProductNameTime(ownerId,productName,date);
	}
	
	public Integer getHistoryIdsBySerialId(String serialId) {
		return hDao.getHistoryIdsBySerialId(serialId);
	}
	
	@Transactional
	public void deleteCancelledHistory(String serialId) {
		hDao.deleteCancelledHistory(serialId);
	}

}