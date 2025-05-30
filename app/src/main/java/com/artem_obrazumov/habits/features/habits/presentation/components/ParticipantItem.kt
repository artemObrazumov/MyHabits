package com.artem_obrazumov.habits.features.habits.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.artem_obrazumov.habits.R
import com.artem_obrazumov.habits.common.ui.components.text.RegularText
import com.artem_obrazumov.habits.features.auth.domain.model.User

@Composable
fun ParticipantItem(
    user: User,
    isActive: Boolean,
    modifier: Modifier = Modifier
) {
    ParticipantContainer(
        modifier = modifier,
        isActive = isActive
    ) {
        RegularText(
            text = user.name
        )
    }
}

@Composable
fun SelfParticipantItem(
    isActive: Boolean,
    modifier: Modifier = Modifier,
    name: String? = null,
) {
    ParticipantContainer(
        modifier = modifier,
        isActive = isActive
    ) {
        RegularText(
            text = if (name == null) {
                stringResource(R.string.me)
            } else {
                stringResource(
                    R.string.me_name,
                    name
                )
            }
        )
    }
}

@Composable
fun ParticipantContainer(
    modifier: Modifier = Modifier,
    isActive: Boolean,
    content: @Composable RowScope.() -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = if (isActive) {
                    MaterialTheme.colorScheme.onSecondaryFixed
                } else {
                    MaterialTheme.colorScheme.onSecondary
                },
                shape = MaterialTheme.shapes.medium
            )
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        content()
    }
}