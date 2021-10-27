package org.wit.workoutbuilder.models

interface ExerciseStore {
    fun findAll(): List<ExerciseModel>
    fun create(exercise: ExerciseModel)
    fun update(exercise: ExerciseModel)
    fun delete(exercise: ExerciseModel)
}