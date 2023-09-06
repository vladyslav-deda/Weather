package com.example.presentation.location.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.domain.search.model.HintsList
import com.example.presentation.R
import com.example.presentation.base.Loader
import java.util.Locale
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.text.EmojiSupportMatch

@Composable
fun HintsView(
    modifier: Modifier = Modifier,
    hintsList: HintsList,
    isLoading: Boolean = false,
    countryCodeToEmojiFlag: (String) -> String,
    onHintClicked: (String) -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        if (isLoading) {
            Loader(
                modifier = Modifier
                    .size(100.dp)
            )
        } else {
            val listOgHints = hintsList.geonames?.distinctBy {
                it?.name
            }
            listOgHints?.let { geonamesList ->
                LazyColumn(
                    contentPadding = PaddingValues(20.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(geonamesList) { geoname ->
                        geoname?.name?.let { name ->
                            val countryCode = geoname.countryCode
                            HintItem(
                                name = name,
                                countryCode = countryCode,
                                onHintClicked = onHintClicked,
                                countryCodeToEmojiFlag = countryCodeToEmojiFlag
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun HintItem(
    name: String,
    countryCode: String?,
    onHintClicked: (String) -> Unit,
    countryCodeToEmojiFlag: (String) -> String,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier
                    .size(20.dp),
                imageVector = Icons.Outlined.LocationOn,
                tint = colorResource(id = R.color.dark_yellow),
                contentDescription = null
            )
            Text(
                modifier = Modifier
                    .clickable {
                        onHintClicked(name)
                    }
                    .padding(start = 20.dp),
                text = name,
                style = TextStyle(
                    color = colorResource(id = R.color.dark_yellow),
                    fontSize = 22.sp,
                    fontFamily = FontFamily(Font(R.font.fabrik)),
                    letterSpacing = 0.5.sp,
                )
            )
        }
        countryCode?.let {
            Text(
                text = countryCodeToEmojiFlag(it),
                fontSize = 22.sp,
            )
        }
    }
}


@Preview
@Composable
private fun HintsSectionPreview() {
    HintsView(
        hintsList = HintsList(),
        countryCodeToEmojiFlag = { countryCode -> countryCode },
        onHintClicked = {}
    )
}