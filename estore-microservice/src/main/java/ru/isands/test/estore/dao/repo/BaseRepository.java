package ru.isands.test.estore.dao.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import ru.isands.test.estore.dao.entity.Shop;

@NoRepositoryBean
public interface BaseRepository<T, K extends Number> extends JpaRepository<T, K> {
}