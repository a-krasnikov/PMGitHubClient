package krasnikov.project.pmgithubclient.repo.issue.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import krasnikov.project.pmgithubclient.repo.info.data.model.CommentUser

@Serializable
data class Comment(
    @SerialName("id") val id: Int,
    @SerialName("body") val body: String,
    @SerialName("user") val user: CommentUser,
    @SerialName("issue_url") val issueUrl: String
)