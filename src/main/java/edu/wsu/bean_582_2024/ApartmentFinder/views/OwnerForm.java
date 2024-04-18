package edu.wsu.bean_582_2024.ApartmentFinder.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import edu.wsu.bean_582_2024.ApartmentFinder.model.Role;
import edu.wsu.bean_582_2024.ApartmentFinder.model.Unit;
import edu.wsu.bean_582_2024.ApartmentFinder.model.User;
import edu.wsu.bean_582_2024.ApartmentFinder.service.UserService;
import java.io.Serial;

@SuppressWarnings("serial")
public class OwnerForm extends FormLayout {

  final Binder<Unit> binder = new Binder<>(Unit.class);
  final TextField address = new TextField("Address");
  final TextField kitchen = new TextField("Kitchen Notes");
  final TextField livingRoom = new TextField("Living Room Notes");
  final IntegerField bedrooms = new IntegerField("Bedroom Count");
  final NumberField bathrooms = new NumberField("Bathroom Count");
  final Checkbox featured = new Checkbox("featured");
  final ComboBox<User> user = new ComboBox<>("Owner");
  private final UserService userService;
  private final User this_user;
  private final boolean is_admin;

  Button save = new Button("Save");
  Button delete = new Button("Delete");
  Button cancel = new Button("Cancel");

  public OwnerForm(User user, UserService userService) {
    addClassName("owner-form");
    this.userService = userService;
    this_user = user;
    this.is_admin = Role.ADMIN.equals(this_user.getRole());
    binder.bindInstanceFields(this);
    configureForm();
    add(address, bedrooms, bathrooms, kitchen, livingRoom, featured, this.user, createButtonLayout());
  }

  private void configureForm() {
    address.setRequired(true);
    kitchen.setRequired(false);
    livingRoom.setRequired(false);
    bedrooms.setRequired(true);
    bathrooms.setRequired(true);
    user.setRequired(true);
    bedrooms.setMin(0);
    bedrooms.setMax(20);
    bedrooms.setStep(1);
    bathrooms.setMin(0d);
    bathrooms.setMax(10.5d);
    bathrooms.setStep(0.5d);
    address.getElement().setAttribute("data-testid", "address");
    kitchen.getElement().setAttribute("data-testid", "kitchen");
    livingRoom.getElement().setAttribute("data-testid", "livingRoom");
    bedrooms.getElement().setAttribute("data-testid", "bedrooms");
    bedrooms.getElement().setAttribute("data-testid", "bedrooms");
    featured.getElement().setAttribute("data-testid", "featured");
    user.getElement().setAttribute("data-testid", "owner");
    user.setItems(userService.getAllUsers());
    user.setReadOnly(!is_admin);
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
    save.getElement().setAttribute("data-testid", "save");
    cancel.getElement().setAttribute("data-testid", "cancel");
    delete.getElement().setAttribute("data-testid", "delete");
    return new HorizontalLayout(save, delete, cancel);
  }

  private void validateAndSave() {
    if (binder.isValid())
      fireEvent(new SaveEvent(this, binder.getBean()));
  }

  public void setUnit(Unit unit) {
    assert unit == null // There is no unit. 
        || unit.getUser().equals(this_user) // The user is the unit owner
        || is_admin; // The user is an admin.
    binder.setBean(unit);
    binder.readBean(unit);
  }

  @SuppressWarnings("serial")
  public static abstract class OwnerFormEvent extends ComponentEvent<OwnerForm> {

    private final Unit unit;

    public OwnerFormEvent(OwnerForm source, Unit unit) {
      super(source, false);
      this.unit = unit;
    }

    public Unit getUnit() {
      return unit;
    }
  }

  public static class SaveEvent extends OwnerForm.OwnerFormEvent {

    @Serial
    private static final long serialVersionUID = 6288447328604003658L;

    public SaveEvent(OwnerForm source, Unit unit) {
      super(source, unit);
    }
  }

  public static class DeleteEvent extends OwnerForm.OwnerFormEvent {

    @Serial
    private static final long serialVersionUID = -3753247172007065836L;

    public DeleteEvent(OwnerForm source, Unit unit) {
      super(source, unit);
    }
  }

  public static class CloseEvent extends OwnerForm.OwnerFormEvent {

    @Serial
    private static final long serialVersionUID = 4600479831012813485L;

    public CloseEvent(OwnerForm source) {
      super(source, null);
    }
  }

  public void addSaveListener(ComponentEventListener<OwnerForm.SaveEvent> listener) {
    addListener(OwnerForm.SaveEvent.class, listener);
  }

  public void addDeleteListener(ComponentEventListener<OwnerForm.DeleteEvent> listener) {
    addListener(OwnerForm.DeleteEvent.class, listener);
  }

  public void addCloseListener(ComponentEventListener<OwnerForm.CloseEvent> listener) {
    addListener(OwnerForm.CloseEvent.class, listener);
  }

}
