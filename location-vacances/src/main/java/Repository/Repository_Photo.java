package Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import Model.Photo;

@Repository
public interface Repository_Photo extends CrudRepository<Photo,Integer>{

}
