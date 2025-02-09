package ru.isands.test.estore.service;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.isands.test.estore.dao.entity.ElectroItem;
import ru.isands.test.estore.dao.entity.ElectroShop;
import ru.isands.test.estore.dao.entity.ElectroType;
import ru.isands.test.estore.dao.entity.Shop;
import ru.isands.test.estore.dao.repo.ElectroItemRepository;
import ru.isands.test.estore.dao.repo.ElectroTypeRepository;
import ru.isands.test.estore.dto.ElectroItemDTO;
import ru.isands.test.estore.exception.EntityAlreadyExistsException;
import ru.isands.test.estore.exception.EntityNotExistsException;
import ru.isands.test.estore.exception.TooManyEntitiesExistsException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ElectroItemService extends BaseService<ElectroItem, Long>{
    private final ElectroTypeService electroTypeService;

    public ElectroItemService(ElectroItemRepository repository, ModelMapper modelMapper, ElectroTypeService electroTypeService) {
        super(repository, modelMapper);
        this.electroTypeService = electroTypeService;
    }

    public void save(ElectroItemDTO dto) {
        Optional<ElectroType> electroType = electroTypeService.findById(dto.getElectroType());

        if(electroType.isEmpty()){
            throw new EntityNotExistsException("Such electronic type does not exists");
        }

        List<ElectroItem> existingEntities = ((ElectroItemRepository)getBaseRepository())
                .findByNameAndElectroType(dto.getName(), electroType.get());
        ElectroItem entity = getModelMapper().map(dto, ElectroItem.class);
        entity.setElectroType(electroType.get());
        super.save(entity, existingEntities.size() != 0);
    }

    public List<ElectroItem> findByNameAndElectroType(String name, ElectroType electroType) {
        return ((ElectroItemRepository)getBaseRepository()).findByNameAndElectroType(name, electroType);
    }

    public List<ElectroItem> findByNameAndElectroTypeName(String name, String electroTypeName) {
        return ((ElectroItemRepository)getBaseRepository()).findByNameAndElectroTypeName(name, electroTypeName);
    }

    public void makePurchase(ElectroItem item, int purchaseVolume) {
        if(item.getCount() >= purchaseVolume){
            item.setCount(item.getCount() - purchaseVolume);
        }
    }

    public List<ElectroItemDTO> findAllDto(int page, int size) {
        List<ElectroItem> entities = super.findAll(page, size);
        return entities.stream().map(ElectroItemDTO::new).collect(Collectors.toList());
    }
}
