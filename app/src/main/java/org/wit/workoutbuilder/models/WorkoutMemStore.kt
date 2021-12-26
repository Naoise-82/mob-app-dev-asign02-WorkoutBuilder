package org.wit.workoutbuilder.models

import timber.log.Timber.i


class WorkoutMemStore : WorkoutStore {

    private val workouts = ArrayList<WorkoutModel>()

    override fun findAll(): List<WorkoutModel> {
        return workouts
    }

    override fun create(workout: WorkoutModel) {
        workout.id = getId()
        workouts.add(workout)
        logAll()
    }

    override fun update(workout: WorkoutModel) {
        var foundWorkout: WorkoutModel? = workouts.find { e -> e.id == workout.id }
        if (foundWorkout != null) {
            foundWorkout.title = workout.title
            foundWorkout.description = workout.description
            logAll()
        }
    }

    override fun delete(workout: WorkoutModel) {
        workouts.remove(workout)
    }

    private fun logAll() {
        workouts.forEach{ i("$it") }
    }
}