package com.example.demo.DBDAO;




import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.Customer;
@Repository
public interface CustomerRepo extends CrudRepository<Customer, Integer>{
	
	
	/***
	 * Insert into Customer Coupon
	 * @param customerId
	 * @param couponId
	 */
	@Transactional
	@Modifying
	@Query(value = "INSERT INTO customer_coupons (customers_id, coupons_id) VALUES (:customerId, :couponId)", nativeQuery = true) 
	void insertCustomerCoupon(@Param("customerId") int  customerId, @Param("couponId") int couponId);

	/***
	 * Updating Customer set only password by ID
	 * @param password
	 * @param customerId
	 */
	@Transactional
	@Modifying
	@Query("UPDATE Customer cust SET cust.password = :password WHERE cust.id = :customerId")
	void updateCustomer(@Param("password")String password ,@Param("customerId") int customerId);
	
	/***
	 * Get Customer by name
	 * @param custName
	 * @return Customer
	 */
	Customer findBycustName(String custName);
	
	
	/***
	 * Get Customer by name & password
	 * @param custName
	 * @param password
	 * @return Customer
	 */
	Customer findBycustNameAndPassword(String custName , String password);
	
	
}
