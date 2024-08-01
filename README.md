# DHEC-POC-Adr
### MyHealthAssistant POC Application - README

---

#### Overview   

This project is a Proof-of-Concept (POC) for the "MyHealthAssistant" mobile application, focusing on secure file upload and synchronization across devices based on user consent. The application leverages Jetpack Compose for UI, Koin for dependency injection, and FHIR standards for healthcare data representation.

#### Architecture

**Architecture Pattern:** MVVM (Model-View-ViewModel) with a clean architecture approach.

- **Data Layer:** Handles data retrieval and storage.
- **Domain Layer:** Contains business logic and use cases.
- **Presentation Layer:** UI components and ViewModels.

**Dependency Injection:** Koin is used for service location and dependency injection, facilitating decoupled and testable code.

#### Folder Structure

```
app/
|-- build/
|-- src/
    |-- androidTest/
    |-- main/
        |-- java/
            |-- com.example.myhealthassistant/
                |-- data/
                    |-- repository/
                        |-- AuthRepositoryImpl.kt
                        |-- ConsentRepositoryImpl.kt
                        |-- FileRepositoryImpl.kt
                    |-- service/
                    |-- di/
                |-- domain/
                    |-- model/
                        |-- fihrmodels/
                            |-- FihrModels.kt
                        |-- Consent.kt
                        |-- File.kt
                        |-- User.kt
                    |-- repository/
                        |-- AuthRepository.kt
                        |-- ConsentRepository.kt
                        |-- FileRepository.kt
                    |-- usecase/
                        |-- consent/
                            |-- GetConsentUseCase.kt
                            |-- GrantConsentUseCase.kt
                            |-- RevokeConsentUseCase.kt
                        |-- filemanagement/
                            |-- FileSyncUseCase.kt
                        |-- LoginUseCase.kt
                |-- extensions/
                |-- navigation/
                    |-- NavigationRoute.kt
                |-- presentation/
                    |-- consent/
                        |-- components/
                            |-- NoConsentScreen.kt
                        |-- ConsentContract.kt
                        |-- ConsentCoordinator.kt
                        |-- ConsentRoute.kt
                        |-- ConsentScreen.kt
                        |-- ConsentViewModel.kt
                    |-- filemanagement/
                        |-- components/
                            |-- FileManagementContract.kt
                            |-- FileManagementCoordinator.kt
                            |-- FileManagementRoute.kt
                            |-- FileManagementScreen.kt
                            |-- FileManagementViewModel.kt
                    |-- login/
                        |-- components/
                            |-- LoginContract.kt
                            |-- LoginCoordinator.kt
                            |-- LoginRoute.kt
                            |-- LoginScreen.kt
                            |-- LoginViewModel.kt
                    |-- ui/
                        |-- components/
                            |-- GlobalAppBar.kt
                        |-- theme/
                |-- MainActivity.kt
                |-- MainApp.kt
        |-- res/
            |-- AndroidManifest.xml
    |-- test/
        |-- java/
            |-- com.example.myhealthassistant/
                |-- dispatchers/
                    |-- MainDispatcherRule.kt
                |-- domain/
                    |-- consent/
                        |-- usecase/
                            |-- GrantConsentUseCaseTest.kt
```

#### Key Target Features

- **Secure File Management:** Files are uploaded and synchronized across devices post-consent.
- **FHIR Integration:** Adopts FHIR resources such as `DocumentReference` and `Consent` for data representation.
- **Biometric Security:** Uses biometric authentication for secure access.
- **Jetpack Compose:** Modern UI framework for building declarative UI components.
- **Koin for DI:** Simplifies dependency injection setup and management.

#### Testing Strategy

- **Unit Tests:** Utilizes Kluent for assertions and Mockito for mocking. Tests focus on business logic in the domain layer.
- **UI Tests:** Employ Jetpack Compose testing framework for UI components.
- **Robolectric Tests:** Facilitates testing Android components on the JVM.

#### Continuous Integration (CI)

**GitLab CI/CD Pipeline**

The CI pipeline is defined using a `.gitlab-ci.yml` file and uses fastlane for platform agnostic build pipeline development. The pipeline includes the following stages:

- **build_ios & build_android:** Builds the iOS and Android applications using Fastlane.
- **test_ios & test_android:** Runs tests for both platforms.
- **analyze:** Runs code analysis tools such as SwiftLint, Klint, SonarCloud.
- **deploy_beta:** Deploys to beta environments for manual QA testing or internal Beta Testing Channels.

#### GitLab CI Configuration Example

```yaml
stages:
  - build
  - test
  - analyze
  - deploy

variables:
  LC_ALL: "en_US.UTF-8"
  LANG: "en_US.UTF-8"

before_script:
  - bundle install

build_ios:
  stage: build
  script:
    - fastlane ios build

build_android:
  stage: build
  script:
    - fastlane android build

test_ios:
  stage: test
  script:
    - fastlane ios test

test_android:
  stage: test
  script:
    - fastlane android test

analyze:
  stage: analyze
  script:
    - fastlane run swiftlint
    - fastlane run ktlint

deploy_beta:
  stage: deploy
  script:
    - fastlane ios beta
    - fastlane android beta
  when: manual
```

This README provides an overview of the project structure, key components, and the CI/CD process. The setup ensures a robust and secure file management system aligned with healthcare standards, focusing on modularity, testability, and maintainability.
