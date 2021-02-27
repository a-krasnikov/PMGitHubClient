package krasnikov.project.pmgithubclient.repo.issue.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import krasnikov.project.pmgithubclient.userinfo.data.model.User

@Serializable
data class Comment(
    @SerialName("id") val id: Int,
    @SerialName("body") val body: String,
    @SerialName("user") val user: User,
    @SerialName("issue_url") val issueUrl: String
)