package ru.isands.test.estore.dao.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.isands.test.estore.dao.entity.PurchaseType;
import ru.isands.test.estore.dao.entity.Shop;

import java.util.List;

public interface PurchaseTypeRepository extends BaseRepository<PurchaseType, Long> {
    @Query("select pt from PurchaseType pt where pt.name = ?1")
    List<PurchaseType> findByName(String name);
}