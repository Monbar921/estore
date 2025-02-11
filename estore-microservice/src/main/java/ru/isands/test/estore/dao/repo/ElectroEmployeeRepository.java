package ru.isands.test.estore.dao.repo;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ru.isands.test.estore.dao.entity.*;

import java.util.Optional;

public interface ElectroEmployeeRepository extends BaseRepository<ElectroEmployee, Long> {
    @Query("select ee from ElectroEmployee ee where ee.employee = ?1 and ee.electroType = ?2")
    Optional<ElectroEmployee> findByEmployeeAndElectroType(Employee employee, ElectroType electroType);

    @Modifying
    @Query("DELETE FROM ElectroEmployee e WHERE e.employee = ?1 AND e.electroType = ?2")
    void deleteByEmployeeAndElectroType(Employee employee, ElectroType electroType);
}