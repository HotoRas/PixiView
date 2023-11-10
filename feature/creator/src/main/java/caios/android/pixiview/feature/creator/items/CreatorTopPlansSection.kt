package caios.android.pixiview.feature.creator.items

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import caios.android.pixiview.core.model.fanbox.FanboxCreatorPlan
import caios.android.pixiview.core.ui.extensition.SimmerPlaceHolder
import caios.android.pixiview.core.ui.extensition.fanboxHeader
import caios.android.pixiview.core.ui.theme.bold
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import kotlinx.collections.immutable.ImmutableList

@Composable
internal fun CreatorTopPlansSection(
    creatorPlans: ImmutableList<FanboxCreatorPlan>,
    onClickPlan: (FanboxCreatorPlan) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        for (plan in creatorPlans) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .clickable { onClickPlan.invoke(plan) },
                shape = RoundedCornerShape(8.dp),
                colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surfaceColorAtElevation(4.dp)),
            ) {
                if (plan.coverImageUrl != null) {
                    SubcomposeAsyncImage(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(16 / 9f)
                            .clip(RoundedCornerShape(4.dp)),
                        model = ImageRequest.Builder(LocalContext.current)
                            .fanboxHeader()
                            .crossfade(true)
                            .data(plan.coverImageUrl)
                            .build(),
                        loading = {
                            SimmerPlaceHolder()
                        },
                        contentScale = ContentScale.Crop,
                        contentDescription = null,
                    )
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                    ) {
                        Text(
                            modifier = Modifier.weight(1f),
                            text = plan.title,
                            style = MaterialTheme.typography.titleMedium.bold(),
                            color = MaterialTheme.colorScheme.onSurface,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                        )

                        Card(
                            shape = RoundedCornerShape(4.dp),
                            colors = CardDefaults.cardColors(MaterialTheme.colorScheme.secondaryContainer),
                        ) {
                            Text(
                                modifier = Modifier.padding(6.dp, 4.dp),
                                text = "￥${plan.fee}",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                            )
                        }
                    }

                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = plan.description.trimEnd(),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }
        }
    }
}
