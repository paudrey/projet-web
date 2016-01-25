package demo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import demo.model.Utilisateur;

@Repository
public interface UtilisateurRepository extends CrudRepository<Utilisateur,Integer> {

}
