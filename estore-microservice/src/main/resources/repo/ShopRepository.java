package repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.isands.test.estore.dao.entity.Employee;
import ru.isands.test.estore.dao.entity.Shop;

import java.util.Date;
import java.util.List;

public interface ShopRepository extends JpaRepository<Shop, Long> {
	
//	@Query("select s from Shop s where e.lastName = ?1 and e.firstName = ?2 and e.patronymic = ?3 and e.birthDate = ?4")
//	public Employee findFullName(String lastName, String firstName, String patronymic, Date birthDate);
	
//	@Query("select e from Employee e where e.positionId = ?1")
//	public List<Employee> findByPosition(Long positionId);
	
}