package org.wit.workoutbuilder.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class WorkoutModel( var id: Long = 0,
                         var title: String = "",
                         var description: String = "",
                         var targetBodyAreas: String = "",
                         var email: String = "someone@somewhere.org",
                         //var exercises: ArrayList<ExerciseModel>
) : Parcelable
