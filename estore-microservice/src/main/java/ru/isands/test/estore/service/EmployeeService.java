package ru.isands.test.estore.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.isands.test.estore.dao.entity.*;
import ru.isands.test.estore.dao.repo.ElectroItemRepository;
import ru.isands.test.estore.dao.repo.EmployeeRepository;
import ru.isands.test.estore.dao.repo.PositionTypeRepository;
import ru.isands.test.estore.dto.ElectroItemDTO;
import ru.isands.test.estore.dto.EmployeeDTO;
import ru.isands.test.estore.exception.EntityAlreadyExistsException;
import ru.isands.test.estore.exception.EntityNotExistsException;
import ru.isands.test.estore.exception.TooManyEntitiesExistsException;

import java.time.LocalDate;
import java.util.List;

@Service
public class EmployeeService extends BaseService<Employee, Long>{
    private final PositionTypeService positionTypeService;
    private final ShopService shopService;


    public EmployeeService(EmployeeRepository repository, ModelMapper modelMapper,
                           PositionTypeService positionTypeService, ShopService shopService) {
        super(repository, modelMapper);
        this.positionTypeService = positionTypeService;
        this.shopService = shopService;
    }

    public void save(EmployeeDTO dto) {
        List<Shop> shops = shopService.findByNameAndAddress(dto.getShop().getName(), dto.getShop().getAddress());

        if(shops.size() == 0){
            throw new EntityNotExistsException("Such shop does not exists");
        }else if(shops.size() > 1){
            throw new TooManyEntitiesExistsException("More than one shop with such name and address");
        }

        List<PositionType> positions = positionTypeService.findByName(dto.getPositionType().getName());

        if(positions.size() == 0){
            throw new EntityNotExistsException("Such position type does not exists");
        }else if(positions.size() > 1){
            throw new TooManyEntitiesExistsException("More than one position type with such name");
        }

        List<Employee> existingEntities = ((EmployeeRepository)getBaseRepository())
                .findByFullNameAndBirthdayAndPositionAndShop(dto.getLastName(), dto.getFirstName(), dto.getPatronymic(),
                        dto.getBirthDate(), positions.get(0), shops.get(0));

        Employee entity = getModelMapper().map(dto, Employee.class);
        entity.setPositionType(positions.get(0));
        entity.setShop(shops.get(0));

        super.save(entity, existingEntities.size() != 0);
    }

    public List<Employee> findByFullNameAndBirthdayAndPositionAndShop(String lastName, String firstName, String patronymic,
                                     LocalDate birthDate, PositionType positionType, Shop shop) {
        return ((EmployeeRepository)getBaseRepository())
                .findByFullNameAndBirthdayAndPositionAndShop(lastName, firstName, patronymic,
                                                            birthDate, positionType, shop);
    }

    public List<Employee> findByFullNameAndBirthdayAndPositionAndShop(String lastName, String firstName, String patronymic,
                                                                      LocalDate birthDate, String positionTypeName, Shop shop) {
        return ((EmployeeRepository)getBaseRepository())
                .findByFullNameAndBirthdayAndPositionAndShop(lastName, firstName, patronymic,
                        birthDate, positionTypeName, shop);
    }
}
