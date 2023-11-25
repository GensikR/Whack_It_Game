plugins {
    id("com.android.application")
}

android {
    namespace = "com.example.whack_it"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.whack_it"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    //TODO: Uncomment next two lines if you want to generate javadoc. DO NOT FORGET TO COMMENT THEM BACK OR THE APP WILL NOT START
    //implementation(files("C:\\Users\\gensi\\AppData\\Local\\Android\\Sdk\\platforms\\android-34\\android.jar"))
    //implementation(files("C:\\Users\\gensi\\AppData\\Local\\Android\\Sdk\\platforms\\android-34"))
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}
