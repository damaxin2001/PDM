package ipca.pdm.myshoppinglist.models

data class ListItem(
    var docId: String?,
    var name: String?,
    var description: String?) {
    constructor():this(null, null, null){}
}