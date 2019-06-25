package com.example.lib_common.base

import com.example.lib_wedgit.bases.BaseApplication

abstract class CommonApplication :BaseApplication(){

    override fun onCreate() {
        super.onCreate()
        bindServiceOrHost()
    }

    abstract fun bindServiceOrHost()
}