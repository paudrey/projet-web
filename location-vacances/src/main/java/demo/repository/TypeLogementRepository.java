package demo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import demo.model.Format_typeLogement;

@Repository
public interface TypeLogementRepository extends CrudRepository<Format_typeLogement, Integer> {

}
