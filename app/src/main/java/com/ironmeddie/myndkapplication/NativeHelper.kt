package com.ironmeddie.myndkapplication

object NativeHelper {
    init {
        System.loadLibrary("myndkapplication") // Загружаем библиотеку
    }

    external fun crashAppAbrt() // Нативный метод для вызова краша
    external fun crashAppSegv() // Нативный метод для вызова краша
    external fun stringFromJNI() // Нативный метод для вызова краша
    external fun startCrash() // Нативный метод для вызова краша
    external fun circleCrash() // Нативный метод для вызова краша
    external fun tgkillCrash() // Нативный метод для вызова краша
}