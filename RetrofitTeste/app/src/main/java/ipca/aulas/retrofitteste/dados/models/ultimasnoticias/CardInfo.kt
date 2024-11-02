package ipca.aulas.retrofitteste.dados.models.ultimasnoticias

data class CardInfo(
    val css: List<String>,
    val isHeadlineBlock: Boolean,
    val maxLinks: Int,
    val mediaCss: String,
    val showLead: Boolean,
    val showMedia: Boolean
)