package com.example.techshop

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.techshop.ui.theme.TechShopTheme
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Headset
import androidx.compose.material.icons.filled.Laptop
import androidx.compose.material.icons.filled.Mouse
import androidx.compose.material.icons.filled.Speaker
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.*
import androidx.compose.runtime.*

import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.NumberFormat
import java.util.Locale
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items

import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.clickable

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.IconButton
import androidx.compose.ui.draw.rotate



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val produtos = listOf(
            Produto("Fone Bluetooth", 150.0, "Áudio", Icons.Default.Headset),
            Produto("Notebook", 3200.0, "Informática", Icons.Default.Laptop),
            Produto("Mouse", 45.0, "Informática", Icons.Default.Mouse),
            Produto("Caixa de Som", 220.0, "Áudio", Icons.Default.Speaker)
        )
        setContent {
            TechShopTheme {
                Scaffold(topBar = { TechShopTopAppBar() }) { innerPadding ->
                    CatalogoDeProdutos(
                        produtos = produtos,
                        modifier = Modifier.padding(innerPadding)
                    )
                }

            }
        }
    }
}

data class Produto(
    val nome: String,
    val preco: Double,
    val categoria: String,
    val icone: ImageVector
)



@Composable
fun ProdutoCard(produto: Produto, modifier: Modifier = Modifier) {
    var expandido by remember { mutableStateOf(false) }
    Card(modifier = modifier
        .fillMaxWidth()
        .animateContentSize(
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessLow
            )
        )
    ) {
        Row(
            modifier = Modifier.padding(16.dp).clickable{ expandido = !expandido },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = produto.icone,
                contentDescription = null,
                modifier = Modifier.size(40.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = produto.nome, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                Text(text = produto.categoria, fontSize = 13.sp, color = Color.Gray)
            }
            Text(
                text = NumberFormat.getCurrencyInstance(Locale("pt", "BR")).format(produto.preco),
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            )

            val rotacao by animateFloatAsState(
                targetValue = if (expandido) 180f else 0f,
                label = "rotacaoSeta"
            )

            IconButton(onClick = { expandido = !expandido }) {
                Icon(
                    imageVector = Icons.Default.ExpandMore,
                    contentDescription = if (expandido) "Recolher detalhes" else "Expandir detalhes",
                    modifier = Modifier.rotate(rotacao)
                )
            }

        }

        if (expandido) {
            Text(
                text = "Categoria: ${produto.categoria}. Disponível para entrega imediata.",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
            )
        }

    }
}


@Composable
fun CatalogoDeProdutos(produtos: List<Produto>, modifier: Modifier = Modifier) {
    var termoBusca by remember { mutableStateOf("") }
    val produtosFiltrados = produtos.filter {
        it.nome.contains(termoBusca, ignoreCase = true)
    }
    val produtosPorCategoria = produtosFiltrados.groupBy { it.categoria }


    Column(modifier = modifier) {
        TextField(
            value = termoBusca,
            onValueChange = { termoBusca = it },
            label = { Text("Buscar produto") },
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        )

        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            produtosPorCategoria.forEach { (categoria, produtosDaCategoria) ->
                item {
                    Text(
                        text = categoria,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                items(produtosDaCategoria, key = { produto -> produto.nome }) { produto ->
                    ProdutoCard(produto = produto)
                }

            }
        }

    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TechShopTopAppBar(modifier: Modifier = Modifier) {
    CenterAlignedTopAppBar(
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(imageVector = Icons.Default.ShoppingCart, contentDescription = null)
                Spacer(modifier = Modifier.width(4.dp))
                Text("TechShop", style = MaterialTheme.typography.titleLarge)
            }
        },
        expandedHeight = 48.dp,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
            navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
            actionIconContentColor = MaterialTheme.colorScheme.onPrimary
        ),
        modifier = modifier
    )
}





