package com.example.demo.DBDAO;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.example.demo.entities.Coupon;
import com.example.demo.entities.Customer;
import com.example.demo.exceptions.CouponsNotFoundException;
import com.example.demo.exceptions.CustomerExistException;
import com.example.demo.exceptions.CustomerNotFoundException;
import com.example.demo.exceptions.CustomersNotFoundException;
/***
 * Data Access Object for Customer
 * @author Oria
 *
 */
@Service
public interface CustomerDAO {

	/***
	 * Creating new Customer
	 * @param customer
	 * @throws CustomerExistException
	 * @throws InterruptedException 
	 */
	void createCustomer(Customer customer)throws CustomerExistException, InterruptedException;

	/***
	 * Removing Customer by ID
	 * @param customerId
	 * @throws CustomerNotFoundException
	 * @throws InterruptedException 
	 */
	void removeCustomer(int customerId)throws CustomerNotFoundException, InterruptedException;

	/***
	 * Updating Customer set only password by ID 
	 * @param password
	 * @param customerId
	 * @throws CustomerNotFoundException
	 * @throws InterruptedException 
	 */
	void updateCustomer(String password , int customerId)throws CustomerNotFoundException, InterruptedException;

	/***
	 * Get Customer by ID
	 * @param customerId
	 * @return Customer
	 * @throws CustomerNotFoundException
	 * @throws InterruptedException 
	 */
	Customer getCustomer(int customerId)throws CustomerNotFoundException, InterruptedException;

	/***
	 * Get all Customers
	 * @return ArrayList<Customer>
	 * @throws CustomersNotFoundException
	 * @throws InterruptedException 
	 */
	ArrayList<Customer> getAllCustomers() throws CustomersNotFoundException, InterruptedException;

	/***
	 * Get all Customer's Coupons
	 * @param customerId
	 * @return ArrayList<Coupon>
	 * @throws CouponsNotFoundException
	 * @throws CustomerNotFoundException
	 * @throws InterruptedException 
	 */
	ArrayList<Coupon> getCoupons(int customerId)throws CouponsNotFoundException , CustomerNotFoundException, InterruptedException;

	/***
	 * Login method for Customers
	 * @param custName
	 * @param password
	 * @return true if exist
	 * @throws InterruptedException 
	 */
	boolean login(String custName, String password) throws InterruptedException;

}
