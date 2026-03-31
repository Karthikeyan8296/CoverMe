pluginManagement {
    repositories {
        maven {
            url = uri("https://maven.pkg.github.com/Karthikeyan8296/dependencyDoctor")
            credentials {
                username = System.getenv("GITHUB_ACTOR")
                    ?: providers.gradleProperty("gpr.user").orNull
                password = System.getenv("GITHUB_TOKEN")
                    ?: providers.gradleProperty("gpr.token").orNull
            }
        }
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        //ADD THIS — needed so the plugin jar itself can be downloaded
        maven {
            url = uri("https://maven.pkg.github.com/Karthikeyan8296/dependencyDoctor")
            credentials {
                username = System.getenv("GITHUB_ACTOR")
                    ?: providers.gradleProperty("gpr.user").orNull
                password = System.getenv("GITHUB_TOKEN")
                    ?: providers.gradleProperty("gpr.token").orNull
            }
        }
        google()
        mavenCentral()
    }
}

rootProject.name = "CoverMe"
include(":app")