package com.artem_obrazumov.habits.common.ui.components.bars

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.artem_obrazumov.habits.R
import com.artem_obrazumov.habits.common.ui.components.text.Title
import com.artem_obrazumov.habits.common.ui.util.UIText

@Composable
fun TopAppBar(
    configuration: TopAppBarConfiguration,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 8.dp)
            .padding(vertical = 8.dp)
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (configuration.onBackPressed != null) {
            Icon(
                Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = stringResource(R.string.back),
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable {
                        configuration.onBackPressed.invoke()
                    }
            )
        }

        Title(
            text = configuration.title.asString(),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

data class TopAppBarConfiguration(
    val title: UIText,
    val onBackPressed: (() -> Unit)? = null
)
