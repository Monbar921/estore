package ru.isands.test.estore.dao.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ElectroEmployee implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ElectroEmployeePK electroEmployeePK;
	/**
	 * Идентификатор магазина
	 */
	@ManyToOne
	@MapsId("employeeId")
	@JoinColumn(name = "employee_id", nullable = false)
	private Employee employee;
	
	/**
	 * Идентификатор электротовара
	 */
	@ManyToOne
	@MapsId("electroTypeId")
	@JoinColumn(name = "electro_type_id", nullable = false)
	private ElectroType electroType;

}
