/**
 * 
 */
package com.example.demo.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.common.ClientType;
import com.example.demo.facades.AdminFacade;
import com.example.demo.facades.CompanyFacade;
import com.example.demo.facades.CouponClientFacade;
import com.example.demo.facades.CustomerFacade;

/**
 * Factory for Facades to login
 * @author Oria
 *
 */
@Component
public class FacadeFactory {

	// Facades
	@Autowired
	private AdminFacade adminf;
	@Autowired 
	private CompanyFacade compf;
	@Autowired
	private CustomerFacade custf;
	
	
	/***
	 * Returning the right Facade with login method
	 * @param name
	 * @param password
	 * @param type
	 * @return CouponClientFacade
	 * @throws InterruptedException 
	 */
	public CouponClientFacade login(String name , String password , ClientType type) throws InterruptedException
	{
		switch(type)
		{
		case ADMIN:
			return adminf.login(name, password, type);
		case COMPANY:
			return compf.login(name, password, type);
		case CUSTOMER:
			return custf.login(name, password, type);
			default:
				return null;
		}
	}
}
