package ru.isands.test.estore.dao.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
public class ElectroItem implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Идентификатор товара
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	private Long id;
	
	/**
	 * Наименование товара
	 */
	@Column(nullable = false, length = 150)
	private String name;

	/**
	 * Тип электроники
	 */
	@ManyToOne
	@JoinColumn(name = "etype_id", nullable = false)
	private ElectroType electroType;

	/**
	 * Стоимость товара в рублях
	 */
	@Column(nullable = false)
	private Long price;

	/**
	 * Количество товара
	 */
	@Column(nullable = false)
	private Integer count;

	/**
	 * Признак архивности товара
	 */
	@Column(name = "archive", nullable = false)
	private boolean archived;

	/**
	 * Описание товара
	 */
	@Column
	private String description;
}
