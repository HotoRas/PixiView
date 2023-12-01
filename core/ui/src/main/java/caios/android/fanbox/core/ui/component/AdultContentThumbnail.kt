package caios.android.fanbox.core.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NoAdultContent
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import caios.android.fanbox.core.ui.R
import caios.android.fanbox.core.ui.extensition.SimmerPlaceHolder
import caios.android.fanbox.core.ui.extensition.fanboxHeader
import caios.android.fanbox.core.ui.theme.center
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest

@Composable
fun AdultContentThumbnail(
    coverImageUrl: String?,
    modifier: Modifier = Modifier,
    isAllowedShow: Boolean = false,
    isTestUser: Boolean = false,
    onClickShowAdultContent: () -> Unit = { },
) {
    Box(modifier) {
        SubcomposeAsyncImage(
            modifier = Modifier
                .fillMaxSize()
                .blur(20.dp),
            model = ImageRequest.Builder(LocalContext.current)
                .fanboxHeader()
                .data(coverImageUrl)
                .build(),
            loading = {
                SimmerPlaceHolder()
            },
            contentScale = ContentScale.Crop,
            contentDescription = null,
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(if (isAllowedShow) Color.Black.copy(alpha = 0.5f) else Color.DarkGray),
        )

        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(
                space = 16.dp,
                alignment = Alignment.CenterVertically,
            ),
        ) {
            Icon(
                modifier = Modifier.size(48.dp),
                imageVector = Icons.Default.NoAdultContent,
                tint = Color.White,
                contentDescription = null,
            )

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(if (isTestUser) R.string.error_adult_content_test_user else R.string.error_adult_content),
                style = MaterialTheme.typography.bodySmall.center(),
                color = Color.White,
            )

            if (isAllowedShow) {
                Button(onClick = { onClickShowAdultContent.invoke() }) {
                    Text(text = stringResource(R.string.error_adult_content_description))
                }
            }
        }
    }
}
