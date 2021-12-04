package com.example.demo.adapter.out.repository;

import com.example.demo.adapter.out.jpa.PoliticiansJpaEntity;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface PoliticiansJpaRepository extends JpaRepository<PoliticiansJpaEntity, String> {

    List<PoliticiansJpaEntity> findByLastNameAndFirstName(String lastName, String firstName);

    @Query(value = """
            SELECT * FROM politicians ORDER BY SUBSTRING(id from 1 for 9) ASC , CAST (SUBSTRING(id from 11) as BIGINT) ASC
            LIMIT :numberOfItemsToFetch OFFSET :itemsToSkip
            """,
            nativeQuery = true)
    List<PoliticiansJpaEntity> findAllWithPage(int itemsToSkip, int numberOfItemsToFetch);



}
