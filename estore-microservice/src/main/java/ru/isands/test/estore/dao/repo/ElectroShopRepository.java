package ru.isands.test.estore.dao.repo;

import org.springframework.data.jpa.repository.Query;
import ru.isands.test.estore.dao.entity.ElectroItem;
import ru.isands.test.estore.dao.entity.ElectroShop;
import ru.isands.test.estore.dao.entity.ElectroType;
import ru.isands.test.estore.dao.entity.Shop;

import java.util.List;
import java.util.Optional;

public interface ElectroShopRepository extends BaseRepository<ElectroShop, Long> {
    @Query("select es from ElectroShop es where es.shop = ?1 and es.electroItem = ?2")
    Optional<ElectroShop> findByShopAndElectroItem(Shop shop, ElectroItem electroItem);
}