package org.wit.workoutbuilder.main

import android.app.Application
import org.wit.workoutbuilder.models.ExerciseMemStore
import timber.log.Timber
import timber.log.Timber.i

class MainApp : Application() {

    //val exercises = ArrayList<ExerciseModel>()
    val exercises = ExerciseMemStore()

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        i("Workout Builder started")
        //exercises.add(ExerciseModel("Squats", "Standards squats"))
        //exercises.add(ExerciseModel("Push-ups", "Standards push-ups"))
        //exercises.add(ExerciseModel("Diamond Push-ups", "Push-ups with hands close together under the chest"))

    }
}