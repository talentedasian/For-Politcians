package com.example.demo.repository;

import com.example.demo.model.entities.PoliticiansRating;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

public abstract class AbstractFakeRatingRepository implements RatingRepository {

    @Override
    public List<PoliticiansRating> findAll(Sort sort) {
        return null;
    }

    @Override
    public List<PoliticiansRating> findAllById(Iterable<Integer> integers) {
        return null;
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends PoliticiansRating> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends PoliticiansRating> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<PoliticiansRating> entities) {}

    @Override
    public void deleteAllByIdInBatch(Iterable<Integer> integers) {

    }

    @Override
    public void deleteAllInBatch() {}

    @Override
    public PoliticiansRating getOne(Integer integer) {
        return null;
    }

    @Override
    public PoliticiansRating getById(Integer integer) {
        return null;
    }

    @Override
    public <S extends PoliticiansRating> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends PoliticiansRating> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public Page<PoliticiansRating> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public void deleteAllById(Iterable<? extends Integer> integers) {

    }

    @Override
    public void deleteAll(Iterable<? extends PoliticiansRating> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public <S extends PoliticiansRating> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends PoliticiansRating> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends PoliticiansRating> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends PoliticiansRating> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public Optional<PoliticiansRating> findById(Integer integer) {
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
