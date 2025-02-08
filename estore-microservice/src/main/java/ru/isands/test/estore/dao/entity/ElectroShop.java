package ru.isands.test.estore.dao.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

//@Getter
//@Setter
//@Entity
//@IdClass(ElectroShopPK.class)
//@Table(name = "store_eshop")
public class ElectroShop implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Идентификатор магазина
	 */
	@Id
	@Column(name = "shopId", nullable = false)
	Long shopId;
	
	/**
	 * Идентификатор электротовара
	 */
	@Id
	@Column(name = "electroItemId", nullable = false)
	Long electroItemId;
	
	/**
	 * Оставшееся количество
	 */
	@Column(name = "count_", nullable = false)
	int count;
}
