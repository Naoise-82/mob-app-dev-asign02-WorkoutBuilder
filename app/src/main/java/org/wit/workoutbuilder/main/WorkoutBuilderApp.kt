package org.wit.workoutbuilder.main

import android.app.Application
import org.wit.workoutbuilder.models.ExerciseJSONStore
import org.wit.workoutbuilder.models.ExerciseStore
import timber.log.Timber
import timber.log.Timber.i

class WorkoutBuilderApp : Application() {

    lateinit var exercises: ExerciseStore

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        exercises = ExerciseJSONStore(applicationContext)
        i("Workout Builder started")
    }
}