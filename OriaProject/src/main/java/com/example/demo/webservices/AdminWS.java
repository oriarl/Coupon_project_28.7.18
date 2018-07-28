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
import com.example.demo.entities.Company;
import com.example.demo.entities.Customer;
import com.example.demo.exceptions.CompaniesNotFoundException;
import com.example.demo.exceptions.CompanyExistException;
import com.example.demo.exceptions.CompanyNotFoundException;
import com.example.demo.exceptions.CustomerExistException;
import com.example.demo.exceptions.CustomerNotFoundException;
import com.example.demo.exceptions.CustomersNotFoundException;
import com.example.demo.facades.AdminFacade;
import com.example.demo.facades.CustomerFacade;
import com.example.demo.system.CouponSystem;

@RestController
@CrossOrigin("*")
public class AdminWS {

	@Autowired
	CouponSystem couponSystem;
	
	
	
	private AdminFacade getFacade(HttpServletRequest request , HttpServletResponse response)
	{
		HttpSession session = request.getSession();
		AdminFacade af = (AdminFacade) session.getAttribute("facade");
		return af;
	}
	
	
	
	//Fake Login
//	private AdminFacade getFacade() throws InterruptedException {
//		// Getting a Facade
//		AdminFacade af = (AdminFacade) couponSystem.login("admin", "1234", ClientType.ADMIN);
//		return af;
//	}

	// ----- Company -----

	@RequestMapping(value = "/adminws/company/createcompany", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity createCompany(@RequestBody Company company, HttpServletRequest request , HttpServletResponse response)
			throws InterruptedException, CompanyExistException {

		// Getting a Facade
		AdminFacade af = this.getFacade(request, response);
//		AdminFacade af = this.getFacade();
		if (af!=null) {
		System.out.println("after login");
		}else System.out.println("af is null");
		// Body
		try {
			af.createCompany(company);
			System.out.println("after creating");
			return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.TEXT_MARKDOWN)
					.body("Created Successfully");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).contentType(MediaType.TEXT_PLAIN)
					.body(e.getMessage());
		}

	}

	@RequestMapping(value = "/adminws/company/removecompany/{id}", method = RequestMethod.DELETE)
	public @ResponseBody ResponseEntity removeCompany(@PathVariable("id") int companyId, HttpServletRequest request , HttpServletResponse response)
			throws CompanyNotFoundException, InterruptedException {
		// Getting a Facade
		AdminFacade af = this.getFacade(request, response);
//		AdminFacade af = this.getFacade();
		System.out.println("after login");
		// Body
		try {
			af.removeCompany(companyId);
			return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.TEXT_MARKDOWN)
					.body("Removed Successfully");
		} catch (CompanyNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).contentType(MediaType.TEXT_PLAIN)
					.body(e.getMessage());
		}
	}

	@RequestMapping(value = "/adminws/company/updatecompany", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity updateCompany(@RequestBody Company company, HttpServletRequest request , HttpServletResponse response)
			throws InterruptedException, CompanyNotFoundException {
		// Getting a Facade
		AdminFacade af = this.getFacade(request, response);
//		AdminFacade af = this.getFacade();
		System.out.println("after login");
		// Body
		try {
			af.updateCompany(company.getPassword(), company.getEmail(), company.getId());
			return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.TEXT_MARKDOWN)
					.body("Updated Successfully");
		} catch (CompanyNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).contentType(MediaType.TEXT_PLAIN)
					.body(e.getMessage());
		}

	}

	@RequestMapping(value = "/adminws/company/getcompany/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity getCompany(@PathVariable("id") int companyId, HttpServletRequest request , HttpServletResponse response) throws InterruptedException {
		// Getting a Facade
		AdminFacade af;
		af = this.getFacade(request, response);
//		AdminFacade af = this.getFacade();
		System.out.println("after login");
		// Body
		try {

			Company company = af.getCompany(companyId);

			return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(company);

		} catch (CompanyNotFoundException | InterruptedException e) {

			return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.TEXT_PLAIN).body(e.getMessage());
		}

	}

	@RequestMapping(value = "/adminws/company/getallcompanies", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity getAllCompanies( HttpServletRequest request , HttpServletResponse response) throws InterruptedException, CompaniesNotFoundException {
		// Getting a Facade
		AdminFacade af = this.getFacade(request, response);
//		AdminFacade af = this.getFacade();
		System.out.println("after login");
		// Body
		try {
			ArrayList<Company> allCompanies = af.getAllCompanies();
			return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(allCompanies);
		} catch (CompaniesNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.TEXT_PLAIN).body(e.getMessage());
		}

	}
	// ----- Customer -----

	@RequestMapping(value = "/adminws/customer/createcustomer", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity createCustomer(@RequestBody Customer customer, HttpServletRequest request , HttpServletResponse response)
			throws InterruptedException, CustomerExistException {
		// Getting a Facade
		AdminFacade af = this.getFacade(request, response);
//		AdminFacade af = this.getFacade();
		System.out.println("after login");
		// Body
		try {
			af.createCustomer(customer);
			return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.TEXT_MARKDOWN)
					.body("Created Successfully");
		} catch (CustomerExistException e) {
			return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).contentType(MediaType.TEXT_PLAIN)
					.body(e.getMessage());
		}
	}

	@RequestMapping(value = "/adminws/customer/removecustomer/{id}", method = RequestMethod.DELETE)
	public @ResponseBody ResponseEntity removeCustomer(@PathVariable("id") int customerId, HttpServletRequest request , HttpServletResponse response)
			throws InterruptedException, CustomerNotFoundException {
		// Getting a Facade
		AdminFacade af = this.getFacade(request, response);
//		AdminFacade af = this.getFacade();
		System.out.println("after login");
		// Body
		try {
			af.removeCustomer(customerId);
			return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.TEXT_MARKDOWN)
					.body("Removed Successfully");
		} catch (CustomerNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).contentType(MediaType.TEXT_PLAIN)
					.body(e.getMessage());
		}

	}

	
	@RequestMapping(value = "/adminws/customer/updatecustomer", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity updateCustomer(@RequestBody Customer customer, HttpServletRequest request , HttpServletResponse response)
			throws InterruptedException, CustomerNotFoundException {
		// Getting a Facade
		AdminFacade af = this.getFacade(request, response);
//		AdminFacade af = this.getFacade();
		System.out.println("after login");
		// Body
		try {
			af.updateCustomer(customer.getPassword(), customer.getId());
			return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.TEXT_MARKDOWN).body("Updated Successfully");
		} catch (CustomerNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).contentType(MediaType.TEXT_PLAIN)
					.body(e.getMessage());

		}
	}

	@RequestMapping(value = "/adminws/customer/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity getCustomer(@PathVariable ("id") int customerId, HttpServletRequest request , HttpServletResponse response)
			throws CustomerNotFoundException, InterruptedException {
		// Getting a Facade
		AdminFacade af = this.getFacade(request, response);
//		AdminFacade af = this.getFacade();
		System.out.println("after login");
		// Body

		try {
			Customer customer = af.getCustomer(customerId);
			return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(customer);

		} catch (CustomerNotFoundException e) {

			return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.TEXT_PLAIN).body(e.getMessage());
		}

	}

	@RequestMapping(value = "/adminws/customer/getallcustomers", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity getAllCustomers(HttpServletRequest request , HttpServletResponse response) throws CustomersNotFoundException, InterruptedException {
		// Getting a Facade
		AdminFacade af = this.getFacade(request, response);
//		AdminFacade af = this.getFacade();
		System.out.println("after login");
		// Body
		try {
			ArrayList<Customer> allCustomers = af.getAllCustomers();
			return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(allCustomers);
		} catch (CustomersNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.TEXT_PLAIN).body(e.getMessage());
		}
	}

}
