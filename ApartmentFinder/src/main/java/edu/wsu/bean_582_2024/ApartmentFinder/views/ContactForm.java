package edu.wsu.bean_582_2024.ApartmentFinder.views;

import java.util.List;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.shared.Registration;
import edu.wsu.bean_582_2024.ApartmentFinder.model.Company;
import edu.wsu.bean_582_2024.ApartmentFinder.model.Contact;
import edu.wsu.bean_582_2024.ApartmentFinder.model.Status;

@SuppressWarnings("serial")
public class ContactForm extends FormLayout {
  Binder<Contact> binder = new BeanValidationBinder<>(Contact.class);// binds model object and UI
                                                                     // components

  TextField firstName = new TextField("First name");
  TextField lastName = new TextField("Last name");
  TextField email = new TextField("Email");
  ComboBox<Status> status = new ComboBox<>("Status");
  ComboBox<Company> company = new ComboBox<>("Company");

  Button save = new Button("Save");
  Button delete = new Button("Delete");
  Button cancel = new Button("Cancel");

  public ContactForm(List<Company> companies, List<Status> statuses) {
    addClassName("contact-form");
    binder.bindInstanceFields(this);

    company.setItems(companies);
    company.setItemLabelGenerator(Company::getName);

    status.setItems(statuses);
    status.setItemLabelGenerator(Status::getName);

    add(firstName, lastName, email, company, status, createButtonLayout());
  }

  public void setContact(Contact contact) {
    binder.setBean(contact);
    binder.readBean(contact);
  }

  private Component createButtonLayout() {
    save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
    delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
    cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

    save.addClickListener(event -> validateAndSave());
    delete.addClickListener(event -> fireEvent(new DeleteEvent(this, binder.getBean())));
    cancel.addClickListener(event -> fireEvent(new CloseEvent(this)));

    save.addClickShortcut(Key.ENTER);
    cancel.addClickShortcut(Key.ESCAPE);

    return new HorizontalLayout(save, delete, cancel);
  }


  private void validateAndSave() {
    if (binder.isValid()) {
      fireEvent(new SaveEvent(this, binder.getBean()));
    }
  }


  // Events
  public static abstract class ContactFormEvent extends ComponentEvent<ContactForm> {
    private Contact contact;

    protected ContactFormEvent(ContactForm source, Contact contact) {
      super(source, false);
      this.contact = contact;
    }

    public Contact getContact() {
      return contact;
    }
  }

  public static class SaveEvent extends ContactFormEvent {
    SaveEvent(ContactForm source, Contact contact) {
      super(source, contact);
    }
  }

  public static class DeleteEvent extends ContactFormEvent {
    DeleteEvent(ContactForm source, Contact contact) {
      super(source, contact);
    }

  }

  public static class CloseEvent extends ContactFormEvent {
    CloseEvent(ContactForm source) {
      super(source, null);
    }
  }

  public Registration addDeleteListener(ComponentEventListener<DeleteEvent> listener) {
    return addListener(DeleteEvent.class, listener);
  }

  public Registration addSaveListener(ComponentEventListener<SaveEvent> listener) {
    return addListener(SaveEvent.class, listener);
  }

  public Registration addCloseListener(ComponentEventListener<CloseEvent> listener) {
    return addListener(CloseEvent.class, listener);
  }
}
