/*
 * Unitto is a calculator for Android
 * Copyright (c) 2023-2024 Elshan Agaev
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

package app.myzel394.numberhub.core.ui.common.textfield

import android.content.ClipData
import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import app.myzel394.numberhub.core.base.FormatterSymbols
import app.myzel394.numberhub.core.ui.theme.LocalNumberTypography

@Composable
fun FixedExpressionInputTextField(
    modifier: Modifier = Modifier,
    value: String,
    formatterSymbols: FormatterSymbols,
    textColor: Color,
    onClick: () -> Unit,
) {
    val clipboardManager = FormattedExpressionClipboardManager(
        formatterSymbols = formatterSymbols,
        clipboardManager = LocalContext.current.getSystemService(Context.CLIPBOARD_SERVICE)
            as android.content.ClipboardManager,
    )

    CompositionLocalProvider(LocalClipboardManager provides clipboardManager) {
        SelectionContainer(
            modifier = Modifier
                .horizontalScroll(rememberScrollState()) // Must be first
                .clickable(onClick = onClick)
                .then(modifier),
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = value.formatExpression(formatterSymbols),
                style = LocalNumberTypography.current.displaySmall
                    .copy(color = textColor, textAlign = TextAlign.End),
            )
        }
    }
}

private class FormattedExpressionClipboardManager(
    private val formatterSymbols: FormatterSymbols,
    private val clipboardManager: android.content.ClipboardManager,
) : ClipboardManager {
    override fun getText(): AnnotatedString? = null

    override fun setText(annotatedString: AnnotatedString) {
        clipboardManager.setPrimaryClip(
            ClipData.newPlainText(
                PLAIN_TEXT_LABEL,
                annotatedString
                    .text
                    .replace(formatterSymbols.grouping, ""),
            ),
        )
    }
}
