package krasnikov.project.pmgithubclient.repo.info.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReadMe(
    @SerialName("content")
    val content: String
)