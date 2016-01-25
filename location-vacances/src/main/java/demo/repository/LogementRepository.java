package demo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import demo.model.Logement;

@Repository
public interface LogementRepository extends CrudRepository<Logement,Integer>{

}
