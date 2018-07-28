package com.example.demo.facades;

import org.springframework.stereotype.Component;

import com.example.demo.common.ClientType;
@Component
public interface CouponClientFacade {
	
	/***
	 * Login method for Facades
	 * @param name
	 * @param password
	 * @param clientType
	 * @return
	 * @throws InterruptedException 
	 */
	CouponClientFacade login(String name, String password, ClientType clientType) throws InterruptedException;

}