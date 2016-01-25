package demo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import demo.model.Format_pays;

@Repository
public interface PaysRepository extends CrudRepository<Format_pays, Integer>{

}
