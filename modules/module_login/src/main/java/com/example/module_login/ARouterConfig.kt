package com.example.module_login

import com.alibaba.android.arouter.launcher.ARouter


const val STARTACTIVITY_NAME = "APP 应用入口页面"
const val STARTACTIVITY = "/login/StartActivity"

const val LOGINACTIVITY_NAME = "登录页面"
const val LOGINACTIVITY = "/login/LoginActivity"

fun startStartActivity() {
    ARouter.getInstance().build(STARTACTIVITY).navigation()
}

fun startLoginActivity() {
    ARouter.getInstance().build(LOGINACTIVITY).navigation()
}