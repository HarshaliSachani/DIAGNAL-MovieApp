# MovieWave

Content listing app with responsive UI and search functionality.

![MovieWave](app/src/main/res/mipmap-mdpi/ic_launcher.png)

## Structure

The App comprises of module [APP](app) Main Android App.

This Project develop in kotlin language. 

Use Dependency Injection `koin` through Application.

Create some custom kotlin extension when needed.

Implemented `MVVM` design pattern.

Use `Gson` and `Glide` third party library.

Distribute app via Firebase App Distribution

## Features

* Responsive UI design with portrait & Landscape orientations.
* Maintain various device screen resolution for UI.
* Load data form the local JSON file.
* New data will be fetch pagination wise while scrolling with lazy loading concept.
* Search data on type words with maintain buffer call.
* For long text added marquee effect to maintain UI consistency.
* Showing 'placeholder' image when relevant image not found.
* Highlight searched text in search listing screen.

## Compiling & Running

* Import project in `Android Studio Giraffe | 2022.3.1 Patch 1` and compile.
* Run [App](app) for main app.
* Main App contains two flavors `(production, dev)` and two build types `(debug, release)`.

### Distribute App to tester

* Implemented Firebase App Distribution for distribute app to testers
* Update release note inside `releasenotes/version-1.0(1)` folder with `version` and specify details in `firebaseAppDistribution` block in `buildTypes-release` variant.
