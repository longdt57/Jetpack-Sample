package com.example.jetpackcompose.ui.cart

import android.app.Activity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpackcompose.R
import com.example.jetpackcompose.ui.base.BaseActivity
import com.example.jetpackcompose.ui.theme.JetpackComposeTheme
import com.example.jetpackcompose.util.ProductItemPreviewData
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartActivity : BaseActivity() {

    override val viewModel: CartViewModel by viewModels()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackComposeTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = { CartTopAppBar() },
                ) { padding ->
                    Surface(modifier = Modifier.padding(padding)) {
                        Column {
                            viewModel.items.collectAsState().value.forEach {
                                CartView(it)
                                Spacer(modifier = Modifier.padding(vertical = 8.dp))
                            }
                        }
                    }
                    LoadingProgress()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CartTopAppBar() {
    val activity = LocalContext.current as Activity
    TopAppBar(
        title = { Text(text = stringResource(R.string.cart_screen_title)) },
        navigationIcon = {
            IconButton(onClick = { activity.finish() }) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Cart Btn Back")
            }
        },
    )
}

@Composable
private fun CartView(cartItem: ShoppingItem) {
    val item = cartItem.item
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(modifier = Modifier) {
            Text(text = item.name, style = MaterialTheme.typography.labelLarge)
            Text(
                stringResource(id = R.string.price_x, item.price.toString()),
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(start = 8.dp),
            )
        }
        Spacer(modifier = Modifier.weight(1f))

        Text("Count: ${cartItem.count}")
    }
}

@Composable
@Preview(showBackground = true)
private fun CartPreView() {
    CartView(cartItem = ShoppingItem(ProductItemPreviewData.FakeItem, 2))
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview(showBackground = true)
private fun CartScreenPreview() {
    JetpackComposeTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = { CartTopAppBar() },
        ) { padding ->
            Surface(modifier = Modifier.padding(padding)) {
                Column {
                    ProductItemPreviewData.FakeListData.take(2)
                        .map { ShoppingItem(it, 2) }
                        .forEach {
                            CartView(it)
                            Spacer(modifier = Modifier.padding(vertical = 8.dp))
                        }
                }
            }
        }
    }
}
