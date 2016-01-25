package Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import Model.Logement;

@Repository
public interface Repository_Logement extends CrudRepository<Logement,Integer>{

}
