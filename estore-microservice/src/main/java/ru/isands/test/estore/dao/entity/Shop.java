package ru.isands.test.estore.dao.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Entity
public class Shop implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Идентификатор магазина
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	private Long id;
	
	/**
	 * Наименование магазина
	 */
	@Column(nullable = false, length = 150)
	@NotNull
	@NotEmpty
	private String name;
	
	/**
	 * Адрес магазина
	 */
	private String address;
}
