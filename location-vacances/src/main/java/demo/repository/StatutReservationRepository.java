package demo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import demo.model.Format_statutReservation;

@Repository
public interface StatutReservationRepository extends CrudRepository<Format_statutReservation, Integer>{

}
