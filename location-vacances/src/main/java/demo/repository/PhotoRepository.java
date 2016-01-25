package demo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import demo.model.Photo;

@Repository
public interface PhotoRepository extends CrudRepository<Photo,Integer>{

}

