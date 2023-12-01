package caios.android.fanbox.core.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import caios.android.fanbox.core.ui.R
import caios.android.fanbox.core.ui.theme.bold
import caios.android.fanbox.core.ui.theme.center

@Composable
fun PagingErrorSection(
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(
            space = 8.dp,
            alignment = Alignment.CenterVertically,
        ),
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.error_network),
            style = MaterialTheme.typography.titleMedium.bold().center(),
            color = MaterialTheme.colorScheme.onSurface,
        )

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.error_paging),
            style = MaterialTheme.typography.bodyMedium.center(),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )

        Button(
            modifier = Modifier.padding(top = 24.dp),
            onClick = onRetry,
        ) {
            Text(
                text = stringResource(R.string.common_reload),
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onPrimary,
            )
        }
    }
}

@Preview
@Composable
fun PagingErrorSectionPreview() {
    PagingErrorSection(
        modifier = Modifier.fillMaxWidth(),
        onRetry = {},
    )
}
