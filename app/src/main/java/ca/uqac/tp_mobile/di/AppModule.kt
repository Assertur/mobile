package ca.uqac.tp_mobile.di

import android.app.Application
import androidx.room.Room
import ca.uqac.tp_mobile.data.RoutineDatabase
import ca.uqac.tp_mobile.domain.useCase.DeleteRoutineUseCase
import ca.uqac.tp_mobile.domain.useCase.GetOneRoutineUseCase
import ca.uqac.tp_mobile.domain.useCase.GetRoutinesUseCase
import ca.uqac.tp_mobile.domain.useCase.RoutineUseCases
import ca.uqac.tp_mobile.domain.useCase.UpsertRoutineUseCase
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
    fun provideRoutinesDatabase(context: Application): RoutineDatabase {
        return Room.databaseBuilder(
            context,
            RoutineDatabase::class.java,
            RoutineDatabase.DATABASE_NAME
        ).build()
    }
    @Provides
    @Singleton
    fun provideRoutinesUseCases(db: RoutineDatabase) : RoutineUseCases {
        return RoutineUseCases(
            getRoutines = GetRoutinesUseCase(db.dao),
            getOneRoutine = GetOneRoutineUseCase(db.dao),
            upsertRoutine = UpsertRoutineUseCase(db.dao),
            deleteRoutine = DeleteRoutineUseCase(db.dao)
        )
    }
}