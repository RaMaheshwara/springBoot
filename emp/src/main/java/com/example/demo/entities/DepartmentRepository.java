package com.example.demo.entities;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;



public interface DepartmentRepository extends JpaRepository<Department, Long>{
		@Query("from Department where department=:departmentName")
		public Department findByDepartmentName(@Param("departmentName")String departmentName);
}
