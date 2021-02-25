package krasnikov.project.pmgithubclient.issueinfo.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import krasnikov.project.pmgithubclient.repoinfo.data.model.CommentUser

@Serializable
data class Comment(
        @SerialName("id") val id: Int,
        @SerialName("body") val body: String,
        @SerialName("user") val user: CommentUser) {
}