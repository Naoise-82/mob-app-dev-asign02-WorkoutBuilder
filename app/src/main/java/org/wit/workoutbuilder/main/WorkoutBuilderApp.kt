package org.wit.workoutbuilder.main

import android.app.Application
import org.wit.workoutbuilder.models.*
import timber.log.Timber
import timber.log.Timber.i

class WorkoutBuilderApp : Application() {

    lateinit var exercises: ExerciseStore
    //lateinit var workouts: WorkoutStore

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        exercises = ExerciseJSONStore(applicationContext)
        //exercises = ExerciseMemStore()
        //workouts = WorkoutMemStore()
        i("Workout Builder started")
    }
}