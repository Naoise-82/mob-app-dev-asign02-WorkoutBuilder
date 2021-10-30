package org.wit.workoutbuilder.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.CheckBox
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import org.wit.workoutbuilder.R
import org.wit.workoutbuilder.adapters.ExerciseAdapter
import org.wit.workoutbuilder.adapters.ExerciseListener
import org.wit.workoutbuilder.databinding.ActivityExerciseListBinding
import org.wit.workoutbuilder.main.MainApp
import org.wit.workoutbuilder.models.ExerciseModel
import timber.log.Timber.i

class ExerciseListActivity : AppCompatActivity(), ExerciseListener {

    private lateinit var app: MainApp
    private lateinit var binding: ActivityExerciseListBinding
    private lateinit var refreshIntentLauncher : ActivityResultLauncher<Intent>

    private lateinit var unfilteredExercises: List<ExerciseModel>
    private lateinit var filteredExercises: MutableList<ExerciseModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExerciseListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.title = "All Exercises"
        setSupportActionBar(binding.toolbar)
        registerRefreshCallback()

        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        loadExercises()

        unfilteredExercises = app.exercises.findAll()
        filteredExercises = mutableListOf()

        registerRefreshCallback()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add -> {
                val launcherIntent = Intent(this, ExerciseActivity::class.java)
                refreshIntentLauncher.launch(launcherIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCheckboxClick(view: View) {
        if (view is CheckBox) {
            val checked: Boolean = view.isChecked

            when (view.id) {
                R.id.checkBox_strength -> {
                    if (checked) {
                        i("Strength Checked")
                        filterExercisesByCategory("Strength")
                    }
                    if (!checked) {
                        i("Strength Unchecked")
                        unFilterExercisesByCategory("Strength")
                    }
                }
                R.id.checkBox_endurance -> {
                    if (checked) {
                        i("Endurance Checked")
                        filterExercisesByCategory("Endurance")
                    }
                    if (!checked) {
                        i("Endurance Unchecked")
                        unFilterExercisesByCategory("Endurance")
                    }
                }
                R.id.checkBox_balance -> {
                    if (checked) {
                        i("Balance Checked")
                        filterExercisesByCategory("Balance")
                    }
                    if (!checked) {
                        i("Balance Unchecked")
                        unFilterExercisesByCategory("Balance")
                    }
                }
                R.id.checkBox_flexibility -> {
                    if (checked) {
                        i("Flexibility Checked")
                        filterExercisesByCategory("Flexibility")
                    }
                    if (!checked) {
                        i("Flexibility unchecked")
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

    override fun onExerciseClick(exercise: ExerciseModel) {
        val launcherIntent = Intent(this, ExerciseActivity::class.java)
        launcherIntent.putExtra("exercise_edit", exercise)
       refreshIntentLauncher.launch(launcherIntent)
    }

    private fun registerRefreshCallback() {
        refreshIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { loadExercises() }
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

    private fun loadExercises() {
        showExercises(app.exercises.findAll())
    }

    private fun loadFilteredExercises() {
        showFilteredExercises(filteredExercises)
    }

    private fun showExercises (exercises: List<ExerciseModel>) {
        binding.recyclerView.adapter = ExerciseAdapter(exercises, this)
    }

    private fun showFilteredExercises (exercises: MutableList<ExerciseModel>) {
        binding.recyclerView.adapter = ExerciseAdapter(exercises.distinct(), this)
    }
}