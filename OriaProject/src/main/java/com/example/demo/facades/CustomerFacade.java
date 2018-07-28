package com.example.demo.facades;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.DBDAO.CouponDBDAO;
import com.example.demo.DBDAO.CustomerDBDAO;
import com.example.demo.common.ClientType;
import com.example.demo.common.CouponType;
import com.example.demo.entities.Coupon;
import com.example.demo.entities.Customer;
import com.example.demo.exceptions.CouponExistException;
import com.example.demo.exceptions.CouponNotFoundException;
import com.example.demo.exceptions.CouponsNotFoundException;
import com.example.demo.exceptions.CustomerNotFoundException;
import com.example.demo.exceptions.ExpiredCouponException;
import com.example.demo.exceptions.IllegalAmountException;

import lombok.Getter;
import lombok.ToString;

/***
 * Customer Facade for buying Coupons & getting them
 * 
 * @author Oria
 *
 */
@ToString
@Component
public class CustomerFacade implements CouponClientFacade {

	// Object's members
	@Getter
	private Customer loginCustomer;

	// DBDAO
	@Autowired
	private CustomerDBDAO custdb;
	@Autowired
	private CouponDBDAO coupdb;

	/**
	 * Login method for CustomerFacade
	 * @throws InterruptedException 
	 */
	@Override
	public CustomerFacade login(String name, String password, ClientType clientType) throws InterruptedException {
		// Checking type first
		if (!clientType.equals(ClientType.CUSTOMER)) {
			return null;
		}
		// Checking name & password
		if (!custdb.login(name, password)) {
			return null;
		}

		loginCustomer = custdb.getCustomerByNameAndPassword(name, password);
		return this;
	}

	/***
	 * Purchasing Coupon
	 * 
	 * @param couponId
	 * @throws CustomerNotFoundException
	 * @throws CouponExistException
	 * @throws IllegalAmountException
	 * @throws ExpiredCouponException
	 * @throws CouponNotFoundException
	 * @throws InterruptedException 
	 */
	public void purchaseCoupon(int couponId) throws CustomerNotFoundException, CouponExistException,
			IllegalAmountException, ExpiredCouponException, CouponNotFoundException, InterruptedException {
		// Checking Customer
		if (loginCustomer == null) {
			throw new CustomerNotFoundException("Customer is not logged in");
		}
		// Making sure Coupon exist for purchasing
		Coupon toPurchase = coupdb.getCoupon(couponId);
		if (toPurchase == null) {
			throw new CouponNotFoundException("Coupon with the ID:" + couponId + " does not exist");
		}

		// Make sure Customer not already holds this Coupon
		Coupon exist = custdb.getCustomerCoupon(couponId, loginCustomer.getId());
		if (exist != null) {
			throw new CouponExistException(loginCustomer.getCustName() + " already holds : " + toPurchase.getTitle());
		}

		// Making sure amount not smaller then 1
		if (toPurchase.getAmount() <= 0) {
			throw new IllegalAmountException("Illegal amount, can not purchase if amount smaller than 1.");
		}

		// Making sure Coupon not expired
		if (toPurchase.getEndDate().before(new Date(System.currentTimeMillis()))) {
			throw new ExpiredCouponException("Can not purchase Coupon that expired.");
		}

		// Success - purchasing Coupon
		custdb.purchaseCoupon(loginCustomer.getId(), couponId);

	}

	/***
	 * Get all Customer's Coupons
	 * 
	 * @return ArrayList<Coupon>
	 * @throws CustomerNotFoundException
	 * @throws CouponsNotFoundException
	 * @throws InterruptedException 
	 */
	public ArrayList<Coupon> getAllCoupons() throws CustomerNotFoundException, CouponsNotFoundException, InterruptedException {
		// Checking Customer
		if (loginCustomer == null) {
			throw new CustomerNotFoundException("Customer is not logged in");
		}

		ArrayList<Coupon> allCouopns = custdb.getCoupons(loginCustomer.getId());
		// Making sure its not null
		if (allCouopns == null) {
			throw new CouponsNotFoundException("There are no Coupons , ArrayList = null");
		}
		// Success - providing Coupons
		return allCouopns;
	}

	/***
	 * Get Coupon by type
	 * 
	 * @param type
	 * @return ArrayList<Coupon>
	 * @throws CustomerNotFoundException
	 * @throws CouponsNotFoundException
	 * @throws InterruptedException 
	 */
	public ArrayList<Coupon> getCouponsByType(CouponType type)
			throws CustomerNotFoundException, CouponsNotFoundException, InterruptedException {
		// Checking Customer
		if (loginCustomer == null) {
			throw new CustomerNotFoundException("Customer is not logged in");
		}

		ArrayList<Coupon> byType = custdb.getCouponsByType(type, loginCustomer.getId());
		// Making sure its not null
		if (byType == null) {
			throw new CouponsNotFoundException("There are no Coupons , ArrayList = null");
		}

		// Success - providing Coupons by type
		return byType;
	}

	/***
	 * Get Coupons by price
	 * @param price
	 * @return ArrayList<Coupon>
	 * @throws CustomerNotFoundException
	 * @throws CouponsNotFoundException
	 * @throws InterruptedException 
	 */
	public ArrayList<Coupon> getCouponsByPrice(double price) throws CustomerNotFoundException, CouponsNotFoundException, InterruptedException {
		// Checking Customer
		if (loginCustomer == null) {
			throw new CustomerNotFoundException("Customer is not logged in");
		}
		
		ArrayList<Coupon> byPrice = custdb.getCouponsByPrice(price, loginCustomer.getId());
		// Making sure its not null
		if (byPrice == null) {
			throw new CouponsNotFoundException("There are no Coupons , ArrayList = null");
		}
		// Success - providing Coupons that the price attribute lower then the price parameter
		return byPrice;
	}

}