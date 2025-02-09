package ru.isands.test.estore.service;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.isands.test.estore.dao.entity.*;
import ru.isands.test.estore.dao.repo.EmployeeRepository;
import ru.isands.test.estore.dto.BestSellerDTO;
import ru.isands.test.estore.dto.ElectroItemDTO;
import ru.isands.test.estore.dto.EmployeeDTO;
import ru.isands.test.estore.exception.EntityNotExistsException;;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeService extends BaseService<Employee, Long>{
    private final PositionTypeService positionTypeService;
    private final ShopService shopService;
    private final ElectroTypeService electroTypeService;


    public EmployeeService(EmployeeRepository repository, ModelMapper modelMapper,
                           PositionTypeService positionTypeService, ShopService shopService, ElectroTypeService electroTypeService) {
        super(repository, modelMapper);
        this.positionTypeService = positionTypeService;
        this.shopService = shopService;
        this.electroTypeService = electroTypeService;
    }

    public void save(EmployeeDTO dto) {
        Optional<Shop> shop = shopService.findById(dto.getShop());

        if(shop.isEmpty()){
            throw new EntityNotExistsException("Such shop does not exists");
        }

        Optional<PositionType> position = positionTypeService.findById(dto.getPositionType());

        if(position.isEmpty()){
            throw new EntityNotExistsException("Such position type does not exists");
        }

        List<Employee> existingEntities = ((EmployeeRepository)getBaseRepository())
                .findByFullNameAndBirthdayAndPositionAndShop(dto.getLastName(), dto.getFirstName(), dto.getPatronymic(),
                        dto.getBirthDate(), position.get(), shop.get());

        Employee entity = getModelMapper().map(dto, Employee.class);
        entity.setPositionType(position.get());
        entity.setShop(shop.get());

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

    public List<BestSellerDTO> findBestEmployeesByPositionAndYearAndPurchaseCount(Long positionTypeId, LocalDate year) {
        PositionType positionType = positionTypeService.findById(positionTypeId).orElseThrow(
                () -> new EntityNotExistsException("Such position type does not exists")
        );

        if(year == null){
            year = LocalDate.of(LocalDate.now().getYear(), 1, 1);
        }

        LocalDate nextYear = year.plusYears(1);

        return ((EmployeeRepository)getBaseRepository()).findBestEmployeesByPositionAndYearOrderByPurchaseCountDesc(
                positionType, year, nextYear);
    }

    public List<BestSellerDTO> findBestEmployeesByPositionAndPurchaseSum(Long positionTypeId, LocalDate year) {
        PositionType positionType = positionTypeService.findById(positionTypeId).orElseThrow(
                () -> new EntityNotExistsException("Such position type does not exists")
        );

        if(year == null){
            year = LocalDate.of(LocalDate.now().getYear(), 1, 1);
        }

        LocalDate nextYear = year.plusYears(1);

        return ((EmployeeRepository)getBaseRepository()).findBestEmployeesByPositionAndYearOrderByPurchaseSumDesc(
                positionType, year, nextYear);
    }

    public Optional<BestSellerDTO> findBestEmployeeByPositionAndElectroType(Long positionTypeId, Long electroTypeId) {
        PositionType positionType = positionTypeService.findById(positionTypeId).orElseThrow(
                () -> new EntityNotExistsException("Such position type does not exists")
        );

        ElectroType electroType = electroTypeService.findById(electroTypeId).orElseThrow(
                () -> new EntityNotExistsException("Such electro type does not exists")
        );
        List<BestSellerDTO> bestSellers = ((EmployeeRepository)getBaseRepository()).findBestEmployeeByPositionAndElectroType(
                positionType, electroType,  PageRequest.of(0, 1));

        return Optional.ofNullable(bestSellers.get(0));
    }

    public List<EmployeeDTO> findAllDto(int page, int size) {
        List<Employee> entities = super.findAll(page, size);
        return entities.stream().map(EmployeeDTO::new).collect(Collectors.toList());
    }
}
