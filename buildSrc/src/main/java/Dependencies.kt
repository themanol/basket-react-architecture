object ApplicationId{
    val id = "com.themanol.reactbasket"
}

object Android {
    val compileSdkVersion = 28
    val buildToolsVersion = "29.0.0"
    val minSdkVersion = 21
    val targetSdkVersion = 28
    val versionCode = 1
    val versionName = "1.0"

}

object Modules {
    val app = ":app"
    val network = ":common:network"
    val models = ":common:model"
    val teamsDomain = ":features:teams:domain"
    val teamsPresentation = ":features:teams:presentation"
    val teamsData = ":features:teams:data"
    val teamsDI = ":features:teams:di"
}

object Versions {
    val kotlin = "1.3.50"
    val andoidX = "1.1.0"
    val recyclerView = "1.0.0"
    val andoidXLifeCycle = "2.1.0"
    val andoidNavigation = "2.1.0"
    val junit = "4.12"
    val test_runner = "1.2.0"
    val espresso = "3.2.0"
    val gradle = "3.5.0"
    val rxjava = "2.2.10"
    val rxkotlin = "2.3.0"
    val retrofit = "2.6.0"
    val loggingInterceptor = "4.0.0"
    val koin = "2.0.1"
}

object Plugins {
    val kotlin_gradle = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
    val gradle = "com.android.tools.build:gradle:${Versions.gradle}"
    val kotlin_stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"
}

object Libs {
    val androidx_appcompat = "androidx.appcompat:appcompat:${Versions.andoidX}"
    val recyclerview = "androidx.recyclerview:recyclerview:${Versions.recyclerView}"
    val androidx_core = "androidx.core:core-ktx:${Versions.andoidX}"
    val androidx_viewmodels = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.andoidXLifeCycle}"
    val androidx_livedata = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.andoidXLifeCycle}"
    val navigationFragment = "androidx.navigation:navigation-fragment-ktx:${Versions.andoidNavigation}"
    val navigationUi = "androidx.navigation:navigation-ui-ktx:${Versions.andoidNavigation}"

    val rxjava = "io.reactivex.rxjava2:rxjava:${Versions.rxjava}"
    val rxkotlin = "io.reactivex.rxjava2:rxkotlin:${Versions.rxkotlin}"

    val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    val rxjavaAdapter = "com.squareup.retrofit2:adapter-rxjava2:${Versions.retrofit}"
    val moshiConverter = "com.squareup.retrofit2:converter-moshi:${Versions.retrofit}"
    val loggingInterceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.loggingInterceptor}"

    val koinAndroid = "org.koin:koin-android:${Versions.koin}"
    val koinViewModel = "org.koin:koin-androidx-viewmodel:${Versions.koin}"

}

object TestLibs {
    val instrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    val junit = "junit:junit:${Versions.junit}"
    val test_runner = "androidx.test:runner:${Versions.test_runner}"
    val espresso_core = "androidx.test.espresso:espresso-core:${Versions.espresso}"
}

