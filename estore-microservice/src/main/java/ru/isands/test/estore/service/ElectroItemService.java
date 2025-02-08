package ru.isands.test.estore.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.isands.test.estore.dao.entity.ElectroItem;
import ru.isands.test.estore.dao.entity.ElectroType;
import ru.isands.test.estore.dao.repo.ElectroItemRepository;
import ru.isands.test.estore.dao.repo.ElectroTypeRepository;
import ru.isands.test.estore.dto.ElectroItemDTO;
import ru.isands.test.estore.exception.EntityAlreadyExistsException;
import ru.isands.test.estore.exception.EntityNotExistsException;
import ru.isands.test.estore.exception.TooManyEntitiesExistsException;

import java.util.List;

@Service
public class ElectroItemService extends BaseService<ElectroItem, Long>{
    private final ElectroTypeService electroTypeService;

    public ElectroItemService(ElectroItemRepository repository, ModelMapper modelMapper, ElectroTypeService electroTypeService) {
        super(repository, modelMapper);
        this.electroTypeService = electroTypeService;
    }

    public void save(ElectroItemDTO dto) {
        List<ElectroType> electroType = electroTypeService.findByName(dto.getElectroType().getName());

        if(electroType.size() == 0){
            throw new EntityNotExistsException("Such electronic type does not exists");
        }else if(electroType.size() > 1){
            throw new TooManyEntitiesExistsException("More than one electronic type with such name");
        }

        List<ElectroItem> existingEntities = ((ElectroItemRepository)getBaseRepository())
                .findByNameAndElectroType(dto.getName(), electroType.get(0));
        ElectroItem entity = getModelMapper().map(dto, ElectroItem.class);
        entity.setElectroType(electroType.get(0));
        super.save(entity, existingEntities.size() != 0);
    }

    public List<ElectroItem> findByNameAndElectroType(String name, ElectroType electroType) {
        return ((ElectroItemRepository)getBaseRepository()).findByNameAndElectroType(name, electroType);
    }

    public List<ElectroItem> findByNameAndElectroTypeName(String name, String electroTypeName) {
        return ((ElectroItemRepository)getBaseRepository()).findByNameAndElectroTypeName(name, electroTypeName);
    }
}
