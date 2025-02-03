package ipca.pdm.myshoppinglist.models

data class ListItem(
    val id: String = "", // O Firestore gera automaticamente
    val name: String?,
    val description: String?,
    val userId: String?) {
    constructor():this("", null, null, null){}
}