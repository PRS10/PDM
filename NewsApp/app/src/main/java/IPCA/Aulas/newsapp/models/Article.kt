package IPCA.Aulas.newsapp.models

import org.json.JSONObject

data  class Article(

    var title: String,
    var description: String? = null,
    var url: String? = null,
    var urlToImage: String? = null,
    var publishedAt: String? = null,
    var content: String? = null


){
    companion object {
        fun fromJson(articleObject: JSONObject): Article{
            val title = articleObject.getString("title")
            val description = articleObject.getString("description")
            val url = articleObject.getString("url")
            val urlToImage = articleObject.getString("urlToImage")
            val publishedAt = articleObject.getString("publishedAt")
            val content = articleObject.getString("content")
            val article = Article(title, description, url, urlToImage, publishedAt, content)
            return article
        }
    }
}
