package org.wit.workoutbuilder.models

interface WorkoutStore {
    fun findAll(): List<WorkoutModel>
    fun create(workout: WorkoutModel)
    fun update(workout: WorkoutModel)
    fun delete(workout: WorkoutModel)
}