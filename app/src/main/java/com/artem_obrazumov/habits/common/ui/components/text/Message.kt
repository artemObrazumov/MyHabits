package com.artem_obrazumov.habits.common.ui.components.text

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import com.artem_obrazumov.habits.common.ui.components.text.MessageTestTags.MESSAGE_TEXT
import com.artem_obrazumov.habits.common.ui.theme.successColor
import com.artem_obrazumov.habits.common.ui.util.Message

@Composable
fun Message(
    message: Message,
    modifier: Modifier = Modifier,
    overflow: TextOverflow = TextOverflow.Clip,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    textAlign: TextAlign = TextAlign.Unspecified
) {
    RegularText(
        modifier = modifier
            .testTag(MESSAGE_TEXT),
        text = message.content.asString(),
        color = when(message) {
            is Message.Neutral -> Color.Unspecified
            is Message.Error -> MaterialTheme.colorScheme.error
            is Message.Success -> successColor()
        },
        overflow = overflow,
        minLines = minLines,
        maxLines = maxLines,
        textAlign = textAlign,
    )
}

object MessageTestTags {

    const val MESSAGE_TEXT = "message_component_test"
}
