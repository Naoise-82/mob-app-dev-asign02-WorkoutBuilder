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
                R.id.checkBox_balance -> {
                    if (checked) {
                        i("Balance Checked")
                    }
                }
            }
        }
        return onCheckboxClick(view)
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

    private fun loadExercises() {
        showExercises(app.exercises.findAll())
    }

    private fun showExercises (exercises: List<ExerciseModel>) {
        binding.recyclerView.adapter = ExerciseAdapter(exercises, this)
    }

}