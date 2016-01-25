package demo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import demo.model.Login;

@Repository
public interface LoginRepository extends CrudRepository<Login,Integer> {

}