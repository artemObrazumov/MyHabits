package com.artem_obrazumov.habits.common.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.artem_obrazumov.habits.R
import com.artem_obrazumov.habits.common.ui.components.button.Button
import com.artem_obrazumov.habits.common.ui.components.text.MediumTitle
import com.artem_obrazumov.habits.common.ui.components.text.RegularText
import com.artem_obrazumov.habits.common.ui.screens.FailureScreenTags.FAILURE_SCREEN
import com.artem_obrazumov.habits.common.ui.screens.FailureScreenTags.RETRY_BUTTON_TAG
import com.artem_obrazumov.habits.common.ui.theme.HabitsTheme

@Composable
fun FailureScreen(
    message: String,
    modifier: Modifier = Modifier,
    onRetry: () -> Unit = {},
) {
    Column (
        modifier = modifier
            .fillMaxSize()
            .padding(48.dp)
            .testTag(FAILURE_SCREEN),
        verticalArrangement = Arrangement.spacedBy(
            space = 8.dp,
            alignment = Alignment.CenterVertically
        ),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        MediumTitle(
            text = stringResource(R.string.error_occured),
            textAlign = TextAlign.Center
        )
        RegularText(
            text = message,
            textAlign = TextAlign.Center
        )
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .testTag(RETRY_BUTTON_TAG),
            onClick = onRetry,
            text = stringResource(R.string.retry)
        )
    }
}

@PreviewLightDark
@Composable
fun FailureScreenPreview() {
    HabitsTheme {
        FailureScreen(
            message = "Test error message"
        )
    }
}

object FailureScreenTags {

    const val FAILURE_SCREEN = "failure_screen"
    const val RETRY_BUTTON_TAG = "retry_button"
}