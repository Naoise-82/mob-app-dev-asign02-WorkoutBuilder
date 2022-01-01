# Mobile App Development - Assignment 2

**Name:** Naoise O'Sullivan  
**Student Number:** 20091403

## Overview

This App is designed to be a companion to a fitness enthusiast, so that they can build workout routines from a library of common exercises that they can create themselves, adding a number of reps per exercise to the workout when they build it. I didn't actually get to complete the Workout Model implementation fully for this assignment, but I showcase the intended functionality in my demo video.

## Main Functionality

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
  
## UML & Class Diagrams

## UML

![UML Diagram][uml]

## Class Diagram

![class-diagram][class-diagram]

## UX & DX

### UX

The user experience in my app follows closely enough to the designs of the Donation/Placemark case studies, with similar nav drawer and data entry methods used, although the filtering method employed on the "All Exercises" fragment are custom built. I did not vary any further from the design choices in the lab case studies.

### DX

I limited my approach to fragments for this assignment, as I found I hadn't the time to refactor to MVVM or MVP, except for the *auth* package, which uses MVVM for the login handling.  

As for persistence, is stuck with a JSON local file, as I didn't get to the stage of implementing a Firebase DB or storage.

## Git Approach

My Git approach was the most basic approach possible; a series of commits to the **main** branch, with a final tagged release. As I do not use Git to any real depth in my personal or work endeavours (teaching 2nd Level Computer Science) currently, I have a very basic interaction with it to date.

## Personal Statement

The work completed in this assignment is entirely my own, and all of the features I implemented were covered in the talks and lab case studies. I realise the simplicity and ultimately unusable nature of the (un)finished product means it will not be a very successful one, which is a disappointment to me. Time has not been my friend in this semester, and the results of that fact are borne out in the app you have before you for grading.

[uml]: ./readme-images/uml.png
[class-diagram]: ./readme-images/class-diagram.png
