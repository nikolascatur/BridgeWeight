package com.weight.bridge.di

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.weight.bridge.data.local.RealmRepositoryImpl
import com.weight.bridge.data.local.dao.BridgeTicketDao
import com.weight.bridge.data.remote.RemoteRepositoryImpl
import com.weight.bridge.domain.manager.RepositoryManager
import com.weight.bridge.domain.repository.FirebaseRepository
import com.weight.bridge.domain.repository.RealmRepository
import com.weight.bridge.util.Constant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRealm(): Realm =
        Realm.open(RealmConfiguration.create(schema = setOf(BridgeTicketDao::class)))

    @Provides
    @Singleton
    fun provideRealmRepository(realm: Realm): RealmRepository = RealmRepositoryImpl(realm)


//    @Singleton
//    @Provides
//    fun provideRealtimeDatabase(): DatabaseReference =
//        FirebaseDatabase.getInstance().getReferenceFromUrl(Constant.DATABASE_URL)

    @Singleton
    @Provides
    fun providesFirebaseRepository(): FirebaseRepository =
        RemoteRepositoryImpl()

    @Singleton
    @Provides
    fun provideRepositoryManager(realmRepository: RealmRepository, database: FirebaseRepository) =
        RepositoryManager(realmRepository, database)
}