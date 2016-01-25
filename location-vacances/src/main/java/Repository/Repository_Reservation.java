package Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import Model.Reservation;

@Repository
public interface Repository_Reservation extends CrudRepository<Reservation, Integer>{

}
