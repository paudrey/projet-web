package Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import Model.Format_typeLogement;

@Repository
public interface Repository_TypeLogement extends CrudRepository<Format_typeLogement, Integer> {

}
