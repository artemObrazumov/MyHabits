package com.artem_obrazumov.habits.app

import androidx.compose.animation.ContentTransform
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.artem_obrazumov.habits.R
import com.artem_obrazumov.habits.common.ui.components.bars.TopAppBar
import com.artem_obrazumov.habits.common.ui.components.bars.TopAppBarConfiguration
import com.artem_obrazumov.habits.common.ui.util.UIText
import com.artem_obrazumov.habits.features.habits.presentation.habits_editor.HabitsEditorScreen
import com.artem_obrazumov.habits.features.habits.presentation.habits_editor.HabitsEditorScreenViewModel
import com.artem_obrazumov.habits.features.habits.presentation.habits_list.HabitsListScreen
import com.artem_obrazumov.habits.features.habits.presentation.habits_list.HabitsListScreenViewModel
import com.artem_obrazumov.habits.features.habits.presentation.routes.HabitsDetails
import com.artem_obrazumov.habits.features.habits.presentation.routes.HabitsEditor
import com.artem_obrazumov.habits.features.habits.presentation.routes.HabitsList

@Composable
fun App(
    modifier: Modifier = Modifier
) {

    val backStack = rememberNavBackStack(HabitsList)

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        contentColor = LocalContentColor.current
    ) { innerPadding ->

        val contentTransform = ContentTransform(
            scaleIn(
                initialScale = 0.9f,
                animationSpec = tween(400)
            ) + fadeIn(animationSpec = tween(400)),
            scaleOut(
                targetScale = 0.9f,
                animationSpec = tween(400)
            ) + fadeOut(animationSpec = tween(400))
        )

        NavDisplay(
            modifier = modifier
                .padding(innerPadding),
            backStack = backStack,
            onBack = { backStack.removeLastOrNull() },
            transitionSpec = { contentTransform },
            popTransitionSpec = { contentTransform }
        ) { route ->
            when (route) {

                HabitsList -> NavEntry(route) {
                    val viewModel: HabitsListScreenViewModel = hiltViewModel()
                    HabitsListScreen(
                        backStack = backStack,
                        viewModel = viewModel
                    )
                }

                is HabitsEditor -> NavEntry(route) {
                    val viewModel = hiltViewModel<HabitsEditorScreenViewModel,
                            HabitsEditorScreenViewModel.Factory> { factory ->
                        factory.create(route.id)
                    }
                    HabitsEditorScreen(
                        backStack = backStack,
                        viewModel = viewModel,
                        menu = {
                            TopAppBar(
                                configuration = TopAppBarConfiguration(
                                    title = UIText.StringResource(R.string.editing_habit),
                                    onBackPressed = { backStack.removeLastOrNull() }
                                )
                            )
                        }
                    )
                }

                is HabitsDetails -> NavEntry(route) {
                    Text(route.id.toString())
                }

                else -> throw IllegalArgumentException("No such route: $route")
            }
        }
    }
}