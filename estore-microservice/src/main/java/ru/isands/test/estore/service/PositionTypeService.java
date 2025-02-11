package ru.isands.test.estore.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.isands.test.estore.dao.entity.PositionType;
import ru.isands.test.estore.dao.repo.PositionTypeRepository;
import ru.isands.test.estore.dto.PositionTypeDTO;

import java.util.List;
import java.util.stream.Collectors;

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

    public List<PositionTypeDTO> findAllDto(int page, int size) {
        List<PositionType> entities = super.findAll(page, size);
        return entities.stream().map(PositionTypeDTO::new).collect(Collectors.toList());
    }
}
