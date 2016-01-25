package Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import Model.Login;

@Repository
public interface Repository_Login extends CrudRepository<Login,Integer> {

}
