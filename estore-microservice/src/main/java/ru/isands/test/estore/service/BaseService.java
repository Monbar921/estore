package ru.isands.test.estore.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import ru.isands.test.estore.dao.repo.BaseRepository;
import ru.isands.test.estore.exception.DatabaseException;
import ru.isands.test.estore.exception.EntityAlreadyExistsException;

import java.util.List;
import java.util.Optional;

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

    public Optional<T> findById(K id) {
        return baseRepository.findById(id);
    }

    public List<T> findAll(Pageable pageable) {
        return baseRepository.findAll(pageable).getContent();
    }

    public List<T> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return baseRepository.findAll(pageable).getContent();
    }

    public void deleteById(K id) {
        try {
            baseRepository.deleteById(id);
        }catch (Exception e){
            throw new DatabaseException(e.getMessage());
        }
    }
}
