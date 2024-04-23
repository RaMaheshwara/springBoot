package com.example.demo.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.entities.Department;
import com.example.demo.entities.DepartmentRepository;
import com.example.demo.entities.Employee;
import com.example.demo.entities.EmployeeRepository;
/*business logic layer*/
@Component
public class Service {
	
	Logger logger=LoggerFactory.getLogger(Service.class);		/*logger to log error and requests*/
	
	@Autowired
	EmployeeRepository empRep;			/*injecting object of employeeRepository 
										  in empRep using autowired*/
	
	@Autowired
	DepartmentRepository depRep;        /*injecting object of employeeRepository in empRep using autowired*/
	
	
	
	/* to save data in the dB*/
	public JSONObject saveData(JSONArray jsonArray)  {                 
		logger.info(jsonArray.toString());
		
		/*to parse date in required format*/
		SimpleDateFormat sdf=new SimpleDateFormat("MMMM-dd-yyyy"); 
		
		
		JSONObject resp=new JSONObject();
		
		try {
		/*iterating on the request data*/
		for(int i=0;i<jsonArray.length();i++) 		
		{
			
			JSONObject empData=jsonArray.getJSONObject(i);
			logger.info(empData.toString());
			
			/*creating object of database to save data in it*/
			Employee emp=new Employee();			
			
			
			/*this was used to prevent multiple entries 
			  with same name to create uniqueness in the dB */
			/* Employee prevE=empRep.findByName(empData.getString("empName")); 
			if(prevE==null) { */		
			
			emp.setAmount(empData.getDouble("amount"));
			emp.setCurrency(empData.getString("currency"));
			emp.setEmpName(empData.getString("empName"));
			emp.setExitDate(sdf.parse(empData.getString("exitDate")));
			emp.setJoiningDate(sdf.parse(empData.getString("joiningDate")));
			
			/* to ensure one department 
			does not get created again*/
			Department dept=depRep.findByDepartmentName(empData.getString("department"));
			if(dept==null) {												
				dept=new Department();
				dept.setDepartment(empData.getString("department"));
				dept=depRep.save(dept);
			}
			emp.setDepartmentId(dept);
			empRep.save(emp);
		/*}else {
			resp.put("status", 302);
			resp.put("message", "data already present");
			return resp;
		}*/
			}
		
			
			
		
		resp.put("status", 200);							/* success response*/
		resp.put("message", "data saved successfully");
		}catch(Exception e) {
			resp.put("status", 400);			/* if some data is missing*/
			resp.put("message","data not found");
		}
		return resp;
	}

	/* to fetch employee eligible for bonus*/
	public JSONObject fetchEmpData(String date) throws ParseException {
		logger.info(date);
		JSONObject result=new JSONObject();
		SimpleDateFormat sdf=new SimpleDateFormat("MMMM-dd-yyyy");
		SimpleDateFormat sdf1=new SimpleDateFormat("yyyy-MM-dd");
		Date d=sdf.parse(date);
		logger.info(d.toString());
		List<Employee> ls=empRep.fetchFromDate(sdf1.format(d));
		logger.info(ls.toString());
		if(!ls.isEmpty()) {
		HashMap<String,JSONArray> hash=new HashMap<>();
		for(Employee e:ls) {
			if(!hash.containsKey(e.getCurrency())) {
				hash.put(e.getCurrency(), new JSONArray());
			}
			JSONObject empData=new JSONObject();
			empData.put("empName", e.getEmpName());
			empData.put("amount", e.getAmount());
			hash.get(e.getCurrency()).put(empData);
		}
		JSONArray resp=new JSONArray();
		
		result.put("errorMessage", "");
		for(String key:hash.keySet()) {
			JSONObject jsonN=new JSONObject();
			jsonN.put("currency", key);
			jsonN.put("employees", hash.get(key));
			resp.put(jsonN);
		}
		
		result.put("data",resp);
		}else {
			result.put("errorMessage", "No matching record found");
		}
		// TODO Auto-generated method stub
		return result;
	}

}
