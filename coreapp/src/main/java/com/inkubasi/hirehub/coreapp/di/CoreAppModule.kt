package com.inkubasi.hirehub.coreapp.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.inkubasi.hirehub.coreapp.BuildConfig
import com.inkubasi.hirehub.coreapp.data.source.NewHirehubRepository
import com.inkubasi.hirehub.coreapp.data.source.remote.NewRemoteDataSource
import com.inkubasi.hirehub.coreapp.data.source.remote.network.NewApiService
import com.inkubasi.hirehub.coreapp.domain.repository.NewIHirehubRepository
import com.inkubasi.hirehub.coreapp.domain.usecase.NewHireHubInteractor
import com.inkubasi.hirehub.coreapp.domain.usecase.NewHirehubUseCase
import com.inkubasi.hirehub.coreapp.ui.UserPreference
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.client.logger.ChatLogLevel
import io.getstream.chat.android.offline.plugin.factory.StreamOfflinePluginFactory
import io.getstream.chat.android.state.plugin.config.StatePluginConfig
import io.getstream.chat.android.state.plugin.factory.StreamStatePluginFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {
    single {
        OkHttpClient.Builder()
            .addInterceptor(
                if (BuildConfig.DEBUG) {
                    HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
                } else {
                    HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
                }
            )
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()
    }
    single {
        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(NewApiService::class.java)
    }
}

val newRepositoryModule = module {
    single { NewRemoteDataSource(get()) }
    single<NewIHirehubRepository> { NewHirehubRepository(get()) }
}


val newUseCaseModule = module {
    factory<NewHirehubUseCase> {
        NewHireHubInteractor(get())
    }
}

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
val preferenceModule = module {
    factory {
        UserPreference.getInstance(androidContext().dataStore)
    }
}

val chatClientModule = module {
    factory {
        val offlinePluginFactory = StreamOfflinePluginFactory(appContext = androidContext())
        val statePluginFactory = StreamStatePluginFactory(
            config = StatePluginConfig(
                backgroundSyncEnabled = true,
                userPresence = true,
            ),
            appContext = androidContext(),
        )
        ChatClient.Builder("xxnehajcahm4", androidApplication())
            .withPlugins(offlinePluginFactory, statePluginFactory)
            .logLevel(ChatLogLevel.ALL)
            .build()
    }
}

