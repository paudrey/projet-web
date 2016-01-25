package demo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import demo.model.AdressePostale;

@Repository
public interface AdressePostaleRepository extends CrudRepository<AdressePostale, Integer>{

}

