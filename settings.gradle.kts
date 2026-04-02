pluginManagement {
    repositories {
        maven {
            url = uri("https://maven.pkg.github.com/Karthikeyan8296/dependencyDoctor")
            credentials {
                username = System.getenv("GITHUB_ACTOR")
                    ?: providers.gradleProperty("gpr.user").orNull
                password = System.getenv("GH_PACKAGES_TOKEN")
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

//should have this!!!
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        maven {
            url = uri("https://maven.pkg.github.com/Karthikeyan8296/dependencyDoctor")
            credentials {
                username = System.getenv("GITHUB_ACTOR")
                    ?: providers.gradleProperty("gpr.user").orNull
                password = System.getenv("GH_PACKAGES_TOKEN")
                    ?: System.getenv("GITHUB_TOKEN")
                            ?: providers.gradleProperty("gpr.token").orNull
            }
        }
        google()
        mavenCentral()
    }
}


rootProject.name = "CoverMe"
include(":app")