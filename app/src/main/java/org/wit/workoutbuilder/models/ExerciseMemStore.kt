package org.wit.workoutbuilder.models

import timber.log.Timber.i

class ExerciseMemStore : ExerciseStore {

    val exercises = ArrayList<ExerciseModel>()

    override fun findAll(): List<ExerciseModel> {
        return exercises
    }

    override fun create(exercise: ExerciseModel) {
        exercises.add(exercise)
        logAll()
    }

    fun logAll() {
        exercises.forEach{ i("$it") }
    }
}