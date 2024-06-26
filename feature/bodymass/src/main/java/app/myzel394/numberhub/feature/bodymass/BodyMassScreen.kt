/*
 * Unitto is a calculator for Android
 * Copyright (c) 2024 Elshan Agaev
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

package app.myzel394.numberhub.feature.bodymass

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.Crossfade
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.myzel394.numberhub.core.base.FormatterSymbols
import app.myzel394.numberhub.core.base.R
import app.myzel394.numberhub.core.base.Token
import app.myzel394.numberhub.core.ui.common.DrawerButton
import app.myzel394.numberhub.core.ui.common.EmptyScreen
import app.myzel394.numberhub.core.ui.common.ScaffoldWithTopBar
import app.myzel394.numberhub.core.ui.common.SegmentedButton
import app.myzel394.numberhub.core.ui.common.SegmentedButtonsRow
import app.myzel394.numberhub.core.ui.common.textfield.ExpressionTransformer
import app.myzel394.numberhub.core.ui.openLink
import app.myzel394.numberhub.data.common.isEqualTo
import app.myzel394.numberhub.feature.bodymass.components.BodyMassResult
import app.myzel394.numberhub.feature.bodymass.components.BodyMassTextField
import java.math.BigDecimal

@Composable
internal fun BodyMassRoute(
    openDrawer: () -> Unit,
    viewModel: BodyMassViewModel = hiltViewModel(),
) {
    when (val uiState = viewModel.uiState.collectAsStateWithLifecycle().value) {
        UIState.Loading -> EmptyScreen()
        is UIState.Ready -> BodyMassScreen(
            uiState = uiState,
            updateHeight1 = viewModel::updateHeight1,
            updateHeight2 = viewModel::updateHeight2,
            updateWeight = viewModel::updateWeight,
            updateIsMetric = viewModel::updateIsMetric,
            openDrawer = openDrawer,
        )
    }
}

@Composable
private fun BodyMassScreen(
    uiState: UIState.Ready,
    updateHeight1: (TextFieldValue) -> Unit,
    updateHeight2: (TextFieldValue) -> Unit,
    updateWeight: (TextFieldValue) -> Unit,
    updateIsMetric: (Boolean) -> Unit,
    openDrawer: () -> Unit,
) {
    val mContext = LocalContext.current
    val expressionTransformer = remember(uiState.formatterSymbols) {
        ExpressionTransformer(uiState.formatterSymbols)
    }
    val weightShortLabel = remember(uiState.isMetric) {
        mContext.resources.getString(
            if (uiState.isMetric) R.string.unit_kilogram_short else R.string.unit_pound_short,
        )
    }

    ScaffoldWithTopBar(
        title = { Text(stringResource(R.string.body_mass_title)) },
        navigationIcon = { DrawerButton(openDrawer) },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            SegmentedButtonsRow(
                modifier = Modifier.fillMaxWidth(),
            ) {
                SegmentedButton(
                    label = stringResource(R.string.body_mass_metric),
                    onClick = { updateIsMetric(true) },
                    selected = uiState.isMetric,
                    modifier = Modifier.weight(1f),
                )
                SegmentedButton(
                    label = stringResource(R.string.body_mass_imperial),
                    onClick = { updateIsMetric(false) },
                    selected = !uiState.isMetric,
                    modifier = Modifier.weight(1f),
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        MaterialTheme.colorScheme.secondaryContainer,
                        RoundedCornerShape(32.dp),
                    )
                    .padding(16.dp, 24.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Crossfade(
                    targetState = uiState.isMetric,
                    label = "Measurement system change",
                ) { isMetric ->
                    if (isMetric) {
                        BodyMassTextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = uiState.height1,
                            onValueChange = updateHeight1,
                            label = "${stringResource(R.string.body_mass_height)}, ${stringResource(R.string.unit_centimeter_short)}",
                            expressionFormatter = expressionTransformer,
                        )
                    } else {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                        ) {
                            BodyMassTextField(
                                modifier = Modifier.weight(1f),
                                value = uiState.height1,
                                onValueChange = updateHeight1,
                                label = "${stringResource(R.string.body_mass_height)}, ${stringResource(R.string.unit_foot_short)}",
                                expressionFormatter = expressionTransformer,
                            )
                            BodyMassTextField(
                                modifier = Modifier.weight(1f),
                                value = uiState.height2,
                                onValueChange = updateHeight2,
                                label = "${stringResource(R.string.body_mass_height)}, ${stringResource(R.string.unit_inch_short)}",
                                expressionFormatter = expressionTransformer,
                            )
                        }
                    }
                }
                BodyMassTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = uiState.weight,
                    onValueChange = updateWeight,
                    label = "${stringResource(R.string.body_mass_weight)}, $weightShortLabel",
                    expressionFormatter = expressionTransformer,
                    imeAction = ImeAction.Done,
                )
            }

            AnimatedContent(
                modifier = Modifier.fillMaxWidth(),
                targetState = uiState.result,
                transitionSpec = {
                    (fadeIn() togetherWith fadeOut()) using SizeTransform(false)
                },
                label = "Body mass value visibility",
            ) { targetState ->
                if (targetState.isEqualTo(BigDecimal.ZERO)) return@AnimatedContent

                BodyMassResult(
                    value = targetState,
                    range = uiState.normalWeightRange,
                    rangeSuffix = weightShortLabel,
                    formatterSymbols = uiState.formatterSymbols,
                )
            }

            ElevatedButton(
                onClick = {
                    openLink(mContext, "https://github.com/Myzel394/NumberHub/blob/master/HELP.md#body-mass-index")
                },
            ) {
                Text(text = stringResource(R.string.time_zone_no_results_button)) // TODO Rename
            }
        }
    }
}

@Preview
@Composable
fun PreviewBodyMassScreen() {
    BodyMassScreen(
        uiState = UIState.Ready(
            isMetric = false,
            height1 = TextFieldValue(),
            height2 = TextFieldValue(),
            weight = TextFieldValue(),
            normalWeightRange = BigDecimal(30) to BigDecimal(50),
            result = BigDecimal(18.5),
            formatterSymbols = FormatterSymbols(Token.SPACE, Token.PERIOD),
        ),
        updateHeight1 = {},
        updateHeight2 = {},
        updateWeight = {},
        updateIsMetric = {},
        openDrawer = {},
    )
}
