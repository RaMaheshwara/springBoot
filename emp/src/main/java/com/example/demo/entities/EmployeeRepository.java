package com.example.demo.entities;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


public interface EmployeeRepository extends JpaRepository<Employee,Long>{
	@Query(nativeQuery = true,value="select * from Employee where :date between joining_date and exit_date order by currency,emp_name")
	public List<Employee> fetchFromDate(@Param("date")String date);
	

	@Query(nativeQuery = true,value="select * from Employee where emp_name=:empName")	/* to find whether any previous result of 
																							same data is already present */
	public Employee findByName(@Param("empName")String empName);

}
