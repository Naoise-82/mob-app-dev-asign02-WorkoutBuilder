package org.wit.workoutbuilder.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import org.wit.workoutbuilder.R
import org.wit.workoutbuilder.adapters.WorkoutAdapter
import org.wit.workoutbuilder.adapters.WorkoutListener
import org.wit.workoutbuilder.databinding.FragmentExerciseListBinding
import org.wit.workoutbuilder.main.WorkoutBuilderApp
import org.wit.workoutbuilder.models.ExerciseModel
import org.wit.workoutbuilder.models.WorkoutModel

class WorkoutListFragment : Fragment(), WorkoutListener {

    lateinit var app: WorkoutBuilderApp
    private var _fragBinding: FragmentExerciseListBinding? = null
    private val fragBinding get() = _fragBinding!!
    private lateinit var refreshIntentLauncher : ActivityResultLauncher<Intent>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = activity?.application as WorkoutBuilderApp
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = fragBinding.root
        activity?.title = getString(R.string.action_workoutList)

        fragBinding.recyclerView.layoutManager = LinearLayoutManager(activity)
        loadWorkouts()
        return root
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            WorkoutListFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }

    override fun onWorkoutClick(exercise: ExerciseModel) {
        TODO("Not yet implemented")
    }

    override fun onCheckboxClick(view: View) {
        TODO("Not yet implemented")
    }

    private fun loadWorkouts() {
        showWorkouts(app.workouts.findAll())
    }

    private fun showWorkouts (workouts: List<WorkoutModel>) {
        fragBinding.recyclerView.adapter = WorkoutAdapter(workouts as ArrayList<WorkoutModel>, this)
        fragBinding.recyclerView.adapter?.notifyDataSetChanged()
    }
}