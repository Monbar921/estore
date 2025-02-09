package ru.isands.test.estore.dao.entity;

import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
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
