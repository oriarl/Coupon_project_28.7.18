package com.example.demo.DBDAO;



import java.util.ArrayList;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.common.CouponType;
import com.example.demo.entities.Coupon;
@Repository
public interface CouponRepo extends CrudRepository<Coupon, Integer>{


	/***
	 * Removing Company Coupon
	 * @param couponId
	 * @param companyId
	 */
	@Transactional
	@Modifying
	@Query("DELETE FROM Coupon coup WHERE coup.id = :couponId AND coup.company.id = :companyId")
	void removeCoupon(@Param("couponId") int couponId, @Param("companyId") int companyId);
	
	/***
	 * Removing expired Coupons
	 */
	@Transactional
	@Modifying
	@Query("DELETE FROM Coupon coup WHERE coup.endDate < :endDate")
	void removeExpiredCoupon(@Param("endDate")Date endDate);
	
	/***
	 * Updating amount -1 when purchase Coupon
	 * @param customerId
	 */
	@Transactional
	@Modifying
	@Query("UPDATE Coupon coup SET coup.amount = amount-1 WHERE coup.id = :couponId")
	void updateAmount(@Param("couponId") int couponId);
	
	/***
	 * Removing Company Coupon
	 * @param companyId
	 */
	@Transactional
	@Modifying
	@Query("DELETE FROM Coupon coup WHERE coup.company.id = :companyId")
	void removeAllCompanyCoupons(@Param("companyId") int companyId);

	/***
	 * Updating Coupon Set only endDate & price
	 * @param endDate
	 * @param price
	 * @param couponId
	 * @param companyId
	 */
	@Transactional
	@Modifying
	@Query("UPDATE Coupon coup SET coup.endDate = :endDate, coup.price = :price WHERE coup.id = :couponId AND coup.company.id = :companyId")
	void updateCoupon(@Param("endDate")Date endDate, @Param("price")double price, @Param("couponId")int couponId, @Param("companyId")int companyId);
	
	/***
	 * Get Coupon by title
	 * @param title
	 * @return Coupon
	 */
	Coupon findBytitle(String title);
	
	/***
	 * Getting Coupon By Type
	 * @param type
	 * @return ArrayList 
	 */
	ArrayList<Coupon> findBytype(CouponType type); 
	
	/***
	 * Get Coupon by Company ID
	 * @param companyId
	 * @return Array List
	 */
	ArrayList<Coupon> findByCompanyId(int companyId);

	/***
	 * Get Coupon by Customer ID
	 * @param customerId
	 * @return Array List
	 */
	ArrayList<Coupon> findByCustomersId(int customerId);

	/***
	 * Get Coupon by Company's ID
	 * @param companyId
	 * @return Coupon
	 */
	Coupon findByidAndCompanyId(int couponId, int companyId);
	
	/***
	 * Get Coupon by Customer's ID
	 * @param customerId
	 * @return Coupon
	 */
	Coupon findByidAndCustomersId(int couponId, int customerId);
	
	/***
	 * Get all Company's Coupons by type
	 * @param type
	 * @param companyId
	 * @return ArrayList<Coupon>
	 */
	ArrayList<Coupon> findBytypeAndCompanyId(CouponType type , int companyId);
	
	/***
	 * Get all Customer's Coupons by type
	 * @param type
	 * @param customerId
	 * @return ArrayList<Coupon>
	 */
	ArrayList<Coupon> findBytypeAndCustomersId(CouponType type , int customerId);
	
	/***
	 * Find Customer's Coupons by price
	 * @param customerId
	 * @param price
	 * @return ArrayList<Coupon>
	 */
	@Query("SELECT coup FROM Coupon coup WHERE coup.price < :price AND coup.id IN (SELECT coup.id FROM coup.customers c WHERE c.id = :customerId)") 
	ArrayList<Coupon> findCustomerCouponsByPrice(@Param("customerId") int customerId, @Param("price") double price);
}
