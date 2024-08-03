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
# Kotlin 데이터 클래스 및 Firebase 모델을 위한 규칙
-keep class com.odal.wooco.datamodels.** { *; }
-keep class kotlin.Metadata { *; }
-keep class kotlin.jvm.internal.** { *; }
-keepclassmembers class com.odal.wooco.datamodels.CoachCategoryDataModel {
    public <init>();
}



# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile