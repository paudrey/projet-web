package Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import Model.Format_statutReservation;


@Repository
public interface Repository_StatutReservation extends CrudRepository<Format_statutReservation, Integer>{

}
