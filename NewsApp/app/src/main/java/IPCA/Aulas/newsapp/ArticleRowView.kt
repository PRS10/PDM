package IPCA.Aulas.newsapp

import IPCA.Aulas.newsapp.models.Article
import IPCA.Aulas.newsapp.ui.theme.NewsAppTheme
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ArticleRowView(article: Article){
    Row (
        modifier = Modifier
            .fillMaxWidth(),
horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    )
    {
        Image(
            modifier = Modifier
                .width(120.dp)
                .height(120.dp)
            ,
            painter = painterResource(id = R.drawable.baseline_image_24),
            contentDescription = "Article Image")
        Column () {
            Text(text = article.title ?: "")
            Text(text = article.description ?: "")
            Text(text = article.publishedAt ?: "")
        }


    }
}


@Preview (showBackground = true, showSystemUi = true)
@Composable
fun PrevArticleRowView(){
    NewsAppTheme {
        val article = Article(
            "Title 1",
            "Description 1",
            "url 1",
            "urlToImage 1",
            "publishedAt 1",
            "content 1"
        )
        ArticleRowView(article)
    }

}