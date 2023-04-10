![version](https://img.shields.io/badge/Gradle-8.0.2%2B-blue)
![version](https://img.shields.io/badge/version-0.0.1-green)
![license](https://img.shields.io/badge/license-MIT-blue)
<img src="https://upload.wikimedia.org/wikipedia/commons/thumb/0/06/Kotlin_Icon.svg/240px-Kotlin_Icon.svg.png" alt= “Kotlin” height="20">

# HannApp

HannApp is an Android application for following a specific diet plan.

The application is motivated by a friends daughter named Hanna, whose metabolism needs to receive a
precise amount of nutrients throughout the day.
It helps to track meals, determine the exact nutritional values and calculate any necessary
nutritional supplements.

Please note that the application is currently in development. Therefore some functions and UI
components are not fully implemented yet. Moreover the captured application data are still bound to
a single device.

# Table of Contents

1. [Prerequisity](#prerequisity)
2. [Installation](#installation)
3. [Getting Started](#getting-started)
4. [Features](#features)
5. [Run Tests](#run-tests)
6. [Credits](#credits)
7. [Licence](#licence)
8. [Contact](#contact)

# Prerequisity

Make sure you have installed the current [Gradle](https://gradle.org/install/) version

```
$ gradle -v

------------------------------------------------------------
Gradle 8.0.2
------------------------------------------------------------
```

# Installation

Use the latest [Gradle](https://gradle.org) version to build the application

`./gradlew build`

# Getting Started

![search](docs/gettingStarted.gif)

# Features

## Food Storage

### Search and store food

Search for food with a query using the search bar. Suggested food can be selected and be stored in
the local database.

### Enter food manually

Food can be entered manually by filling in all nutriment fields completely. Empty fields, *null*
and *non-digits* are not valid and therefore not possible to add.

### Modify or delete locally stored food

By selecting the *edit* icon, a locally stored food can be selected and modified.
A long press on a food deletes it from the local storage.

## Track Meals and Nutriments

### Capture a meal

The components of a meal can be selected and logged. Based on the selection and amount, the total
amount of nutriments will be calculated. (not implemented yet)

### Nutrition Limit Indication

The application indicates visually if the captured amount of nutriments is getting close to the
prescribed diet limits or exceeding.

# Run Tests

`./gradlew test`

# Credits

HannApp is fetching nutriments from a open database
named [Open Food Facts](https://world.openfoodfacts.org/), whichs is a non-profit project developed
by thousands of volunteers. Thanks to their enthusiasm it is possible to search and to add food in
such a handy way.

# Licence

This project is licensed under the terms of the MIT license.

# Contact

[Stefan Elsner](https://github.com/StefanElsnerDev/)