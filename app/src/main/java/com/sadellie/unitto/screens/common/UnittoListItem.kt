/*
 * Unitto is a unit converter for Android
 * Copyright (c) 2022-2022 Elshan Agaev
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

package com.sadellie.unitto.screens.common

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.sadellie.unitto.R

/**
 * Basic list item for settings screen. By default only has label and support text, clickable.
 * This component can be easily modified if you provide additional component to it,
 * for example a switch or a checkbox.
 *
 * @param modifier Modifier that will be applied to a Row.
 * @param label Main text.
 * @param supportText Text that is located below label.
 * @param trailingItem Additional composable, icons for example.
 * @param leadingItem Additional composable: buttons, switches, checkboxes or something else.
 */
@Composable
private fun BasicUnittoListItem(
    modifier: Modifier = Modifier,
    label: String,
    supportText: String? = null,
    leadingItem: @Composable() (RowScope.() -> Unit) = {},
    trailingItem: @Composable() (RowScope.() -> Unit) = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        leadingItem()
        Column(
            Modifier.weight(1f),  // This makes additional composable to be seen
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = label,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            supportText?.let {
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = it,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
        trailingItem()
    }
}

/**
 * Represents one item in list on Settings screen.
 *
 * @param modifier Modifier that will be applied to a Row.
 * @param label Main text.
 * @param supportText Text that is located below label.
 * @param onClick Action to perform when user clicks on this component (whole component is clickable).
 */
@Composable
fun UnittoListItem(
    modifier: Modifier = Modifier,
    label: String,
    supportText: String? = null,
    onClick: () -> Unit,
) = BasicUnittoListItem(
    modifier = modifier
        .clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = rememberRipple(),
            onClick = onClick
        )
        .padding(horizontal = 16.dp, vertical = 12.dp),
    label = label,
    supportText = supportText
)

/**
 * Represents one item in list on Settings screen.
 *
 * @param label Main text.
 * @param supportText Text that is located below label.
 * @param switchState Current switch state.
 * @param onSwitchChange Action to perform when user clicks on this component or just switch. Gives
 * you new value.
 */
@Composable
fun UnittoListItem(
    label: String,
    supportText: String? = null,
    switchState: Boolean,
    onSwitchChange: (Boolean) -> Unit
) = BasicUnittoListItem(
    modifier = Modifier
        .clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = rememberRipple(),
            onClick = { onSwitchChange(!switchState) }
        )
        .padding(horizontal = 16.dp, vertical = 12.dp),
    label = label,
    supportText = supportText
) {
    Switch(checked = switchState, onCheckedChange = { onSwitchChange(it) })
}

/**
 * Represents one item in list on Settings screen with drop-down menu.
 *
 * @param label Main text.
 * @param supportText Text that is located below label.
 * @param allOptions Options in drop-down menu. Key is option itself and value is the string that
 * will be shown.
 * @param selected Selected option.
 * @param onSelectedChange Action to perform when drop-down menu item is selected.
 */
@Composable
fun <T> UnittoListItem(
    label: String,
    supportText: String? = null,
    allOptions: Map<T, String>,
    selected: T,
    onSelectedChange: (T) -> Unit
) {
    BasicUnittoListItem(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
        label = label,
        supportText = supportText
    ) {
        var dropDownExpanded by rememberSaveable { mutableStateOf(false) }
        var currentOption by rememberSaveable { mutableStateOf(selected) }
        val dropDownRotation: Float by animateFloatAsState(
            targetValue = if (dropDownExpanded) 180f else 0f,
            animationSpec = tween(easing = FastOutSlowInEasing)
        )

        ExposedDropdownMenuBox(
            modifier = Modifier,
            expanded = dropDownExpanded,
            onExpandedChange = { dropDownExpanded = it }
        ) {
            OutlinedTextField(
                modifier = Modifier.widthIn(1.dp),
                value = allOptions[currentOption] ?: selected.toString(),
                onValueChange = {},
                readOnly = true,
                singleLine = true,
                enabled = false,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    disabledBorderColor = MaterialTheme.colorScheme.outline,
                    disabledTextColor = MaterialTheme.colorScheme.onSurface,
                    disabledTrailingIconColor = MaterialTheme.colorScheme.onSurfaceVariant
                ),
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.ArrowDropDown,
                        modifier = Modifier.rotate(dropDownRotation),
                        contentDescription = stringResource(R.string.drop_down_description)
                    )
                }
            )
            ExposedDropdownMenu(
                modifier = Modifier.exposedDropdownSize(),
                expanded = dropDownExpanded,
                onDismissRequest = { dropDownExpanded = false }
            ) {
                allOptions.forEach {
                    DropdownMenuItem(
                        text = { Text(it.value) },
                        onClick = {
                            currentOption = it.key
                            onSelectedChange(it.key)
                            dropDownExpanded = false
                        }
                    )
                }
            }
        }
    }
}

/**
 * Composable with leading and trailing items.
 *
 * @param label Main text.
 * @param modifier Modifier that will be applied to a Row.
 * @param trailingItem Additional composable, icons for example.
 * @param leadingItem Additional composable: buttons, switches, checkboxes or something else.
 */
@Composable
fun UnittoListItem(
    label: String,
    supportText: String? = null,
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
    leadingItem: @Composable RowScope.() -> Unit = {},
    trailingItem: @Composable RowScope.() -> Unit = {}
) {
    BasicUnittoListItem(
        modifier = modifier.padding(paddingValues),
        label = label,
        supportText = supportText,
        trailingItem = trailingItem,
        leadingItem = leadingItem
    )
}

