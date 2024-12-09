package ipca.example.newsapp

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ipca.example.newsapp.respository.ArticlesAPI
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideContext(@ApplicationContext app:Context) : Context{
        return app
    }

    @Provides
    @Singleton
    fun provideArticlesAPI():ArticlesAPI {
        return ArticlesAPI
    }

}