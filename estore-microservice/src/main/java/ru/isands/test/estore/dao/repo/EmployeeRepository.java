package ru.isands.test.estore.dao.repo;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import ru.isands.test.estore.dao.entity.*;
import ru.isands.test.estore.dto.BestSellerDTO;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface EmployeeRepository extends BaseRepository<Employee, Long> {

    @Query("select e from Employee e where e.lastName = ?1 and e.firstName = ?2 and e.patronymic = ?3 " +
            "and e.birthDate = ?4 and e.positionType = ?5 and e.shop = ?6")
    List<Employee> findByFullNameAndBirthdayAndPositionAndShop(String lastName, String firstName, String patronymic,
                                                               LocalDate birthDate, PositionType positionType, Shop shop);

    @Query("select e from Employee e join PositionType pt on e.positionType = pt " +
            "where e.lastName = ?1 and e.firstName = ?2 and e.patronymic = ?3 " +
            "and e.birthDate = ?4 and pt.name = ?5 and e.shop = ?6")
    List<Employee> findByFullNameAndBirthdayAndPositionAndShop(String lastName, String firstName, String patronymic,
                                                               LocalDate birthDate, String positionTypeName, Shop shop);


    @Query("select new ru.isands.test.estore.dto.BestSellerDTO(e, count(1), 0l) from Employee e join Purchase p on e = p.employee where e.positionType = ?1 " +
            "and p.purchaseDate >= ?2 and p.purchaseDate < ?3 group by e order by 2 desc")
    List<BestSellerDTO> findBestEmployeesByPositionAndYearOrderByPurchaseCountDesc(PositionType positionType, LocalDate startDate, LocalDate endDate);

    @Query("select new ru.isands.test.estore.dto.BestSellerDTO(e, 0l, sum(ei.price)) from Employee e join Purchase p on e = p.employee " +
            "join ElectroItem ei on ei = p.electroItem " +
            "where e.positionType = ?1 and p.purchaseDate >= ?2 and p.purchaseDate < ?3 group by e order by 3 desc")
    List<BestSellerDTO> findBestEmployeesByPositionAndYearOrderByPurchaseSumDesc(PositionType positionType, LocalDate startDate, LocalDate endDate);

    @Query("select new ru.isands.test.estore.dto.BestSellerDTO(e, count(1), 0l) from Employee e join Purchase p on e = p.employee " +
            "join ElectroItem ei on ei = p.electroItem join ElectroType et on et = ei.electroType " +
            "where e.positionType = ?1 and et = ?2 group by e order by 2 desc")
    List<BestSellerDTO> findBestEmployeeByPositionAndElectroType(PositionType positionType, ElectroType electroType
            , Pageable pageable);
}