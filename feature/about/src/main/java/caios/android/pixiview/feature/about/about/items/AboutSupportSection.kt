package caios.android.pixiview.feature.about.about.items

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Feedback
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.Redeem
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import caios.android.pixiview.core.ui.theme.bold
import caios.android.pixiview.feature.about.R

@Composable
internal fun AboutSupportSection(
    onClickVersionHistory: () -> Unit,
    onClickRateApp: () -> Unit,
    onClickEmail: () -> Unit,
    onClickDonation: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier) {
        Card(
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(6.dp),
            colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surfaceContainerHigh),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                Text(
                    modifier = Modifier
                        .padding(
                            top = 24.dp,
                            start = 24.dp,
                            end = 24.dp,
                            bottom = 18.dp,
                        )
                        .fillMaxWidth(),
                    text = stringResource(R.string.about_support).uppercase(),
                    style = MaterialTheme.typography.bodyMedium.bold(),
                    color = MaterialTheme.colorScheme.primary,
                )

                AboutSupportItem(
                    modifier = Modifier.fillMaxWidth(),
                    titleRes = R.string.about_support_version_history,
                    imageVector = Icons.Outlined.History,
                    onClick = onClickVersionHistory,
                )

                AboutSupportItem(
                    modifier = Modifier.fillMaxWidth(),
                    titleRes = R.string.about_support_rate_app,
                    imageVector = Icons.Outlined.StarOutline,
                    onClick = onClickRateApp,
                )

                AboutSupportItem(
                    modifier = Modifier.fillMaxWidth(),
                    titleRes = R.string.about_support_opinion,
                    imageVector = Icons.Outlined.Feedback,
                    onClick = onClickEmail,
                )

                AboutSupportItem(
                    modifier = Modifier
                        .padding(bottom = 16.dp)
                        .fillMaxWidth(),
                    titleRes = R.string.about_support_donate,
                    imageVector = Icons.Outlined.Redeem,
                    onClick = onClickDonation,
                )
            }
        }
    }
}

@Composable
private fun AboutSupportItem(
    @StringRes titleRes: Int,
    imageVector: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .clickable { onClick.invoke() }
            .padding(horizontal = 24.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Icon(
            modifier = Modifier.size(20.dp),
            imageVector = imageVector,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
        )

        Text(
            modifier = Modifier.weight(1f),
            text = stringResource(titleRes),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
        )
    }
}
