package org.wit.workoutbuilder.models

import android.content.Context
import org.wit.workoutbuilder.helpers.exists
import org.wit.workoutbuilder.helpers.read
import org.wit.workoutbuilder.helpers.write
import timber.log.Timber
import java.util.*


class WorkoutJSONStore(private val context: Context) : WorkoutStore {

    var workouts = mutableListOf<WorkoutModel>()

    init {
        if (exists(context, JSON_FILE)) {
            deserialize()
        }
    }

    override fun findAll(): MutableList<WorkoutModel> {
        logAll()
        return workouts
    }

    override fun create(workout: WorkoutModel) {
        workout.id = generateRandomId()
        workouts.add(workout)
        serialize()
    }


    override fun update(workout: WorkoutModel) {
        val workoutList = findAll() as ArrayList<WorkoutModel>
        val foundWorkout: WorkoutModel? = workoutList.find { p -> p.id == workout.id }
        if (foundWorkout != null) {
            foundWorkout.title = workout.title
            foundWorkout.description = workout.description
            foundWorkout.targetBodyAreas = workout.targetBodyAreas
        }
        serialize()
    }

    override fun delete(workout: WorkoutModel) {
        workouts.remove(workout)
        serialize()
    }

    private fun serialize() {
        val jsonString = gsonBuilder.toJson(workouts, listType)
        write(context, JSON_FILE, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(context, JSON_FILE)
        workouts = gsonBuilder.fromJson(jsonString, listType)
    }

    private fun logAll() {
        workouts.forEach { Timber.i("$it") }
    }
}
