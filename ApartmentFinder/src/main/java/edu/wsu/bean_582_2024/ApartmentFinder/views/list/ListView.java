package edu.wsu.bean_582_2024.ApartmentFinder.views.list;

import java.util.Collections;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import edu.wsu.bean_582_2024.ApartmentFinder.data.Contact;
import edu.wsu.bean_582_2024.ApartmentFinder.data.CrmService;
import jakarta.annotation.security.PermitAll;

@PageTitle("Contacts | Vaadin CRM")
@Route(value = "", layout = MainLayout.class)
@PermitAll
public class ListView extends VerticalLayout {
	  Grid<Contact> grid = new Grid<>(Contact.class);
    TextField filterText = new TextField();
    ContactForm form;
    CrmService service;
    

    public ListView() {//(CrmService service) will populate the form
    	this.service = service;
    	addClassName("List-view");
    	setSizeFull();
    	
    	configureGrid();
    	configureForm();
    	
    	add(getToolbar(),getContent());    	
    	//updateList(); //populates form
    	closeEditor();
    	
    	
    	 
    }
    private void closeEditor() {
        form.setContact(null);
        form.setVisible(false);
        removeClassName("editing");
    }
    
	// goes to database and fetch all contacts that match filter. 
    private void updateList() {
		grid.setItems(service.findAllContacts(filterText.getValue()));
		
	}

	private Component getContent() {
		HorizontalLayout content = new HorizontalLayout(grid, form);
		content.setFlexGrow(2, grid);
		content.setFlexGrow(1, form);
		content.addClassNames("content");
		content.setSizeFull();
		
		return content;
	}

	private void configureForm() {
		form = new ContactForm(Collections.emptyList(), Collections.emptyList()) ;
		//(service.findAllCompanies(), service.findAllStatuses()); (Collections.emptyList(), Collections.emptyList()) 
		form.setWidth("25em");
		form.addSaveListener(this::saveContact); // <1>
	    form.addDeleteListener(this::deleteContact); // <2>
	    form.addCloseListener(e -> closeEditor()); // <3>
		
	}
	
	 private void saveContact(ContactForm.SaveEvent event) {
	        service.saveContact(event.getContact());
	        updateList();
	        closeEditor();
	        updateList();
	        closeEditor();
	    }
	
	private void deleteContact(ContactForm.DeleteEvent event) {
        service.deleteContact(event.getContact());
        updateList();
        closeEditor();
    }

	private Component getToolbar() {
		filterText.setPlaceholder("Filter by name...");
		filterText.setClearButtonVisible(true);
		filterText.setValueChangeMode(ValueChangeMode.LAZY); // search wont advance until the user stops typing 
		filterText.addValueChangeListener(e -> updateList());// filters as text is typed
		Button addContactButton = new Button("Add contact");
		addContactButton.addClickListener(click -> addContact());
		
		HorizontalLayout toolbar = new HorizontalLayout(filterText, addContactButton);
		toolbar.addClassName("toolbar");
		return toolbar;
	}

    private void addContact() {
        grid.asSingleSelect().clear();
        editContact(new Contact());
    }
    
	private void configureGrid() {
    	grid.addClassName("contact-grid");
    	grid.setSizeFull();
    	grid.setColumns("firstName", "lastName", "email");
    	grid.addColumn(contact -> contact.getStatus().getName()).setHeader("Status");
    	grid.addColumn(contact -> contact.getCompany().getName()).setHeader("Company");
    	grid.getColumns().forEach(col -> col.setAutoWidth(true));
    	
    	grid.asSingleSelect().addValueChangeListener(event -> editContact(event.getValue()));
    	
    }
	
    public void editContact(Contact contact) {
        if (contact == null) {
            closeEditor();
        } else {
            form.setContact(contact);
            form.setVisible(true);
            addClassName("editing");
        }
    }

}
