package org.wit.workoutbuilder.main

import android.app.Application
import org.wit.workoutbuilder.models.ExerciseJSONStore
import org.wit.workoutbuilder.models.ExerciseStore
import org.wit.workoutbuilder.models.WorkoutJSONStore
import org.wit.workoutbuilder.models.WorkoutStore
import timber.log.Timber
import timber.log.Timber.i

class WorkoutBuilderApp : Application() {

    lateinit var exercises: ExerciseStore
    lateinit var workouts: WorkoutStore

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        exercises = ExerciseJSONStore(applicationContext)
        workouts = WorkoutJSONStore(applicationContext)
        i("Workout Builder started")
    }
}