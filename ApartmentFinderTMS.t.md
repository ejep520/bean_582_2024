# Apartment Finder TMS

## 1 AdminFormTests
* 11 whenUserIsSavedTest
    * Setup admin form
    * Set user to new admin user
    * Save new user
    * Get saved user
    * Assert username
    * Assert user enabled
    * Assert user role
    * Assert user password
* 12 whenUserIsValidatedAndSavedTest
  * Setup admin form
  * Set new user
  * Validate and save user
  * Get user
  * Assert username
  * Assert user enabled
  * Assert user role
  * Assert user password
* 13 whenUserIsDeletedTest
  * Setup admin form
  * Delete owner user
  * Get deleted user
  * Assert username
  * Assert user enabled
  * Assert user role
  * Assert user password
 * 14 whenUserIsCancelledTest
   * Setup admin form
   * Set the user to a normal user
   * Click cancel
   * Assert the cancelled user
 
## 2 AdminViewTests
* 21 formShownAndClosedWhenUserIsSavedTest
* 22 formShownAndClosedWhenUserIsDeletedTest
* 23 formShownAndClosedWhenUserIsCancelledTest

## 3 HomeViewTests
* 31 testGridSetupWithEmptyUnitList
* 32 testGridSetup
* 33 testConstructor
* 34 testGetGrid
* 35 testGridSetupWithNonEmptyUnitList
* 36 testFormNotShownWhenUnitIsSelected
* 37 testFormNotShownWhenUnitIsDeleted
* 38 testFormNotShownWhenUnitIsCancelled

## 4 OwnerFormTests
## 5 OwnerViewTests
## 6 ApartmentFinderApplicationTests
## 7 UnitServiceTests
## 8 UserServiceTests
## 9 AbstractClassTests
## 10 AuthorityTests
## 11 UnitTests
## 12 UserTests
## 13 AuthorityRepositoryTests
## 14 UnitRepositoryTests
## 15 UserRepositoryTests
## 16 AuthorityDaoTests
## 17 UnitDaoTests
## 18 UserDaoTests
