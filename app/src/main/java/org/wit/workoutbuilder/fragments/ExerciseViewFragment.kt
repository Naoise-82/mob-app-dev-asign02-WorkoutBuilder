package org.wit.workoutbuilder.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.wit.workoutbuilder.R

class ExerciseViewFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_exercise_view, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            ExerciseViewFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}