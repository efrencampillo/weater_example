package com.example.weater.dependency

import com.example.weater.presentation.MainActivity
import dagger.Component

@Component(modules = [ModuleProvider::class])
interface Component {
    fun inject(mainActivity: MainActivity)
}
