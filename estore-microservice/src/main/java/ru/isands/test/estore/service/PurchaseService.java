package ru.isands.test.estore.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.isands.test.estore.dao.entity.*;
import ru.isands.test.estore.dao.repo.EmployeeRepository;
import ru.isands.test.estore.dao.repo.PurchaseRepository;
import ru.isands.test.estore.dto.EmployeeDTO;
import ru.isands.test.estore.dto.PositionTypeDTO;
import ru.isands.test.estore.dto.PurchaseDTO;
import ru.isands.test.estore.exception.EntityAlreadyExistsException;
import ru.isands.test.estore.exception.EntityNotExistsException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PurchaseService extends BaseService<Purchase, Long> {
    private final ElectroItemService electroItemService;
    private final ShopService shopService;
    private final EmployeeService employeeService;
    private final PurchaseTypeService purchaseTypeService;

    private final static int PURCHASE_VOLUME = 1;

    public PurchaseService(PurchaseRepository repository, ModelMapper modelMapper,
                           ElectroItemService electroItemService, ShopService shopService,
                           EmployeeService employeeService, PurchaseTypeService purchaseTypeService) {
        super(repository, modelMapper);
        this.electroItemService = electroItemService;
        this.shopService = shopService;
        this.employeeService = employeeService;
        this.purchaseTypeService = purchaseTypeService;
    }

    @Transactional
    public void save(PurchaseDTO dto) {
        Purchase entity = prepareEntity(dto);
        shopService.makePurchase(entity.getShop(), entity.getElectroItem(), PURCHASE_VOLUME, false);

        List<Purchase> existingEntities = ((PurchaseRepository)getBaseRepository())
                .findByElectroItemAndShopAndDate(entity.getElectroItem(), entity.getShop(), dto.getPurchaseDate());

        super.save(entity, existingEntities.size() != 0);
    }

    private Purchase prepareEntity(PurchaseDTO dto){
        ElectroItem item = electroItemService.findById(dto.getElectroItem()).orElseThrow(
                () -> new EntityNotExistsException("Such electronic item does not exists")
        );

        Shop shop = shopService.findById(dto.getShop()).orElseThrow(
                () -> new EntityNotExistsException("Such shop does not exists")
        );

        Employee employee = employeeService.findById(dto.getEmployee()).orElseThrow(
                () -> new EntityNotExistsException("Such employee does not exists")
        );

        PurchaseType purchaseType = purchaseTypeService.findById(dto.getPurchaseType()).orElseThrow(
                () -> new EntityNotExistsException("Such purchase type does not exists")
        );

        Purchase entity = getModelMapper().map(dto, Purchase.class);
        entity.setElectroItem(item);
        entity.setShop(shop);
        entity.setEmployee(employee);
        entity.setPurchaseType(purchaseType);

        return entity;
    }

    @Transactional
    public void saveWithoutItemCheck(PurchaseDTO dto) {
        Purchase entity = prepareEntity(dto);
        shopService.makePurchase(entity.getShop(), entity.getElectroItem(), PURCHASE_VOLUME, true);

        List<Purchase> existingEntities = ((PurchaseRepository)getBaseRepository())
                .findByElectroItemAndShopAndDate(entity.getElectroItem(), entity.getShop(), dto.getPurchaseDate());

        super.save(entity, existingEntities.size() != 0);
    }

    public List<PurchaseDTO> findAllDto(int page, int size) {
        List<Purchase> entities = super.findAll(page, size);
        return entities.stream().map(PurchaseDTO::new).collect(Collectors.toList());
    }
}
