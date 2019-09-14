package com.ubn.service;

import com.ubn.model.TseRequest;
import com.ubn.model.TseResponse;

public interface TSEService {

	public TseResponse getStatus(TseRequest request);
}
