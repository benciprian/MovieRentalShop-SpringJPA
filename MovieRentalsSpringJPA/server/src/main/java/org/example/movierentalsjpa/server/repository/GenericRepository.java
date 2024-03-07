package org.example.movierentalsjpa.server.repository;

import org.example.movierentalsjpa.common.model.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import java.io.Serializable;

@NoRepositoryBean
public interface GenericRepository<T extends BaseEntity<ID>, ID extends Serializable> extends JpaRepository<T, ID>,
        QueryByExampleExecutor<T>, PagingAndSortingRepository<T, ID> {
}
