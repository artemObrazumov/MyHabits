package com.artem_obrazumov.habits.features.habits.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.artem_obrazumov.habits.R
import com.artem_obrazumov.habits.common.ui.components.bars.ProgressBar
import com.artem_obrazumov.habits.common.ui.components.containers.Card
import com.artem_obrazumov.habits.common.ui.components.text.MediumTitle
import com.artem_obrazumov.habits.common.ui.components.text.RegularText
import com.artem_obrazumov.habits.common.ui.theme.HabitsTheme
import com.artem_obrazumov.habits.common.ui.util.formatToString
import com.artem_obrazumov.habits.features.habits.domain.model.Habit
import com.artem_obrazumov.habits.features.habits.presentation.preview.HABITS_LIST

@Composable
fun HabitItem(
    habit: Habit,
    modifier: Modifier = Modifier
) {

    Card(
        modifier = modifier
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
            }
            Spacer(
                modifier = Modifier.height(4.dp)
            )
            ProgressBar(
                value = habit.progress.toFloat(),
                total = habit.goal.toFloat()
            )
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
