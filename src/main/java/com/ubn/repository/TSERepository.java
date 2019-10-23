package com.ubn.repository;

import com.ubn.model.TseRequest;
import com.ubn.model.TseResponse;

public interface TSERepository {

	public TseResponse getStatus(TseRequest request);
}
