#COVID-19 Symptom Tracker & Tracer Andriod Application
The application is meant to automatically notify users who have been in close contact with users that have tested positive for COVID-19
##Instalation
*Item1
Create a local GitHub repository
*Item2
Open the recently acquired code from Github and get the Package Name and SHA-1 signing certificate
   *Item2a
   The package name should appear in the first line of Every Activity/Fragment/Java class
   Ex: com.example
   *Item2b
   To get the SHA-1 signing certificate look at the top-right margin of Android Studio and click Gradle.
   Then follow the following path: LoginDemo2/Tasks/android/signingReport
   Double click the signingReport, SHA1 certificate code will pop up in "Run"
   Ex: 12:34:56:78:90:AB:CD:EF:12:34:56:78:90:AB:CD:EF:AA:BB:CC:DD
*Item3:
Get the credentials for Google API
   *Item3a
   Follow Google Step-by-Step instructions on how to get the Google API credentials.
   https://developers.google.com/identity/sign-in/android/start developer
   *Item3b
   Once the JSON file containing the credentials is acquired put in inside the 'app' directory as 'credemtials.json'
*Item4:
Inside Android Studio download an Emulator that has an API level of 30
*Item5
Run the application