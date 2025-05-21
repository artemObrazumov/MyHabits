package com.artem_obrazumov.habits.features.habits.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.artem_obrazumov.habits.R
import com.artem_obrazumov.habits.common.ui.components.bars.ProgressBar
import com.artem_obrazumov.habits.common.ui.components.containers.Card
import com.artem_obrazumov.habits.common.ui.components.text.Label
import com.artem_obrazumov.habits.common.ui.components.text.MediumTitle
import com.artem_obrazumov.habits.common.ui.components.text.RegularText
import com.artem_obrazumov.habits.common.ui.theme.HabitsTheme
import com.artem_obrazumov.habits.common.ui.theme.secondaryContentColor
import com.artem_obrazumov.habits.common.ui.util.PluralStringSet
import com.artem_obrazumov.habits.common.ui.util.formatToString
import com.artem_obrazumov.habits.common.ui.util.getPluralString
import com.artem_obrazumov.habits.features.habits.domain.model.Habit
import com.artem_obrazumov.habits.features.habits.presentation.preview.HABITS_LIST
import java.time.format.DateTimeFormatter

@Composable
fun HabitItem(
    habit: Habit,
    modifier: Modifier = Modifier,
    onHabitClicked: () -> Unit = {},
) {
    Card(
        modifier = modifier
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { onHabitClicked() }
    ) {
        Column {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                MediumTitle(
                    text = habit.name,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    modifier = Modifier.weight(1f)
                )
                val habitStartDateLabel = DateTimeFormatter.ofPattern("dd.MM.yyyy").format(habit.startedAt)
                RegularText(
                    text = habitStartDateLabel
                )
            }
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
                val progressPercent = (habit.progress / habit.goal * 100f).formatToString(digits = 0)
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
            if (habit.usersCount > 1) {
                Spacer(
                    modifier = Modifier.height(8.dp)
                )
                val userCounterLabel = "${habit.usersCount} ${getPluralString(habit.usersCount, PluralStringSet.Users)}"
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painterResource(R.drawable.user),
                        contentDescription = userCounterLabel,
                        tint = secondaryContentColor(),
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(
                        modifier = Modifier.width(4.dp)
                    )
                    Label(
                        text = userCounterLabel
                    )
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
fun HabitItemPreview() {
    HabitsTheme {
        HabitItem(
            habit = HABITS_LIST[0]
        )
    }
}

@Preview
@Composable
fun HabitItemLongTitlePreview() {
    HabitsTheme {
        HabitItem(
            habit = HABITS_LIST[1]
        )
    }
}
