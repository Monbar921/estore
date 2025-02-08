package ru.isands.test.estore.dao.repo;

import org.springframework.data.jpa.repository.Query;
import ru.isands.test.estore.dao.entity.ElectroItem;
import ru.isands.test.estore.dao.entity.ElectroType;
import ru.isands.test.estore.dao.entity.Purchase;

import java.util.List;

public interface PurchaseRepository extends BaseRepository<Purchase, Long> {
    @Query("select ei from ElectroItem ei join ElectroType et on ei.electroType = et where ei.name = ?1 and et.name = ?2")
    List<ElectroItem> findByNameAndElectroTypeName(String name, String electroTypeName);

    @Query("select ei from ElectroItem ei where ei.name = ?1 and ei.electroType = ?2")
    List<ElectroItem> findByNameAndElectroTypeId(String name, ElectroType electroType);
}