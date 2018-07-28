package com.example.demo;




import java.util.ArrayList;

import org.junit.AfterClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.demo.DBDAO.CompanyDBDAO;
import com.example.demo.DBDAO.CouponDBDAO;
import com.example.demo.DBDAO.CustomerDBDAO;
import com.example.demo.common.ClientType;
import com.example.demo.common.CouponType;
import com.example.demo.entities.Company;
import com.example.demo.entities.Coupon;
import com.example.demo.entities.Customer;
import com.example.demo.exceptions.CompaniesNotFoundException;
import com.example.demo.exceptions.CompanyExistException;
import com.example.demo.exceptions.CompanyNotFoundException;
import com.example.demo.exceptions.CouponExistException;
import com.example.demo.exceptions.CouponNotFoundException;
import com.example.demo.exceptions.CouponsNotFoundException;
import com.example.demo.exceptions.CustomerExistException;
import com.example.demo.exceptions.CustomerNotFoundException;
import com.example.demo.exceptions.CustomersNotFoundException;
import com.example.demo.exceptions.ExpiredCouponException;
import com.example.demo.exceptions.IllegalAmountException;
import com.example.demo.facades.AdminFacade;
import com.example.demo.facades.CompanyFacade;
import com.example.demo.facades.CustomerFacade;
import com.example.demo.system.CouponSystem;
import com.example.sidekicks.DateMaker;


@RunWith(SpringRunner.class)
@SpringBootTest
public class OriaProjectApplicationTests {
	
	// DBDAO
	@Autowired
	private CompanyDBDAO compdb;
	@Autowired
	private CustomerDBDAO custdb;
	@Autowired
	private CouponDBDAO coupdb;
	@Autowired
	private CouponSystem cs;
	
	// Facades
	@Autowired
	private AdminFacade af;
	@Autowired
	private CompanyFacade compf;
	@Autowired
	private CustomerFacade custf;

	@AfterClass
	public static void afterAll()
	{
		System.out.println("****************************");
	}
	
	@Test
	public void contextLoads() {
	}
	/*
	 
	
	/***
	 * Test for  DBDAO
	 */
	@Ignore
	@Test
	public void DBDAOTest() throws InterruptedException
	{
		
		//______________________________
		//||||| Company ||||||||||||||||
		//------------------------------
		
		
		// Creating new Company
		try {
			compdb.createCompany(new Company("Aroma", "1234", "aroma@gmail.com"));
			compdb.createCompany(new Company("IDSoftware", "1234", "ID@gmail.com"));
		} catch (CompanyExistException e) {
		}
		
		// Removing  Company
//		try {
//			compdb.removeCompany(1);
//		} catch (CompanyNotFoundException e) {
//		}
		
		// Updating Company
		try {
			compdb.updateCompany("zibi", "email@walla.co.il", 1);
		} catch (CompanyNotFoundException e) {
		}
		
		
		// Get Company by ID
		try {
			Company fromDB = compdb.getCompany(1);
			System.out.println(fromDB);
		} catch (CompanyNotFoundException e) {
		}
		
		// Get All Companies
		try {
			ArrayList<Company> allCompanies = compdb.getAllCompanies();
			System.out.println(allCompanies);
		} catch (CompaniesNotFoundException e) {
		}
		
		// Login as a Company
		boolean loginCompany = compdb.login("Aroma", "zibi");
		System.out.println(loginCompany);
		
		//______________________________
		//||||| Customer ||||||||||||||||
		//------------------------------
		
		// Create new Customer
		try {
			custdb.createCustomer(new Customer("Avner", "1234"));
			custdb.createCustomer(new Customer("Iftach", "1234"));
		} catch (CustomerExistException e) {
		}
		
		// Updating Customer
		try {
			custdb.updateCustomer("Drakula", 1);
		} catch (CustomerNotFoundException e) {
		}
		
		// Getting Customer by ID
		try {
			Customer cust = custdb.getCustomer(1);
			System.out.println(cust);
		} catch (CustomerNotFoundException e) {
		}
		
		// Getting all Customers
		try {
			ArrayList<Customer> allCustomers = custdb.getAllCustomers();
			System.out.println(allCustomers);
		} catch (CustomersNotFoundException e) {
		}
		
		// By name
		Customer byName = custdb.getCustomerByName("Avner");
		System.out.println("by name " + byName);
		
		
		// By name & password
		Customer byNameAndPass = custdb.getCustomerByNameAndPassword("Avner", "Drakula");
		System.out.println("by name & pass " + byNameAndPass);
		
		// Login for Customer
		boolean loginCustomer = custdb.login("Iftach", "1234");
		System.out.println(loginCustomer);
		
		// Removing Customer
//		try {
//			custdb.removeCustomer(1);
//		} catch (CustomerNotFoundException e) {
//		}
		
		//______________________________
		//||||| Coupon  |||||||||||||||||
		//------------------------------
				
		// Creating Coupon
		Coupon doom = new Coupon("Doom", DateMaker.fixDate(2017, 1, 1), DateMaker.fixDate(2019, 1, 1), 100, CouponType.ELECTRICITY, "nightmare", 52, "www.doom.com/pic.jpg");
		Coupon coffeBreak = new Coupon("Coffe Break", DateMaker.fixDate(2017, 1, 1), DateMaker.fixDate(2019, 1, 1), 100, CouponType.FOOD, "high quality coffe", 52, "www.aroma.com/pic.jpg");
		
		try {
			coupdb.createCoupon(doom, 2);
			coupdb.createCoupon(coffeBreak, 1);
		} catch (CouponExistException | CompanyNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Updating Coupon
		try {
			coupdb.updateCoupon(DateMaker.fixDate(2018, 1, 1), 9000, 1, 2);
		} catch (CouponNotFoundException | CompanyNotFoundException e) {
			e.printStackTrace();
		}
		
		// Get Coupon
		try {
			Coupon couponFromDB = coupdb.getCoupon(1);
			System.out.println(couponFromDB);
		} catch (CouponNotFoundException e) {
		}
		
		// Get all Coupons
		try {
			ArrayList<Coupon> allCoupons = coupdb.getAllCoupons();
			System.out.println(allCoupons);
		} catch (CouponsNotFoundException e) {
		}
		
		try {
			ArrayList<Coupon> couponsByType = coupdb.getCouponByType(CouponType.ELECTRICITY);
			System.out.println(couponsByType);
		} catch (CouponNotFoundException e) {
		}
		
		
			try {
				ArrayList<Coupon> idCoupons = compdb.getCoupons(2);
				System.out.println(idCoupons);
			} catch (CouponsNotFoundException | CompanyNotFoundException e) {
			}
		
			// Purchase Coupon
			try {
				custdb.purchaseCoupon(1	, 2);
			} catch (CustomerNotFoundException | CouponNotFoundException e) {
			}
		
			// Get Customer Coupons
			try {
				ArrayList<Coupon> customerCoupons = custdb.getCoupons(2);
				System.out.println(customerCoupons);
			} catch (CouponsNotFoundException | CustomerNotFoundException e) {
			}
			
		// Removing Coupon
//		try {
//			coupdb.removeCoupon(2, 1);
//		} catch (CouponNotFoundException | CompanyNotFoundException e) {
//		}
	}
	
	/***
	 * Test for AdminFacade
	 * @throws InterruptedException 
	 */
	@Ignore
	@Test
	public void adminTest() throws InterruptedException
	{
		// Login AdminFacade
		af = af.login("admin", "1234", ClientType.ADMIN);
		System.out.println(af);
		
		
		// Create Company
		try {
			af.createCompany(new Company("ForTraveller", "1234", "ft@gmail.com"));
		} catch (CompanyExistException e) {
			System.out.println(e.getMessage());
		}
		
		// Remove Company
		try {
			af.removeCompany(3);
			af.removeCompany(3);
		} catch (CompanyNotFoundException e) {
			System.out.println(e.getMessage());
		}
		
		// Updating Company 
		try {
			af.updateCompany("aro123", "AA@walla.co.il", 1888);
		} catch (CompanyNotFoundException e) {
			System.out.println(e.getMessage());
		}
		
		// Get Company by ID
		try {
			Company fromAdmin = af.getCompany(2654321);
			System.out.println(fromAdmin);
		} catch (CompanyNotFoundException e) {
			System.out.println(e.getMessage());
		}
		
		// Get all Companies
		try {
			ArrayList<Company> allCompanies = af.getAllCompanies();
			System.out.println(allCompanies);
		} catch (CompaniesNotFoundException e) {
		}
		
		// Creating Customer
		try {
			af.createCustomer(new Customer("adi", "1234"));
			af.createCustomer(new Customer("adi", "1234"));
		} catch (CustomerExistException e) {
			System.out.println(e.getMessage());
		}
		
		// Removing Customer
		try {
			af.removeCustomer(1);
			af.removeCustomer(1);
		} catch (CustomerNotFoundException e) {
			System.out.println(e.getMessage());
		}
		
		// Updating Customer
		try {
			af.updateCustomer("1221", 3);
			af.updateCustomer("1221", 3888);
		} catch (CustomerNotFoundException e) {
			System.out.println(e.getMessage());
		}
		
		// Getting Customer by ID
		try {
			Customer adi = af.getCustomer(3);
			System.out.println(adi);
		} catch (CustomerNotFoundException e) {
			System.out.println(e.getMessage());
		}
		
		// Get all Customers
		try {
			ArrayList<Customer> allCustomers = af.getAllCustomers();
			System.out.println(allCustomers);
		} catch (CustomersNotFoundException e) {
		}
		
	}
	
	/***
	 * Test for CompanyFacade
	 * @throws InterruptedException 
	 */
	@Ignore
	@Test
	public void companyFacadeTest() throws InterruptedException{
		
		// Login
		compf = compf.login("ForTraveller", "1234", ClientType.COMPANY);
		System.out.println(compf);
		
		// Create Coupon
		Coupon tents = new Coupon("Tents", DateMaker.fixDate(2018, 1, 1), DateMaker.fixDate(2019, 1, 1), 100, CouponType.CAMPING, "high qulity tents", 150, "tent.com/t.jpg");
		try {
			compf.createCoupon(tents);
			compf.createCoupon(tents);
		} catch (CouponExistException | CompanyNotFoundException e) {
			System.out.println(e.getMessage());
		}
		
		// Removing Coupon
//		try {
//			compf.removeCoupon(3);
//			compf.removeCoupon(3);
//		} catch (CompanyNotFoundException | CouponNotFoundException e) {
//			System.out.println(e.getMessage());
//		}
		
		// Updating Coupon
		try {
			compf.updateCoupon(DateMaker.fixDate(2026, 12, 1), 12, 4);
			compf.updateCoupon(DateMaker.fixDate(2026, 12, 1), 12, 1);
		} catch (CompanyNotFoundException | CouponNotFoundException e) {
			System.out.println(e.getMessage());
		}
		
		// Getting Coupon
		try {
			Coupon companyCoupon = compf.getCoupon(4);
			System.out.println(companyCoupon);
			Coupon companyCouponFail = compf.getCoupon(1);
			System.out.println(companyCouponFail);
		} catch (CompanyNotFoundException | CouponNotFoundException e) {
			System.out.println(e.getMessage());
		}
		
		// Getting all Coupons 
		try {
			ArrayList<Coupon> allTravellerCoupons = compf.getAllCoupons();
			System.out.println(allTravellerCoupons);
		} catch (CompanyNotFoundException | CouponsNotFoundException e) {
		}
		
		// Getting Company's Coupons by type
		try {
			ArrayList<Coupon> byType = compf.getCompanyCouponsByType(CouponType.ELECTRICITY);
			System.out.println(byType);
		} catch (CompanyNotFoundException | CouponsNotFoundException e) {
		}
	}

	/***
	 * Test for Customer Facade
	 * @throws InterruptedException 
	 */
	@Ignore
	@Test
	public void customerFacadeTest() throws InterruptedException
	{
		// Login
		custf = custf.login("Iftach", "1234", ClientType.CUSTOMER);
		System.out.println(custf);
		
		// Purchasing Coupon
		try {
			custf.purchaseCoupon(4);
		} catch (CustomerNotFoundException | CouponExistException | IllegalAmountException | ExpiredCouponException
				| CouponNotFoundException e) {
			System.out.println(e.getMessage());
		}
		
		// Get all Coupons
		try {
			ArrayList<Coupon> allCoupons = custf.getAllCoupons();
			System.out.println(allCoupons);
		} catch (CustomerNotFoundException | CouponsNotFoundException e) {
		}
		
		// Get Coupons by type
		try {
			ArrayList<Coupon> byType = custf.getCouponsByType(CouponType.ELECTRICITY);
			System.out.println(byType);
			ArrayList<Coupon> byType1 = custf.getCouponsByType(CouponType.CAMPING);
			System.out.println(byType1);
			ArrayList<Coupon> byType2 = custf.getCouponsByType(CouponType.FOOD);
			System.out.println(byType2);
		} catch (CustomerNotFoundException | CouponsNotFoundException e) {
			System.out.println(e.getMessage());
		}
		
		// Get Coupons by price
		try {
			ArrayList<Coupon> byPrice = custf.getCouponsByPrice(11.99);
			System.out.println(byPrice);
		} catch (CustomerNotFoundException | CouponsNotFoundException e) {
		}
	
	}
	
	/**
	 * Test for CouponSystem
	 * @throws InterruptedException 
	 */
	@Ignore
	@Test
	public void csTest() throws InterruptedException
	{
		// Coupon System
		System.out.println(cs);
		
	    // Login
		af = (AdminFacade) cs.login("admin", "1234", ClientType.ADMIN);
		System.out.println(af);
		
		compf = (CompanyFacade) cs.login("IDSoftware", "1234", ClientType.COMPANY);
		System.out.println(compf);

		custf = (CustomerFacade) cs.login("Iftach", "1234", ClientType.CUSTOMER);
		System.out.println(custf);
		
		cs.start();
		 
	}
	@Test
	public void wsTest() {
		
		
		try {
			compdb.createCompany(new Company("Aroma" , "1234" , "aroma@gmail.com"));
		} catch (CompanyExistException | InterruptedException e1) {
			System.out.println(e1.getMessage());
		}
	
		try {
			Company comp = compdb.getCompany(1);

			System.out.println(comp);
		} catch (CompanyNotFoundException | InterruptedException e) {
			System.out.println(e.getMessage());
		}
		try {
			coupdb.createCoupon(new Coupon("Doom", DateMaker.fixDate(1987, 07, 01), DateMaker.fixDate(2019, 1, 01), 100, CouponType.ELECTRICITY, "nightmare", 52, "www.doom.com/pic.jpg"), 1);
		} catch (CouponExistException | CompanyNotFoundException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			System.out.println(coupdb.getCoupon(2));
		} catch (CouponNotFoundException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			custdb.createCustomer(new Customer("LIONEL MESSI", "M E S S I A H"));
		} catch (CustomerExistException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			System.out.println(custdb.getCouponsByPrice(52, 3));
		} catch (CustomerNotFoundException | CouponsNotFoundException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
		
	
	
}	

		 

