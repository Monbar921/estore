package ru.isands.test.estore.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.isands.test.estore.dao.entity.PurchaseType;
import ru.isands.test.estore.dao.entity.Shop;
import ru.isands.test.estore.dao.repo.PurchaseTypeRepository;
import ru.isands.test.estore.dao.repo.ShopRepository;
import ru.isands.test.estore.dto.PurchaseTypeDTO;

import java.util.List;

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
}
