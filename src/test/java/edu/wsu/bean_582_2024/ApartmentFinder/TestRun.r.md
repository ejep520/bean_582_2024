# TestRun

## Apartment Finder TMS

### S1 AdminFormTests
* [passed] C11 whenUserIsSavedTest

* [passed] C12 whenUserIsValidatedAndSavedTest

* [passed] C13 whenUserIsDeletedTest

* [passed] C14 whenUserIsCancelledTest


### S2 AdminViewTests
* [passed] C21 formShownAndClosedWhenUserIsSavedTest

* [passed] C22 formShownAndClosedWhenUserIsDeletedTest

* [passed] C23 formShownAndClosedWhenUserIsCancelledTest


### S3 HomeViewTests
* [passed] C31 testGridSetupWithEmptyUnitList

* [passed] C32 testGridSetup

* [passed] C33 testConstructor

* [passed] C34 testGetGrid

* [passed] C35 testGridSetupWithNonEmptyUnitList

* [passed] C36 testFormNotShownWhenUnitIsSelected

* [passed] C37 testFormNotShownWhenUnitIsDeleted

* [passed] C38 testFormNotShownWhenUnitIsCancelled


### S4 OwnerFormTests
* [passed] C41 formFieldsPopulated

* [passed] C42 formFiresSaveEventTest

* [passed] C43 formFiresDeleteEventTest

* [passed] C44 formFiresCancelEventTest

* [passed] C45 formSaveEventFiresUpdatedUnit

* [passed] C46 saveFormWithInvalidDataDoesntFireSave


### S5 OwnerViewTests
* [passed] C51 allPropertiesTest


### S6 ApartmentFinderApplicationTests
* [passed] C61 contextLoads


### S7 AuthServiceTests
* [passed] C71 authenticateEachEnumTest

* [passed] C72 authenticateNullReturnsNull

* [passed] C73 supportsTests

* [passed] C74 getUserCountTest

* [passed] C75 deleteAuthorityTest

* [passed] C76 getAuthorizedRoutesTest

* [passed] C77 registerTest


### S8 UnitServiceTest
* [passed] C81 testGetAllUnits

* [passed] C82 testFindUnits

* [passed] C83 testGetUnitCount

* [passed] C84 testDeleteUnit

* [passed] C85 testSaveUnit


### S9 UnitServiceTests
* [passed] C91 testGetAllUnits

* [passed] C92 testFindUnits

* [passed] C93 testGetUnitCount


### S10 UserServiceTests
* [passed] C101 initializationResultsInNotNull

* [passed] C102 findAllReturnsAll

* [passed] C103 findUsersNullReturnsAllUsers

* [passed] C104 findUsersEmptyReturnsAllUsers

* [passed] C105 findUsersBlankReturnsAllUsers

* [passed] C106 findUsersValidFilterReturnsOne

* [passed] C107 findUserInvalidFilterReturnsNone

* [passed] C108 deleteUserTest

* [passed] C109 saveUserTest

* [passed] C1010 updateUserTest

* [passed] C1011 findingUsersReturnsUsersFaithfully

* [passed] C1012 unableToFindNonexistentUserById

* [passed] C1013 SearchForUsersByUsername

* [passed] C1014 saveNullUserMakesNoRepoCalls


### S11 AbstractClassTests
* [passed] C111 setIdsStick

* [passed] C112 versionIsAnInt


### S12 AuthorityTests
* [passed] C121 defaultAuthorityInitReturnsNotNullTest

* [passed] C122 parameterizedAuthorityInitializationNotNullTest

* [passed] C123 parameterizedAuthorityWithoutValidUserReturnsNotNull

* [passed] C124 defaultAuthorityUserIsNull

* [passed] C125 defaultAuthorityAuthorityIsNull

* [passed] C126 parameterizedAuthorityKeepsItsUser

* [passed] C127 parameterizedAuthorityKeepsItsAuthority

* [passed] C128 defaultAuthorityKeepsItsSetUser


### S13 UnitTests
* [passed] C131 defaultUnitInitializationIsNotNull

* [passed] C132 parameterizedUnitInitializationIsNotNull

* [passed] C133 defaultUnitAddressIsNotNull

* [passed] C134 parameterizedUnitAddressIsNotNull

* [passed] C135 defaultUnitAddressIsEmpty

* [passed] C136 parameterizedUnitAddressIsKept

* [passed] C137 changedAddressIsKept

* [passed] C138 defaultUnitBedroomsIsZero

* [passed] C139 parameterizedUnitBedroomsAreKept

* [passed] C1310 changedBedroomsAreKept

* [passed] C1311 defaultUnitBathroomsIsZero

* [passed] C1312 parameterizedUnitBathroomsAreKept

* [passed] C1313 changedBathroomsAreKept

* [passed] C1314 defaultUnitKitchenIsEmpty

* [passed] C1315 parameterizedUnitKitchenIsKept

* [passed] C1316 changedKitchenIsKept

* [passed] C1317 defaultUnitLivingRoomIsEmpty

* [passed] C1318 parameterizedUnitLivingRoomIsKept

* [passed] C1319 changedUnitLivingRoomIsKept

* [passed] C1320 defaultUnitFeaturedIsFalse

* [passed] C1321 parameterizedUnitFeaturedIsKept

* [passed] C1322 changedUnitFeaturedIsKept

* [passed] C1323 defaultUnitHasNullUser

* [passed] C1324 parameterizedUnitKeepsItsUser

* [passed] C1325 changedUnitKeepsItsUser

* [passed] C1326 unitNullUnequal

* [passed] C1327 aUnitIsEqualToItself

* [passed] C1328 differentUnitsNotEqual

* [passed] C1329 unitAndOtherClassInstanceAreNotEqual


### S14 UserTests
* [passed] C141 defaultUserCreationNotNull

* [passed] C142 defaultUserNotEnabled

* [passed] C143 defaultUsernameNotNull

* [passed] C144 defaultUsernameEmpty

* [passed] C145 defaultUserPasswordNotNull

* [passed] C146 defaultUserPasswordEmpty

* [passed] C147 parameterizedUsername

* [passed] C148 parameterizedPasswordHashed

* [passed] C149 parameterizedPasswordPasses

* [passed] C1410 parameterizedUserWrongPasswordFails

* [passed] C1411 defaultUserUnexpired

* [passed] C1412 defaultUserUnlocked

* [passed] C1413 parameterizedUserIsEnabled

* [passed] C1414 defaultUserRoleIsNull

* [passed] C1415 parameterizedUserKeepsItsRole

* [passed] C1416 userSetUsernameHolds

* [passed] C1417 defaultUserSetPasswordTest

* [passed] C1418 parameterizedUserSetPasswordTest

* [passed] C1419 userSetRoleHolds

* [passed] C1420 userGetAuthorities

* [passed] C1421 userSetEnabledTest

* [passed] C1422 userEraseCredentialsTest

* [passed] C1423 userCredentialsAreUnexpiredTest

* [passed] C1424 changingPasswordSetsChangedPasswordFlag

* [passed] C1425 gettingUnitsReturnsListOfUnits

* [passed] C1426 usernameIsReturnedByUserToString

* [passed] C1427 settingNewAuthoritiesCollectionReplacesExistingCollection

* [passed] C1428 affirmativelyNotChangingThePasswordWontRaiseChangePasswordFlag

* [passed] C1429 changingSaltInvalidatesPasswordHash


### S15 AuthorityRepositoryTests
* [passed] C151 getAllFunctionTest

* [passed] C152 addFunctionTest

* [passed] C153 updateFunctionTest

* [passed] C154 deleteFunctionTest


### S16 UnitRepositoryTests
* [passed] C161 getAllFunctionTest

* [passed] C162 getByIdFunctionTest

* [passed] C163 saveFunctionTest

* [passed] C164 updateFunctionTest

* [passed] C165 deleteFunctionTest

* [passed] C166 searchFunctionTest

* [passed] C167 findByOwnerFunctionTest

* [passed] C168 findOwnedUnitsByFilter

* [passed] C169 countFunctionTest


### S17 UserRepositoryTests
* [passed] C171 testGetAllFunctionPassesCall

* [passed] C172 testGetById

* [passed] C173 addFunctionTest

* [passed] C174 updateFunctionTest

* [passed] C175 deleteFunctionTest

* [passed] C176 getUserByUsernameTest

* [passed] C177 countFunctionTest


### S18 AuthorityDaoTests
* [passed] C181 findAllAuthoritiesTest

* [passed] C182 testGetById

* [passed] C183 testSaveFunction

* [passed] C184 updateFunctionTest

* [passed] C185 testDeleteFunction


### S19 UnitDaoTests
* [passed] C191 countFunctionTest

* [passed] C192 getByIdTest

* [passed] C193 getAllFunctionTest

* [passed] C194 saveFunctionTest

* [passed] C195 updateFunctionTest

* [passed] C196 deleteFunctionTest

* [passed] C197 findFunctionTest

* [passed] C198 findFunctionWithoutKeyTest

* [passed] C199 findFunctionWithNullKeyTest

* [passed] C1910 findByUserFunction

* [passed] C1911 findByUserFunctionWithNullUserFindsNoUnits

* [passed] C1912 findOwnedUnitsWithFilterTestValidInputs

* [passed] C1913 findOwnedUnitsWithValidBadDataReturnsNothing


### S20 UserDaoTests
* [passed] C201 getUserByIdTest

* [passed] C202 findUserByUsernameSuccessReturnsUser

* [passed] C203 findUserByUsernameFailureReturnsNull

* [passed] C204 testGetAllFunction

* [passed] C205 testSaveUserFunction

* [passed] C206 testUpdateFunction

* [passed] C207 deleteUserFunctionTest

* [passed] C208 countFunctionTest


