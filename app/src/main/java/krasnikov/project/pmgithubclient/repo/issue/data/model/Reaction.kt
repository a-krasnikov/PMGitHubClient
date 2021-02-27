package krasnikov.project.pmgithubclient.repo.issue.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Reaction(
    @SerialName("content")
    val content: String
)