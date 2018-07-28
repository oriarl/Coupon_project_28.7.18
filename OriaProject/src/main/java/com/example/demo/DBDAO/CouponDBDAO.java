package com.example.demo.DBDAO;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.common.CouponType;
import com.example.demo.entities.Company;
import com.example.demo.entities.Coupon;
import com.example.demo.entry.Connection;
import com.example.demo.entry.ConnectionPool;
import com.example.demo.exceptions.CompanyNotFoundException;
import com.example.demo.exceptions.CouponExistException;
import com.example.demo.exceptions.CouponNotFoundException;
import com.example.demo.exceptions.CouponsNotFoundException;

/***
 * Data Base Data Access Object for Company
 * @author Oria
 *
 */
@Service
public class CouponDBDAO implements CouponDAO {

	// Object's members
	@Autowired
	private CouponRepo coupRepo;
	@Autowired
	private CompanyRepo compRepo;
	@Autowired
	private ConnectionPool connectionPool; 
	
	/***
	 * Creating new Coupon
	 * @throws InterruptedException 
	 */
	@Override
	public synchronized void createCoupon(Coupon coupon, int companyId) throws CouponExistException, CompanyNotFoundException, InterruptedException {
		//Connection
		Connection con = connectionPool.getConnection();
		Company father = compRepo.findOne(companyId);
		coupon.setCompany(father);
		coupRepo.save(coupon);
		//Return Connection
		connectionPool.returnConnection(con);
		
	}

	/***
	 * Remove Coupon
	 * @throws InterruptedException 
	 */
	@Override
	public void removeCoupon(int couponId, int companyId) throws CouponNotFoundException, CompanyNotFoundException, InterruptedException {
		//Connection
		Connection con = connectionPool.getConnection();
		coupRepo.removeCoupon(couponId, companyId);
		//Return Connection
		connectionPool.returnConnection(con);
		
	}

	/***
	 * Removing all Company's Coupons
	 * @param companyId
	 * @throws CompanyNotFoundException
	 * @throws InterruptedException 
	 */
	public void removeAllCompanyCoupons(int companyId)throws CompanyNotFoundException, InterruptedException
	{
		//Connection
		Connection con = connectionPool.getConnection();
		coupRepo.removeAllCompanyCoupons(companyId);
		//Return Connection
		connectionPool.returnConnection(con);
	}
	
	/***
	 * Updating Coupon Set only endDate  & price 
	 * @throws InterruptedException 
	 */
	@Override
	public synchronized void updateCoupon(Date endDate, double price, int couponId , int companyId)
			throws CouponNotFoundException, CompanyNotFoundException, InterruptedException {
		//Connection
		Connection con = connectionPool.getConnection();
		coupRepo.updateCoupon(endDate, price, couponId, companyId);
		//Return Connection
		connectionPool.returnConnection(con);
		
	}

	/***
	 * Get Coupon by ID
	 * @throws InterruptedException 
	 */
	@Override
	public Coupon getCoupon(int couponId) throws CouponNotFoundException, InterruptedException {
		//Connection
		Connection con = connectionPool.getConnection();
		//Return Connection
		connectionPool.returnConnection(con);
		return coupRepo.findOne(couponId);
	}

	/***
	 * Get Coupon by title
	 * @param title
	 * @return Coupon
	 * @throws InterruptedException 
	 */
	public Coupon getCouponByTitle(String title) throws InterruptedException
	{
		//Connection
		Connection con = connectionPool.getConnection();
		//Return Connection
		connectionPool.returnConnection(con);
		return coupRepo.findBytitle(title);
	}
	
	/***
	 * Get all Coupons
	 * @throws InterruptedException 
	 */
	@Override
	public ArrayList<Coupon> getAllCoupons() throws CouponsNotFoundException, InterruptedException {
		//Connection
		Connection con = connectionPool.getConnection();
		ArrayList<Coupon> allCoupons = (ArrayList<Coupon>) coupRepo.findAll();
		//Return Connection
		connectionPool.returnConnection(con);
		return allCoupons;
	}

	/***
	 * Get All Coupons by type
	 * @throws InterruptedException 
	 */
	@Override
	public ArrayList<Coupon> getCouponByType(CouponType type) throws CouponNotFoundException, InterruptedException {
		//Connection
		Connection con = connectionPool.getConnection();
		//Return Connection
		connectionPool.returnConnection(con);
		return coupRepo.findBytype(type);
	}
	
}
