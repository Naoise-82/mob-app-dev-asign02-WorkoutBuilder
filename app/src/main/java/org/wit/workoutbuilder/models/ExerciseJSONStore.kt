package org.wit.workoutbuilder.models

import android.content.Context
import android.net.Uri
import com.google.gson.*
import com.google.gson.reflect.TypeToken
import org.wit.workoutbuilder.helpers.*
import timber.log.Timber
import java.lang.reflect.Type
import java.util.*

const val JSON_FILE = "workoutbuilder.json"
val gsonBuilder: Gson = GsonBuilder().setPrettyPrinting()
    .registerTypeAdapter(Uri::class.java, UriParser())
    .create()
val listType: Type = object : TypeToken<ArrayList<ExerciseModel>>() {}.type

fun generateRandomId(): Long {
    return Random().nextLong()
}

class ExerciseJSONStore(private val context: Context) : ExerciseStore {

    var exercises = mutableListOf<ExerciseModel>()

    init {
        if (exists(context, JSON_FILE)) {
            deserialize()
        }
    }

    override fun findAll(): MutableList<ExerciseModel> {
        logAll()
        return exercises
    }

    override fun create(exercise: ExerciseModel) {
        exercise.id = generateRandomId()
        exercises.add(exercise)
        serialize()
    }


    override fun update(exercise: ExerciseModel) {
        val exerciseList = findAll() as ArrayList<ExerciseModel>
        val foundExercise: ExerciseModel? = exerciseList.find { p -> p.id == exercise.id }
        if (foundExercise != null) {
            foundExercise.title = exercise.title
            foundExercise.description = exercise.description
            foundExercise.image = exercise.image
            foundExercise.category = exercise.category
            foundExercise.targetBodyArea = exercise.targetBodyArea
        }
        serialize()
    }

    override fun delete(exercise: ExerciseModel) {
        exercises.remove(exercise)
        serialize()
    }

    private fun serialize() {
        val jsonString = gsonBuilder.toJson(exercises, listType)
        write(context, JSON_FILE, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(context, JSON_FILE)
        exercises = gsonBuilder.fromJson(jsonString, listType)
    }

    private fun logAll() {
        exercises.forEach { Timber.i("$it") }
    }
}

class UriParser : JsonDeserializer<Uri>,JsonSerializer<Uri> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Uri {
        return Uri.parse(json?.asString)
    }

    override fun serialize(
        src: Uri?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        return JsonPrimitive(src.toString())
    }
}