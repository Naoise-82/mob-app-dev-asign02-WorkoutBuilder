package org.wit.workoutbuilder.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import org.wit.workoutbuilder.databinding.ActivityExerciseBinding
import org.wit.workoutbuilder.models.ExerciseModel
import timber.log.Timber.i

class ExerciseActivity : AppCompatActivity() {
    private lateinit var binding: ActivityExerciseBinding

    var exercise = ExerciseModel()
    val exercises = ArrayList<ExerciseModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        i("Exercise Activity started...")

        binding.btnAdd.setOnClickListener() {
            exercise.title = binding.exerciseTitle.text.toString()
            exercise.description = binding.exerciseDescription.text.toString()
            if (exercise.title.isNotEmpty()) {
                exercises.add(exercise.copy())
                i("add Button Pressed: $exercise")
                for (i in exercises.indices)
                    { i("Exercise[$i]:${this.exercises[i]}") }
            }
            else {
                Snackbar
                    .make(it,"Please Enter a title", Snackbar.LENGTH_LONG)
                    .show()
            }
        }
    }
}