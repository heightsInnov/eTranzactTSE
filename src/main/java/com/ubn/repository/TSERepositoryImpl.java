package com.ubn.repository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Statement;
import java.text.SimpleDateFormat;

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
	static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	static SimpleDateFormat sdf2 = new SimpleDateFormat("MMdd");
	@Override
	public TseResponse getStatus(TseRequest request) {
		// TODO Auto-generated method stub
		Connection conn = null;
		CallableStatement cll = null;
		TseResponse resp = null;
		int i = 1;
		try {
			conn = DbConnect.getConn();
			cll = conn.prepareCall("{? = call fcubslive.TransactionStatusQuery.getStatus(?,?,?,?,?)}");
			cll.registerOutParameter(1, OracleTypes.VARCHAR);
			cll.setString(++i, request.getRrn());
			cll.setString(++i, request.getStan());
			cll.setString(++i, request.getAcctno());
			cll.setString(++i, request.getAmount());
			cll.setString(++i, sdf2.format(sdf.parse(request.getTxndate())));
			
			LOGGER.info("Request to DB: "+"RRN >> "+request.getRrn() + ", STAN >> "+ request.getStan() 
			+ ", ACCNO >> "+request.getAcctno() + ", Amount >> "+ request.getAmount() + ", TxnDate >> " + sdf2.format(sdf.parse(request.getTxndate())));

			if (cll.executeUpdate() != Statement.EXECUTE_FAILED) {
				resp = new TseResponse();
				resp.setResponseCode(cll.getString(1));
				resp.setRrn(request.getRrn());
				resp.setStan(request.getStan());
				resp.setAcctno(request.getAcctno());
				resp.setAmount(request.getAmount());
				resp.setTxndate(request.getTxndate());
			}
		} catch (Exception e) {
			// TODO: handle exception
			LOGGER.info(e.getMessage());
		}finally {
			try {
				if(cll !=null) {
					cll.close();
				}
				if(conn != null) {
					conn.close();
				}
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
		return resp;
	}
	
//	public static void main(String[] args) {
//		try {
//			System.out.println(sdf2.parse("2019-05-05 22:07:22"));
//			System.out.println(sdf2.format(sdf.parse("2019-10-23 22:07:22")));
//			System.out.println(sdf2.parse("2019-06-01 22:07:22"));
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
	
//	public static String convert(TseRequest request) {
//		SimpleDateFormat sdf = new SimpleDateFormat("MMM-DD");
//		Date lastLoadedDate = null;
//		try {
//			JSONObject jsonObject = new JSONObject(request);
//			System.out.println("%%%%%%%%%%%%%%" + jsonObject);
//			long timestamp = jsonObject.getLong("txndate");
//			lastLoadedDate = new Date(timestamp);
//			System.out.println("%%%%%%%%%%%%%%" + lastLoadedDate);
//		} catch (JSONException e) {
//			// Handle the exception
//			LOGGER.info(e.getMessage());
//			e.printStackTrace();
//		}
//		return sdf.format(lastLoadedDate).toUpperCase();
//	}
}
