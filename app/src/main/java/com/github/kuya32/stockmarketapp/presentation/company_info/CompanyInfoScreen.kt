package com.github.kuya32.stockmarketapp.presentation.company_info

import android.widget.Space
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.github.kuya32.stockmarketapp.R
import com.github.kuya32.stockmarketapp.ui.theme.DarkBlue
import com.ramcosta.composedestinations.annotation.Destination

@Composable
@Destination
fun CompanyInfoScreen(
    symbol: String,
    viewModel: CompanyInfoViewModel = hiltViewModel()
) {
    val state = viewModel.state
    if (state.error == null) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(DarkBlue)
                .padding(16.dp)
        ) {
           state.company?.let { company ->
               Row(
                   horizontalArrangement = Arrangement.SpaceBetween,
                   modifier = Modifier.fillMaxWidth()
               ) {
                   Text(
                       text = company.name,
                       fontWeight = FontWeight.Bold,
                       fontSize = 18.sp,
                       overflow = TextOverflow.Ellipsis,
                   )
                   Image(
                       painter = painterResource(id = R.drawable.ic_refresh),
                       contentDescription = stringResource(id = R.string.refesh_button),
                       modifier = Modifier
                           .size(24.dp)
                           .clickable { viewModel.onEvent(CompanyInfoEvent.Refresh) }
                   )
               }
               Spacer(modifier = Modifier.height(8.dp))
               Text(
                   text = company.symbol,
                   fontStyle = FontStyle.Italic,
                   fontSize = 14.sp,
                   modifier = Modifier.fillMaxWidth()
               )
               Spacer(modifier = Modifier.height(8.dp))
               Divider(
                   modifier = Modifier
                       .fillMaxWidth()
               )
               Spacer(modifier = Modifier.height(8.dp))
               Text(
                   text = "Industry: ${company.industry}",
                   fontSize = 14.sp,
                   modifier = Modifier.fillMaxWidth(),
                   overflow = TextOverflow.Ellipsis
               )
               Spacer(modifier = Modifier.height(8.dp))
               Text(
                   text = "Country: ${company.country}",
                   fontSize = 14.sp,
                   modifier = Modifier.fillMaxWidth(),
                   overflow = TextOverflow.Ellipsis
               )
               Spacer(modifier = Modifier.height(8.dp))
               Divider(
                   modifier = Modifier
                       .fillMaxWidth()
               )
               Spacer(modifier = Modifier.height(8.dp))
               Text(
                   text = company.description,
                   fontSize = 12.sp,
                   modifier = Modifier.fillMaxWidth(),
               )
               if (state.stockInfos.isNotEmpty()) {
                   Spacer(modifier = Modifier.height(16.dp))
                   Text(text = "Market Summary")
                   Spacer(modifier = Modifier.height(32.dp))
                   StockChart(
                       infos = state.stockInfos,
                       modifier = Modifier
                           .fillMaxWidth()
                           .height(250.dp)
                           .align(CenterHorizontally)
                   )
               }
           }
        }
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Center
    ) {
        if (state.isLoading) {
            CircularProgressIndicator()
        } else if (state.error != null) {
            Text(
                text = state.error,
                color = MaterialTheme.colors.error
            )
        }
    }
}