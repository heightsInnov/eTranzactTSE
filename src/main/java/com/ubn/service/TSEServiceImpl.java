package com.ubn.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ubn.model.TseRequest;
import com.ubn.model.TseResponse;
import com.ubn.repository.TSERepository;

@Service(value = "TSEService")
public class TSEServiceImpl implements TSEService {

	@Autowired
	TSERepository repo;

	static Logger LOGGER = LoggerFactory.getLogger(TSEServiceImpl.class);

	@Override
	public TseResponse getStatus(TseRequest request) {
		// TODO Auto-generated method stub
		TseResponse resp = new TseResponse();
		try {			
			resp = repo.getStatus(request);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LOGGER.info(e.getMessage());
			e.printStackTrace();
		}
		return resp;
	}
}
