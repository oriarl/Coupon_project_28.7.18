/**
 * 
 */
package com.example.demo.system;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.example.demo.DBDAO.CouponRepo;
import com.example.demo.common.ClientType;
import com.example.demo.facades.CouponClientFacade;
import com.example.demo.factory.FacadeFactory;

import lombok.ToString;

/**
 * Coupon System for Login & Daily Thread for expired Coupons
 * @author Oria
 *
 */
@ToString
@Component
@Scope("singleton")
public class CouponSystem {
	
	// Factory
	@Autowired
	private FacadeFactory factory;
	// Repo
	@Autowired
	private CouponRepo coupRepo;
	
	// Object's members
	private boolean running;
	private Thread thread;

	/***
	 * Private CTR
	 */
	private CouponSystem() {
		
	}
	
	/***
	 * Login inside the system
	 * @param name
	 * @param password
	 * @param type
	 * @return CouponClientFacade
	 * @throws InterruptedException 
	 */
	public CouponClientFacade login(String name , String password , ClientType type) throws InterruptedException
	{
		return factory.login(name, password, type);
	}
	
	public synchronized void start()
	{
		// Check if running
		if (running) return;
		
		running = true;
		thread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(running)
				{
					coupRepo.removeExpiredCoupon(new Date(System.currentTimeMillis()));
					try {
						Thread.sleep(86400000);
					} catch (InterruptedException e) {
					}
				}
				
			}
		});
		
		thread.start();
	}
	
	/***
	 * Shut Down the System
	 */
	public synchronized void shutDown()
	{
		// Checking if running
		if(!running) return;
		
		running = false;
	}
}
