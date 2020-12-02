# COVID-19 Symptom Tracker & Tracer Andriod Application
The application is meant to automatically notify users who have been in close contact with users that have tested positive for COVID-19
## Instalation
**Step 1:** Clone the Android GitHub repository \n
**Step 2:** Open the recently acquired code from Github and get the Package Name and SHA-1 signing certificate\n
   - The package name should appear in the first line of Every Activity/Fragment/Java class
        - Ex: com.example
   - To get the SHA-1 signing certificate look at the top-right margin of Android Studio and click Gradle.
   Then follow the following path: LoginDemo2/Tasks/android/signingReport
   Double click the signingReport, SHA1 certificate code will pop up in "Run"
        - Ex: 12:34:56:78:90:AB:CD:EF:12:34:56:78:90:AB:CD:EF:AA:BB:CC:DD\n
**Step 3:** Get the credentials for Google API
   - Follow Google Step-by-Step instructions on how to get the Google API credentials as a JSON file using the Package Name the SHA-1 certificate.
   https://developers.google.com/identity/sign-in/android/start
   - Once the JSON file containing the credentials is acquired put in inside the 'app' directory as 'credentials.json'\n
**Step 4:** Update POST and GET request based on the new domain in the following Fragments (Replace **localhost:300** with a new domain such as **covidtrackerdev.herokuapp**)
MyCovidData_MySocialCircle
```
private static final String getSocialURL = "https://localhost:3000/get_social_circle";
private static final String postSocialUrl = "https://localhost:3000/post_social_circle";
```
MyCovidData_ReportPositiveTest
```
private static final String get_isAllowed = "https://localhost:3000/get_allowed_to_report";
private static final String post_self_report = "https://localhost:3000/post_self_report";
private static final String sendAlertSocial = "https://localhost:3000/post_send_alert";
private static final String sendAlertClass = "https://localhost:3000/post_class_alert";
private static final String sendAlertWorkplace = "https://localhost:3000/post_alert_workplace";
```
MyCovidData_UpdateClasses
```
private final String postCourseList = "https://localhost:3000/post_courselist";
private final String getCourseList = "https://localhost:3000/get_courselist";
```
MyCovidData_UpdateWorkplace
```
private static final String get_Workplace = "https://localhost:3000/get_workplace";
private static final String post_Workplace = "https://http://localhost:3000/post_workplace";
```
**Step 5:** Update the WEBSITE_URL at MyCovidData fragmnet
```
private static final String WEBSITE_URL = "https://your_new_domain.com/";
```
**Step 6:** Inside Android Studio download an Emulator that has an API level of 30 or higher
**Step 7:** Run the application