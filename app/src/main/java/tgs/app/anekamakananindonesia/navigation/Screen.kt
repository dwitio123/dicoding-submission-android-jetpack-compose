package tgs.app.anekamakananindonesia.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Detail : Screen("detail")
    object About : Screen("about")
}
