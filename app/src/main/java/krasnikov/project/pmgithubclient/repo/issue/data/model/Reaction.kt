package krasnikov.project.pmgithubclient.repo.issue.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import krasnikov.project.pmgithubclient.userinfo.data.model.User

@Serializable
data class Reaction(@SerialName("id") val id: Int, @SerialName("content") val content: String) {
}