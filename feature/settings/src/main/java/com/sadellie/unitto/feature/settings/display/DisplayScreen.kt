/*
 * Unitto is a unit converter for Android
 * Copyright (c) 2022-2023 Elshan Agaev
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.sadellie.unitto.feature.settings.display

import android.os.Build
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Colorize
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.ExposureZero
import androidx.compose.material.icons.filled.FontDownload
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.HdrAuto
import androidx.compose.material.icons.outlined.LightMode
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sadellie.unitto.core.base.R
import com.sadellie.unitto.core.ui.common.Header
import com.sadellie.unitto.core.ui.common.NavigateUpButton
import com.sadellie.unitto.core.ui.common.SegmentedButton
import com.sadellie.unitto.core.ui.common.SegmentedButtonsRow
import com.sadellie.unitto.core.ui.common.UnittoListItem
import com.sadellie.unitto.core.ui.common.UnittoScreenWithLargeTopBar
import com.sadellie.unitto.feature.settings.components.ColorSelector
import com.sadellie.unitto.feature.settings.components.MonetModeSelector
import io.github.sadellie.themmo.MonetMode
import io.github.sadellie.themmo.ThemingMode
import io.github.sadellie.themmo.Themmo
import io.github.sadellie.themmo.ThemmoController

@Composable
internal fun DisplayRoute(
    viewModel: DisplayViewModel = hiltViewModel(),
    navigateUp: () -> Unit = {},
    themmoController: ThemmoController,
    navigateToLanguages: () -> Unit,
) {
    val prefs = viewModel.prefs.collectAsStateWithLifecycle()

    DisplayScreen(
        navigateUp = navigateUp,
        currentThemingMode = themmoController.currentThemingMode,
        onThemeChange = {
            themmoController.setThemingMode(it)
            viewModel.updateThemingMode(it)
        },
        isDynamicThemeEnabled = themmoController.isDynamicThemeEnabled,
        onDynamicThemeChange = {
            // Prevent old devices from using other monet modes when dynamic theming is on
            if (it) {
                themmoController.setMonetMode(MonetMode.TonalSpot)
                viewModel.updateMonetMode(MonetMode.TonalSpot)
            }
            themmoController.enableDynamicTheme(it)
            viewModel.updateDynamicTheme(it)
        },
        isAmoledThemeEnabled = themmoController.isAmoledThemeEnabled,
        onAmoledThemeChange = {
            themmoController.enableAmoledTheme(it)
            viewModel.updateAmoledTheme(it)
        },
        selectedColor = themmoController.currentCustomColor,
        onColorChange = {
            themmoController.setCustomColor(it)
            viewModel.updateCustomColor(it)
        },
        monetMode = themmoController.currentMonetMode,
        onMonetModeChange = {
            themmoController.setMonetMode(it)
            viewModel.updateMonetMode(it)
        },
        systemFont = prefs.value.systemFont,
        updateSystemFont = viewModel::updateSystemFont,
        middleZero = prefs.value.middleZero,
        updateMiddleZero = viewModel::updateMiddleZero,
        navigateToLanguages = navigateToLanguages
    )
}

@Composable
private fun DisplayScreen(
    navigateUp: () -> Unit,
    currentThemingMode: ThemingMode,
    onThemeChange: (ThemingMode) -> Unit,
    isDynamicThemeEnabled: Boolean,
    onDynamicThemeChange: (Boolean) -> Unit,
    isAmoledThemeEnabled: Boolean,
    onAmoledThemeChange: (Boolean) -> Unit,
    selectedColor: Color,
    onColorChange: (Color) -> Unit,
    monetMode: MonetMode,
    onMonetModeChange: (MonetMode) -> Unit,
    systemFont: Boolean,
    updateSystemFont: (Boolean) -> Unit,
    middleZero: Boolean,
    updateMiddleZero: (Boolean) -> Unit,
    navigateToLanguages: () -> Unit,
) {
    UnittoScreenWithLargeTopBar(
        title = stringResource(R.string.display_settings),
        navigationIcon = { NavigateUpButton(navigateUp) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(paddingValues)
        ) {
            UnittoListItem(
                leadingContent = {
                    Icon(
                        Icons.Default.Palette,
                        stringResource(R.string.color_theme),
                    )
                },
                headlineContent = { Text(stringResource(R.string.color_theme)) },
                supportingContent = { Text(stringResource(R.string.color_theme_support)) },
            )

            Row(
                Modifier
                    .horizontalScroll(rememberScrollState())
                    .wrapContentWidth()
            ) {
                SegmentedButtonsRow(modifier = Modifier.padding(56.dp, 8.dp, 24.dp, 2.dp)) {
                    SegmentedButton(
                        label = stringResource(R.string.auto_label),
                        onClick = { onThemeChange(ThemingMode.AUTO) },
                        selected = ThemingMode.AUTO == currentThemingMode,
                        icon = Icons.Outlined.HdrAuto
                    )
                    SegmentedButton(
                        label = stringResource(R.string.force_light_mode),
                        onClick = { onThemeChange(ThemingMode.FORCE_LIGHT) },
                        selected = ThemingMode.FORCE_LIGHT == currentThemingMode,
                        icon = Icons.Outlined.LightMode
                    )
                    SegmentedButton(
                        label = stringResource(R.string.force_dark_mode),
                        onClick = { onThemeChange(ThemingMode.FORCE_DARK) },
                        selected = ThemingMode.FORCE_DARK == currentThemingMode,
                        icon = Icons.Outlined.DarkMode
                    )
                }
            }

            AnimatedVisibility(
                visible = currentThemingMode != ThemingMode.FORCE_LIGHT,
                enter = expandVertically() + fadeIn(),
                exit = shrinkVertically() + fadeOut(),
            ) {
                UnittoListItem(
                    icon = Icons.Default.DarkMode,
                    iconDescription = stringResource(R.string.force_amoled_mode),
                    headlineText = stringResource(R.string.force_amoled_mode),
                    supportingText = stringResource(R.string.force_amoled_mode_support),
                    switchState = isAmoledThemeEnabled,
                    onSwitchChange = onAmoledThemeChange
                )
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
                UnittoListItem(
                    icon = Icons.Default.Colorize,
                    iconDescription = stringResource(R.string.enable_dynamic_colors),
                    headlineText = stringResource(R.string.enable_dynamic_colors),
                    supportingText = stringResource(R.string.enable_dynamic_colors_support),
                    switchState = isDynamicThemeEnabled,
                    onSwitchChange = onDynamicThemeChange
                )

                AnimatedVisibility(
                    visible = !isDynamicThemeEnabled,
                    enter = expandVertically() + fadeIn(),
                    exit = shrinkVertically() + fadeOut(),
                ) {
                    UnittoListItem(
                        headlineContent = { Text(stringResource(R.string.selected_color)) },
                        supportingContent = {
                            ColorSelector(
                                modifier = Modifier.padding(top = 12.dp),
                                selected = selectedColor,
                                onItemClick = onColorChange,
                                colorSchemes = colorSchemes,
                                defaultColor = Color(0xFF186c31)
                            )
                        },
                        modifier = Modifier.padding(start = 40.dp),
                    )
                }

                AnimatedVisibility(
                    visible = (!isDynamicThemeEnabled) and (selectedColor != Color.Unspecified),
                    enter = expandVertically() + fadeIn(),
                    exit = shrinkVertically() + fadeOut(),
                ) {
                    UnittoListItem(
                        headlineContent = { Text(stringResource(R.string.monet_mode)) },
                        supportingContent = {
                            MonetModeSelector(
                                modifier = Modifier.padding(top = 12.dp),
                                selected = monetMode,
                                onItemClick = onMonetModeChange,
                                monetModes = remember { MonetMode.values().toList() },
                                customColor = selectedColor,
                                themingMode = currentThemingMode,
                            )
                        },
                        modifier = Modifier.padding(start = 40.dp)
                    )
                }
            }

            Header(stringResource(R.string.additional_settings_group))

            UnittoListItem(
                icon = Icons.Default.FontDownload,
                iconDescription = stringResource(R.string.system_font_setting),
                headlineText = stringResource(R.string.system_font_setting),
                supportingText = stringResource(R.string.system_font_setting_support),
                switchState = systemFont,
                onSwitchChange = updateSystemFont
            )

            UnittoListItem(
                icon = Icons.Default.ExposureZero,
                iconDescription = stringResource(R.string.middle_zero_option),
                headlineText = stringResource(R.string.middle_zero_option),
                supportingText = stringResource(R.string.middle_zero_option_support),
                switchState = middleZero,
                onSwitchChange = updateMiddleZero
            )

            UnittoListItem(
                icon = Icons.Default.Language,
                iconDescription = stringResource(R.string.language_setting),
                headlineText = stringResource(R.string.language_setting),
                supportingText = stringResource(R.string.language_setting_support),
                modifier = Modifier.clickable { navigateToLanguages() }
            )
        }
    }
}

@Preview
@Composable
private fun Preview() {
    Themmo { themmoController ->
        DisplayScreen(
            navigateUp = {},
            currentThemingMode = themmoController.currentThemingMode,
            onThemeChange = themmoController::setThemingMode,
            isDynamicThemeEnabled = themmoController.isDynamicThemeEnabled,
            onDynamicThemeChange = themmoController::enableDynamicTheme,
            isAmoledThemeEnabled = themmoController.isAmoledThemeEnabled,
            onAmoledThemeChange = themmoController::enableAmoledTheme,
            selectedColor = themmoController.currentCustomColor,
            onColorChange = themmoController::setCustomColor,
            monetMode = themmoController.currentMonetMode,
            onMonetModeChange = themmoController::setMonetMode,
            systemFont = false,
            updateSystemFont = {},
            middleZero = false,
            updateMiddleZero = {},
            navigateToLanguages = {}
        )
    }
}
