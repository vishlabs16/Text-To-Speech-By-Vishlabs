# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

####################################
# TEXT TO SPEECH APP – PROGUARD RULES
####################################

######## ANDROID TTS (CRITICAL)
-keep class android.speech.tts.** { *; }
-dontwarn android.speech.tts.**

######## VOICE & LOCALE
-keep class android.speech.tts.Voice { *; }
-keep class java.util.Locale { *; }

######## VIEWMODEL
-keep class * extends androidx.lifecycle.ViewModel { *; }

######## JETPACK COMPOSE
-keep class androidx.compose.** { *; }
-dontwarn androidx.compose.**
-keepattributes KotlinMetadata

######## MATERIAL & ICONS
-keep class androidx.compose.material.** { *; }
-keep class androidx.compose.material3.** { *; }

######## COROUTINES & FLOW
-keep class kotlinx.coroutines.** { *; }
-dontwarn kotlinx.coroutines.**

######## DATASTORE (SAFE TO KEEP)
-keep class androidx.datastore.** { *; }
-dontwarn androidx.datastore.**

######## YOUR APP PACKAGE (RECOMMENDED)
-keep class com.rach.texttospeechbyvishlabs.** { *; }

######## GENERAL SAFETY
-ignorewarnings
