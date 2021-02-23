package krasnikov.project.pmgithubclient.repoinfo.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class IssueModel(
    @SerialName("title")
    val title: String,
    @SerialName("body")
    val body: String
)