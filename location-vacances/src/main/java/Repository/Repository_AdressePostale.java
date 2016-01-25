package Repository;

import Model.AdressePostale;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Repository_AdressePostale extends CrudRepository<AdressePostale, Integer>{

}
