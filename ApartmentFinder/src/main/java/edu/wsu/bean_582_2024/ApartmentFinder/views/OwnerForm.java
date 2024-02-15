package edu.wsu.bean_582_2024.ApartmentFinder.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import edu.wsu.bean_582_2024.ApartmentFinder.model.Unit;

@SuppressWarnings("serial")
public class OwnerForm extends FormLayout {
  private final Binder<Unit> binder = new Binder<>(Unit.class);
  private final TextField address = new TextField("Address");
  private final TextField kitchen = new TextField("Kitchen Notes");
  private final TextField livingRoom = new TextField("Living Room Notes");
  private final IntegerField bedrooms = new IntegerField("Bedroom Count");
  private final NumberField bathrooms = new NumberField("Bathroom Count");
  private final Checkbox featured = new Checkbox("featured");

  public OwnerForm() {
    addClassName("owner-form");
    binder.bindInstanceFields(this);
    configureForm();
    add(address, bedrooms, bathrooms, kitchen, livingRoom, featured, createButtonLayout());
  }

  private void configureForm() {
    address.setRequired(true);
    kitchen.setRequired(false);
    livingRoom.setRequired(false);
    bedrooms.setRequired(true);
    bathrooms.setRequired(true);
    bedrooms.setMin(0);
    bedrooms.setMax(20);
    bedrooms.setStep(1);
    bathrooms.setMin(0f);
    bathrooms.setMax(10.5d);
    bathrooms.setStep(0.5d);
  }

  private Component createButtonLayout() {
    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button cancel = new Button("Cancel");
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

  private void validateAndSave() {
    if (binder.isValid())
      fireEvent(new SaveEvent(this, binder.getBean()));
  }

  public void setUnit(Unit unit) {
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
    private static final long serialVersionUID = 6288447328604003658L;

    public SaveEvent(OwnerForm source, Unit unit) {
      super(source, unit);
    }
  }

  public static class DeleteEvent extends OwnerForm.OwnerFormEvent {
    private static final long serialVersionUID = -3753247172007065836L;

    public DeleteEvent(OwnerForm source, Unit unit) {
      super(source, unit);
    }
  }

  public static class CloseEvent extends OwnerForm.OwnerFormEvent {
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
