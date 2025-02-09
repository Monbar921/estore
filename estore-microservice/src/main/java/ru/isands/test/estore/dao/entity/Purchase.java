package ru.isands.test.estore.dao.entity;


import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Purchase implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Идентификатор покупки
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	private Long id;
	
	/**
	 * Идентификатор товара
	 */
	@ManyToOne
	@JoinColumn(name = "electro_id", nullable = false)
	private ElectroItem electroItem;
	
	/**
	 * Идентификатор сотрудника
	 */
	@ManyToOne
	@JoinColumn(name = "employee_id", nullable = false)
	private Employee employee;
	
	/**
	 * Идентификатор магазина
	 */
	@ManyToOne
	@JoinColumn(name = "shop_id", nullable = false)
	private Shop shop;
	
	/**
	 * Дата совершения покупки
	 */
	@Column(nullable = false)
	private LocalDate purchaseDate;
	
	/**
	 * Способ оплаты
	 */
	@ManyToOne
	@JoinColumn(name = "type_id", nullable = false)
	private PurchaseType purchaseType;
}