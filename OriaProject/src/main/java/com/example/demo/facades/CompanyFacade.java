package com.example.demo.facades;


import java.util.ArrayList;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.DBDAO.CompanyDBDAO;
import com.example.demo.DBDAO.CouponDBDAO;
import com.example.demo.common.ClientType;
import com.example.demo.common.CouponType;
import com.example.demo.entities.Company;
import com.example.demo.entities.Coupon;
import com.example.demo.exceptions.CompanyNotFoundException;
import com.example.demo.exceptions.CouponExistException;
import com.example.demo.exceptions.CouponNotFoundException;
import com.example.demo.exceptions.CouponsNotFoundException;

import lombok.Getter;
import lombok.ToString;

/***
 * Company Facade for managing Coupons
 * 
 * @author Oria
 *
 */
@ToString
@Component
public class CompanyFacade implements CouponClientFacade {

	// Object's members
	@Getter
	private Company loginCompany;

	// DBDAO
	@Autowired
	private CompanyDBDAO compdb;
	@Autowired
	private CouponDBDAO coupdb;

	/***
	 * Login method for Company Facade
	 * @throws InterruptedException 
	 */
	@Override
	public CompanyFacade login(String name, String password, ClientType clientType) throws InterruptedException {
		// Checking type first
		if (!clientType.equals(ClientType.COMPANY)) {
			return null;
		}

		// Checking name & password
		if (!compdb.login(name, password)) {
			return null;
		}

		loginCompany = compdb.getCompanyByNameAndPassword(name, password);
		return this;
	}

	/***
	 * Creating Coupon if not exist , after login
	 * 
	 * @param coupon
	 * @throws CouponExistException
	 * @throws CompanyNotFoundException
	 * @throws InterruptedException 
	 */
	public void createCoupon(Coupon coupon) throws CouponExistException, CompanyNotFoundException, InterruptedException {
		// Checking Company
		if (loginCompany == null) {
			throw new CompanyNotFoundException("Company not logged in.");
		}

		Coupon check = coupdb.getCouponByTitle(coupon.getTitle());
		// Checking Coupon
		if (check != null) {
			throw new CouponExistException("Coupon " + coupon.getTitle() + " already exist");
		}

		// Success - creating Coupon
		coupdb.createCoupon(coupon, loginCompany.getId());
	}

	/***
	 * Removing Coupon by ID & its Company
	 * 
	 * @param couponId
	 * @throws CompanyNotFoundException
	 * @throws CouponNotFoundException
	 * @throws InterruptedException 
	 */
	public void removeCoupon(int couponId) throws CompanyNotFoundException, CouponNotFoundException, InterruptedException {

		// Checking Company
		if (loginCompany == null) {
			throw new CompanyNotFoundException("Company not logged in.");
		}

		// Checking Coupon
		Coupon check = coupdb.getCoupon(couponId);
		if (check == null) {
			throw new CouponNotFoundException("Coupon with the ID:" + couponId + " does not exist.");
		}

		// Success - removing Coupon
		coupdb.removeCoupon(couponId, loginCompany.getId());
	}

	/***
	 * Update Coupon set only endDate & price by its ID
	 * 
	 * @param endDate
	 * @param price
	 * @param couponId
	 * @throws CompanyNotFoundException
	 * @throws CouponNotFoundException
	 * @throws InterruptedException 
	 */
	public void updateCoupon(Date endDate, double price, int couponId)
			throws CompanyNotFoundException, CouponNotFoundException, InterruptedException {

		// Checking Company
		if (loginCompany == null) {
			throw new CompanyNotFoundException("Company not logged in.");
		}

		// Checking Coupon
		Coupon check = coupdb.getCoupon(couponId);
		if (check == null) {
			throw new CouponNotFoundException("Coupon with the ID:" + couponId + " does not exist.");
		}

		// Success - updating Coupon
		coupdb.updateCoupon(endDate, price, couponId, loginCompany.getId());
	}

	/***
	 * Get Coupon by ID
	 * 
	 * @param couponId
	 * @return Coupon
	 * @throws CompanyNotFoundException
	 * @throws CouponNotFoundException
	 * @throws InterruptedException 
	 */
	public Coupon getCoupon(int couponId) throws CompanyNotFoundException, CouponNotFoundException, InterruptedException {

		// Checking Company
		if (loginCompany == null) {
			throw new CompanyNotFoundException("Company not logged in.");
		}

		// Checking Coupon
		Coupon check = compdb.getCompanyCoupon(couponId, loginCompany.getId());
		if (check == null) {
			throw new CouponNotFoundException("Coupon with the ID:" + couponId + " does not exist.");
		}

		// Success - providing Coupon
		return check;
	}

	/***
	 * Get all Coupons
	 * 
	 * @return ArrayList<Coupon>
	 * @throws CompanyNotFoundException
	 * @throws CouponsNotFoundException
	 * @throws InterruptedException 
	 */
	public ArrayList<Coupon> getAllCoupons() throws CompanyNotFoundException, CouponsNotFoundException, InterruptedException {
		// Checking Company
		if (loginCompany == null) {
			throw new CompanyNotFoundException("Company not logged in.");
		}
		ArrayList<Coupon> allCompanyCoupons = compdb.getCoupons(loginCompany.getId());
		// Make sure its not null
		if (allCompanyCoupons == null) {
			throw new CouponsNotFoundException("Coupons not exist - ArrayList = null");
		}
		// Success - return all Coupons
		return allCompanyCoupons;

	}

	/***
	 * Get Company's Coupons by type
	 * @param type
	 * @return ArrayList<Coupon>
	 * @throws CompanyNotFoundException
	 * @throws CouponsNotFoundException
	 * @throws InterruptedException 
	 */
	public ArrayList<Coupon> getCompanyCouponsByType(CouponType type)
			throws CompanyNotFoundException, CouponsNotFoundException, InterruptedException {
		// Checking Company
		if (loginCompany == null) {
			throw new CompanyNotFoundException("Company not logged in.");
		}

		ArrayList<Coupon> byType = compdb.getCompanyCouponsByType(type, loginCompany.getId());
		// Make sure its not null
		if (byType == null) {
			throw new CouponsNotFoundException("Coupons not exist - ArrayList = null");
		}
		
		// Success - providing Coupons by type
		return byType;
	}
}