package Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import Model.Contact;


@Repository
public interface Repository_Contact extends CrudRepository<Contact, Integer>{

	
}
