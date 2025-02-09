package ru.isands.test.estore.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"electroemployees"})
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

	@OneToMany(mappedBy = "electroType")
	Set<ElectroEmployee> electroemployees;
}
