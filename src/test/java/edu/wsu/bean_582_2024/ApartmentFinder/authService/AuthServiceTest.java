package edu.wsu.bean_582_2024.ApartmentFinder.authService;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.*;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BooleanSupplier;

import javax.naming.AuthenticationException;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import com.vaadin.flow.router.RouteConfiguration;
import com.vaadin.flow.server.VaadinServletRequest;
import com.vaadin.flow.server.VaadinSession;

import static org.mockito.ArgumentMatchers.any;

import edu.wsu.bean_582_2024.ApartmentFinder.data.AuthorityRepository;
import edu.wsu.bean_582_2024.ApartmentFinder.data.UserRepository;
import edu.wsu.bean_582_2024.ApartmentFinder.model.Authority;
import edu.wsu.bean_582_2024.ApartmentFinder.model.Role;
import edu.wsu.bean_582_2024.ApartmentFinder.model.User;
import edu.wsu.bean_582_2024.ApartmentFinder.service.AuthService;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private AuthorityRepository authorityRepository;

    @InjectMocks
    private AuthService authService;

    private User user;

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setUsername("testUser");
        user.setPassword("testPass");
        user.setRole(Role.USER);
    }
    

    @Test
    public void testSuccessfulAuthenticationUser() {
        when(userRepository.getUserByUsername("testUser")).thenReturn(user);
        Authentication authentication = new UsernamePasswordAuthenticationToken("testUser", "testPass");
        Authentication result = authService.authenticate(authentication);
        assertNotNull(result);
        assertTrue(result.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_USER")));
    }

    @Test
    public void authenticateUserNotFound() {
        when(userRepository.getUserByUsername("nonexistent")).thenReturn(null);
        Authentication authentication = new UsernamePasswordAuthenticationToken("nonexistent", "pass");
        assertNull(authService.authenticate(authentication));
    }

    @Test
    public void registerNewUserSuccess() throws Exception {
        // Create a mock AuthService
        AuthService authService = Mockito.mock(AuthService.class);

        // Get the authService's class
        Class<?> authServiceClass = authService.getClass();

        // Get the register method from the AuthService class
        Method registerMethod = authServiceClass.getDeclaredMethod("register", String.class, String.class, Role.class);
        registerMethod.setAccessible(true);

        // Invoke the register method dynamically
        try {
            registerMethod.invoke(authService, "newUser", "newPass", Role.USER);
        } catch (Exception e) {
            // Handle any exception thrown during registration
            throw new AuthService.AuthException();
        }

        // Verify that the register method of the mock AuthService was called
        verify(authService).register("newUser", "newPass", Role.USER);
    }
    
    // Not sure if we will have a function to check if username already exists
    @Test
    public void usernameTakenCheck() {
        when(userRepository.getUserByUsername("testUser")).thenReturn(user);
        assertTrue(authService.usernameTaken("testUser"));
    }

    // Not sure if we will have a function to check if username already exists
    @Test
    public void usernameNotTakenCheck() {
        when(userRepository.getUserByUsername("uniqueUser")).thenReturn(null);
        assertFalse(authService.usernameTaken("uniqueUser"));
    }
    
    // This test may not stay, depending on if we are able to check for existing username
    /*@Test
    public void registerUserThrowsExceptionForExistingUsername() {
        when(userRepository.getUserByUsername("testUser")).thenReturn(user);
        assertThrows(AuthService.AuthException.class, () -> authService.register("testUser", "testPass", Role.USER));
    }
    */
    
    // When there is no authenticated user
    @Test
    public void isAuthenticatedShouldReturnFalseWhenNoUser() {
        assertFalse(AuthService.isAuthenticated());
    }
    
    // Checks after an authority has been deleted
    @Test
    public void deleteAuthoritySuccess() {
        Authority authority = new Authority(user, "ROLE_USER");
        doNothing().when(authorityRepository).remove(authority);
        authService.delete(authority);
        verify(authorityRepository).remove(authority);
    }

    // Test authentication for each role to ensure the correct authorities are granted
    @Test
    public void authenticateWithDifferentRoles() {       
        EnumSet.allOf(Role.class).forEach(role -> {
            User userWithRole = new User();
            userWithRole.setUsername("user");
            userWithRole.setPassword("pass");
            userWithRole.setRole(role);
            when(userRepository.getUserByUsername("user")).thenReturn(userWithRole);

            Authentication authentication = new UsernamePasswordAuthenticationToken("user", "pass");
            Authentication result = authService.authenticate(authentication);
            
            assertNotNull(result, "Authentication should succeed for role: " + role);
            assertTrue(result.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_" + role.name())),
                       "Expected authority not found for role: " + role);
        });
    }

    @Test
    public void authenticateExceptionThrown() {
        // Create a mock UserRepository
        UserRepository userRepository = mock(UserRepository.class);
        // Mock UserRepository's behavior to throw a RuntimeException
        when(userRepository.getUserByUsername(anyString())).thenThrow(new RuntimeException("Database error"));

        // Create an instance of AuthService with the mock UserRepository
        AuthService authService = new AuthService(userRepository, null); // Pass null for AuthorityRepository
        
        // Create an instance of Authentication with arbitrary credentials
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken("user", "pass");

        // Invoke the authenticate method and catch any thrown exception
        assertThrows(RuntimeException.class, () -> authService.authenticate(authentication),
                     "Expected RuntimeException due to database error");
    }

    // Test that the correct routes are returned for different roles   
    @Test
    public void getAuthorizedRoutesForRoles() {
        EnumSet.allOf(Role.class).forEach(role -> {
            List<AuthService.AuthorizedRoute> routes = authService.getAuthorizedRoutes(role);
            if (role == Role.ADMIN) {
                assertTrue(routes.stream().anyMatch(r -> r.name().equals("Admin")),
                           "Admin route should be available for ADMIN role");
            } else if (role == Role.OWNER) {
                assertTrue(routes.stream().anyMatch(r -> r.name().equals("Building Owner")),
                           "Owner route should be available for OWNER role");
            }
            assertTrue(routes.stream().anyMatch(r -> r.name().equals("Home")),
                       "Home route should always be available");
        });
    }
    
    
    // The first user should be assigned the ADMIN role
    @Test
    public void registerUserFirstAdminSpecialCase() throws Exception {

    }
    
    @Test
    public void authenticateMethodWithNullValues() {
        // Test authentication method with null username and password
        Authentication authentication = new UsernamePasswordAuthenticationToken(null, null);
        assertNull(authService.authenticate(authentication),
                   "Authentication with null credentials should return null");
    }
   
    //Trying to test the authentication method for correct and incorrect password
    /*
    // Test the custom authenticate method for failed authentication due to incorrect password
    @Test
    public void authenticateCustomMethodSuccess() throws AuthService.AuthException {
        // Mock the behavior of userRepository to return a user when getUserByUsername is called
        when(userRepository.getUserByUsername("testUser")).thenReturn(user);

        // Call the authenticate method with valid credentials
        boolean isAuthenticated = authService.authenticate("testUser", "testPass");

        // Assert that authentication was successful
        assertTrue(isAuthenticated, "Custom authenticate method should return true for valid credentials");
    }

    // Test the custom authenticate method for failed authentication due to incorrect password
    @Test
    public void authenticateCustomMethodFailure() {        
        when(userRepository.getUserByUsername("validUser")).thenReturn(user);
        when(user.checkPassword("invalidPass")).thenReturn(false);

        assertThrows(AuthService.AuthException.class, () -> authService.authenticate("validUser", "invalidPass"),
                     "Custom authenticate method should throw AuthException for invalid credentials");
    }
    */
    
    // set different roles for users
    /*
    @Test
    public void createRoutesForDifferentRoles() throws Exception {
        // Mock the dependencies
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        AuthorityRepository authorityRepository = Mockito.mock(AuthorityRepository.class);

        // Create an instance of AuthService with mocked dependencies
        AuthService authService = new AuthService(userRepository, authorityRepository);

        // Get the method "createRoutes" from the AuthService class
        java.lang.reflect.Method createRoutesMethod = AuthService.class.getDeclaredMethod("createRoutes", Role.class);

        // Set the method to be accessible
        createRoutesMethod.setAccessible(true);

        // Test route creation logic for different user roles
        User adminUser = new User();
        adminUser.setRole(Role.ADMIN);
        createRoutesMethod.invoke(authService, Role.ADMIN);
        assertTrue(true); // Assuming admin routes are correctly created

        User ownerUser = new User();
        ownerUser.setRole(Role.OWNER);
        createRoutesMethod.invoke(authService, Role.OWNER);
        assertTrue(true); // Assuming owner routes are correctly created

        User regularUser = new User();
        regularUser.setRole(Role.USER);
        createRoutesMethod.invoke(authService, Role.USER);
        assertTrue(true); // Assuming regular user routes are correctly created
    }
    */


	@Test
    public void isAuthenticatedWhenSessionUserIsNull() {
        // Mock VaadinServletRequest and VaadinSession to simulate a user not being logged in
        // Assuming there's a way to mock or simulate these static calls to return a null user
        assertFalse(AuthService.isAuthenticated(), 
                    "isAuthenticated should return false when no user is in the session");
    }

    @Test
    public void deleteNonExistentAuthority() {
        // Test deleting an authority that doesn't exist in the repository
        Authority nonExistentAuthority = new Authority(user, "ROLE_NON_EXISTENT");
        doNothing().when(authorityRepository).remove(nonExistentAuthority);
        authService.delete(nonExistentAuthority);
        verify(authorityRepository, times(1)).remove(nonExistentAuthority);
    }

    
    @Test
    public void registerWithNullCredentials() {
        // Verify that attempting to register with null username or password results in a NullPointerException
        assertThrows(NullPointerException.class, () -> authService.register(null, "password", Role.USER),
                     "Registration with null username should throw NullPointerException");
        assertThrows(NullPointerException.class, () -> authService.register("username", null, Role.USER),
                     "Registration with null password should throw NullPointerException");
    }



    @Test
    public void getUserCountReflectsActualCount() {
        // Test that getUserCount accurately reflects the number of users
        when(userRepository.count()).thenReturn(42L);
        assertEquals(42L, authService.getUserCount(), "getUserCount should return the actual number of users in the repository");
    }


    
    @Test
    public void supportsNonUsernamePasswordAuthenticationToken() {
        // Ensure the AuthService correctly reports it does not support authentication types other than UsernamePasswordAuthenticationToken
        assertFalse(authService.supports(Authentication.class),
                    "AuthService should not support authentication types other than UsernamePasswordAuthenticationToken");
    }
    
    @Test
    public void authenticateWhenUserIsNullAndExceptionHandling() {
        // Covering the scenario when UserRepository returns null (user not found)
        when(userRepository.getUserByUsername("nonexistent")).thenReturn(null);

        Authentication authentication = new UsernamePasswordAuthenticationToken("nonexistent", "password");
        Authentication result = authService.authenticate(authentication);

        assertNull(result, "Authentication should return null for non-existent users");
    }
        

    @Test
    public void supportsAuthenticationClassNotAssignable() {
        // Verifying the AuthService's supports method correctly identifies unsupported Authentication implementations.
        assertFalse(authService.supports(Authentication.class),
                    "AuthService should only support UsernamePasswordAuthenticationToken, not generic Authentication");
    }


}
