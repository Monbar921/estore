package ru.isands.test.estore.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class PositionType implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Идентификатор должности
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	private Long id;
	
	/**
	 * Наименование должности
	 */
	@Column(nullable = false, length = 150)
	private String name;
}
