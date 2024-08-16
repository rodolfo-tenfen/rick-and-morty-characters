pluginManagement {
    repositories {
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
        google()
        mavenCentral()
    }
}

rootProject.name = "Rick and Morty Characters"

include(":app")

include(":data:character")
include(":data:character:datasource:remote")
include(":data:character:datasource:memory")
include(":data:page:datasource:remote")

include(":domain:character")

include(":presentation:characteritem")
include(":presentation:home")
include(":presentation:characterdetails")

include(":presentation:theme")
include(":presentation:table")
include(":presentation:filter")
