package ru.isands.test.estore.dao.entity;

import javax.persistence.*;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
public class ElectroShop implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ElectroShopPK electroShopPK;
	/**
	 * Идентификатор магазина
	 */
	@ManyToOne
	@MapsId("shopId")
	@JoinColumn(name = "shop_id", nullable = false)
	private Shop shop;
	
	/**
	 * Идентификатор электротовара
	 */
	@ManyToOne
	@MapsId("electroItemId")
	@JoinColumn(name = "electro_item_id", nullable = false)
	private ElectroItem electroItem;
	
	/**
	 * Оставшееся количество
	 */
	@Column(nullable = false)
	private int count;
}
