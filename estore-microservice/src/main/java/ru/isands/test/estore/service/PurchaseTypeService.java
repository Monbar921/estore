package ru.isands.test.estore.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.isands.test.estore.dao.entity.PurchaseType;
import ru.isands.test.estore.dao.repo.PurchaseTypeRepository;
import ru.isands.test.estore.dto.PurchaseTypeDTO;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PurchaseTypeService extends BaseService<PurchaseType, Long>{
    public PurchaseTypeService(PurchaseTypeRepository repository, ModelMapper modelMapper) {
        super(repository, modelMapper);
    }
    public void save(PurchaseTypeDTO dto) {
        List<PurchaseType> existingEntities = ((PurchaseTypeRepository)getBaseRepository())
                .findByName(dto.getName());
        PurchaseType entity = getModelMapper().map(dto, PurchaseType.class);
        super.save(entity, existingEntities.size() != 0);
    }

    public List<PurchaseType> findByName(String name) {
        return ((PurchaseTypeRepository)getBaseRepository()).findByName(name);
    }

    public List<PurchaseTypeDTO> findAllDto(int page, int size) {
        List<PurchaseType> entities = super.findAll(page, size);
        return entities.stream().map(PurchaseTypeDTO::new).collect(Collectors.toList());
    }
}
