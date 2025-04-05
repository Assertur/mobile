package ca.uqac.tp_mobile.di

import android.app.Application
import androidx.room.Room
import ca.uqac.tp_mobile.data.RoutineDatabase
import ca.uqac.tp_mobile.domain.useCase.DeleteRoutineUseCase
import ca.uqac.tp_mobile.domain.useCase.GetOneRoutineUseCase
import ca.uqac.tp_mobile.domain.useCase.GetRoutinesUseCase
import ca.uqac.tp_mobile.domain.useCase.RoutinesUseCases
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
        /*val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE routine ADD COLUMN locationName TEXT NOT NULL DEFAULT ''")
                database.execSQL("ALTER TABLE routine ADD COLUMN locationLat REAL NOT NULL DEFAULT 0.0")
                database.execSQL("ALTER TABLE routine ADD COLUMN locationLng REAL NOT NULL DEFAULT 0.0")
            }
        }*/
        return Room.databaseBuilder(
            context,
            RoutineDatabase::class.java,
            RoutineDatabase.DATABASE_NAME
        )
            //.addMigrations(MIGRATION_1_2) //faire la migration en rajoutant seulement les attributs manquants
            //.fallbackToDestructiveMigration() //Pour supprimer la base de données et la recréer
            .build()
    }

    @Provides
    @Singleton
    fun provideRoutineUseCases(db: RoutineDatabase): RoutinesUseCases {
        return RoutinesUseCases(
            getRoutines = GetRoutinesUseCase(db.dao),
            getOneRoutine = GetOneRoutineUseCase(db.dao),
            upsertRoutine = UpsertRoutineUseCase(db.dao),
            deleteRoutine = DeleteRoutineUseCase(db.dao)
        )
    }
}