package pers.cc.spring.data.jpa.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.repository.NoRepositoryBean;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author chengce
 * @version 2018-05-17 00:25
 */
@NoRepositoryBean
public class BaseRepository {

  @PersistenceContext
  protected EntityManager entityManager;

  protected JPAQueryFactory getJPAQueryFactory() {
    return new JPAQueryFactory(entityManager);
  }
}
