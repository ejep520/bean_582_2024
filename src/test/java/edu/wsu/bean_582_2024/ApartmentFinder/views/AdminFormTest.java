package edu.wsu.bean_582_2024.ApartmentFinder.views;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.vaadin.flow.component.listbox.ListBox;
import edu.wsu.bean_582_2024.ApartmentFinder.model.Role;
import edu.wsu.bean_582_2024.ApartmentFinder.model.User;
import edu.wsu.bean_582_2024.ApartmentFinder.views.AdminForm;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicReference;

public class AdminFormTest {

    private User adminUser;
    private User ownerUser;
    private User normalUser;

    @BeforeEach
    public void populateUser() {
        adminUser = new User();
        adminUser.setEnabled(true);
        adminUser.setPassword("adminPassword");
        adminUser.setRole(Role.ADMIN);
        adminUser.setUsername("myAdminUserName");

        ownerUser = new User("myOwnerUserName", "ownerPassword", Role.OWNER);
        ownerUser.setEnabled(false);

        normalUser = new User("myNormalUserName", "userPasword", Role.USER);
        normalUser.setEnabled(true);
    }
    @Test
    public void whenUserIsSavedTest() {
        AdminForm adminForm = new AdminForm();
        AtomicReference<User> savedUserRef = new AtomicReference<>(null);
        adminForm.addSaveListener(e -> savedUserRef.set(e.getUser()));
        adminForm.setUser(adminUser);

        adminForm.save.click();

        User savedUser = savedUserRef.get();

        Assert.assertEquals(adminUser.getUsername(), savedUser.getUsername());
        Assert.assertEquals(true, savedUser.getEnabled());
        Assert.assertEquals(Role.ADMIN, savedUser.getRole());
        Assert.assertEquals(adminUser.getPassword(), savedUser.getPassword());
    }

    @Test
    public void whenUserIsValidatedAndSavedTest() {
        AdminForm adminForm = new AdminForm();
        AtomicReference<User> savedUserRef = new AtomicReference<>(null);
        adminForm.addSaveListener(e -> savedUserRef.set(e.getUser()));
        adminForm.setUser(adminUser);
        adminForm.validateAndSave();

        User savedUser = savedUserRef.get();

        Assert.assertEquals(adminUser.getUsername(), savedUser.getUsername());
        Assert.assertEquals(true, savedUser.getEnabled());
        Assert.assertEquals(Role.ADMIN, savedUser.getRole());
        Assert.assertEquals(adminUser.getPassword(), savedUser.getPassword());
    }

    @Test
    public void whenUserIsDeletedTest() {
        AdminForm adminForm = new AdminForm();
        AtomicReference<User> deletedUserRef = new AtomicReference<>(null);
        adminForm.addDeleteListener(e -> deletedUserRef.set(e.getUser()));
        adminForm.setUser(ownerUser);

        adminForm.delete.click();

        User deletedUser = deletedUserRef.get();

        Assert.assertEquals(ownerUser.getPassword(), deletedUser.getPassword());
        Assert.assertEquals(false, deletedUser.getEnabled());
        Assert.assertEquals(Role.OWNER, deletedUser.getRole());
        Assert.assertEquals(ownerUser.getUsername(), deletedUser.getUsername());
    }

    @Test
    public void whenUserIsCancelledTest() {
        AdminForm adminForm = new AdminForm();
        AtomicReference<User> cancelledUserRef = new AtomicReference<>(null);
        adminForm.addCloseListener(e -> cancelledUserRef.set(e.getUser()));
        adminForm.setUser(normalUser);
        adminForm.cancel.click();

        User cancelledUser = cancelledUserRef.get();
        Assert.assertNull(cancelledUser);
    }
}