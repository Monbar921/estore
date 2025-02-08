package ru.isands.test.estore.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.isands.test.estore.dao.entity.Shop;
import ru.isands.test.estore.dao.repo.BaseRepository;
import ru.isands.test.estore.dao.repo.ShopRepository;
import ru.isands.test.estore.exception.EntityAlreadyExistsException;

@AllArgsConstructor
@Data
public abstract class BaseService<T, K extends Number> {
    private final BaseRepository<T, K> baseRepository;
    private final ModelMapper modelMapper;
    protected T save(T entity, boolean isEntityExist) {
        if(isEntityExist){
            throw new EntityAlreadyExistsException("Entity already exist");
        }
        return baseRepository.save(entity);
    }
}
