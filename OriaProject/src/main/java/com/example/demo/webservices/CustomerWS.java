package com.example.demo.webservices;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.common.ClientType;
import com.example.demo.common.CouponType;
import com.example.demo.entities.Coupon;
import com.example.demo.exceptions.CouponExistException;
import com.example.demo.exceptions.CouponNotFoundException;
import com.example.demo.exceptions.CouponsNotFoundException;
import com.example.demo.exceptions.CustomerNotFoundException;
import com.example.demo.exceptions.CustomersNotFoundException;
import com.example.demo.exceptions.ExpiredCouponException;
import com.example.demo.exceptions.IllegalAmountException;
import com.example.demo.facades.CompanyFacade;
import com.example.demo.facades.CustomerFacade;
import com.example.demo.system.CouponSystem;

@RestController
@CrossOrigin("*")
public class CustomerWS {
	@Autowired
	CouponSystem couponSystem;

	
	private CustomerFacade getFacade(HttpServletRequest request , HttpServletResponse response)
	{
		HttpSession session = request.getSession();
		CustomerFacade cf = (CustomerFacade) session.getAttribute("facade");
		return cf;
	}
	
	
	
//	//Fake Login
//	private CustomerFacade getFacade() throws InterruptedException {
//		CustomerFacade cs = (CustomerFacade) couponSystem.login("Oria", "4567", ClientType.CUSTOMER);
//		return cs;
//	}

	@RequestMapping(value = "/customerws/purchasecoupon/{couponid}", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity purchaseCoupon(@PathVariable("couponid") int couponid, HttpServletRequest request , HttpServletResponse response) throws InterruptedException, CustomerNotFoundException, CouponExistException, IllegalAmountException, ExpiredCouponException, CouponNotFoundException {
		//Getting a Facade
		CustomerFacade cf = this.getFacade(request, response);
//		CustomerFacade cf = this.getFacade(); 
		//Body
		try {
		cf.purchaseCoupon(couponid);
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.TEXT_MARKDOWN).body("Purchased Successfully");
	}
		catch(Exception e ){
			return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).contentType(MediaType.TEXT_PLAIN)
			.body(e.getMessage());
		}

		}

	@RequestMapping(value = "/customerws/getallcoupons", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity getAllCoupons(HttpServletRequest request , HttpServletResponse response)
			throws InterruptedException, CustomerNotFoundException, CouponsNotFoundException {
		// Getting a Facade
		CustomerFacade cf = this.getFacade(request, response);
//		CustomerFacade cf = this.getFacade();
		// Body
		try {
			ArrayList<Coupon> allCoupons = cf.getAllCoupons(); 
			return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(allCoupons);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.TEXT_PLAIN).body(e.getMessage());
		}
		}
		
	@RequestMapping(value = "/customerws/getcouponsbytype/{type}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity getCouponsByType(@PathVariable ("type") CouponType type, HttpServletRequest request , HttpServletResponse response)
			throws InterruptedException, CustomerNotFoundException, CouponsNotFoundException {
		// Getting a Facade
		CustomerFacade cf = this.getFacade(request, response);
//		CustomerFacade cf = this.getFacade();
		// Body
		try {
			ArrayList<Coupon> allCouponsByType = cf.getCouponsByType(type);  
			return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(allCouponsByType);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.TEXT_PLAIN).body(e.getMessage());
		}
		}
	
	

	@RequestMapping(value = "/customerws/getcouponsbyprice/{price}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity getCouponsByPrice(@PathVariable ("price") double price, HttpServletRequest request , HttpServletResponse response)
			throws InterruptedException, CustomerNotFoundException, CouponsNotFoundException {
		// Getting a Facade
		CustomerFacade cf = this.getFacade(request, response);
//		CustomerFacade cf = this.getFacade();
		// Body
		try {
			ArrayList<Coupon> allCouponsByPrice = cf.getCouponsByPrice(price);
			return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(allCouponsByPrice); 
		}catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.TEXT_PLAIN).body(e.getMessage());
		}
		}
		
	}


