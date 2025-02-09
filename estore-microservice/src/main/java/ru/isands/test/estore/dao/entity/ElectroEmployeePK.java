package ru.isands.test.estore.dao.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Embeddable
public class ElectroEmployeePK implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 *  Идентификатор сотрудника
	 */
	@Column(name = "employee_id")
	private Long employeeId;
	
	/**
	 *  Идентификатор типа электротовара
	 */
	@Column(name = "electro_type_id")
	private Long electroTypeId;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		ElectroEmployeePK that = (ElectroEmployeePK) o;
		return Objects.equals(employeeId, that.employeeId) && Objects.equals(electroTypeId, that.electroTypeId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(employeeId, electroTypeId);
	}
}
