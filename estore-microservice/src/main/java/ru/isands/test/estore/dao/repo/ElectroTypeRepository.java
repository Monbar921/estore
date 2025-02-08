package ru.isands.test.estore.dao.repo;

import org.springframework.data.jpa.repository.Query;
import ru.isands.test.estore.dao.entity.ElectroType;

import java.util.List;

public interface ElectroTypeRepository extends BaseRepository<ElectroType, Long> {
    @Query("select et from ElectroType et where et.name = ?1")
    List<ElectroType> findByName(String name);
}