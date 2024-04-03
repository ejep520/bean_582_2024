package edu.wsu.bean_582_2024.ApartmentFinder.views;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.listbox.ListBox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import edu.wsu.bean_582_2024.ApartmentFinder.model.Role;
import edu.wsu.bean_582_2024.ApartmentFinder.model.User;
import java.util.List;
import java.util.Objects;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicReference;

public class AdminFormTests {

    private User adminUser;
    private User ownerUser;
    private User normalUser;
    private AdminForm adminForm;
    
    private record Children(TextField username, PasswordField pass1, PasswordField pass2,
                            ListBox<Role> role, Checkbox enabled, HorizontalLayout buttonsLayout)
    { }
    
    private record Buttons(Button saveButton, Button deleteButton, Button cancelButton) { }

    @BeforeEach
    public void populateUser() {
        adminUser = new User();
        adminUser.setEnabled(true);
        adminUser.setPassword("adminPassword");
        adminUser.setRole(Role.ADMIN);
        adminUser.setUsername("myAdminUserName");

        ownerUser = new User("myOwnerUserName", "ownerPassword", Role.OWNER);
        ownerUser.setEnabled(false);

        normalUser = new User("myNormalUserName", "userPassword", Role.USER);
        normalUser.setEnabled(true);
        
        adminForm = new AdminForm();
    }
    
    @Test
    public void formPropertiesTest() {
        assertEquals("admin-form", adminForm.getClassName());
        assertEquals(6L, adminForm.getChildren().count());
        
        Children children = getChildren();
        assertEquals("username", children.username.getLabel());
        assertEquals("password", children.pass1.getLabel());
        assertEquals("confirm password", children.pass2.getLabel());
        assertEquals(3L, children.role.getGenericDataView().getItems().count());
        assertEquals("enabled", children.enabled.getLabel());
        assertEquals(3, children.buttonsLayout.getComponentCount());
        
        Buttons buttons = getButtons(children.buttonsLayout);
        assertEquals("primary", buttons.saveButton.getThemeName());
        assertEquals("tertiary", buttons.cancelButton.getThemeName());
        assertEquals("error", buttons.deleteButton.getThemeName());
    }
    
    @Test
    public void whenUserIsSavedTest() {
        AtomicReference<User> savedUserRef = new AtomicReference<>(null);
        adminForm.addSaveListener(e -> savedUserRef.set(e.getUser()));
        adminForm.setUser(adminUser);

        adminForm.save.click();

        User savedUser = savedUserRef.get();

        assertEquals(adminUser.getUsername(), savedUser.getUsername());
        assertEquals(true, savedUser.getEnabled());
        assertEquals(Role.ADMIN, savedUser.getRole());
        assertEquals(adminUser.getPassword(), savedUser.getPassword());
    }

    @Test
    public void whenUserIsValidatedAndSavedTest() {
        AtomicReference<User> savedUserRef = new AtomicReference<>(null);
        adminForm.addSaveListener(e -> savedUserRef.set(e.getUser()));
        adminForm.setUser(adminUser);
        adminForm.validateAndSave();

        User savedUser = savedUserRef.get();

        assertEquals(adminUser.getUsername(), savedUser.getUsername());
        assertEquals(true, savedUser.getEnabled());
        assertEquals(Role.ADMIN, savedUser.getRole());
        assertEquals(adminUser.getPassword(), savedUser.getPassword());
    }

    @Test
    public void whenUserIsDeletedTest() {
        AtomicReference<User> deletedUserRef = new AtomicReference<>(null);
        adminForm.addDeleteListener(e -> deletedUserRef.set(e.getUser()));
        adminForm.setUser(ownerUser);

        adminForm.delete.click();

        User deletedUser = deletedUserRef.get();

        assertEquals(ownerUser.getPassword(), deletedUser.getPassword());
        assertEquals(false, deletedUser.getEnabled());
        assertEquals(Role.OWNER, deletedUser.getRole());
        assertEquals(ownerUser.getUsername(), deletedUser.getUsername());
    }

    @Test
    public void whenUserIsCancelledTest() {
        AtomicReference<User> cancelledUserRef = new AtomicReference<>(null);
        adminForm.addCloseListener(e -> cancelledUserRef.set(e.getUser()));
        adminForm.setUser(normalUser);
        adminForm.cancel.click();

        User cancelledUser = cancelledUserRef.get();
        assertNull(cancelledUser);
    }
    
    @SuppressWarnings("unchecked")
    private Children getChildren() {
        TextField username;
        PasswordField pass1, pass2;
        ListBox<Role> role;
        Checkbox enabled;
        HorizontalLayout buttonsLayout;
        List<Component> childrenList = adminForm.getChildren().toList();
        username = getChild(TextField.class, childrenList);
        pass1 = getChild(PasswordField.class, childrenList, "password");
        pass2 = getChild(PasswordField.class, childrenList, "confirm password");
        role = (ListBox<Role>) getChild(ListBox.class, childrenList);
        enabled = getChild(Checkbox.class, childrenList);
        buttonsLayout = getChild(HorizontalLayout.class, childrenList);
        return new Children(username, pass1, pass2, role, enabled, buttonsLayout);
    }

    
    private <T extends Component> T getChild(Class<T> clazz, List<Component> children) {
        T returnValue;
        try {
            returnValue = clazz.cast(Objects.requireNonNull(children.stream()
                .filter(clazz::isInstance)
                .findFirst()
                .orElseThrow()));
        } catch (ClassCastException err) {
            System.err.print(err.getMessage());
            return null;
        }
        return returnValue;
    }
    
    private <T extends Component> T getChild(Class<T> clazz, List<Component> children, String className) {
        T returnValue;
        try {
            returnValue = clazz.cast(Objects.requireNonNull(children.stream()
                .filter(e -> clazz.isInstance(e) && className.equals(((PasswordField) e).getLabel()))
                .findFirst()
                .orElseThrow()));
        } catch (ClassCastException err) {
            System.err.print(err.getMessage());
            return null;
        }
        return returnValue;
    }
    
    private Buttons getButtons(HorizontalLayout buttonLayout) {
        List<Component> children = buttonLayout.getChildren().toList();
        Button saveButton = getButton(children, "save");
        Button deleteButton = getButton(children, "delete");
        Button cancelButton = getButton(children, "cancel");
        return new Buttons(saveButton, deleteButton, cancelButton);
    }
    
    private Button getButton(List<Component> children, String label) {
        return (Button) Objects.requireNonNull(children.stream()
            .filter(e -> label.equals(((Button) e).getText()))
            .findFirst()
            .orElseThrow());
    }
}
