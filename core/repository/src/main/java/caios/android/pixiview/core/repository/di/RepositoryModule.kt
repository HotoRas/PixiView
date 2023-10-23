package caios.android.pixiview.core.repository.di

import caios.android.pixiview.core.repository.FanboxRepository
import caios.android.pixiview.core.repository.FanboxRepositoryImpl
import caios.android.pixiview.core.repository.PixivRepository
import caios.android.pixiview.core.repository.PixivRepositoryImpl
import caios.android.pixiview.core.repository.UserDataRepository
import caios.android.pixiview.core.repository.UserDataRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Singleton
    @Binds
    fun bindUserDataRepository(userDataRepository: UserDataRepositoryImpl): UserDataRepository

    @Singleton
    @Binds
    fun bindPixivRepository(pixivRepository: PixivRepositoryImpl): PixivRepository

    @Singleton
    @Binds
    fun bindFanboxRepository(fanboxRepository: FanboxRepositoryImpl): FanboxRepository
}
