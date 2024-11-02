package ipca.aulas.retrofitteste.dados.models.ultimasnoticias

data class News (
    val id : String,
    val titulo: String,
    val descricao: String,
    val isImagePortait : Boolean,
    val imageUrl : String
)
