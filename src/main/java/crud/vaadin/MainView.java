package crud.vaadin;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;
import crud.backend.PersonRepository;
import crud.backend.PersonService;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import crud.backend.Person;

@Route
public class MainView extends VerticalLayout {

	private static final long serialVersionUID = 1L;
	private final PersonGrid grid;

	public MainView(PersonService service, PersonRepository repo) {
		add("Person Grid pulling from Spring Data powered backend");
		add(new NameFilter());
		add(grid = new PersonGrid(service));
	}

	class PersonGrid extends Grid<Person> {
		private PersonService service;

		PersonGrid(PersonService service) {
			super(Person.class);
			this.service = service;
			filterByName("");
		}

		void filterByName(String filter) {
			setItems(vaadinQuery -> {
				var springPageRequest = VaadinSpringDataHelpers.toSpringPageRequest(vaadinQuery);
				return service.findPersons(filter, springPageRequest).stream();
			});
		}
	}


	class NameFilter extends TextField {
		{
			setPlaceholder("Filter by name...");
			setClearButtonVisible(true);
			setValueChangeMode(ValueChangeMode.LAZY);
			addValueChangeListener(e -> grid.filterByName(e.getValue()));
		}
	}
}
