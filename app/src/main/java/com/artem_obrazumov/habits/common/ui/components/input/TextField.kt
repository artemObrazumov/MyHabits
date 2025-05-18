package com.artem_obrazumov.habits.common.ui.components.input

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.input.VisualTransformation
import com.artem_obrazumov.habits.common.ui.components.containers.InputContainer
import com.artem_obrazumov.habits.common.ui.theme.Typography
import com.artem_obrazumov.habits.common.ui.theme.primaryContentColor

@Composable
fun TextField(
    value: String,
    onValueChange: (value: String) -> Unit,
    modifier: Modifier = Modifier,
    minLines: Int = 1,
    maxLines: Int = 1,
    label: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    var isFocused by remember { mutableStateOf(false) }

    InputContainer(
        isFocused = isFocused,
        label = label,
        modifier = modifier
    ) {
        if (value.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .alpha(0.6f)
            ) {
                placeholder?.invoke()
            }
        }
        BasicTextField(
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged { isFocused = it.isFocused },
            value = value,
            singleLine = maxLines == 1,
            onValueChange = onValueChange,
            textStyle = Typography.bodyMedium.copy(color = primaryContentColor()),
            minLines = minLines,
            maxLines = maxLines,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            visualTransformation = visualTransformation
        )
    }
}