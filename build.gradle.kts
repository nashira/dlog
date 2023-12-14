import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("jvm")
    id("org.jetbrains.compose")
    id("app.cash.sqldelight") version "2.0.0"
}

group = "com.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    google()
}


val ktor_version: String by project

dependencies {
    implementation(compose.desktop.currentOs)

    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.5.0")

    implementation("io.insert-koin:koin-core:3.5.0")
    implementation("io.insert-koin:koin-core-coroutines:3.5.0")

    implementation("com.github.h0tk3y.betterParse:better-parse:0.4.4")

    implementation("app.cash.sqldelight:sqlite-driver:2.0.0")
    implementation("app.cash.sqldelight:coroutines-extensions:2.0.0")


    implementation("io.ktor:ktor-client-core:$ktor_version")
    implementation("io.ktor:ktor-client-cio:$ktor_version")
    implementation("io.ktor:ktor-client-websockets:$ktor_version")
}

kotlin {
    jvmToolchain(14)
}

compose.desktop {
    application {
        mainClass = "xyz.rthqks.dlog.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "dlog"
            packageVersion = "1.0.0"
        }
    }
}

sqldelight {
    databases {
        create("Database") {
            packageName.set("xyz.rthqks.dlog.db")
            generateAsync.set(true)
            srcDirs("src/main/sqldelight")
        }
    }
}