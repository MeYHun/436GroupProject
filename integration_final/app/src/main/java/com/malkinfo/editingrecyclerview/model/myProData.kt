package com.malkinfo.editingrecyclerview.model

import com.google.firebase.database.DataSnapshot

data class myProData(
    var proName: String,
    var proEmail: String,
    var proDescription: String,
    var proReview: String,
    var proRating: Float
)