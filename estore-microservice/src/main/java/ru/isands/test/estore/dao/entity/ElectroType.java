package ru.isands.test.estore.dao.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
public class ElectroType implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Идентификатор типа электроники
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	private Long id;
	
	/**
	 * Наименование типа электроники
	 */
	@Column(nullable = false, length = 150)
	private String name;
}
