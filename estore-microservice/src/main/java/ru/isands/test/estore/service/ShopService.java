package ru.isands.test.estore.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.isands.test.estore.dao.entity.*;
import ru.isands.test.estore.dao.repo.ShopRepository;
import ru.isands.test.estore.dto.ShopDTO;
import ru.isands.test.estore.exception.EntityNotExistsException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class ShopService extends BaseService<Shop, Long> {
    private final ElectroItemService electroItemService;
    private final PurchaseTypeService purchaseTypeService;

    public ShopService(ShopRepository repository, ModelMapper modelMapper, ElectroItemService electroItemService
            , PurchaseTypeService purchaseTypeService) {
        super(repository, modelMapper);
        this.electroItemService = electroItemService;
        this.purchaseTypeService = purchaseTypeService;
    }

    public void save(ShopDTO dto) {
        List<Shop> existingEntities = ((ShopRepository) getBaseRepository())
                .findByNameAndAddress(dto.getName(), dto.getAddress());
        Shop entity = getModelMapper().map(dto, Shop.class);
        entity.setId(dto.getId());
        super.save(entity, existingEntities.size() != 0);
    }

    public List<Shop> findByNameAndAddress(String name, String address) {
        return ((ShopRepository) getBaseRepository()).findByNameAndAddress(name, address);
    }

    public void makePurchase(Shop shop, ElectroItem item, int purchaseVolume, boolean suppressItemCheck) {
        ElectroShop electroShop = shop.getElectroShops().stream()
                .filter(es -> suppressItemCheck || (es.getElectroItem().equals(item) && es.getCount() >= purchaseVolume))
                .findFirst().orElseThrow(
                        () -> new EntityNotExistsException("Such electronic item does not exists in this store")
                );
        electroShop.setCount(electroShop.getCount() - purchaseVolume);

        electroItemService.makePurchase(item, purchaseVolume);
    }

    @Transactional
    public Long findPurchaseSumByPurchaseType(Long shopId, Long purchaseTypeId) {
        Shop shop = getBaseRepository().findById(shopId).orElseThrow(
                () -> new EntityNotExistsException("Such shop does not exists")
        );

        PurchaseType purchaseType  = purchaseTypeService.findById(purchaseTypeId).orElseThrow(
                () -> new EntityNotExistsException("Such electro type does not exists")
        );

        return ((ShopRepository) getBaseRepository()).findPurchaseSumByPurchaseType(shop, purchaseType);
    }

    public List<ShopDTO> findAllDto(int page, int size) {
        List<Shop> entities = super.findAll(page, size);
        return entities.stream().map(ShopDTO::new).collect(Collectors.toList());
    }
}
