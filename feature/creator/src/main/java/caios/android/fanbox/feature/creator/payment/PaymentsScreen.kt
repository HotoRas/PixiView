package caios.android.fanbox.feature.creator.payment

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import caios.android.fanbox.core.model.fanbox.id.CreatorId
import caios.android.fanbox.core.ui.AsyncLoadContents
import caios.android.fanbox.core.ui.component.PixiViewTopBar
import caios.android.fanbox.core.ui.extensition.drawVerticalScrollbar
import caios.android.fanbox.feature.creator.R
import caios.android.fanbox.feature.creator.payment.items.PaymentItem
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

@Composable
internal fun PaymentsRoute(
    navigateToCreatorPosts: (CreatorId) -> Unit,
    terminate: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: PaymentsViewModel = hiltViewModel(),
) {
    val screenState by viewModel.screenState.collectAsStateWithLifecycle()

    AsyncLoadContents(
        modifier = modifier,
        screenState = screenState,
        retryAction = { viewModel.fetch() },
    ) {
        PaymentsScreen(
            modifier = Modifier.fillMaxSize(),
            payments = it.payments.toImmutableList(),
            onClickCreatorPosts = navigateToCreatorPosts,
            onTerminate = terminate,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PaymentsScreen(
    onClickCreatorPosts: (CreatorId) -> Unit,
    onTerminate: () -> Unit,
    payments: ImmutableList<Payment>,
    modifier: Modifier = Modifier,
) {
    val state = rememberLazyListState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            PixiViewTopBar(
                modifier = Modifier.fillMaxWidth(),
                title = stringResource(R.string.library_navigation_payments),
                onClickNavigation = onTerminate,
                scrollBehavior = scrollBehavior,
            )
        },
        bottomBar = {
            Divider()
        },
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .drawVerticalScrollbar(state),
            state = state,
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            items(payments) { item ->
                PaymentItem(
                    modifier = Modifier.fillMaxWidth(),
                    payment = item,
                    onClickCreator = onClickCreatorPosts,
                )
            }

            item {
                Spacer(modifier = Modifier.navigationBarsPadding())
            }
        }
    }
}
