package org.wit.workoutbuilder.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import org.wit.workoutbuilder.R
import org.wit.workoutbuilder.adapters.ExerciseAdapter
import org.wit.workoutbuilder.adapters.ExerciseListener
import org.wit.workoutbuilder.databinding.ActivityExerciseListBinding
import org.wit.workoutbuilder.main.MainApp
import org.wit.workoutbuilder.models.ExerciseModel

class ExerciseListActivity : AppCompatActivity(), ExerciseListener {

    lateinit var app: MainApp
    private lateinit var binding: ActivityExerciseListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExerciseListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)

        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = ExerciseAdapter(app.exercises.findAll(),this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add -> {
                val launcherIntent = Intent(this, ExerciseActivity::class.java)
                startActivityForResult(launcherIntent,0)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onExerciseClick(exercise: ExerciseModel) {
        val launcherIntent = Intent(this, ExerciseActivity::class.java)
        launcherIntent.putExtra("exercise_edit", exercise)
        startActivityForResult(launcherIntent,0)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        binding.recyclerView.adapter?.notifyDataSetChanged()
        super.onActivityResult(requestCode, resultCode, data)
    }
}