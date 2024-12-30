package ipca.aulas.myshoppinglist.lists

class ListItem(
    var name: String?,
    var description: String?
){
    companion object{
        fun fromMap(map: Map<String, Any?>): ListItem{
            return {
                map.get()
            }
        }
    }
}