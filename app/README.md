SvaraFlow Android Application
1. Project Overview
   SvaraFlow is a powerful Text-to-Speech (TTS) and voice generation application for Android. It provides users with a suite of tools to create high-definition voiceovers, manage audio files, and explore different voice and language settings. The app is designed with a modern, clean user interface and is structured to be scalable for future features.
This document serves as the official README for the project, detailing its structure, setup process, and core components.

2. Core Technologies
   The application is built using a modern Android technology stack, leveraging both traditional and declarative UI paradigms for optimal performance and maintainability.

Language: Kotlin

Primary UI Toolkit: Jetpack Compose for dynamic and interactive screens (TTSActivity).

Secondary UI Toolkit: XML with ViewBinding for static screens like authentication and informational pages (LoginActivity, ProfileActivity, etc.).

Architecture: The app follows a basic Model-View-ViewModel (MVVM) pattern, with UI state managed in composable functions and activities.

Backend Services: Firebase for analytics and authentication (integration pending).

Key Android Components:

AppCompatActivity for XML-based screens.

ComponentActivity for Jetpack Compose screens.

Material Design 3 for UI components and styling.

ModalNavigationDrawer for the main app navigation.

3. Project Setup
   To get the project running correctly in Android Studio, follow these essential setup steps.

Step 3.1: Firebase Configuration
The app uses Firebase for backend services. The build process requires a google-services.json configuration file to connect to the correct Firebase project.

Download google-services.json: Go to your project's settings in the Firebase Console. In the "Your apps" card, download the configuration file for the Android app (com.pragyashal.SvaraflowApp).

Place the File: In Android Studio, switch to the Project view. Drag and drop the downloaded google-services.json file directly into the app folder. The final path should be SvaraFlow/app/google-services.json.

Step 3.2: Build the Project
Once the configuration file is in place, you can build the project.

Sync Gradle Files: Click the "Sync Now" banner that appears after you modify any build.gradle.kts file.

Rebuild Project: It is highly recommended to do a clean rebuild to ensure all resources are correctly linked. In the top menu, go to Build > Rebuild Project.

Run the App: Select an emulator or connect a physical device and click the "Run 'app'" button.

4. Application Structure and Flow
   The application is structured around a central TTSActivity which acts as the main hub after a user logs in.

4.1. Key Files and Directories
java/com/pragyashal/SvaraflowApp/: The root package for all Kotlin source code.

LoginActivity.kt & SignUpActivity.kt: Handle user authentication.

TTSActivity.kt: The core of the application. This Jetpack Compose activity contains the navigation drawer and hosts all the main feature screens (HdVoiceGenerationScreen, YourFilesScreen, etc.).

ProfileActivity.kt & SubscriptionActivity.kt: Standard XML-based activities for displaying user profile and subscription plans.

theme/: Contains the Jetpack Compose theme files (Color.kt, Theme.kt, Type.kt).

res/layout/: Contains all the XML layout files for the non-Compose activities.

res/drawable/: Contains all vector icons used throughout the application.

res/values/: Contains resource files for colors, strings, and XML-based themes.

AndroidManifest.xml: Registers all activities and defines the app's core properties.

4.2. User Navigation Flow
The app launches to the LoginActivity.

The user can log in or tap "Sign Up" to navigate to the SignUpActivity.

Upon successful login, the user is taken to the TTSActivity.

The TTSActivity displays a navigation drawer on the left, allowing the user to switch between different feature screens.

The top app bar in TTSActivity contains icons to navigate to the SubscriptionActivity and ProfileActivity.

5. Backend Integration Points
   The frontend has been built with clear placeholders indicating where backend logic is required.

Authentication: The loginButton, signUpButton, and Google Sign-In buttons in LoginActivity and SignUpActivity currently show Toast messages. These need to be wired to your Firebase Authentication service.

TTS Service: In TTSActivity.kt, the "Synthesize Speech" button's onClick lambda is the integration point for your TTS API.

File Management: The YourFilesScreen composable needs to be connected to a backend service to fetch and display a list of the user's generated files.

Payments: The "Pay Now" buttons in SubscriptionActivity need to be connected to your payment gateway.