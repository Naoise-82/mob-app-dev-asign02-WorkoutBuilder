package org.wit.workoutbuilder.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import org.wit.workoutbuilder.R
import org.wit.workoutbuilder.databinding.FragmentWorkoutBinding
import org.wit.workoutbuilder.main.WorkoutBuilderApp
import org.wit.workoutbuilder.models.WorkoutModel


class WorkoutFragment : Fragment() {

    lateinit var app: WorkoutBuilderApp
    private var _fragBinding: FragmentWorkoutBinding? = null
    private val fragBinding get() = _fragBinding
    var workout = WorkoutModel()
    var edit = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = activity?.application as WorkoutBuilderApp
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _fragBinding = FragmentWorkoutBinding.inflate(inflater, container, false)
        val root = fragBinding?.root
        activity?.title = getString(R.string.action_workout)

        if (activity?.intent?.hasExtra("workout_edit") == true) {
            edit = true
            workout = activity!!.intent.extras?.getParcelable("workout_edit")!!
            fragBinding?.workoutTitle?.setText(workout.title)
            fragBinding?.workoutDescription?.setText(workout.description)

            fragBinding?.btnAdd?.setText(R.string.save_workout)
        }

        fragBinding?.let { setButtonListener(it) }

        return root
    }

    private fun setButtonListener(layout: FragmentWorkoutBinding) {
        layout.btnAdd.setOnClickListener {
            workout.title = fragBinding?.workoutTitle?.text.toString()
            workout.description = fragBinding?.workoutDescription?.text.toString()
            onCheckboxClicked(it)

            if (workout.title.isEmpty()) {
                Snackbar.make(it,R.string.enter_exercise_title, Snackbar.LENGTH_LONG).show()
            }
            else {
                if (workout.targetBodyAreas.isEmpty()) {
                    Snackbar.make(it,R.string.select_target_area, Snackbar.LENGTH_LONG).show()
                } else {
                    if (edit) {
                        app.workouts.update(workout.copy())
                    } else {
                        app.workouts.create(workout.copy())
                    }
                }
            }
            activity?.setResult(AppCompatActivity.RESULT_OK)

            activity?.finish()
        }

    }

    fun onCheckboxClicked(view: View) {
        if (view is CheckBox) {
            val checked: Boolean = view.isChecked

            when (view.id) {
                R.id.checkBox_upper_body -> {
                    if (checked) {
                        if (workout.targetBodyAreas.isEmpty()) {
                            workout.targetBodyAreas = "Upper"
                        } else {
                            workout.targetBodyAreas += ", Upper"
                        }
                    }
                }
                R.id.checkBox_lower_body -> {
                    if (checked) {
                        if (workout.targetBodyAreas.isEmpty()) {
                            workout.targetBodyAreas = "Lower"
                        } else {
                            workout.targetBodyAreas += ", Lower"
                        }
                    }
                }
                R.id.checkBox_core_abs -> {
                    if (checked) {
                        if (workout.targetBodyAreas.isEmpty()) {
                            workout.targetBodyAreas = "Core/Abs"
                        } else {
                            workout.targetBodyAreas += ", Core/Abs"
                        }
                    }
                }
                R.id.checkBox_whole_body -> {
                    if (checked) {
                        if (workout.targetBodyAreas.isEmpty()) {
                            workout.targetBodyAreas = "Whole Body"
                        } else {
                            workout.targetBodyAreas += ", Whole Body"
                        }
                    }
                }
            }
        }
    }

    companion object {
        fun newInstance(param1: String, param2: String) =
            WorkoutFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}