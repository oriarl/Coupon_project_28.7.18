package com.example.demo.webservices;

import java.util.ArrayList;
import java.util.Date;

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
import com.example.demo.exceptions.CompanyNotFoundException;
import com.example.demo.exceptions.CouponExistException;
import com.example.demo.exceptions.CouponNotFoundException;
import com.example.demo.exceptions.CouponsNotFoundException;
import com.example.demo.exceptions.CustomerNotFoundException;
import com.example.demo.facades.AdminFacade;
import com.example.demo.facades.CompanyFacade;
import com.example.demo.system.CouponSystem;

@RestController
@CrossOrigin("*")
public class CompanyWS {

	@Autowired
	CouponSystem couponSystem;
	private CompanyFacade getFacade(HttpServletRequest request , HttpServletResponse response)
	{
		HttpSession session = request.getSession();
		CompanyFacade cf = (CompanyFacade) session.getAttribute("facade");
		return cf;
	}
	
	
	//Fake Login
//	private CompanyFacade getFacade() throws InterruptedException {
//		// Getting a Facade
//		CompanyFacade cf = (CompanyFacade) couponSystem.login("Aroma", "BlaBla", ClientType.COMPANY);
//		return cf;
//	}
	@RequestMapping(value = "/comapnyws/createcoupon", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity createCoupon(@RequestBody Coupon coupon, HttpServletRequest request , HttpServletResponse response) throws InterruptedException, CouponExistException, CompanyNotFoundException {
		//Getting a Facade
		CompanyFacade cf = this.getFacade(request, response); 
//		CompanyFacade cf = this.getFacade(); 

		//Body
		try {
		cf.createCoupon(coupon);
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.TEXT_MARKDOWN)
				.body("Created Successfully");
	} catch (CouponExistException | CompanyNotFoundException e) {
		return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).contentType(MediaType.TEXT_PLAIN)
				.body(e.getMessage());
	}
	}
	@RequestMapping(value = "/companyws/removecoupon/{couponId}", method = RequestMethod.DELETE)
	public @ResponseBody ResponseEntity removeCoupon(@PathVariable ("couponId")  int couponId, HttpServletRequest request , HttpServletResponse response) throws CompanyNotFoundException, CouponNotFoundException, InterruptedException {
		//Getting a Facade
		CompanyFacade cf = this.getFacade(request, response);  
//		CompanyFacade cf = this.getFacade(); 
		//Body
		try {
		cf.removeCoupon(couponId);
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.TEXT_MARKDOWN)
				.body("Removed Successfully");
	} catch (CompanyNotFoundException | CouponNotFoundException e) {
		return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).contentType(MediaType.TEXT_PLAIN)
				.body(e.getMessage());
	}
}
	@RequestMapping(value = "/companyws/updatecoupon/", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity updateCoupon(@RequestBody Coupon coupon, HttpServletRequest request , HttpServletResponse response) throws InterruptedException,
	CompanyNotFoundException, CouponNotFoundException {
		//Getting a Facade
		CompanyFacade cf = this.getFacade(request, response);
//		CompanyFacade cf = this.getFacade();
		//Body
		try {
			cf.updateCoupon(coupon.getEndDate(), coupon.getPrice(), coupon.getId());
			return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.TEXT_MARKDOWN).body("Updated Successfully");
		} catch (CompanyNotFoundException | CouponNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).contentType(MediaType.TEXT_PLAIN)
					.body(e.getMessage());

		}
		}
		
	@RequestMapping(value = "/companyws/getcoupon/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity getCoupon(@PathVariable("id") int couponId, HttpServletRequest request , HttpServletResponse response) {
		//Getting a Facade
		try {
			CompanyFacade cf = this.getFacade(request, response);
//			CompanyFacade cf = this.getFacade();
			
			Coupon coupon = cf.getCoupon(couponId);
			
			return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(coupon);
			
		} catch (InterruptedException | CompanyNotFoundException | CouponNotFoundException e) {
			
			return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.TEXT_PLAIN).body(e.getMessage());
		} 
		
	}
	@RequestMapping(value = "/companyws/getallcoupons", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity getAllCoupons(HttpServletRequest request , HttpServletResponse response) throws InterruptedException, CompanyNotFoundException, CouponsNotFoundException{
		//Getting a Facade
		CompanyFacade cf = this.getFacade(request, response); 
//		CompanyFacade cf = this.getFacade();
		//Body
		try {
		ArrayList<Coupon> allCoupons = cf.getAllCoupons();
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(allCoupons);
		} catch ( CompanyNotFoundException | CouponsNotFoundException e) {

		return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.TEXT_PLAIN).body(e.getMessage());
	} 	
	}
	@RequestMapping(value = "/companyws/getcompanycouponsbytype/{type}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity getCompanyCouponsByType(@PathVariable ("type") CouponType type, HttpServletRequest request , HttpServletResponse response) throws InterruptedException, CompanyNotFoundException, CouponsNotFoundException{
		//Getting a Facade
		CompanyFacade cf = this.getFacade(request, response); 
//		CompanyFacade cf = this.getFacade();
		//Body
		try {
		ArrayList<Coupon> allCouponsByType = cf.getCompanyCouponsByType(type);	
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(allCouponsByType);
		} catch ( CompanyNotFoundException | CouponsNotFoundException e) {

		return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.TEXT_PLAIN).body(e.getMessage());
	} 
		}
	
	}
	
	
	




	
	
