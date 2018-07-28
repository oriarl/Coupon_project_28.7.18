package com.example.demo.DBDAO;



import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.Company;
@Repository
public interface CompanyRepo extends CrudRepository<Company, Integer>{

	/***
	 * Updating Company Set only password & email by ID
	 * @param password
	 * @param email
	 * @param companyId
	 */
	@Transactional
	@Modifying
	@Query("UPDATE Company comp SET comp.password = :password , comp.email = :email WHERE comp.id= :companyId")
	void updateCompany(@Param("password") String password ,@Param("email") String email ,@Param("companyId") int companyId);
	
	/***
	 * Get Company by its name
	 * @param companyName
	 * @return Company
	 */
	Company findBycompanyName(String companyName);

	/***
	 * Get Company by its name
	 * @param companyName
	 * @return Company
	 */
	Company findBycompanyNameAndPassword(String companyName , String password);
}
