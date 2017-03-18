1、在项目的build.gradle文件中添加：
    allprojects {
        repositories {
            jcenter()
            mavenLocal()
        }
    }
2、在libapp的build.gradle文件中添加：
    apply plugin: 'com.android.library'
    apply plugin: 'maven'
    android {
        compileSdkVersion 25
        buildToolsVersion "25.0.2"

        defaultConfig {
            minSdkVersion 15
            targetSdkVersion 25
            versionCode 1
            versionName "1.0"
            testInstrumentationRunner "android.support.test.runner.AndroidJUnitRunner"

        }
        buildTypes {
            release {
                minifyEnabled false
                proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro'
            }
        }
    }
    uploadArchives {
        repositories.mavenDeployer {
            repository(url: "http://localhost:8081/repository/com.zscdm.utils2017/") {
                authentication(userName: "yourusername", password: "yourpassword")
            }
            pom.version = "0.0.3"
            pom.artifactId = "ZSCDumin"
            pom.groupId = "com.zscdumin2017"
        }
    }
    dependencies {
        compile fileTree(dir: 'libs', include: ['*.jar'])
        androidTestCompile('com.android.support.test.espresso:espresso-core:2.2.2', {
            exclude group: 'com.android.support', module: 'support-annotations'
        })
        compile 'com.android.support:appcompat-v7:25.3.0'
        testCompile 'junit:junit:4.12'
    }

3、在app的build.gradle文件中添加：
    apply plugin: 'com.android.application'
    android {
        compileSdkVersion 25
        buildToolsVersion "25.0.2"

        defaultConfig {
            applicationId "com.edu.zscdm.myapplication"
            minSdkVersion 15
            targetSdkVersion 25
            versionCode 1
            versionName "1.0"

            testInstrumentationRunner "android.support.test.runner.AndroidJUnitRunner"

        }
        buildTypes {
            release {
                minifyEnabled false
                proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro'
            }
        }
    }
    allprojects {
        repositories {
            maven {
                url "http://localhost:8081/repository/com.zscdm.utils2017/"
            }
        }
    }
    dependencies {
        compile fileTree(dir: 'libs', include: ['*.jar'])
        androidTestCompile('com.android.support.test.espresso:espresso-core:2.2.2', {
            exclude group: 'com.android.support', module: 'support-annotations'
        })
        compile 'com.android.support:appcompat-v7:25.2.0'
        compile 'com.android.support.constraint:constraint-layout:1.0.1'
        compile 'com.android.support:design:25.2.0'
        compile 'com.zscdumin2017:ZSCDumin:0.0.3'
        testCompile 'junit:junit:4.12'
    }
