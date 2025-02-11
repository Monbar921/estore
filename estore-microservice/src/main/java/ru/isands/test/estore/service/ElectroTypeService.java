package ru.isands.test.estore.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.isands.test.estore.dao.entity.ElectroType;
import ru.isands.test.estore.dao.repo.ElectroTypeRepository;
import ru.isands.test.estore.dto.ElectroTypeDTO;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ElectroTypeService extends BaseService<ElectroType, Long>{
    public ElectroTypeService(ElectroTypeRepository repository, ModelMapper modelMapper) {
        super(repository, modelMapper);
    }

    public void save(ElectroTypeDTO dto) {
        List<ElectroType> existingEntities = ((ElectroTypeRepository)getBaseRepository())
                .findByName(dto.getName());
        ElectroType entity = getModelMapper().map(dto, ElectroType.class);
        super.save(entity, existingEntities.size() != 0);
    }

    public List<ElectroType> findByName(String name) {
        return ((ElectroTypeRepository)getBaseRepository()).findByName(name);
    }

    public List<ElectroTypeDTO> findAllDto(int page, int size) {
        List<ElectroType> entities = super.findAll(page, size);
        return entities.stream().map(ElectroTypeDTO::new).collect(Collectors.toList());
    }
}
