# Mobile App Development Assignment 2

**Name:** Naoise O'Sullivan  
**Student Number:** 20091403

## Overview/Functionality

This App is designed to be a companion to a fitness enthusiast, so that they can build workout routines from a library of common exercises that they can create themselves, adding a number of reps per exercise to the workout when they build it.

### Main Functionality

**Note:** The following feature list includes features that were not fully implemented, and they are in *italics*.

- User login/signup via Firebase Auth
- Navigation via Nav Drawer
- Create/Edit individual Exercises featuring:
    - Title
    - Description
    - Category
    - Target Body Area
- Swipe-to-delete on Exercise List
- *Create/Edit Workouts featuring:*
    - *Title*
    - *Description*
    - *Target Body Areas(s)*
    - *List of exercises in the workout (including reps per exercise)*
    - *Exercise View from Workout (non-editable, with reps included)*

## UX & DX

### UX

The user experience in my app follows closely enough to the designs of the Donation/Placemark case studies, with similar nav drawer and data entry methods used, although the filtering method employed on the "All Exercises" fragment are custom built. I did not vary any firther from the design choices in the lab case studies.

### DX

I limited my approach to fragments for this assignment, as I found I hadn't the time to refactor to MVVM or MVP, except for the *auth* package, which uses MVVM for the login handling.  

As for persistence, is stuck with a JSON local file, as I did get to implement a Firebase DB or storage. 


[model]: 
