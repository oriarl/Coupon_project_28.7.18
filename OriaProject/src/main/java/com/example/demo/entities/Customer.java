package com.example.demo.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
/***
 * Entity for Customer table
 * @author Oria
 *
 */
@NoArgsConstructor
@Entity
@Table(name = "Customer")
@XmlRootElement
public class Customer implements Serializable{

	// Objec's members
	@Getter
	@Setter
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Getter
	@Setter
	@Column
	private String custName;

	@Getter
	@Setter
	@Column
	private String password;
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Coupon> coupons = new ArrayList<>();


	/***
	 * CTR with no id
	 * @param id
	 * @param custName
	 * @param password
	 */
	public Customer( String custName, String password) {
		super();
		this.custName = custName;
		this.password = password;
	}
	

	/***
	 * ToString
	 */
	@Override
	public String toString() {
		return "Customer [id=" + id + ", custName=" + custName + ", password=" + password  + "]";
	}

}
