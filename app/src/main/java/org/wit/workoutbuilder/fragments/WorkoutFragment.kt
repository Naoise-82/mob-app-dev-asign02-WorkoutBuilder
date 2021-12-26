package org.wit.workoutbuilder.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import org.wit.workoutbuilder.R
import org.wit.workoutbuilder.databinding.FragmentExerciseBinding
import org.wit.workoutbuilder.databinding.FragmentWorkoutBinding
import org.wit.workoutbuilder.main.WorkoutBuilderApp
import org.wit.workoutbuilder.models.ExerciseModel
import org.wit.workoutbuilder.models.WorkoutModel


class WorkoutFragment : Fragment() {

    lateinit var app: WorkoutBuilderApp
    private var _fragBinding: FragmentWorkoutBinding? = null
    private val fragBinding get() = _fragBinding
    var workout = WorkoutModel()

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

        return root
    }

    companion object {
        fun newInstance(param1: String, param2: String) =
            WorkoutFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}