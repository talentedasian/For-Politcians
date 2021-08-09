package com.example.demo.adapter.out.repository;

import com.example.demo.domain.politicians.Politicians;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

/*
    Fake object used for tests for PoliticiansRepository. All methods defined in this abstract class
    are methods that are not used at all in tests.
 */
public abstract class AbstractFakePoliticiansRepo implements PoliticiansRepository {

    @Override
    public List<Politicians> findAll(Sort sort) {
        return null;
    }

    @Override
    public List<Politicians> findAllById(Iterable<Integer> integers) {
        return null;
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends Politicians> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends Politicians> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<Politicians> entities) {}

    @Override
    public void deleteAllByIdInBatch(Iterable<Integer> integers) {

    }

    @Override
    public void deleteAllInBatch() {}

    @Override
    public Politicians getOne(Integer integer) {
        return null;
    }

    @Override
    public Politicians getById(Integer integer) {
        return null;
    }

    @Override
    public <S extends Politicians> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Politicians> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public Page<Politicians> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public void deleteAllById(Iterable<? extends Integer> integers) {

    }

    @Override
    public void deleteAll(Iterable<? extends Politicians> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public <S extends Politicians> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Politicians> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Politicians> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Politicians> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public Optional<Politicians> findById(Integer integer) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Integer integer) {
        return false;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Integer integer) {

    }

}
