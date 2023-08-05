package tgs.app.anekamakananindonesia

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import tgs.app.anekamakananindonesia.model.FoodsData
import tgs.app.anekamakananindonesia.navigation.Screen
import tgs.app.anekamakananindonesia.screen.AboutScreen
import tgs.app.anekamakananindonesia.screen.DetailScreen
import tgs.app.anekamakananindonesia.ui.theme.AnekaMakananIndonesiaTheme

@Composable
fun AnekaMakananApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
    ) {
        composable(Screen.Home.route) {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {
                            Text(stringResource(R.string.app_name))
                        },
                        backgroundColor = colorResource(R.color.purple_500),
                        actions = {
                            IconButton(onClick = { navController.navigate(Screen.About.route) }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_person),
                                    contentDescription = stringResource(R.string.about_page)
                                )
                            }
                        },
                    )
                },
            ) { paddingValues ->
                Box(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                ) {
                    LazyColumn {
                        items(FoodsData.foods, key = { it.name }) { food ->
                            FoodsListItem(
                                name = food.name,
                                photo = food.photo,
                                description = food.description,
                                ingredient = food.ingredient,
                                procedure = food.procedure,
                                modifier = Modifier.fillMaxWidth(),
                                navController = navController
                            )
                        }
                    }
                }
            }
        }
        composable(
            route = Screen.Detail.route + "/{name}/{photo}/{description}/{ingredient}/{procedure}",
            arguments = listOf(
                navArgument("name") {
                    type = NavType.StringType
                },
                navArgument("photo") {
                    type = NavType.IntType
                },
                navArgument("description") {
                    type = NavType.StringType
                },
                navArgument("ingredient") {
                    type = NavType.StringType
                },
                navArgument("procedure") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            DetailScreen(
                navController = navController,
                name = backStackEntry.arguments?.getString("name") ?: "",
                photo = backStackEntry.arguments?.getInt("photo") ?: 0,
                description = backStackEntry.arguments?.getString("description") ?: "",
                ingredient = backStackEntry.arguments?.getString("ingredient") ?: "",
                procedure = backStackEntry.arguments?.getString("procedure") ?: "",
            )
        }
        composable(Screen.About.route) {
            AboutScreen(navController = navController)
        }
    }

}

@Composable
fun FoodsListItem(
    modifier: Modifier = Modifier,
    name: String,
    photo: Int,
    description: String,
    ingredient: String,
    procedure: String,
    navController: NavController
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.clickable {
            navController.navigate(Screen.Detail.route + "/$name/$photo/$description/$ingredient/$procedure")
        }
    ) {
        Image(
            painter = painterResource(id = photo),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(8.dp)
                .size(60.dp)
                .clip(CircleShape)
        )
        Text(
            text = name,
            fontWeight = FontWeight.Medium,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(start = 16.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AnekaMakananAppPreview() {
    AnekaMakananIndonesiaTheme {
        AnekaMakananApp()
    }
}