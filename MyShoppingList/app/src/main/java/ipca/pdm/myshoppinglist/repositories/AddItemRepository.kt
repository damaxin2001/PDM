package ipca.pdm.myshoppinglist.repositories

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import ipca.pdm.myshoppinglist.models.ListItem

object AddItemRepository {

    private val db: FirebaseFirestore = Firebase.firestore
    private const val COLLECTION_NAME = "shoppingLists"

    fun add(listItem: ListItem, onSuccess: () -> Unit) {
        val listItemMap = hashMapOf(
            "name" to listItem.name,
            "description" to listItem.description,
            "userId" to listItem.userId
        )

        db.collection(COLLECTION_NAME)
            .add(listItemMap)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener { e ->

            }
    }
}