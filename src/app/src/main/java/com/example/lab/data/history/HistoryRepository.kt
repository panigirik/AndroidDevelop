package com.example.lab.data.history

import com.example.lab.domain.HistoryItem
import com.google.firebase.firestore.FirebaseFirestore

open class HistoryRepository {

    private val db = FirebaseFirestore.getInstance()

    private val collection = db
        .collection("111")          // root collection
        .document("calculator")     // ОБЯЗАТЕЛЬНО документ
        .collection("111")          // subcollection

    private val localCache = mutableListOf<HistoryItem>()

    open fun save(item: HistoryItem) {
        localCache.add(0, item)
        collection.add(item)
            .addOnFailureListener { e ->
                e.printStackTrace()
            }
    }

    fun loadFromFirebase(onResult: (List<HistoryItem>) -> Unit) {
        collection
            .orderBy("timestamp", com.google.firebase.firestore.Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { snapshot ->
                val items = snapshot.toObjects(HistoryItem::class.java)
                localCache.clear()
                localCache.addAll(items)
                onResult(localCache)
            }
            .addOnFailureListener {
                it.printStackTrace()
            }
    }
}
