package org.wit.workoutbuilder.models

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ExerciseModel(var id: Long = 0,
                         var title: String = "",
                         var description: String = "",
                         var image: Uri = Uri.EMPTY,
                         var category: String = "",
                         var targetBodyArea: String = "") : Parcelable
