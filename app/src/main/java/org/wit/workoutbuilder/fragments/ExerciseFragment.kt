package org.wit.workoutbuilder.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import org.wit.workoutbuilder.R
import org.wit.workoutbuilder.auth.LoggedInViewModel
import org.wit.workoutbuilder.databinding.FragmentExerciseBinding
import org.wit.workoutbuilder.helpers.showImagePicker
import org.wit.workoutbuilder.main.WorkoutBuilderApp
import org.wit.workoutbuilder.models.ExerciseModel
import timber.log.Timber.i


class ExerciseFragment : Fragment() {

    lateinit var app: WorkoutBuilderApp
    private var _fragBinding: FragmentExerciseBinding? = null
    private val fragBinding get() = _fragBinding
    private lateinit var imageIntentLauncher : ActivityResultLauncher<Intent>
    var exercise = ExerciseModel()
    var edit = false
    private val loggedInViewModel : LoggedInViewModel by activityViewModels()

    private fun registerImagePickerCallback() {
        imageIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when(result.resultCode){
                    AppCompatActivity.RESULT_OK -> {
                        if (result.data != null) {
                            i("Got Result ${result.data!!.data}")
                            exercise.image = result.data!!.data!!
                            Picasso.get()
                                .load(exercise.image)
                                .into(fragBinding?.exerciseImage)
                            fragBinding?.chooseImage?.setText(R.string.change_exercise_image)
                        }
                    }
                    AppCompatActivity.RESULT_CANCELED -> { } else -> { }
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = activity?.application as WorkoutBuilderApp
        setHasOptionsMenu(true)
        registerImagePickerCallback()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _fragBinding = FragmentExerciseBinding.inflate(inflater, container, false)
        val root = fragBinding?.root
        activity?.title = getString(R.string.action_exercise)
        registerImagePickerCallback()

        if (activity?.intent?.hasExtra("exercise_edit") == true) {
            edit = true
            exercise = activity!!.intent.extras?.getParcelable("exercise_edit")!!
            fragBinding?.exerciseTitle?.setText(exercise.title)
            fragBinding?.exerciseDescription?.setText(exercise.description)

            when (exercise.category) {
                "Strength" -> {
                    fragBinding?.exerciseCategory?.check(R.id.Strength)
                }
                "Endurance" -> {
                    fragBinding?.exerciseCategory?.check(R.id.Endurance)
                }
                "Balance" -> {
                    fragBinding?.exerciseCategory?.check(R.id.Balance)
                }
                "Flexibility" -> {
                    fragBinding?.exerciseCategory?.check(R.id.Flexibility)
                }
            }

            when (exercise.targetBodyArea) {
                "Upper Body" -> {
                    fragBinding?.targetBodyArea?.check(R.id.upperBody)
                }
                "Lower Body" -> {
                    fragBinding?.targetBodyArea?.check(R.id.lowerBody)
                }
                "Core/Abs" -> {
                    fragBinding?.targetBodyArea?.check(R.id.coreAbs)
                }
            }


            fragBinding?.btnAdd?.setText(R.string.save_exercise)
            Picasso.get()
                .load(exercise.image)
                .into(fragBinding?.exerciseImage)
            if (exercise.image != Uri.EMPTY) {
                fragBinding?.chooseImage?.setText(R.string.change_exercise_image)
            }
        }

        fragBinding?.let { setButtonListener(it) }

        return root;
    }

    private fun setButtonListener(layout: FragmentExerciseBinding) {
        layout.btnAdd.setOnClickListener {
            exercise.title = fragBinding?.exerciseTitle?.text.toString()
            exercise.description = fragBinding?.exerciseDescription?.text.toString()
            exercise.email = loggedInViewModel.liveFirebaseUser.value?.email!!

            // assign the radio buttons to exercise object parameters
            when (fragBinding?.exerciseCategory?.checkedRadioButtonId) {
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

            when (fragBinding?.targetBodyArea?.checkedRadioButtonId) {
                R.id.upperBody -> {
                    exercise.targetBodyArea = "Upper Body"
                }
                R.id.lowerBody -> {
                    exercise.targetBodyArea = "Lower Body"
                }
                R.id.coreAbs -> {
                    exercise.targetBodyArea = "Core/Abs"
                }
                R.id.wholeBody -> {
                    exercise.targetBodyArea = "Whole Body"
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
            activity?.setResult(AppCompatActivity.RESULT_OK)

            activity?.finish()
        }

        fragBinding?.chooseImage?.setOnClickListener {
            showImagePicker(imageIntentLauncher)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_exercise, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item,
            requireView().findNavController()) || super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragBinding = null
    }

    override fun onResume() {
        super.onResume()
    }

    companion object {
        fun newInstance() =
            ExerciseFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}