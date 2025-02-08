package ru.isands.test.estore.dao.entity;


import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

import javax.persistence.*;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Entity
public class Employee implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Идентификатор сотрудника
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	private Long id;
	
	/**
	 * Фамилия сотрудника
	 */
	@Column(nullable = false, length = 100)
	private String lastName;
	
	/**
	 * Имя сотрудника
	 */
	@Column(nullable = false, length = 100)
	private String firstName;
	
	/**
	 * Отчество сотрудника
	 */
	@Column(name = "patronymic", nullable = false, length = 100)
	private String patronymic;
	
	/**
	 * Дата рождения сотрудника
	 */
	@Column(nullable = false)
	private LocalDate birthDate;
	
	/**
	 * Ссылка на должность сотрудника
	 */
	@ManyToOne
	@JoinColumn(name = "position_id", nullable = false)
	private PositionType positionType;

	/**
	 * Магазин, где работает сотрудник
	 */
	@ManyToOne
	@JoinColumn(name = "shop_id", nullable = false)
	private Shop shop;

	/**
	 * Пол сотрудника (true - мужской, false - женский)
	 */
	@Column(name = "gender", nullable = false)
	private boolean gender;
}