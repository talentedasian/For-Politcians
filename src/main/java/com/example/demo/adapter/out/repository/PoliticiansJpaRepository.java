package com.example.demo.adapter.out.repository;

import com.example.demo.adapter.out.jpa.PoliticiansJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PoliticiansJpaRepository extends JpaRepository<PoliticiansJpaEntity, String> {

    List<PoliticiansJpaEntity> findByLastNameAndFirstName(String lastName, String firstName);

    @Query(value = "SELECT * FROM politicians LIMIT :numberOfItemsToFetch OFFSET :itemsToSkip", nativeQuery = true)
    List<PoliticiansJpaEntity> findAllWithPage(int itemsToSkip, int numberOfItemsToFetch);

}
