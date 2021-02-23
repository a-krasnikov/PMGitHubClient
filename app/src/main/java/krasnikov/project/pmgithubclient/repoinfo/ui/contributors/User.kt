package krasnikov.project.pmgithubclient.repoinfo.ui.contributors

import kotlinx.serialization.Serializable

@Serializable
data class User(val login: String) {
}