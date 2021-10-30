package org.wit.workoutbuilder.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import org.wit.workoutbuilder.helpers.showImagePicker
import org.wit.workoutbuilder.R
import org.wit.workoutbuilder.databinding.ActivityExerciseBinding
import org.wit.workoutbuilder.main.MainApp
import org.wit.workoutbuilder.models.ExerciseModel
import timber.log.Timber.i

class ExerciseActivity : AppCompatActivity() {
    private lateinit var binding: ActivityExerciseBinding
    private lateinit var imageIntentLauncher : ActivityResultLauncher<Intent>
    var exercise = ExerciseModel()
    lateinit var app : MainApp
    var edit = false

    private fun registerImagePickerCallback() {
        imageIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when(result.resultCode){
                    RESULT_OK -> {
                        if (result.data != null) {
                            i("Got Result ${result.data!!.data}")
                            exercise.image = result.data!!.data!!
                            Picasso.get()
                                .load(exercise.image)
                                .into(binding.exerciseImage)
                            binding.chooseImage.setText(R.string.change_exercise_image)
                        } // end of if
                    }
                    RESULT_CANCELED -> { } else -> { }
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(binding.root)
        registerImagePickerCallback()

        binding.toolbarAdd.title = "Add Exercise"
        setSupportActionBar(binding.toolbarAdd)

        if (intent.hasExtra("exercise_edit")) {
            edit = true
            binding.toolbarAdd.title = "Edit Exercise"
            exercise = intent.extras?.getParcelable("exercise_edit")!!
            binding.exerciseTitle.setText(exercise.title)
            binding.exerciseDescription.setText(exercise.description)

            when (exercise.category) {
                "Strength" -> {
                    binding.exerciseCategory.check(R.id.Strength)
                }
                "Endurance" -> {
                    binding.exerciseCategory.check(R.id.Endurance)
                }
                "Balance" -> {
                    binding.exerciseCategory.check(R.id.Balance)
                }
                "Flexibility" -> {
                    binding.exerciseCategory.check(R.id.Flexibility)
                }
            }

            when (exercise.targetBodyArea) {
                "Upper Body" -> {
                    binding.targetBodyArea.check(R.id.upperBody)
                }
                "Lower Body" -> {
                    binding.targetBodyArea.check(R.id.lowerBody)
                }
                "Core/Abs" -> {
                    binding.targetBodyArea.check(R.id.coreAbs)
                }
            }


            binding.btnAdd.setText(R.string.save_exercise)
            Picasso.get()
                .load(exercise.image)
                .into(binding.exerciseImage)
            if (exercise.image != Uri.EMPTY) {
                binding.chooseImage.setText(R.string.change_exercise_image)
            }
        }

        app = application as MainApp
        i("Exercise Activity started...")

        binding.btnAdd.setOnClickListener() {

            exercise.title = binding.exerciseTitle.text.toString()
            exercise.description = binding.exerciseDescription.text.toString()

            when (binding.exerciseCategory.checkedRadioButtonId) {
                R.id.Strength -> {
                    exercise.category = "Strength"
                }
                R.id.Endurance -> {
                    exercise.category = "Endurance"
                }
                R.id.Balance -> {
                    exercise.category = "Balance"
                }
                R.id.Flexibility -> {
                    exercise.category = "Flexibility"
                }
            }

            when (binding.targetBodyArea.checkedRadioButtonId) {
                R.id.upperBody -> {
                    exercise.targetBodyArea = "Upper Body"
                }
                R.id.lowerBody -> {
                    exercise.targetBodyArea = "Lower Body"
                }
                R.id.coreAbs -> {
                    exercise.targetBodyArea = "Core/Abs"
                }
            }

            if (exercise.title.isEmpty()) {
                Snackbar.make(it,R.string.enter_exercise_title, Snackbar.LENGTH_LONG)
                    .show()
            } else {
                if (edit) {
                    app.exercises.update(exercise.copy())
                } else {
                    app.exercises.create(exercise.copy())
                }
            }
            setResult(RESULT_OK)
            finish()
        }

        binding.chooseImage.setOnClickListener {
            showImagePicker(imageIntentLauncher)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_exercise, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_cancel -> {
                finish()
            }
            R.id.item_delete -> {
                app.exercises.delete(exercise)
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

}