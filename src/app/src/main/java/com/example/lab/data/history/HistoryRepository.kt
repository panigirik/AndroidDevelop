package com.example.lab.data.history

import com.example.lab.domain.HistoryItem
import com.google.firebase.firestore.FirebaseFirestore

open class HistoryRepository {

    private val db = FirebaseFirestore.getInstance()
    private val collection = db.collection("111")

    // Локальная память
    private val localCache = mutableListOf<HistoryItem>()

    open fun save(item: HistoryItem) {
        // Сохраняем локально
        localCache.add(0, item)

        // Сохраняем в Firebase
        collection.add(item)
    }

    fun getAllLocal(): List<HistoryItem> = localCache

    fun loadFromFirebase(onResult: (List<HistoryItem>) -> Unit) {
        collection.orderBy("timestamp", com.google.firebase.firestore.Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { snapshot ->
                val items = snapshot.toObjects(HistoryItem::class.java)
                localCache.clear()
                localCache.addAll(items)
                onResult(localCache)
            }
    }
}
