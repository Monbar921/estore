package ru.isands.test.estore.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.isands.test.estore.dao.entity.*;
import ru.isands.test.estore.dao.repo.ElectroItemRepository;
import ru.isands.test.estore.dao.repo.ElectroShopRepository;
import ru.isands.test.estore.dto.ElectroItemDTO;
import ru.isands.test.estore.dto.ElectroShopDTO;
import ru.isands.test.estore.exception.EntityNotExistsException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ElectroShopService extends BaseService<ElectroShop, Long>{
    private final ShopService shopService;
    private final ElectroItemService electroItemService;

    public ElectroShopService(ElectroShopRepository repository, ModelMapper modelMapper, ShopService shopService
                            ,ElectroItemService electroItemService) {
        super(repository, modelMapper);
        this.shopService = shopService;
        this.electroItemService = electroItemService;
    }

    public void save(ElectroShopDTO dto) {
        Shop shop = shopService.findById(dto.getShop()).orElseThrow(
                () -> new EntityNotExistsException("Such shop does not exists")
        );

        ElectroItem electroItem = electroItemService.findById(dto.getElectroItem()).orElseThrow(
                () -> new EntityNotExistsException("Such electro item does not exists")
        );

        Optional<ElectroShop> existingEntity = ((ElectroShopRepository)getBaseRepository())
                .findByShopAndElectroItem(shop, electroItem);


        ElectroShopPK pk = new ElectroShopPK(dto.getShop(), dto.getElectroItem());
        ElectroShop entity = new ElectroShop(pk, shop, electroItem, dto.getCount());

        super.save(entity, existingEntity.isPresent());
    }

    public List<ElectroShopDTO> findAllDto(int page, int size) {
        List<ElectroShop> entities = super.findAll(page, size);
        return entities.stream().map(ElectroShopDTO::new).collect(Collectors.toList());
    }

}
