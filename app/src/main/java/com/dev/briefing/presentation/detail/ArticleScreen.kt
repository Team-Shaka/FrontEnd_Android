package com.dev.briefing.presentation.detail

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.dev.briefing.R
import com.dev.briefing.data.NewsDetail
import com.dev.briefing.data.model.Article
import com.dev.briefing.data.model.BriefingDetailResponse
import com.dev.briefing.presentation.theme.GradientEnd
import com.dev.briefing.presentation.theme.GradientStart
import com.dev.briefing.presentation.theme.MainPrimary
import com.dev.briefing.presentation.theme.SubText2
import com.dev.briefing.presentation.theme.White
import com.dev.briefing.util.SharedPreferenceHelper
import java.time.LocalDate

@Composable
fun ArticleDetailScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
    id:Int
) {
    val articleResponse: BriefingDetailResponse = getArticleDetail(id)
    val gradientBrush = Brush.verticalGradient(
        colors = listOf(GradientStart, GradientEnd),
        startY = 0.0f,
        endY = LocalConfiguration.current.screenHeightDp.toFloat()
    )
    Column(
        modifier = modifier
            .fillMaxHeight()
            .background(brush = gradientBrush)
            .padding(horizontal = 30.dp)

    ) {
        DetailHeader(
            onBackClick = onBackClick,
            rank = articleResponse.id
        )
        Spacer(modifier = Modifier.height(34.dp))
        LazyColumn {
            item {
                ArticleDetail(
                    article = articleResponse
                )
                Spacer(modifier = Modifier.height(25.dp))
            }
        }
    }
}

@Composable
fun DetailHeader(
    onBackClick: () -> Unit,
    rank: Int = 0
) {
    var isScrap by remember { mutableStateOf(false) }
    // 이미지 리소스를 불러옵니다.
    val scrap = painterResource(id = R.drawable.scrap_normal)
    val selectScrap = painterResource(id = R.drawable.scrap_selected)
    val newItem =  NewsDetail(1, 1, "잼버리", LocalDate.of(2023, 8, 22), "fdsfd")
    val context = LocalContext.current
    val currentItems = SharedPreferenceHelper.getScrap(context)
    val updatedItems = currentItems + newItem
    // 클릭 이벤트를 처리합니다.
    val image = if (isScrap) selectScrap else scrap
    val contentDescription = if (isScrap) "Unliked" else "Liked"
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 60.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    )
    {
        Image(
            painter = painterResource(
                id = R.drawable.arrow
            ),
            contentDescription = contentDescription, modifier = Modifier
                .clickable(onClick = onBackClick)
        )
        Text(
            text = "Briefing #${rank}",
            style = MaterialTheme.typography.titleMedium.copy(
                color = White,
                fontWeight = FontWeight(400)
            )
        )
        Image(
            //TODO: icon click여부에 따라 asset변경
            painter = image,
            contentDescription = contentDescription,
            modifier = Modifier.clickable(
                onClick = {
                    isScrap = !isScrap
                    SharedPreferenceHelper.saveScrap(context, updatedItems)

                }
            )
        )

    }
}

@Composable
fun ArticleDetail(
    modifier: Modifier = Modifier,
    article:BriefingDetailResponse
) {
    var tmpNewsList: List<Article> = listOf(
        Article(1,"연합뉴스", "잼버리", "test1"),
        Article(2,"연합뉴스", "잼버리", "test1"),
    )
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(color = White, shape = RoundedCornerShape(10.dp))
            .padding(horizontal = 30.dp),
        verticalArrangement = Arrangement.spacedBy(13.dp),

        ) {

        Spacer(modifier = Modifier.height(11.dp))
        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Text(
                //TODO: 날짜 api에서 제공
                text = "23.08.07 Breifing #${article.rank}",
                style = MaterialTheme.typography.headlineLarge.copy(
                    color = SubText2,
                            fontWeight = FontWeight(400)

                ),
                textAlign = TextAlign.End
            )
        }
        Text(
            text = article.title,
            style = MaterialTheme.typography.titleLarge
        )
        Text(
            text = article.subtitle,
            style = MaterialTheme.typography.headlineLarge
        )
        Text(
            text =  article.content,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight(400),

                )
        )
        Text(
            text = stringResource(R.string.detail_article_header),
            style = MaterialTheme.typography.headlineLarge
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(13.dp)
        ) {
            article.articles.forEach { it ->
                ArticleLink(it)
            }
        }
        Spacer(modifier = Modifier.height(25.dp))

    }
}

@Composable
fun ArticleLink(
    newsLink: Article,
    modifier: Modifier = Modifier
) {
    Row(
        modifier
            .fillMaxWidth()
            .background(White, shape = RoundedCornerShape(40.dp))
            .border(1.dp, MainPrimary, shape = RoundedCornerShape(10.dp))
            .padding(vertical = 9.dp, horizontal = 13.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Column() {
            Text(text = newsLink.press, style = MaterialTheme.typography.titleSmall)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = newsLink.title, style = MaterialTheme.typography.labelSmall)
        }
        Image(painter = painterResource(id = R.drawable.left_arrow), contentDescription = "fdfd")
    }
}