package com.example.demo.facades;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.DBDAO.CompanyDBDAO;
import com.example.demo.DBDAO.CouponDBDAO;
import com.example.demo.DBDAO.CustomerDBDAO;
import com.example.demo.common.ClientType;
import com.example.demo.entities.Company;
import com.example.demo.entities.Customer;
import com.example.demo.exceptions.CompaniesNotFoundException;
import com.example.demo.exceptions.CompanyExistException;
import com.example.demo.exceptions.CompanyNotFoundException;
import com.example.demo.exceptions.CustomerExistException;
import com.example.demo.exceptions.CustomerNotFoundException;
import com.example.demo.exceptions.CustomersNotFoundException;

import lombok.ToString;
/***
 * Admin Facade for managing entities
 * @author User
 *
 */
@ToString
@Component
public class AdminFacade implements CouponClientFacade {
	
	// Login attributes
	private final String NAME = "admin";
	private final String PASSWORD = "1234";
	
	// DBDAO
	@Autowired
	private CompanyDBDAO compdb;
	
	@Autowired
	private CouponDBDAO coupdb;
	
	@Autowired
	private CustomerDBDAO custdb;
	
	/***
	 * Login method for AdminFacade
	 */
	@Override
	public AdminFacade login(String name, String password, ClientType clientType) {
		// Checking type First
		if(!clientType.equals(ClientType.ADMIN))
		{
			return null;
		}
		String que = name + password;
		String ans = NAME + PASSWORD;
		
		// Checking name & pass
		if(!que.equals(ans))
		{
			return null;
		}
		return this;
	}

	// ----- Company -----
	
	/***
	 * Creating new Company if its not exist
	 * @param company
	 * @throws CompanyExistException
	 * @throws InterruptedException 
	 */
	public void createCompany(Company company) throws CompanyExistException, InterruptedException
	{
		Company check = compdb.getCompanyByName(company.getCompanyName());
		// Check if exist
		if(check != null)
		{
			throw new CompanyExistException("Company " + company.getCompanyName() + " alreay exist.");
		}
		
		// Success creating Company
		compdb.createCompany(company);
	}
	
	/***
	 * Remove Company and its Coupons
	 * @param companyId
	 * @throws CompanyNotFoundException
	 * @throws InterruptedException 
	 */
	public void removeCompany(int companyId) throws CompanyNotFoundException, InterruptedException
	{
		Company check = compdb.getCompany(companyId);
		// Checking if exist
		if(check == null)
		{
			throw new CompanyNotFoundException("Company with the ID:" + companyId + " does not exist");
		}
		
		// Success - removing Company & its Coupons
		coupdb.removeAllCompanyCoupons(companyId);
		compdb.removeCompany(companyId);
	}
	
	/***
	 * Updating Company if exist
	 * @param password
	 * @param email
	 * @param companyId
	 * @throws CompanyNotFoundException
	 * @throws InterruptedException 
	 */
	public void updateCompany(String password , String email , int companyId) throws CompanyNotFoundException, InterruptedException
	{
		Company check = compdb.getCompany(companyId);
		// Checking if exist
		if(check == null)
		{
			throw new CompanyNotFoundException("Company with the ID:" + companyId + " does not exist");
		}
		
		// Success - updating Company
		compdb.updateCompany(password, email, companyId);
	}
	
	/**
	 * Getting Company by ID
	 * @param companyId
	 * @return Company
	 * @throws CompanyNotFoundException
	 * @throws InterruptedException 
	 */
	public Company getCompany(int companyId) throws CompanyNotFoundException, InterruptedException
	{
		Company check = compdb.getCompany(companyId);
		// Checking if exist
		if(check == null)
		{
			throw new CompanyNotFoundException("Company with the ID:" + companyId + " does not exist");
		}
		// Success - providing Company
		return check;
	}
	
	/***
	 * Get All Companies
	 * @return ArrayList<Company>
	 * @throws CompaniesNotFoundException
	 * @throws InterruptedException 
	 */
	public ArrayList<Company> getAllCompanies() throws CompaniesNotFoundException, InterruptedException
	{
		ArrayList<Company> allCompanies = compdb.getAllCompanies();
		// Checking if its not null
		if(allCompanies == null)
		{
			throw new CompaniesNotFoundException("There are no Companies - ArrayList = null");
		}
		
		// Success - returning all Companies
		return allCompanies;
	}

	// ----- Customer -----
	
	/***
	 * Creating new Customer if its not exist
	 * @param customer
	 * @throws CustomerExistException
	 * @throws InterruptedException 
	 */
	public void createCustomer(Customer customer) throws CustomerExistException, InterruptedException
	{
		Customer check = custdb.getCustomerByName(customer.getCustName());
		// Checking if exist
		if(check != null)
		{
			throw new CustomerExistException("Customer " + customer.getCustName() + " already exist");
		}
		
		custdb.createCustomer(customer);
	}
	
	/***
	 * Removing Customer if exist by ID
	 * @param customerId
	 * @throws CustomerNotFoundException
	 * @throws InterruptedException 
	 */
	public void removeCustomer(int customerId) throws CustomerNotFoundException, InterruptedException
	{
		Customer check = custdb.getCustomer(customerId);
		// Checking if exist
		if(check == null)
		{
			throw new CustomerNotFoundException("Customer with the ID:" + customerId + " does not exist");
		}
		
		// Success - remove Customer
		custdb.removeCustomer(customerId);
	}
	
	/***
	 * Update Customer if exist by ID
	 * @param password
	 * @param customerId
	 * @throws CustomerNotFoundException
	 * @throws InterruptedException 
	 */
	public void updateCustomer(String password , int customerId) throws CustomerNotFoundException, InterruptedException
	{
		Customer check = custdb.getCustomer(customerId);
		// Checking if exist
		if(check == null)
		{
			throw new CustomerNotFoundException("Customer with the ID:" + customerId + " does not exist");
		}
		
		// Success - updating Customer
		custdb.updateCustomer(password, customerId);
		
	}
	
	
	/***
	 * Get Customer by ID
	 * @param customerId
	 * @return Customer
	 * @throws CustomerNotFoundException
	 * @throws InterruptedException 
	 */
	public Customer getCustomer(int customerId) throws CustomerNotFoundException, InterruptedException
	{
		Customer check = custdb.getCustomer(customerId);
		// Checking if exist
		if(check == null)
		{
			throw new CustomerNotFoundException("Customer with the ID:" + customerId + " does not exist");
		}
		
		// Success - providing Customer
		return check;
	}
	
	/***
	 * Get all Customers
	 * @return ArrayList<Customer>
	 * @throws CustomersNotFoundException
	 * @throws InterruptedException 
	 */
	public ArrayList<Customer> getAllCustomers() throws CustomersNotFoundException, InterruptedException
	{
		ArrayList<Customer> allCustomers = custdb.getAllCustomers();
		// Making sure its not null
		if(allCustomers == null)
		{
			throw new CustomersNotFoundException("There are no Customers  ArrayList = null.");
		}
		// Success - providing all Customers
		return allCustomers;
	}
}