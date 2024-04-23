package com.example.demo.controller;



import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.Service;


/*front layer where the request is landing */
@RestController
public class Controller {
	
	Logger logger=LoggerFactory.getLogger(Controller.class); /*logger to log the fields and error*/
	
	/*Autowired is used to inject 
	 * object of Service class in controller */
	@Autowired		
	Service sI;
	
	/*Post Mapping to store the data of all employees*/
	@PostMapping("/tci/employee-bonus")
	public String empData(@RequestBody String employees){
		logger.info(employees);
		JSONObject res=new JSONObject();	/*Json res is used to save the response coming from service class*/
		
		/*try catch is used for 
		 * empty or invalid request*/	
		try {																	
		JSONObject emp=new JSONObject(employees); 
		JSONArray arr=emp.getJSONArray("employees");
		res=sI.saveData(arr);
		}catch(Exception e) {
			res.put("message", "No data found to save");
		}
		return res.toString();
	}
	/*Get mapping is used for returning response*/
	@GetMapping("/tci//employee-bonus")									
	public String getEmpData(@RequestParam(name = "date") String date){
		logger.info(date.toString());
		JSONObject res=new JSONObject();
		try {	
		res=sI.fetchEmpData(date);
		}catch(Exception e) {
			logger.error("no date is found to display");
			res.put("message","No date found for performing operation");
		}
		return res.toString();
	}
	
	
}


