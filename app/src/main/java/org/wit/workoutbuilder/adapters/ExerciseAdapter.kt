package org.wit.workoutbuilder.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import org.wit.workoutbuilder.databinding.CardExerciseBinding
import org.wit.workoutbuilder.models.ExerciseModel

interface ExerciseListener {
    fun onExerciseClick(exercise: ExerciseModel)
    fun onCheckboxClick(view: View)
}

class ExerciseAdapter constructor(private var exercises: List<ExerciseModel>) :
    RecyclerView.Adapter<ExerciseAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardExerciseBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val exercise = exercises[holder.adapterPosition]
        holder.bind(exercise)
    }

    override fun getItemCount(): Int = exercises.size

    class MainHolder(private val binding : CardExerciseBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(exercise: ExerciseModel) {
            binding.exerciseTitle.text = exercise.title
            binding.category.text = exercise.category
            binding.TargetBodyArea.text = exercise.targetBodyArea
            Picasso.get().load(exercise.image).resize(200,200).into(binding.imageIcon)
        }
    }
}