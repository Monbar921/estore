package ru.isands.test.estore.dao.repo;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import ru.isands.test.estore.dao.entity.ElectroItem;
import ru.isands.test.estore.dao.entity.Purchase;
import ru.isands.test.estore.dao.entity.Shop;
import ru.isands.test.estore.dto.PurchaseDTO;

import java.time.LocalDate;
import java.util.List;

public interface PurchaseRepository extends BaseRepository<Purchase, Long> {
    @Query("select p from Purchase p where p.electroItem = ?1 and p.shop = ?2 and p.purchaseDate = ?3")
    List<Purchase> findByElectroItemAndShopAndDate(ElectroItem electroItem, Shop shop, LocalDate purchaseDate);


    @Query("select new ru.isands.test.estore.dto.PurchaseDTO(p) from Purchase p where p.purchaseDate >= ?1 " +
            "and p.purchaseDate < ?2 order by p.purchaseDate asc")
    List<PurchaseDTO> findAllInsideDatesAsc(LocalDate from, LocalDate to, Pageable pageable);

    @Query("select new ru.isands.test.estore.dto.PurchaseDTO(p) from Purchase p where p.purchaseDate >= ?1 " +
            "and p.purchaseDate < ?2 order by p.purchaseDate desc")
    List<PurchaseDTO> findAllInsideDatesDesc(LocalDate from, LocalDate to, Pageable pageable);
}