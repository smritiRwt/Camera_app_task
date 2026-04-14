package com.example.cameraapp.di

import com.example.cameraapp.data.datasource.CameraDataSource
import com.example.cameraapp.data.repository.CameraRepositoryImpl
import com.example.cameraapp.domain.repository.CameraRepository
import com.example.cameraapp.domain.usecase.CaptureImagesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideCameraDataSource(): CameraDataSource {
        return CameraDataSource()
    }

    @Provides
    @Singleton
    fun provideCameraRepository(dataSource: CameraDataSource): CameraRepository {
        return CameraRepositoryImpl(dataSource)
    }

    @Provides
    @Singleton
    fun provideCaptureImagesUseCase(repository: CameraRepository): CaptureImagesUseCase {
        return CaptureImagesUseCase(repository)
    }
}
