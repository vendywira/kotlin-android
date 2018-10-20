package app.learn.kotlin.di.module.builder

import app.learn.kotlin.Activity.MainActivity
import app.learn.kotlin.Activity.MainModule
import app.learn.kotlin.di.scope.ActivityScope
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {

    @ActivityScope
    @ContributesAndroidInjector(modules = arrayOf(MainModule::class))
    internal abstract fun provideMainActivity() : MainActivity
}