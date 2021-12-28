package org.wit.workoutbuilder.fragments

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import org.wit.workoutbuilder.R
import org.wit.workoutbuilder.adapters.ExerciseAdapter
import org.wit.workoutbuilder.databinding.FragmentExerciseListBinding

import org.wit.workoutbuilder.main.WorkoutBuilderApp
import org.wit.workoutbuilder.models.ExerciseModel


class ExerciseListFragment : Fragment() {

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
        _fragBinding = FragmentExerciseListBinding.inflate(inflater, container, false)
        val root = fragBinding?.root
        activity?.title = getString(R.string.action_exerciseList)

        fragBinding.recyclerView.layoutManager = LinearLayoutManager(activity)
        loadExercises()
        registerRefreshCallback()

        return root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_exercise_list, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item,
            requireView().findNavController()) || super.onOptionsItemSelected(item)
    }

    fun onExerciseClick(exercise: ExerciseModel) {
        val launcherIntent = activity?.intent
        launcherIntent?.putExtra("exercise_edit", exercise)
        refreshIntentLauncher.launch(launcherIntent)
    }

    private fun registerRefreshCallback() {
        refreshIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { loadExercises() }
    }

    private fun loadExercises() {
        showExercises(app.exercises.findAll())
    }

    private fun showExercises (exercises: List<ExerciseModel>) {
        fragBinding.recyclerView.adapter = ExerciseAdapter(exercises, this)
        fragBinding.recyclerView.adapter?.notifyDataSetChanged()
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            ExerciseListFragment().apply {
                arguments = Bundle().apply { }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragBinding = null
    }
}