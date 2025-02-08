package ru.isands.test.estore.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.isands.test.estore.dao.entity.*;
import ru.isands.test.estore.dao.repo.EmployeeRepository;
import ru.isands.test.estore.dao.repo.PurchaseRepository;
import ru.isands.test.estore.dto.EmployeeDTO;
import ru.isands.test.estore.dto.PurchaseDTO;
import ru.isands.test.estore.exception.EntityAlreadyExistsException;
import ru.isands.test.estore.exception.EntityNotExistsException;

import java.util.List;
import java.util.Optional;

@Service
public class PurchaseService extends BaseService<Purchase, Long> {
    private final ElectroItemService electroItemService;
    private final ShopService shopService;
    private final EmployeeService employeeService;
    private final PurchaseTypeService purchaseTypeService;

    public PurchaseService(PurchaseRepository repository, ModelMapper modelMapper,
                           ElectroItemService electroItemService, ShopService shopService,
                           EmployeeService employeeService, PurchaseTypeService purchaseTypeService) {
        super(repository, modelMapper);
        this.electroItemService = electroItemService;
        this.shopService = shopService;
        this.employeeService = employeeService;
        this.purchaseTypeService = purchaseTypeService;
    }

    public void save(PurchaseDTO dto) {
        Optional<ElectroItem> item = electroItemService.findById(dto.getElectroItem());

        if(item.isEmpty()){
            throw new EntityNotExistsException("Such electronic item does not exists");
        }

        Optional<Shop> shop = shopService.findById(dto.getShop());

        if(shop.isEmpty()){
            throw new EntityNotExistsException("Such shop does not exists");
        }

        Optional<Employee> employee = employeeService.findById(dto.getEmployee());

        if(employee.isEmpty()){
            throw new EntityNotExistsException("Such employee does not exists");
        }

        Optional<PurchaseType> purchaseType = purchaseTypeService.findById(dto.getPurchaseType());

        if(purchaseType.isEmpty()){
            throw new EntityNotExistsException("Such purchase type does not exists");
        }

        List<Purchase> existingEntities = ((PurchaseRepository)getBaseRepository())
                .findByElectroItemAndShopAndDate(item.get(), shop.get(), dto.getPurchaseDate());

        Purchase entity = getModelMapper().map(dto, Purchase.class);
        entity.setElectroItem(item.get());
        entity.setShop(shop.get());
        entity.setEmployee(employee.get());
        entity.setPurchaseType(purchaseType.get());

        super.save(entity, existingEntities.size() != 0);
    }
}
