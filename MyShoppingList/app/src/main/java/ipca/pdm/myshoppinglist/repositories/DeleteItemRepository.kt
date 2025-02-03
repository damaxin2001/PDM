package ipca.pdm.myshoppinglist.repositories

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

object DeleteItemRepository {

    private val db: FirebaseFirestore = Firebase.firestore
    private const val COLLECTION_NAME = "shoppingLists"

    fun delete(listItemId: String, onSuccess: () -> Unit, onError: (String) -> Unit) {

        db.collection(COLLECTION_NAME)
            .document(listItemId)
            .delete()
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener { e ->
                onError(e.message ?: "Erro ao excluir a lista")
            }
    }
}