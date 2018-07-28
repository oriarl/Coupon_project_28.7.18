package com.example.demo.DBDAO;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.common.CouponType;
import com.example.demo.entities.Company;
import com.example.demo.entities.Coupon;
import com.example.demo.entry.Connection;
import com.example.demo.entry.ConnectionPool;
import com.example.demo.exceptions.CompaniesNotFoundException;
import com.example.demo.exceptions.CompanyExistException;
import com.example.demo.exceptions.CompanyNotFoundException;
import com.example.demo.exceptions.CouponsNotFoundException;
/***
 * Data Base Data Access Object for Company
 * @author Oria
 *
 */
@Service
public class CompanyDBDAO  implements CompanyDAO{
	
	// Object's members
	@Autowired
	private CompanyRepo compRepo;
	
	@Autowired
	private CouponRepo coupRepo;
	@Autowired
	private ConnectionPool connectionPool; 

	/**
	 * Creating new Company
	 * @throws InterruptedException 
	 */
	@Override
	public synchronized void createCompany(Company company) throws CompanyExistException, InterruptedException {
		//Connection
		Connection con = connectionPool.getConnection(); 
		compRepo.save(company);
		//return Connection
		connectionPool.returnConnection(con); 
	}

	/***
	 * Removing Company by ID
	 * @throws InterruptedException 
	 */
	@Override
	public void removeCompany(int companyId) throws CompanyNotFoundException, InterruptedException {
		//Connection
		Connection con = connectionPool.getConnection(); 
		compRepo.delete(companyId);
		//return Connection
		connectionPool.returnConnection(con); 
	}

	/***
	 * Updating Company Set only password & email by ID
	 * @param password
	 * @param email
	 * @param companyId
	 * @throws InterruptedException 
	 */
	@Override
	public synchronized void updateCompany(String password, String email, int companyId) throws CompanyNotFoundException, InterruptedException {
		//Connection
		Connection con = connectionPool.getConnection(); 
		compRepo.updateCompany(password, email, companyId);
		//return Connection
		connectionPool.returnConnection(con);
	}

	/***
	 * Get Company by ID
	 * @throws InterruptedException 
	 */
	@Override
	public Company getCompany(int companyId) throws CompanyNotFoundException, InterruptedException {
		//Connection
		Connection con = connectionPool.getConnection();
		//return Connection
		connectionPool.returnConnection(con);
		return compRepo.findOne(companyId);
	}

	/***
	 * Get Company by Name
	 * @param companyName
	 * @return Company
	 * @throws InterruptedException 
	 */
	public Company getCompanyByName(String companyName) throws InterruptedException
	{
		//Connection
		Connection con = connectionPool.getConnection();
		//return Connection
		connectionPool.returnConnection(con);
		return compRepo.findBycompanyName(companyName);
	}
	
	/***
	 * Get Company by name & password
	 * @param compnyName
	 * @param password
	 * @return Company
	 * @throws InterruptedException 
	 */
	public Company getCompanyByNameAndPassword(String companyName , String password) throws InterruptedException
	{
		//Connection
		Connection con = connectionPool.getConnection();
		//return Connection
		connectionPool.returnConnection(con);
		return compRepo.findBycompanyNameAndPassword(companyName, password);
	}
	
	/***
	 * Get All Companies
	 * @throws InterruptedException 
	 */
	@Override
	public ArrayList<Company> getAllCompanies() throws CompaniesNotFoundException, InterruptedException {
		//Connection
		Connection con = connectionPool.getConnection();
		//return Connection
		connectionPool.returnConnection(con);
		return (ArrayList<Company>) compRepo.findAll();
	}

	/***
	 * Get Company Coupon by ID
	 * @param companyId
	 * @return Coupon
	 * @throws InterruptedException 
	 */
	public Coupon getCompanyCoupon(int couponId , int companyId) throws InterruptedException
	{
		//Connection
		Connection con = connectionPool.getConnection();
		//return Connection
		connectionPool.returnConnection(con);
		return coupRepo.findByidAndCompanyId(couponId, companyId);
	}
	
	/***
	 * Get All Company's Coupons
	 * @throws InterruptedException 
	 */
	@Override
	public ArrayList<Coupon> getCoupons(int companyId) throws CouponsNotFoundException ,CompanyNotFoundException, InterruptedException {
		//Connection
		Connection con = connectionPool.getConnection();
		//return Connection
		connectionPool.returnConnection(con);
		return coupRepo.findByCompanyId(companyId);
	}
	
	/***
	 * Get Company's Coupons by type
	 * @param type
	 * @param companyId
	 * @return ArrayList<Coupon>
	 * @throws CouponsNotFoundException
	 * @throws CompanyNotFoundException
	 * @throws InterruptedException 
	 */
	public ArrayList<Coupon> getCompanyCouponsByType(CouponType type , int companyId)throws CouponsNotFoundException , CompanyNotFoundException, InterruptedException
	{
		//Connection
		Connection con = connectionPool.getConnection();
		//return Connection
		connectionPool.returnConnection(con);
		return coupRepo.findBytypeAndCompanyId(type, companyId);
	}

	/***
	 * Login Method for Company return boolean
	 * @throws InterruptedException 
	 */
	@Override
	public boolean login(String compName, String password) throws InterruptedException {
		//Connection
		Connection con = connectionPool.getConnection();
		
		Company check = compRepo.findBycompanyNameAndPassword(compName, password);
		// Checking if exist
		
		//return Connection
		connectionPool.returnConnection(con);
		if(check == null)
		{
			return false;
		}
		
		return true;
	}

	
}