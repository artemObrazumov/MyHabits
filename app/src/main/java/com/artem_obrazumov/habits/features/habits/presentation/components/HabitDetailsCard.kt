package com.artem_obrazumov.habits.features.habits.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.artem_obrazumov.habits.R
import com.artem_obrazumov.habits.common.ui.components.bars.ProgressBar
import com.artem_obrazumov.habits.common.ui.components.containers.Card
import com.artem_obrazumov.habits.common.ui.components.text.MediumLabel
import com.artem_obrazumov.habits.common.ui.components.text.RegularText
import com.artem_obrazumov.habits.common.ui.theme.secondaryContentColor
import com.artem_obrazumov.habits.common.ui.util.formatToString
import com.artem_obrazumov.habits.features.auth.domain.model.User
import com.artem_obrazumov.habits.features.habits.domain.model.HabitDetails
import java.time.format.DateTimeFormatter

@Composable
fun HabitDetailsCard(
    habitDetails: HabitDetails,
    modifier: Modifier = Modifier,
    activeUserId: Long? = null,
    localUser: User? = null
) {
    val habit = habitDetails.habit
    val users = habitDetails.users

    Card(
        modifier = modifier
    ) {
        Column {
            val habitStartDateLabel =
                DateTimeFormatter.ofPattern("dd.MM.yyyy").format(habit.startedAt)
            MediumLabel(
                text = stringResource(
                    R.string.created_at,
                    habitStartDateLabel
                ),
                color = secondaryContentColor()
            )
            Spacer(
                modifier = Modifier.height(8.dp)
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                RegularText(
                    text = stringResource(
                        R.string.progress,
                        habit.progress.formatToString(), habit.goal.formatToString(),
                        habit.measurement
                    ),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    modifier = Modifier.weight(1f)
                )
                val progressPercent =
                    (habit.progress / habit.goal * 100f).formatToString(digits = 0)
                RegularText(
                    text = "$progressPercent%",
                    maxLines = 1,
                )
            }
            Spacer(
                modifier = Modifier.height(4.dp)
            )
            ProgressBar(
                value = habit.progress.toFloat(),
                total = habit.goal.toFloat()
            )
            Spacer(
                modifier = Modifier.height(8.dp)
            )
            var usersListOpened by remember { mutableStateOf(false) }
            Row(
                modifier = Modifier
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {
                        usersListOpened = !usersListOpened
                    },
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RegularText(
                    text = stringResource(
                        R.string.participants,
                        users.size + 1
                    ),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    modifier = Modifier.weight(1f)
                )
                Icon(
                    if (usersListOpened) {
                        Icons.Default.ArrowDropUp
                    } else {
                        Icons.Default.ArrowDropDown
                    },
                    contentDescription = stringResource(R.string.open),
                    modifier = Modifier
                        .padding(4.dp)
                )
            }
            AnimatedVisibility(
                visible = usersListOpened
            ) {
                Column (
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    SelfParticipantItem(
                        name = localUser?.name,
                        isActive = (activeUserId == null)
                    )
                    users.forEach { user ->
                        ParticipantItem(
                            user = user,
                            isActive = (activeUserId == user.id)
                        )
                    }
                }
            }
        }
    }
}
