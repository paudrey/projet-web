package demo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import demo.model.Photo;
import demo.model.Recherche;

@Repository
public interface RechercheRepository extends CrudRepository<Recherche,Integer>{
}
