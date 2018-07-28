package com.example.demo.DBDAO;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.common.CouponType;
import com.example.demo.entities.Coupon;
import com.example.demo.entities.Customer;
import com.example.demo.entry.Connection;
import com.example.demo.entry.ConnectionPool;
import com.example.demo.exceptions.CouponNotFoundException;
import com.example.demo.exceptions.CouponsNotFoundException;
import com.example.demo.exceptions.CustomerExistException;
import com.example.demo.exceptions.CustomerNotFoundException;
import com.example.demo.exceptions.CustomersNotFoundException;
/***
 * Data Base Data Access Object for Customer
 * @author Oria
 *
 */
@Service
public class CustomerDBDAO implements CustomerDAO {

	// Object's members
	@Autowired
	private CustomerRepo custRepo;
	
	@Autowired
	private CouponRepo coupRepo;
	@Autowired
	ConnectionPool connectionPool; 
	
	/***
	 * Creating new Customer
	 * @throws InterruptedException 
	 */
	@Override
	public synchronized void createCustomer(Customer customer) throws CustomerExistException, InterruptedException {
		//Connection
		Connection con = connectionPool.getConnection();
		custRepo.save(customer);
		//Return Connection
		connectionPool.returnConnection(con);
	}

	/***
	 * Removing Customer by ID
	 * @throws InterruptedException 
	 */
	@Override
	public void removeCustomer(int customerId) throws CustomerNotFoundException, InterruptedException {
		//Connection
		Connection con = connectionPool.getConnection();
		custRepo.delete(customerId);
		//Return Connection
		connectionPool.returnConnection(con);
	}

	/***
	 * Updating Customer set only password by ID
	 * @param password
	 * @param customerId
	 * @throws InterruptedException 
	 */
	@Override
	public synchronized void updateCustomer(String password, int customerId) throws CustomerNotFoundException, InterruptedException {
		//Connection
		Connection con = connectionPool.getConnection();
		custRepo.updateCustomer(password, customerId);
		//Return Connection
		connectionPool.returnConnection(con);
	}

	/***
	 * Get Customer by ID
	 * @throws InterruptedException 
	 */
	@Override
	public Customer getCustomer(int customerId) throws CustomerNotFoundException, InterruptedException {
		//Connection
		Connection con = connectionPool.getConnection();
		//Return Connection
		connectionPool.returnConnection(con);
		return custRepo.findOne(customerId);
	}
	
	/***
	 * Get Customer by name
	 * @param custName
	 * @return Customer
	 * @throws InterruptedException 
	 */
	public Customer getCustomerByName(String custName) throws InterruptedException
	{
		//Connection
		Connection con = connectionPool.getConnection();
		//Return Connection
		connectionPool.returnConnection(con);
		return custRepo.findBycustName(custName);
	}
	
	/***
	 * Get Customer by name & password
	 * @param custName
	 * @param password
	 * @return Customer
	 * @throws InterruptedException 
	 */
	public Customer getCustomerByNameAndPassword(String custName, String password) throws InterruptedException
	{
		//Connection
		Connection con = connectionPool.getConnection();
		//Return Connection
		connectionPool.returnConnection(con);
		return custRepo.findBycustNameAndPassword(custName, password);
	}

	/***
	 * Get All Customers
	 * @throws InterruptedException 
	 */
	@Override
	public ArrayList<Customer> getAllCustomers() throws CustomersNotFoundException, InterruptedException {
		//Connection
		Connection con = connectionPool.getConnection();
		//Return Connection
		connectionPool.returnConnection(con);
		return (ArrayList<Customer>) custRepo.findAll();
	}
	
	/**
	 * Get Customer Coupon
	 * @param couponId
	 * @param customerId
	 * @return Coupon
	 * @throws InterruptedException 
	 */
	public Coupon getCustomerCoupon(int couponId , int customerId) throws InterruptedException
	{
		//Connection
		Connection con = connectionPool.getConnection();
		//Return Connection
		connectionPool.returnConnection(con);
		return coupRepo.findByidAndCustomersId(couponId, customerId);
	}

	/***
	 * Get Customer's Coupons
	 * @throws InterruptedException 
	 */
	@Override
	public ArrayList<Coupon> getCoupons(int customerId) throws CouponsNotFoundException, CustomerNotFoundException, InterruptedException{
		//Connection
		Connection con = connectionPool.getConnection();
		//Return Connection
		connectionPool.returnConnection(con);
		return coupRepo.findByCustomersId(customerId);
	}

	/***
	 * Get Customer's Coupons by type
	 * @param type
	 * @param customerId
	 * @return ArrayList<Coupon>
	 * @throws CustomerNotFoundException
	 * @throws CouponsNotFoundException
	 * @throws InterruptedException 
	 */
	public ArrayList<Coupon> getCouponsByType(CouponType type , int customerId)throws CustomerNotFoundException , CouponsNotFoundException, InterruptedException
	{
		//Connection
		Connection con = connectionPool.getConnection();
		//Return Connection
		connectionPool.returnConnection(con);
		return coupRepo.findBytypeAndCustomersId(type, customerId);
	}
	
	/***
	 * Get Customer's Coupons by price
	 * @param price
	 * @param customerId
	 * @return ArrayList<Coupon>
	 * @throws InterruptedException 
	 */
	public ArrayList<Coupon> getCouponsByPrice(double price , int customerId)throws CustomerNotFoundException , CouponsNotFoundException, InterruptedException
	{
		//Connection
		Connection con = connectionPool.getConnection();
		//Return Connection
		connectionPool.returnConnection(con);
		return coupRepo.findCustomerCouponsByPrice(customerId, price);
	}
	/***
	 * Login method for Customer return boolean
	 * @throws InterruptedException 
	 */
	@Override
	public boolean login(String custName, String password) throws InterruptedException {
		//Connection
		Connection con = connectionPool.getConnection();
		Customer check = custRepo.findBycustNameAndPassword(custName, password);
		//Return Connection
		connectionPool.returnConnection(con);
		// Checking if exist
		if(check == null)
		{
			return false;
		}
		return true;
	}
	
	/***
	 * Purchasing Coupon
	 * @param customerId
	 * @param coupoId
	 * @throws CustomerNotFoundException
	 * @throws CouponsNotFoundException
	 */
	public void purchaseCoupon(int customerId , int couponId)throws CustomerNotFoundException , CouponNotFoundException
	{
		custRepo.insertCustomerCoupon(customerId, couponId);
		coupRepo.updateAmount(couponId);;
	}
	
}
