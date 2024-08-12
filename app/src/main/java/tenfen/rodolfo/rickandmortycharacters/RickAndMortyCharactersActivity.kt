package tenfen.rodolfo.rickandmortycharacters

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import org.koin.androidx.compose.KoinAndroidContext
import org.koin.core.annotation.KoinExperimentalAPI
import tenfen.rodolfo.presentation.home.navigation.HomeRoute
import tenfen.rodolfo.presentation.home.navigation.homeRoute
import tenfen.rodolfo.presentation.theme.RickAndMortyCharactersTheme

class RickAndMortyCharactersActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            RickAndMortyCharactersContent()
        }
    }
}

@OptIn(KoinExperimentalAPI::class)
@Composable
private fun RickAndMortyCharactersContent() {
    KoinAndroidContext {
        RickAndMortyCharactersTheme {
            NavHost(navController = rememberNavController(), startDestination = HomeRoute) {
                homeRoute()
            }
        }
    }
}
