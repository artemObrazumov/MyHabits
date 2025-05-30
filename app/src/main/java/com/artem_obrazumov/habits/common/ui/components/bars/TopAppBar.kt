package com.artem_obrazumov.habits.common.ui.components.bars

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.artem_obrazumov.habits.R
import com.artem_obrazumov.habits.common.ui.components.text.Title
import com.artem_obrazumov.habits.common.ui.util.UIText

@Composable
fun TopAppBar(
    title: UIText,
    modifier: Modifier = Modifier,
    onBackPressed: (() -> Unit)? = null,
    toolbar: @Composable (RowScope.() -> Unit)? = null
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (onBackPressed != null) {
            Icon(
                Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = stringResource(R.string.back),
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable {
                        onBackPressed.invoke()
                    }
            )
        }

        Title(
            modifier = Modifier
                .weight(1f),
            text = title.asString(),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        if (toolbar != null) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                toolbar()
            }
        }
    }
}

@Composable
fun ToolbarItem(
    image: ImageVector,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    onClick: () -> Unit = {},
    tint: Color = MaterialTheme.colorScheme.primary
) {
    Icon(
        image,
        contentDescription,
        modifier = modifier
            .clip(CircleShape)
            .clickable(
                onClick = onClick
            ),
        tint = tint
    )
}

@Composable
fun ToolbarItem(
    painter: Painter,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    onClick: () -> Unit = {},
    tint: Color = MaterialTheme.colorScheme.primary
) {
    Icon(
        painter,
        contentDescription,
        modifier = modifier
            .clip(CircleShape)
            .clickable(
                onClick = onClick
            ),
        tint = tint
    )
}
