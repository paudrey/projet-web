package demo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import demo.model.Reservation;

@Repository
public interface ReservationRepository extends CrudRepository<Reservation, Integer>{

}

