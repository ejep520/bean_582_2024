# Apartment Finder TMS
NOTE: This TMS requires [IntelliJ Early Access Program Edition](https://www.jetbrains.com/idea/nextversion/) </br>
NOTE: This TMS requires the [IntelliJ Test Management Plugin](https://plugins.jetbrains.com/plugin/15109-test-management)

Tags: Unit tests
## 1 AdminFormTests: Unit tests for AdminForm
* 11 fromPropertiesTest (Verify admin form properties are correct)
* 12 whenUserIsSavedTest (User is saved correctly)
* 13 whenUserIsValidatedAndSavedTest (User is validated and saved)
* 14 whenUserIsDeletedTest (Delete event is fired when user is deleted)
* 15 whenUserIsCancelledTest (Form is cleared of data when cancelled)
## 2 AdminViewTests: Unit tests for AdminView
* 21 viewPropertiesTest (Verify view properties are set)
* 22 gridPropertiesTest (Verify view's grid properties are set correctly)
* 23 formShownAndClosedWhenUserIsSavedTest (Verify view's admin form instance closes upon save)
* 24 formShownAndClosedWhenUserIsDeletedTest (Verify view's admin form instance closes upon delete)
* 25 formShownAndClosedWhenUserIsCancelledTest (Verify view's admin form instance closes upon cancel)
## 3 HomeViewTests: Unit tests for the HomeView
* 31 testGridSetupWithEmptyUnitList (Verify grid is empty with no units)
* 32 homeViewPropertiesTest (Verify view is constructed properly)
* 33 testGridSetup (Verify view's data grid is constructed properly)
* 34 testConstructor (Verify view and grid is populated)
* 35 testGetGrid (Verify grid instance is populated)
* 36 testGridSetupWithNonEmptyUnitList (Verify view's grid is constructed correctly with no units)
* 37 testFormNotShownWhenUnitIsDeleted (Test grid property removes items when unit is deleted)
* 38 testFormNotShownWhenUnitIsCancelled (Test grid is cleared properly)
## 4 OwnerFormTests: Unit tests for the OwnerForm
* 41 formFieldsPopulated (OwnerForm setting the Unit populates the fields)
* 42 formFiresSaveEventTest (OwnerForm adds a listener for and fires off save event)
* 43 formFiresDeleteEventTest (OwnerForm adds a listener for and fires off delete event)
* 44 formFiresCancelEventTest (OwnerForm adds listener to and fires off cancel/close event)
* 45 formSaveEventFiresUpdatedUnit (OwnerForm Save Event fired off the updated Unit)
* 46 saveFormWithInvalidDataDoesntFireSave (OwnerForm will not fire if form contains invalid data)
* 47 propertiesTest (Verify form properties with a single unit)
## 5 OwnerViewTests: Unit tests for the OwnerView
* 51 allPropertiesTest (Verify view properties)
* 52 formShownWhenUnitSelectedTest (Verify owner form is shown when unit is selected)
* 53 unitEditedAndSavedTst (Verify unit is changed when edited)
* 54 unitEditDeletedTest (Verify form is closed when unit is deleted)
## 6 ApartmentFinderApplicationTests: Default test
* 61 contextLoads (Default test that starts Spring bootloader)
## 7 AuthServiceTests
* 71 authenticateEachEnumTest (Authenticate each user role)
* 72 authenticateNullReturnsNull (Attempt to authenticate with a null returns a null value)
* 73 supportsTests (Authentication service supports the authentication token classes)
* 74 getUserCountTest (0, MAX_VALUE)
* 75 deleteAuthorityTest (Authentication service is able to delete an authority)
* 76 getAuthorizedRoutesTest (Verify role only has access to the application routes)
* 77 registerTest (User counts from repository are correct)
## 8 UnitServiceTest: Unit tests for UnitService
* 81 testGetAllUnits (2)
* 82 testFindUnits (2)
* 83 testGetUnitCount (10)
* 84 testDeleteUnit (Verify service deletes unit on the repository)
* 85 testSaveUnit (Verify service saves unit on the repository)
## 9 UnitServiceTests (Testing the UnitService)
* 91 testGetAllUnits (2)
* 92 testFindUnits (2)
* 93 testGetUnitCount (10)
## 10 UserServiceTests: Unit tests for UserService
* 101 initializationResultsInNotNull (Initialization of UserService returns not null)
* 102 findAllReturnsAll (FindAll method returns all users)
* 103 findUsersNullReturnsAllUsers (FindUsers with null returns all users)
* 104 findUsersEmptyReturnsAllUsers (FindUsers with empty string returns all users)
* 105 findUsersBlankReturnsAllUsers (FindUsers with blank string returns all users)
* 106 findUsersValidFilterReturnsOne (FindUsers with a valid user returns one User)
* 107 findUserInvalidFilterReturnsNone (FindUser with invalid key returns no users)
* 108 deleteUserTest (Tests that DeleteUser calls the repository's delete method)
* 109 saveUserTest (Tests that SaveUser calls the repository's save method)
* 1010 updateUserTest (Tests that SaveUser calls the repository's update method)
* 1011 findingUsersReturnsUsersFaithfully (Verify finding user by id returns the user)
* 1012 unableToFindNonexistentUserById (Unable to find user nonexistent user by id)
* 1013 SearchForUsersByUsername (Verify searching by username returns the user)
* 1014 saveNullUserMakesNoRepoCalls (Save null User makes no repository calls)
## 11 AbstractClassTests: Unit tests for AbstractClass
* 111 newInstancesHaveNullId (New class instances have a null id)
* 112 setIdsStick (1)
* 113 versionIsAnInt (Valid integer)
## 12 AuthorityTests: Unit tests for Authority class
* 121 defaultAuthorityInitReturnsNotNullTest (Default Authority initialization returns not null)
* 122 parameterizedAuthorityInitializationNotNullTest (Parameterized Authority initialization returns not null)
* 123 parameterizedAuthorityWithoutValidUserReturnsNotNull (Parameterized Authority initialization with default user returns not null)
* 124 defaultAuthorityUserIsNull (Default Authority User is null)
* 125 defaultAuthorityAuthorityIsNull (Default Authority's Authority field is null)
* 126 parameterizedAuthorityKeepsItsUser (Parameterized Authority keeps its User)
* 127 parameterizedAuthorityKeepsItsAuthority (Parameterized Authority keeps its authority)
* 128 defaultAuthorityKeepsItsSetUser (Default Authority keeps its set User)
## 13 UnitTests: Unit tests for Unit class
* 131 defaultUnitInitializationIsNotNull (Default Unit Initialization is not null)
* 132 parameterizedUnitInitializationIsNotNull (Parameterized Unit Initialization is not null)
* 133 defaultUnitAddressIsNotNull (Default Unit address is not null)
* 134 parameterizedUnitAddressIsNotNull (Parameterized Unit address is not null)
* 135 defaultUnitAddressIsEmpty (Default Unit address is empty)
* 136 parameterizedUnitAddressIsKept (Parameterized Unit address is kept)
* 137 changedAddressIsKept (Changed Unit address is kept)
* 138 defaultUnitBedroomsIsZero (0)
* 139 parameterizedUnitBedroomsAreKept (2)
* 1310 changedBedroomsAreKept (2, 4)
* 1311 defaultUnitBathroomsIsZero (0.0)
* 1312 parameterizedUnitBathroomsAreKept (2.5)
* 1313 changedBathroomsAreKept (2.5, 0)
* 1314 defaultUnitKitchenIsEmpty (Default Unit kitchen is empty)
* 1315 parameterizedUnitKitchenIsKept ("Newly Refurnished")
* 1316 changedKitchenIsKept ("Newly Refurnished", "Moldy and yuch!")
* 1317 defaultUnitLivingRoomIsEmpty (Default Unit living room is empty)
* 1318 parameterizedUnitLivingRoomIsKept ("Fully Furnished")
* 1319 changedUnitLivingRoomIsKept ("Fully Furnished", "If I had a black light, this place would Look like a Jackson Pollock painting.")
* 1320 defaultUnitFeaturedIsFalse (False)
* 1321 parameterizedUnitFeaturedIsKept (True)
* 1322 changedUnitFeaturedIsKept (True, False)
* 1323 defaultUnitHasNullUser (Null)
* 1324 parameterizedUnitKeepsItsUser (user)
* 1325 changedUnitKeepsItsUser (user, bad_user)
* 1326 unitNullUnequal (null)
* 1327 aUnitIsEqualToItself (Equal)
* 1328 differentUnitsNotEqual (Not equal)
* 1329 unitAndOtherClassInstanceAreNotEqual (A unit and another class instance are not equal.)
## 14 UserTests: Unit tests for User class
* 141 defaultUserCreationNotNull (Not null)
* 142 defaultUserNotEnabled (False)
* 143 defaultUsernameNotNull (Not null)
* 144 defaultUsernameEmpty ("")
* 145 defaultUserPasswordNotNull (Not null)
* 146 defaultUserPasswordEmpty ("")
* 147 parameterizedUsername ("TestUser")
* 148 parameterizedPasswordHashed (Parameterized User hashes its password)
* 149 parameterizedPasswordPasses (Parameterized User can check its own password (1/2))
* 1410 parameterizedUserWrongPasswordFails (Parameterized User can check its own password (2/2))
* 1411 defaultUserUnexpired (True)
* 1412 defaultUserUnlocked (True)
* 1413 parameterizedUserIsEnabled (True, True)
* 1414 defaultUserRoleIsNull (Null)
* 1415 parameterizedUserKeepsItsRole (Parameterized User keeps its role)
* 1416 userSetUsernameHolds ("WrongPassword", "TestUser")
* 1417 defaultUserSetPasswordTest (Set Password on Default User works)
* 1418 parameterizedUserSetPasswordTest (Set Password on Parameterized User works)
* 1419 userSetRoleHolds (USER, OWNER)
* 1420 userGetAuthorities (User GetAuthorities works)
* 1421 userSetEnabledTest (True, False, False)
* 1422 userEraseCredentialsTest (User EraseCredentials works)
* 1423 userCredentialsAreUnexpiredTest (User Credentials are unexpired)
* 1424 changingPasswordSetsChangedPasswordFlag (Changing the password sets the Changed Password flag.)
* 1425 gettingUnitsReturnsListOfUnits (Getting units returns a list of units)
* 1426 usernameIsReturnedByUserToString ("TestUser")
* 1427 settingNewAuthoritiesCollectionReplacesExistingCollection (Setting new Authorities collection replaces the existing collection)
* 1428 affirmativelyNotChangingThePasswordWontRaiseChangePasswordFlag (Changing the password to the existing password doesn't set the change flag)
* 1429 changingSaltInvalidatesPasswordHash (Changing the salt invalidates the password hash)
## 15 AuthorityRepositoryTests: Unit tests for authority repository
* 151 getAllFunctionTest (42, 11, 12, 13, 21, 22, 30)
* 152 addFunctionTest (Authority)
* 153 updateFunctionTest (Authority)
* 154 deleteFunctionTest (Authority)
## 16 UnitRepositoryTests: Unit tests for unit repository
* 161 getAllFunctionTest (GetAll test)
* 162 getByIdFunctionTest (1, 2, 3, 4)
* 163 saveFunctionTest (Unit)
* 164 updateFunctionTest (Unit)
* 165 deleteFunctionTest (Unit)
* 166 searchFunctionTest ("321 Nowhere Ave", "Address 1", "Address 2", "Address 3")
* 167 findByOwnerFunctionTest (User)
* 168 findOwnedUnitsByFilter (unit, 0)
* 169 countFunctionTest (4)
## 17 UserRepositoryTests: Unit tests for user repository
* 171 testGetAllFunctionPassesCall (GetAll function passes call to DAO and returns result)
* 172 testGetById (1, 4)
* 173 addFunctionTest (User object)
* 174 updateFunctionTest (User object)
* 175 deleteFunctionTest (User object)
* 176 getUserByUsernameTest ("testAdmin", "testOwner", "testUser")
* 177 countFunctionTest (3)
## 18 AuthorityDaoTests: Unit tests for AuthorityDao class
* 181 findAllAuthoritiesTest (Authorities)
* 182 testGetById (Authoritey collection)
* 183 testSaveFunction (Test save function)
* 184 updateFunctionTest (Updating authorities is NOT supported.)
* 185 testDeleteFunction (Test Delete function)
## 19 UnitDaoTests: Unit tests for UnitDao class
* 191 countFunctionTest (3)
* 192 getByIdTest (Unit class)
* 193 getAllFunctionTest (Empty list, unit collection)
* 194 saveFunctionTest (Unit)
* 195 updateFunctionTest ("Address 1)
* 196 deleteFunctionTest (3, collection of units)
* 197 findFunctionTest ("Address 3)
* 198 findFunctionWithoutKeyTest (Collection of units)
* 199 findFunctionWithNullKeyTest (Collection of units)
* 1910 findByUserFunction (Collection of units)
* 1911 findByUserFunctionWithNullUserFindsNoUnits (Empty list)
* 1912 findOwnedUnitsWithFilterTestValidInputs (Unit object)
* 1913 findOwnedUnitsWithValidBadDataReturnsNothing (Empty list)
## 20 UserDaoTests: Unit test for UserDao class
* 201 getUserByIdTest (User)
* 202 findUserByUsernameSuccessReturnsUser (User)
* 203 findUserByUsernameFailureReturnsNull (User)
* 204 testGetAllFunction (Collection of users)
* 205 testSaveUserFunction (Collection of user)
* 206 testUpdateFunction ("TestOwner")
* 207 deleteUserFunctionTest (Collection of users)
* 208 countFunctionTest (3)
## 21 LoginViewTests: Unit tests for LoginView
* 211 propertiesOfLoginView (Verify view properties)
* 212 componentEventTestLoginError (True)
* 213 componentEventTestLoginFalse (True)
* 214 onComponentEventSuccessfulLoginTest (Throws error, true)
* 215 beforeEnterTests (Error state)
## 22 MainLayoutTests: Unit tests for MainLayout class
* 221 loginScreenPreviewTest (Simulate a page view without an authorized user)
* 222 authorizedUserTest (Simulate an authorized user.)
## 23 NewUserViewTests: Unit tests for NewUserView class
* 231 formSettingsTest (Verify view properties)
* 232 noUsernamePreventsRegistration (0, 0)
* 233 takenUsernamePreventsRegistration (True)
* 234 firstEmptyPasswordPreventsRegistration (True, 0)
* 235 secondEmptyPasswordPreventsRegistration (True, 0)
* 236 mismatchedPasswordsPreventRegistration (True, 0)
* 237 successfulRegistrationTest ("Foo", "Bar", User)
## 30 AuthorityRepositoryIntegrationTests (Authority repository integration tests)
* 301 emptyDatabaseTest (disabled)
* 302 addAuthoritiesTest
* 303 readAuthoritiesTest
* 304 updateAuthorityTest
* 305 deleteAuthorityTest
## 31 UnitRepositoryIntegrationTests (Unit repository integration tests)
* 311 resetTest
* 312 addUnitTest
* 313 getUnitTest
* 314 updateUnitTest
* 315 deleteUnitTest
## 32 UserRepositoryIntegrationTests (User repository integration tests)
* 321 getAllUsersTest
* 322 addUserTest
* 323 updateUserTest
* 324 deleteUserTest
## 33 AuthServiceIntegrationTests (Authentication service integration tests)
* 331 authenticate_null_user_returns_null
* 332 authenticate_each_role
* 333 register_void_throws_null_pointer_exception
* 334 register_with_zero_users_creates_admin
* 335 register_each_role_with_existing_users_in_database
* 336 register_overload_no_existing_users_creates_admin
* 337 register_overload_for_role_with_existing_users_in_database
* 338 count_user_test
* 339 delete_authority_test
* 3310 username_taken_test
## 34 UnitServiceIntegrationTests (Unit service integration tests)
* 341 getAllTest
* 342 findUnitsTest
* 343 findUnitsTestFail
* 344 getCountTest
* 345 deleteUnitTest
* 346 saveUnitTest
* 347 getUserUnits
* 348 userSearchUnitsTest
## 35 UserServiceIntegrationTests (User service integration tests)
* 351 addUserTest
* 352 getAllUsers
* 353 getUserByUsernameTest
* 354 deleteUserTest
* 355 saveUserTest
* 356 getUserByIdTest
## 36 AdminFormIntegrationTests (Integration tests for the AdminForm class)
* 361 viewInitializationTest
* 362 addUserTest
* 363 deleteUserTest
## 37 HomeViewIntegrationTests (Integration tests for the HomeView class)
* 371 noUnitsWhenViewInitializedTest
* 372 unitsExistWhenViewInitialized
* 373 updatingFilterValue
## 38 LoginViewIntegrationTests (Integration tests for the LoginView class)
* 381 simulateWithZeroUsers
* 382 simulateAuthenticationWithoutRequest
## 39 MainLayoutIntegrationTests (Integration tests for the MainLayout class)
* 391 loginScreenPreviewTest
* 392 loggedInScreenPreviewTest
## 40 NewUserViewIntegrationTests (Integration tests for the NewUserView class)
* 401 usernameTakenTest
* 402 registrationTest
## 41 OwnerFormIntegrationTests (Integration tests for the OwnerForm class)
* 411 initializationTest
## 42 Performance testing
* 421 (aptFinder.smoke.js) Performance test exercising the application from the UI. See notes in test.
## 43 UI Testing (Selenium test cases)
* 431 test_page_load
* 432 test_register
* 433 add_new_user
* 434 test_login_happy_path_admin
* 435 test_incorrect_username
* 436 add_new_user_pass_dont_match
* 437 test_login_happy_path_user
* 438 test_incorrect_password
* 439 test_logOut_happy_path_admin
* 4310 test_logOut_happy_path_user
* 4311 test_login_admin_grid_access
* 4312 test_login_user_grid_access
* 4313 test_login_admin_add_unit
* 4314 test_login_admin_edit_unit
* 4315 test_login_admin_add_owner_users
* 4316 test_login_happy_path_owner
* 4317 test_logout_happy_path_owner
* 4318 test_owner_user_grid_access
* 4319 test_login_owner_edit_unit
* 4320 test_login_owner_add_unit
* 4321 test_login_owner_delete_unit
* 4322 test_login_admin_edit_user
* 4323 test_login_admin_delete_user
* 4324 test_login_admin_delete_unit