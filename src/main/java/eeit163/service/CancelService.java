package eeit163.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import eeit163.model.Cancel;
import eeit163.model.CancelRepository;
import eeit163.model.History;

@Service
public class CancelService {
	@Autowired
	private CancelRepository cancelDao;
	
	public void addCancelledOrder(Cancel cancel) {
		cancelDao.save(cancel);
	}
	public List<Cancel> findCancelledTable(){
		return cancelDao.findCancelledTable();
	}
	public List<Cancel> findCancelBySerialId(String serialId){
		return cancelDao.findCancelBySerialId(serialId);
	}
}
