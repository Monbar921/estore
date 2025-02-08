package ru.isands.test.estore.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.isands.test.estore.dao.entity.ElectroType;
import ru.isands.test.estore.dao.entity.Shop;
import ru.isands.test.estore.dao.repo.ElectroTypeRepository;
import ru.isands.test.estore.dao.repo.ShopRepository;
import ru.isands.test.estore.dto.ShopDTO;

import java.util.List;


@Service
public class ShopService extends BaseService<Shop, Long>{

    public ShopService(ShopRepository repository, ModelMapper modelMapper) {
        super(repository, modelMapper);
    }

    public void save(ShopDTO dto) {
        List<Shop> existingEntities = ((ShopRepository)getBaseRepository())
                .findByNameAndAddress(dto.getName(), dto.getAddress());
        Shop entity = getModelMapper().map(dto, Shop.class);
        super.save(entity, existingEntities.size() != 0);
    }

    public List<Shop> findByNameAndAddress(String name, String address) {
        return ((ShopRepository)getBaseRepository()).findByNameAndAddress(name, address);
    }
}
