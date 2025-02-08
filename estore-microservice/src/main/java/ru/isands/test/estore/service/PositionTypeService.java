package ru.isands.test.estore.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.isands.test.estore.dao.entity.ElectroType;
import ru.isands.test.estore.dao.entity.PositionType;
import ru.isands.test.estore.dao.entity.PurchaseType;
import ru.isands.test.estore.dao.repo.ElectroTypeRepository;
import ru.isands.test.estore.dao.repo.PositionTypeRepository;
import ru.isands.test.estore.dao.repo.PurchaseTypeRepository;
import ru.isands.test.estore.dto.PositionTypeDTO;
import ru.isands.test.estore.dto.PurchaseTypeDTO;

import java.util.List;

@Service
public class PositionTypeService extends BaseService<PositionType, Long>{
    public PositionTypeService(PositionTypeRepository repository, ModelMapper modelMapper) {
        super(repository, modelMapper);
    }
    public void save(PositionTypeDTO dto) {
        List<PositionType> existingEntities = ((PositionTypeRepository)getBaseRepository())
                .findByName(dto.getName());
        PositionType entity = getModelMapper().map(dto, PositionType.class);
        super.save(entity, existingEntities.size() != 0);
    }

    public List<PositionType> findByName(String name) {
        return ((PositionTypeRepository)getBaseRepository()).findByName(name);
    }
}
