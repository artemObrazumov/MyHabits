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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.artem_obrazumov.habits.R
import com.artem_obrazumov.habits.common.ui.components.bars.TopAppBar
import com.artem_obrazumov.habits.common.ui.components.bars.TopAppBarConfiguration
import com.artem_obrazumov.habits.common.ui.util.UIText
import com.artem_obrazumov.habits.features.habits.presentation.habit_details.HabitDetailsScreen
import com.artem_obrazumov.habits.features.habits.presentation.habit_details.HabitDetailsScreenViewModel
import com.artem_obrazumov.habits.features.habits.presentation.habits_editor.HabitsEditorScreen
import com.artem_obrazumov.habits.features.habits.presentation.habits_editor.HabitsEditorScreenViewModel
import com.artem_obrazumov.habits.features.habits.presentation.habits_list.HabitsListScreen
import com.artem_obrazumov.habits.features.habits.presentation.habits_list.HabitsListScreenViewModel
import com.artem_obrazumov.habits.features.habits.presentation.routes.HabitsDetails
import com.artem_obrazumov.habits.features.habits.presentation.routes.HabitsEditor
import com.artem_obrazumov.habits.features.habits.presentation.routes.HabitsList
import java.util.UUID

const val ANIMATION_DURATION = 300

@Composable
fun App(
    modifier: Modifier = Modifier
) {

    val backStack = rememberNavBackStack(HabitsList)

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        contentColor = LocalContentColor.current
    ) { innerPadding ->

        val contentTransform = ContentTransform(
            scaleIn(
                initialScale = 0.9f,
                animationSpec = tween(ANIMATION_DURATION)
            ) + fadeIn(animationSpec = tween(ANIMATION_DURATION)),
            scaleOut(
                targetScale = 0.9f,
                animationSpec = tween(ANIMATION_DURATION)
            ) + fadeOut(animationSpec = tween(ANIMATION_DURATION))
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
                        viewModel = viewModel,
                        menu = {
                            TopAppBar(
                                configuration = TopAppBarConfiguration(
                                    title = UIText.StringResource(R.string.my_habits)
                                )
                            )
                        }
                    )
                }

                is HabitsEditor -> NavEntry(route) {

                    val viewModelKey = rememberSaveable { UUID.randomUUID().toString() }

                    val viewModel =
                        hiltViewModel<HabitsEditorScreenViewModel, HabitsEditorScreenViewModel.Factory>(
                            key = viewModelKey
                        ) { factory ->
                            factory.create(route.id)
                        }
                    HabitsEditorScreen(
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

                    val viewModel =
                        hiltViewModel<HabitDetailsScreenViewModel, HabitDetailsScreenViewModel.Factory>(
                            key = "habit_details_${route.id}"
                        ) { factory ->
                            factory.create(route.id)
                        }
                    var menuTitle: UIText = remember { UIText.StringResource(R.string.loading) }
                    HabitDetailsScreen(
                        viewModel = viewModel,
                        menu = {
                            TopAppBar(
                                configuration = TopAppBarConfiguration(
                                    title = menuTitle,
                                    onBackPressed = { backStack.removeLastOrNull() }
                                )
                            )
                        },
                        onHabitTitleLoaded = { title ->
                            menuTitle = UIText.DynamicText(title)
                        }
                    )
                }

                else -> throw IllegalArgumentException("No such route: $route")
            }
        }
    }
}
