package Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import Model.Utilisateur;

@Repository
public interface Repository_Utilisateur extends CrudRepository<Utilisateur,Integer> {

}
