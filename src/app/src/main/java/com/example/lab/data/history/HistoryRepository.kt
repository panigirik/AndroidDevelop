package com.example.lab.data.history

import com.example.lab.domain.HistoryItem
import com.google.firebase.firestore.FirebaseFirestore

class HistoryRepository {

    private val db = FirebaseFirestore.getInstance()
    private val collection = db.collection("history")

    fun save(item: HistoryItem) {
        collection.add(item)
    }

    fun load(onResult: (List<HistoryItem>) -> Unit) {
        collection
            .orderBy("timestamp")
            .get()
            .addOnSuccessListener { snapshot ->
                onResult(snapshot.toObjects(HistoryItem::class.java))
            }
    }
}
