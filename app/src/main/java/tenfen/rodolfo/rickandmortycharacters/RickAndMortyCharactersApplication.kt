package tenfen.rodolfo.rickandmortycharacters

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import tenfen.rodolfo.rickandmortycharacters.di.applicationModule

class RickAndMortyCharactersApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@RickAndMortyCharactersApplication)

            modules(applicationModule)
        }
    }
}
