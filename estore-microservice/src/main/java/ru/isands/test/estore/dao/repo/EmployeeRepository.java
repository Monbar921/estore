package ru.isands.test.estore.dao.repo;

import org.springframework.data.jpa.repository.Query;
import ru.isands.test.estore.dao.entity.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface EmployeeRepository extends BaseRepository<Employee, Long> {

    @Query("select e from Employee e where e.lastName = ?1 and e.firstName = ?2 and e.patronymic = ?3 and e.birthDate = ?4")
    public List<Employee> findByFullName(String lastName, String firstName, String patronymic, Date birthDate);

    @Query("select e from Employee e where e.lastName = ?1 and e.firstName = ?2 and e.patronymic = ?3 " +
            "and e.birthDate = ?4 and e.positionType = ?5 and e.shop = ?6")
    public List<Employee> findByFullNameAndBirthdayAndPositionAndShop(String lastName, String firstName, String patronymic,
                                                                      LocalDate birthDate, PositionType positionType, Shop shop);
    @Query("select e from Employee e where e.positionType = ?1")
    public List<Employee> findByPosition(PositionType positionType);
}