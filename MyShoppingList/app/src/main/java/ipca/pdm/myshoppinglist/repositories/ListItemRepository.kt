package ipca.pdm.myshoppinglist.repositories

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import ipca.pdm.myshoppinglist.models.ListItem

object ListItemRepository {

    private val db: FirebaseFirestore = Firebase.firestore
    private const val COLLECTION_NAME = "shoppingLists"

    fun getAll(userId: String, onSuccess: (List<ListItem>) -> Unit) {
        db.collection(COLLECTION_NAME)
            .whereEqualTo("userId", userId)
            .get()
            .addOnSuccessListener { result ->
                val listItems = result.toObjects(ListItem::class.java)
                onSuccess(listItems)
            }
            .addOnFailureListener { e ->
            }
    }
    fun getListById(listId: String, onSuccess: (ListItem) -> Unit, onError: (String) -> Unit) {
        db.collection(COLLECTION_NAME)
            .document(listId)
            .get()
            .addOnSuccessListener { document ->
                val listItem = document.toObject(ListItem::class.java)
                if (listItem != null) {
                    onSuccess(listItem)
                } else {
                    onError("Lista não encontrada")
                }
            }
            .addOnFailureListener { e ->
                onError(e.message ?: "Erro ao carregar a lista")
            }
    }
}