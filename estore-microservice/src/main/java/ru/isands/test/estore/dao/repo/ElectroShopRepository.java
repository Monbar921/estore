package ru.isands.test.estore.dao.repo;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ru.isands.test.estore.dao.entity.*;

import java.util.Optional;

public interface ElectroShopRepository extends BaseRepository<ElectroShop, Long> {
    @Query("select es from ElectroShop es where es.shop = ?1 and es.electroItem = ?2")
    Optional<ElectroShop> findByShopAndElectroItem(Shop shop, ElectroItem electroItem);

    @Modifying
    @Query("DELETE FROM ElectroShop e WHERE e.shop = ?1 AND e.electroItem = ?2")
    void deleteByShopAndElectroItem(Shop shop, ElectroItem electroItem);
}