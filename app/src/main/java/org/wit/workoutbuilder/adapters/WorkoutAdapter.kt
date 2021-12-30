package org.wit.workoutbuilder.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.wit.workoutbuilder.databinding.CardWorkoutBinding
import org.wit.workoutbuilder.fragments.WorkoutListFragment
import org.wit.workoutbuilder.models.ExerciseModel
import org.wit.workoutbuilder.models.WorkoutModel

interface WorkoutListener {
    fun onWorkoutClick(exercise: ExerciseModel)
    fun onCheckboxClick(view: View)
}

class WorkoutAdapter(private var workouts: List<WorkoutModel>,
                     private val listener: WorkoutListFragment
):
    RecyclerView.Adapter<WorkoutAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardWorkoutBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val workout = workouts[holder.adapterPosition]
        holder.bind(workout, listener)
    }

    override fun getItemCount(): Int = workouts.size

    class MainHolder(private val binding : CardWorkoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(workout: WorkoutModel, listener: WorkoutListFragment) {
            binding.root.tag = workout
            binding.workoutTitle.text = workout.title
            binding.workoutDescription.text = workout.description
            binding.workoutTargetBodyAreas.text = workout.targetBodyAreas
            //binding.root.setOnClickListener { listener.onWorkoutClick(workout) }
        }
    }
}