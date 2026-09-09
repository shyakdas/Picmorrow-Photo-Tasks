// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.ktlint) apply false
    alias(libs.plugins.detekt) apply false
    alias(libs.plugins.paparazzi) apply false
}

buildscript {
    dependencies {
        constraints {
            classpath("org.bouncycastle:bcpkix-jdk18on:1.85")
            classpath("org.bouncycastle:bcprov-jdk18on:1.85")
            classpath("org.bouncycastle:bcutil-jdk18on:1.85")
            classpath("org.bitbucket.b_c:jose4j:0.9.6")
            classpath("org.jdom:jdom2:2.0.6.1")
            classpath("org.apache.commons:commons-compress:1.28.0")
            classpath("org.apache.commons:commons-lang3:3.17.0")
            classpath("ch.qos.logback:logback-core:1.5.18")
            classpath("io.netty:netty-codec:4.1.118.Final")
            classpath("io.netty:netty-codec-http:4.1.118.Final")
            classpath("io.netty:netty-codec-http2:4.1.118.Final")
            classpath("io.netty:netty-handler:4.1.118.Final")
        }
    }
}
