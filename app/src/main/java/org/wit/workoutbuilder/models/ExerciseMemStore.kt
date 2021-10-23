package org.wit.workoutbuilder.models

import timber.log.Timber.i

var lastId = 0L

internal fun getId(): Long {
    return lastId++
}

class ExerciseMemStore : ExerciseStore {

    val exercises = ArrayList<ExerciseModel>()

    override fun findAll(): List<ExerciseModel> {
        return exercises
    }

    override fun create(exercise: ExerciseModel) {
        exercise.id = getId()
        exercises.add(exercise)
        logAll()
    }

    override fun update(exercise: ExerciseModel) {
        var foundExercise: ExerciseModel? = exercises.find { e -> e.id == exercise.id }
        if (foundExercise != null) {
            foundExercise.title = exercise.title
            foundExercise.description = exercise.description
            foundExercise.image = exercise.image
            logAll()
        }
    }

    fun logAll() {
        exercises.forEach{ i("$it") }
    }
}