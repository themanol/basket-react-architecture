object ApplicationId{
    const val id = "com.themanol.reactbasket"
}

object Android {
    const val compileSdkVersion = 28
    const val buildToolsVersion = "29.0.0"
    const val minSdkVersion = 21
    const val targetSdkVersion = 28
    const val versionCode = 1
    const val versionName = "1.0"

}

object Modules {
    const val app = ":app"
    const val network = ":common:network"
    const val models = ":common:model"
    const val teamsDomain = ":features:teams:teams-domain"
    const val teamsPresentation = ":features:teams:teams_presentation"
    const val teamsData = ":features:teams:teams-data"
    const val teamsDI = ":features:teams:teams-di"
    const val gamesDomain = ":features:games:games-domain"
    const val gamesPresentation = ":features:games:games_presentation"
    const val gamesData = ":features:games:games-data"
    const val gamesDI = ":features:games:games-di"
    const val playersDomain = ":features:players:players-domain"
    const val playersData = ":features:players:players-data"
    const val playersDI = ":features:players:players-di"
    const val playersPresentation = ":features:players:players_presentation"
}

object Versions {
    const val kotlin = "1.3.50"
    const val andoidX = "1.1.0"
    const val fragment = "1.2.0-alpha04"
    const val recyclerView = "1.0.0"
    const val constraintLayout = "1.1.3"
    const val andoidXLifeCycle = "2.1.0"
    const val andoidNavigation = "2.1.0"
    const val junit = "4.12"
    const val test_runner = "1.2.0"
    const val espresso = "3.2.0"
    const val gradle = "3.5.0"
    const val retrofit = "2.6.2"
    const val loggingInterceptor = "4.2.2"
    const val koin = "2.0.1"
    const val coroutines = "1.3.2"
}

object Plugins {
    const val kotlin_gradle = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
    const val gradle = "com.android.tools.build:gradle:${Versions.gradle}"
    const val kotlin_stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"
    const val navigation_safe_args = "androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.andoidNavigation}"
}

object Libs {
    const val androidx_appcompat = "androidx.appcompat:appcompat:${Versions.andoidX}"
    const val recyclerview = "androidx.recyclerview:recyclerview:${Versions.recyclerView}"
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
    const val androidx_core = "androidx.core:core-ktx:${Versions.andoidX}"
    const val androidx_fragment = "androidx.fragment:fragment:${Versions.fragment}"
    const val androidx_fragment_ktx = "androidx.fragment:fragment-ktx:${Versions.fragment}"
    const val androidx_viewmodels = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.andoidXLifeCycle}"
    const val androidx_livedata = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.andoidXLifeCycle}"
    const val navigationFragment = "androidx.navigation:navigation-fragment-ktx:${Versions.andoidNavigation}"
    const val navigationUi = "androidx.navigation:navigation-ui-ktx:${Versions.andoidNavigation}"

    const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
    const val coroutines_android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"


    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val moshiConverter = "com.squareup.retrofit2:converter-moshi:${Versions.retrofit}"
    const val loggingInterceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.loggingInterceptor}"

    const val koinAndroid = "org.koin:koin-android:${Versions.koin}"
    const val koinViewModel = "org.koin:koin-androidx-viewmodel:${Versions.koin}"

}

object TestLibs {
    const val instrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    const val junit = "junit:junit:${Versions.junit}"
    const val test_runner = "androidx.test:runner:${Versions.test_runner}"
    const val espresso_core = "androidx.test.espresso:espresso-core:${Versions.espresso}"
}

