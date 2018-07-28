package com.example.demo.entry;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.common.ClientType;
import com.example.demo.facades.CouponClientFacade;
import com.example.demo.system.CouponSystem;

@Controller
@CrossOrigin(origins = "*")
public class LoginServlet extends HttpServlet {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private CouponSystem cs;
	
/***
 * Login method that takes values from a form
 * and try to make a facade and save it on session attribute
 * @param request
 * @param response
 * @param name
 * @param password
 * @param type
 * @throws InterruptedException 
 * @throws IOException 
 */
@RequestMapping(value = "/loginServlet" , method = RequestMethod.POST)	
public void login(HttpServletRequest request, HttpServletResponse response ,
		                          @RequestParam String name , 
		                          @RequestParam String password , 
		                          @RequestParam String type) throws InterruptedException, IOException
{
	// Boolean for checking authentication
	boolean authenticated = false;
	ClientType loginType = ClientType.valueOf(type);
	
	
	System.out.println(name + password + loginType.toString());
	

		CouponClientFacade cf = cs.login(name, password, loginType);
		System.out.println(cf.toString());
	
		// Checking if can make a facade from parameters and its legal
		if(cf == null) {
				response.sendRedirect("/index.html");
		}
		else {
		
		
		// Setting authentication to true and save facade on the session
		authenticated = true;
		request.getSession().setAttribute("authenticated", authenticated);
		request.getSession().setAttribute("facade", cf);
		System.out.println(request.getSession().getAttribute("facade"));
		
		// Mapping the URLs for send Redirect
		Map<ClientType, String> urls = new HashMap<>();
		urls.put(ClientType.ADMIN, "./admin_page/index.html");
		urls.put(ClientType.COMPANY, "./company_page/index.html");
		urls.put(ClientType.CUSTOMER, "./customer_page/index.html");
		
		
		// Send Redirect
		response.sendRedirect(urls.get(loginType));
		}
		

}
}