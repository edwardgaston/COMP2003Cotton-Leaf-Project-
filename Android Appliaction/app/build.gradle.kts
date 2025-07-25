plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
    id("com.chaquo.python")
}




android {
    namespace = "com.example.cottonleaf"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.cottonleaf"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        ndk {
            abiFilters += listOf("arm64-v8a", "x86_64")
        }


    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}


chaquopy {
    defaultConfig {
        pip {
            install("numpy")
        }
        buildPython("C:\\Users\\woody\\python.exe") //interpreter
    }
    productFlavors { }
    sourceSets {
        getByName("main") {
            srcDir("src/main/python")
        }
    }
}





dependencies {
    implementation("com.google.firebase:firebase-firestore:23.0.3")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.firebase:firebase-auth:22.3.1")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation("androidx.camera:camera-core:1.3.1")
    implementation("androidx.camera:camera-camera2:1.3.1")
    implementation("androidx.camera:camera-lifecycle:1.3.1")
    implementation("androidx.camera:camera-view:1.3.1")
    implementation("androidx.activity:activity:1.3.1")
}