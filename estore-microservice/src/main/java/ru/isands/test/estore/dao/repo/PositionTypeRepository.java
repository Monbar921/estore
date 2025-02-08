package ru.isands.test.estore.dao.repo;

import org.springframework.data.jpa.repository.Query;
import ru.isands.test.estore.dao.entity.PositionType;

import java.util.List;

public interface PositionTypeRepository extends BaseRepository<PositionType, Long> {
    @Query("select pt from PositionType pt where pt.name = ?1")
    List<PositionType> findByName(String name);
}