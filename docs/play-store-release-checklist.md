# Picmorrow Play Store Privacy Checklist

This checklist reflects the app behavior audited on 30 September 2026. Re-audit it whenever dependencies, permissions, analytics, ads, accounts, networking, or storage behavior change.

## Before submission

- Deploy `docs/privacy-policy.html` to a public, non-editable, non-geofenced HTML URL.
- The app currently expects: `https://shyakdas.github.io/Picmorrow-Photo-Tasks/privacy-policy.html`.
- In GitHub, open **Settings > Pages**, choose **Deploy from a branch**, then select `main` and `/docs`.
- Verify that URL works without login, JavaScript, redirects, or a file download.
- Use the same URL in Play Console under **Policy and programs > App content > Privacy policy**.
- Keep the developer identity/contact in the policy consistent with the Play Store listing.
- Confirm the in-app Privacy Policy link opens the same governing policy.

## Data safety answers

Based on the current code and dependencies:

- **Does the app collect or share required user data types?** No. Google defines collection as transmitting data off the device; Picmorrow has no Internet permission, backend, analytics, ads, or tracking SDK.
- **Data shared with third parties?** No.
- **Account creation?** No.
- **Account deletion URL required?** No, because Picmorrow does not provide an app account. Complete the Data deletion questions accurately in Play Console.
- **User deletion control?** Yes. Tasks and app-held photos can be deleted in the app; uninstalling removes remaining app-held data. Gallery exports must be deleted from the gallery separately.
- **Data encrypted in transit?** Not applicable because the app does not transmit user data.

Recheck every transitive SDK before answering. Adding crash reporting, analytics, ads, cloud sync, support chat, or network image loading changes these answers.

## App content declarations

- Ads: **No** with the current build.
- App access/sign-in: no credentials required.
- Target audience: choose the actual intended age groups. If children are included, review the Families policy before release.
- Complete the content-rating questionnaire.
- Declare camera and notification usage consistently with the store description and privacy policy.
- Camera and notifications are runtime permissions, but they are not the broad storage, SMS, call-log, or background-location permissions that normally trigger specialized declaration forms.

## Policy maintenance

- Update both the in-app policy and hosted policy before releasing changed data practices.
- Update the effective date when policy content changes.
- Keep the support pathway active.
- Test policy and support links from a release build.
- Keep Android cloud backup disabled while claiming app-held data is local-only.

## Official references

- [Google Play User Data policy](https://support.google.com/googleplay/android-developer/answer/10144311)
- [Prepare your app for review](https://support.google.com/googleplay/android-developer/answer/9859455)
- [Provide information for the Data safety section](https://support.google.com/googleplay/android-developer/answer/10787469)
- [Account deletion requirements](https://support.google.com/googleplay/android-developer/answer/13327111)
- [Android permission best practices](https://developer.android.com/training/permissions/usage-notes)
