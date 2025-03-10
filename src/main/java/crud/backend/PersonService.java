package crud.backend;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {

    private final PersonRepository repo;

    public PersonService(PersonRepository repo) {
        this.repo = repo;
    }

    public List<Person> findPersons(String nameFilter, Pageable pageable) {
        nameFilter = nameFilter == null ? "" : "%" + nameFilter + "%";
        return repo.findByNameLikeIgnoreCase(nameFilter, pageable);
    }

}
