package com.ubn.repository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.Statement;
import java.text.SimpleDateFormat;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.ubn.config.DbConnect;
import com.ubn.model.TseRequest;
import com.ubn.model.TseResponse;

import oracle.jdbc.OracleTypes;

@Repository(value = "TSERepository")
public class TSERepositoryImpl implements TSERepository {

	static Logger LOGGER = LoggerFactory.getLogger(TSERepositoryImpl.class);

	@Override
	public TseResponse getStatus(TseRequest request) {
		// TODO Auto-generated method stub
		Connection conn = null;
		CallableStatement cll = null;
		TseResponse resp = null;
		String code = "";
		int i = 1;
		try {
			conn = DbConnect.getConn();
			cll = conn.prepareCall("{? = call TransactionStatusQuery.getStatus(?,?,?,?,?)}");
			cll.registerOutParameter(1, OracleTypes.VARCHAR);
			cll.setString(++i, request.getRrn());
			cll.setString(++i, request.getStan());
			cll.setString(++i, request.getAcctno());
			cll.setString(++i, request.getAmount());
			cll.setString(++i, convert(request));

			if (cll.executeUpdate() != Statement.EXECUTE_FAILED) {
				code = (String) cll.getString(1) != null ? (String) cll.getString(1) : "";
				resp = new TseResponse();
				resp.setResponseCode(code.equals("000") ? "" : code);
				resp.setRrn(request.getRrn());
				resp.setStan(request.getStan());
				resp.setAcctno(request.getAcctno());
				resp.setAmount(request.getAmount());
				resp.setTxndate(request.getTxndate());
			}
		} catch (Exception e) {
			// TODO: handle exception
			LOGGER.info(e.getMessage());
			e.printStackTrace();
		}
		return resp;
	}
	
	public static String convert(TseRequest request) {
		SimpleDateFormat sdf = new SimpleDateFormat("MMM-DD");
		Date lastLoadedDate = null;
		try {
			JSONObject jsonObject = new JSONObject(request);
			System.out.println("%%%%%%%%%%%%%%" + jsonObject);
			long timestamp = jsonObject.getLong("txndate");
			lastLoadedDate = new Date(timestamp);
		} catch (JSONException e) {
			// Handle the exception
			LOGGER.info(e.getMessage());
			e.printStackTrace();
		}
		return sdf.format(lastLoadedDate).toUpperCase();
	}

}
