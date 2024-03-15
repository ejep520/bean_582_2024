package edu.wsu.bean_582_2024.ApartmentFinder.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.listbox.ListBox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import edu.wsu.bean_582_2024.ApartmentFinder.model.Role;
import edu.wsu.bean_582_2024.ApartmentFinder.model.User;
import jakarta.annotation.security.RolesAllowed;

@RolesAllowed("ADMIN")
public class AdminForm extends FormLayout {
  private final Binder<User> binder;
  final TextField username = new TextField("username");
  final PasswordField pass1 = new PasswordField("password");
  final PasswordField pass2 = new PasswordField("confirm password");
  final ListBox<Role> role = new ListBox<>();
  final Checkbox enabled = new Checkbox("enabled");
  Button save = new Button("save");
  Button delete = new Button("delete");
  Button cancel = new Button("cancel");

  public AdminForm() {
    addClassName("admin-form");
    binder = new BeanValidationBinder<>(User.class);
    binder.bind(enabled, User::getEnabled, User::setEnabled);
    binder.bindInstanceFields(this);
    role.setItems(Role.values());
    enabled.setReadOnly(false);
    add(username, pass1, pass2, role, enabled, createButtonLayout());
  }

  private Component createButtonLayout() {
    save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
    cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
    delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
    save.addClickListener(event -> validateAndSave());
    delete.addClickListener(event -> fireEvent(new DeleteEvent(this, binder.getBean())));
    cancel.addClickListener(event -> fireEvent(new CloseEvent(this)));
    save.addClickShortcut(Key.ENTER);
    cancel.addClickShortcut(Key.ESCAPE);
    return new HorizontalLayout(save, delete, cancel);
  }

  public void setUser(User user) {
    binder.setBean(user);
    binder.readBean(user);
  }

  public void validateAndSave() {
    if (binder.isValid())
      fireEvent(new SaveEvent(this, binder.getBean()));
  }

  public static abstract class AdminFormEvent extends ComponentEvent<AdminForm> {

    private final User user;

    public AdminFormEvent(AdminForm source, User user) {
      super(source, false);
      this.user = user;
    }

    public User getUser() {
      return user;
    }
  }
  public static class SaveEvent extends AdminFormEvent {
    public SaveEvent(AdminForm source, User user) {
      super(source, user);
      if (user.getId() == null || user.getId() <= 0)
        user.setPassword(source.pass1.getValue());
    }
  }

  public static class DeleteEvent extends AdminFormEvent {
    public DeleteEvent(AdminForm source, User user) {
      super(source, user);
    }
  }

  public static class CloseEvent extends AdminFormEvent {
    public CloseEvent(AdminForm source) {
      super(source, null);
    }
  }

  public void addSaveListener(ComponentEventListener<SaveEvent> listener) {
    addListener(SaveEvent.class, listener);
  }

  public void addDeleteListener(ComponentEventListener<DeleteEvent> listener) {
    addListener(DeleteEvent.class, listener);
  }

  public void addCloseListener(ComponentEventListener<CloseEvent> listener) {
    addListener(CloseEvent.class, listener);
  }
}
