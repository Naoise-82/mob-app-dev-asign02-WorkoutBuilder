package org.wit.workoutbuilder.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.wit.workoutbuilder.R
import org.wit.workoutbuilder.databinding.FragmentExerciseBinding
import org.wit.workoutbuilder.main.WorkoutBuilderApp


class ExerciseFragment : Fragment() {

    lateinit var app: WorkoutBuilderApp
    private var _fragBinding: FragmentExerciseBinding? = null
    private val fragBinding get() = _fragBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = activity?.application as WorkoutBuilderApp
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _fragBinding = FragmentExerciseBinding.inflate(inflater, container, false)
        val root = fragBinding?.root
        activity?.title = getString(R.string.action_exercise)

        return root;
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