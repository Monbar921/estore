package ru.isands.test.estore.dao.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.isands.test.estore.dao.entity.ElectroType;
import ru.isands.test.estore.dao.entity.PurchaseType;
import ru.isands.test.estore.dao.entity.Shop;

import java.util.List;

public interface ShopRepository extends BaseRepository<Shop, Long> {
	
//	@Query("select s from Shop s where e.lastName = ?1 and e.firstName = ?2 and e.patronymic = ?3 and e.birthDate = ?4")
//	public Employee findFullName(String lastName, String firstName, String patronymic, Date birthDate);
	
//	@Query("select e from Employee e where e.positionId = ?1")
//	public List<Employee> findByPosition(Long positionId);
    @Query("select s from Shop s where s.name = ?1 and s.address = ?2")
	List<Shop> findByNameAndAddress(String name, String address);

    @Query("select sum(ei.price) from Shop s join Purchase p on p.shop = s join PurchaseType pt on p.purchaseType = pt " +
            "join ElectroItem ei on p.electroItem = ei " +
            "where s = ?1 and pt = ?2")
    Long findPurchaseSumByPurchaseType(Shop shop, PurchaseType purchaseType);
}