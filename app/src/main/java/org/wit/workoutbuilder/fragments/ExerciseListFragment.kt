package org.wit.workoutbuilder.fragments


import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.CheckBox
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.wit.workoutbuilder.R
import org.wit.workoutbuilder.adapters.ExerciseAdapter
import org.wit.workoutbuilder.adapters.ExerciseListener
import org.wit.workoutbuilder.databinding.FragmentExerciseListBinding
import org.wit.workoutbuilder.main.WorkoutBuilderApp
import org.wit.workoutbuilder.models.ExerciseModel
import org.wit.workoutbuilder.models.ExerciseStore
import org.wit.workoutbuilder.utils.SwipeToDeleteCallback
import timber.log.Timber


class ExerciseListFragment : Fragment(), ExerciseListener {

    lateinit var app: WorkoutBuilderApp
    private var _fragBinding: FragmentExerciseListBinding? = null
    private val fragBinding get() = _fragBinding!!
    private lateinit var refreshIntentLauncher : ActivityResultLauncher<Intent>

    private lateinit var unfilteredExercises: List<ExerciseModel>
    private lateinit var filteredExercises: MutableList<ExerciseModel>

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
        val root = fragBinding.root
        activity?.title = getString(R.string.action_exerciseList)

        fragBinding.recyclerView.layoutManager = LinearLayoutManager(activity)
        loadExercises()
        registerRefreshCallback()


        val swipeDeleteHandler = object : SwipeToDeleteCallback(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val adapter = fragBinding.recyclerView.adapter as ExerciseAdapter
                adapter.removeAt(viewHolder.adapterPosition)
            }
        }

        val itemTouchDeleteHelper = ItemTouchHelper(swipeDeleteHandler)
        itemTouchDeleteHelper.attachToRecyclerView(fragBinding.recyclerView)

        // not working as planned, incorrect location or call/listener setup used
        view?.let { onCheckboxClick(it) }

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

    override fun onCheckboxClick(view: View) {
        if (view is CheckBox) {
            val checked: Boolean = view.isChecked

            when (view.id) {
                R.id.checkBox_strength -> {
                    if (checked) {
                        Timber.i("Strength Checked")
                        filterExercisesByCategory("Strength")
                    }
                    if (!checked) {
                        Timber.i("Strength Unchecked")
                        unFilterExercisesByCategory("Strength")
                    }
                }
                R.id.checkBox_endurance -> {
                    if (checked) {
                        Timber.i("Endurance Checked")
                        filterExercisesByCategory("Endurance")
                    }
                    if (!checked) {
                        Timber.i("Endurance Unchecked")
                        unFilterExercisesByCategory("Endurance")
                    }
                }
                R.id.checkBox_balance -> {
                    if (checked) {
                        Timber.i("Balance Checked")
                        filterExercisesByCategory("Balance")
                    }
                    if (!checked) {
                        Timber.i("Balance Unchecked")
                        unFilterExercisesByCategory("Balance")
                    }
                }
                R.id.checkBox_flexibility -> {
                    if (checked) {
                        Timber.i("Flexibility Checked")
                        filterExercisesByCategory("Flexibility")
                    }
                    if (!checked) {
                        Timber.i("Flexibility unchecked")
                        unFilterExercisesByCategory("Flexibility")
                    }
                }
                R.id.checkBox_upper_body -> {
                    if (checked) {
                        filterExercisesByTargetArea("Upper Body")
                    }
                    if (!checked) {
                        unFilterExercisesByTargetArea("Upper Body")
                    }
                }
                R.id.checkBox_lower_body -> {
                    if (checked) {
                        filterExercisesByTargetArea("Lower Body")
                    }
                    if (!checked) {
                        unFilterExercisesByTargetArea("Lower Body")
                    }
                }
                R.id.checkBox_core_abs -> {
                    if (checked) {
                        filterExercisesByTargetArea("Core/Abs")
                    }
                    if (!checked) {
                        unFilterExercisesByTargetArea("Core/Abs")
                    }
                }
                R.id.checkBox_whole_body -> {
                    if (checked) {
                        filterExercisesByTargetArea("Whole Body")
                    }
                    if (!checked) {
                        unFilterExercisesByTargetArea("Whole Body")
                    }
                }
            }
        }
    }

    private fun filterExercisesByCategory (value: String) {
        val filter = unfilteredExercises.filter { it.category == value }

        filteredExercises.addAll(filter)

        loadFilteredExercises()
    }

    private fun unFilterExercisesByCategory (value: String) {
        val filter = unfilteredExercises.filter { it.category == value }

        filteredExercises.removeAll(filter)

        if (filteredExercises.isEmpty()) {
            loadExercises()
        } else loadFilteredExercises()
    }

    private fun filterExercisesByTargetArea (value: String) {
        val filter = unfilteredExercises.filter { it.targetBodyArea == value }

        filteredExercises.addAll(filter)

        loadFilteredExercises()
    }

    private fun unFilterExercisesByTargetArea (value: String) {
        val filter = unfilteredExercises.filter { it.targetBodyArea == value }

        filteredExercises.removeAll(filter)

        if (filteredExercises.isEmpty()) {
            loadExercises()
        } else loadFilteredExercises()
    }

    override fun onExerciseClick(exercise: ExerciseModel) {
        activity?.intent?.putExtra("exercise_edit", exercise)
        val action = ExerciseListFragmentDirections.actionExerciseListFragmentToExerciseFragment(exercise.id)
        findNavController().navigate(action)
    }

    private fun registerRefreshCallback() {
        refreshIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { loadExercises() }
    }

    private fun loadExercises() {
        showExercises(app.exercises.findAll())
    }

    private fun loadFilteredExercises() {
        showFilteredExercises(filteredExercises)
    }

    private fun showExercises (exercises: List<ExerciseModel>) {
        fragBinding.recyclerView.adapter = ExerciseAdapter(exercises as ArrayList<ExerciseModel>, this)
        fragBinding.recyclerView.adapter?.notifyDataSetChanged()
    }

    private fun showFilteredExercises (exercises: MutableList<ExerciseModel>) {
        fragBinding.recyclerView.adapter = ExerciseAdapter(exercises.distinct() as ArrayList<ExerciseModel>, this)
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