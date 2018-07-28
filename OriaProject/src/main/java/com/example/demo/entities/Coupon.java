package com.example.demo.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.example.demo.common.CouponType;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
/***
 * Entity for Coupon table
 * @author Oria
 *
 */
@NoArgsConstructor
@Entity
@Table(name = "Coupon")
@XmlRootElement
public class Coupon implements Serializable{

	// Object's members
	@Getter
	@Setter
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Getter
	@Setter
	@Column
	private String title;

	@Getter
	@Setter
	@Column
	private Date startDate;

	@Getter
	@Setter
	@Column
	private Date endDate;

	@Getter
	@Setter
	@Column
	private int amount;

	@Getter
	@Setter
	@Column
	@Enumerated(EnumType.STRING)
	private CouponType type;

	@Getter
	@Setter
	@Column
	private String message;

	@Getter
	@Setter
	@Column
	private double price;

	@Getter
	@Setter
	@Column
	private String image;


	@Setter
	@ManyToOne
	@JoinColumn(name = "Company_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Company company;
	
	@ManyToMany(mappedBy = "coupons")
	private List<Customer> customers = new ArrayList<>();

	/***
	 * CTR with no id
	 * @param title
	 * @param startDate
	 * @param endDate
	 * @param amount
	 * @param type
	 * @param message
	 * @param price
	 * @param image
	 */
	public Coupon( String title, Date startDate, Date endDate, int amount, CouponType type, String message,
			double price, String image) {
		super();

		this.title = title;
		this.startDate = startDate;
		this.endDate = endDate;
		this.amount = amount;
		this.type = type;
		this.message = message;
		this.price = price;
		this.image = image;
	}
	

	/***
	 * ToString
	 */
	@Override
	public String toString() {
		return "Coupon [id=" + id + ", title=" + title + ", startDate=" + startDate + ", endDate=" + endDate
				+ ", amount=" + amount + ", type=" + type + ", message=" + message + ", price=" + price + ", image="
				+ image + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}

	

	

}
