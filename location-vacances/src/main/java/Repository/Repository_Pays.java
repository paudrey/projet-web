package Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import Model.Format_pays;


@Repository
public interface Repository_Pays extends CrudRepository<Format_pays, Integer>{

}
