package ru.isands.test.estore.dao.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Data
@Entity
public class PurchaseType {
	
	/**
	 * Идентификатор типа покупки
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	private Long id;
	
	/**
	 * Наименование типа покупки
	 */
	@Column(nullable = false, length = 150)
	private String name;
}
