package com.ubn;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ubn.model.TseRequest;
import com.ubn.model.TseResponse;
import com.ubn.service.TSEService;

@RestController
public class HomeController {

	static Logger LOGGER = LoggerFactory.getLogger(HomeController.class);

	@Autowired
	TSEService service;
	
	@GetMapping("/echo")
	public String echo() {
		return "echo";

	}

	@PostMapping("/gettxnstatus")
	public @ResponseBody TseResponse getStatus(@RequestBody TseRequest request, HttpServletRequest req,
			Principal principal) {
		SaveInfo(req, "/gettxnstatus");
		TseResponse resp = service.getStatus(request);
		logAudit(req, request, resp, principal.getName().toUpperCase());
		return resp;
	}

	private void SaveInfo(HttpServletRequest req, String methodName) {
		LOGGER.info("********** " + methodName + " in SaveInfo method *********");
		String remoteAddress = "0.0.0.0";
		if (req != null) {
			remoteAddress = req.getHeader("X-FOWARDED-FOR") != null ? req.getHeader("X-FOWARDED-FOR")
					: req.getRemoteAddr();
		}
		LOGGER.info(methodName + " accessed by " + remoteAddress);
	}

	private void logAudit(HttpServletRequest req, Object request, Object resp, String uname) {
		String remoteAddress = "0.0.0.0";
		JSONObject jsonReq = new JSONObject(request);
		JSONObject jsonResp = new JSONObject(resp);
		if (req != null
				) {
			remoteAddress = req.getHeader("X-FOWARDED-FOR") != null ? req.getHeader("X-FOWARDED-FOR")
					: req.getRemoteAddr();
		}
		LOGGER.info("\nRequestJson(" + uname + "-" + remoteAddress + ")>>> " + jsonReq.toString() + "\nResponseJson("
				+ uname + "-" + remoteAddress + ")>>> " + jsonResp.toString());
	}
}
