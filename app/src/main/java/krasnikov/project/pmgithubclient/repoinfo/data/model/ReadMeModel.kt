package krasnikov.project.pmgithubclient.repoinfo.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReadMeModel(
    @SerialName("content")
    val content: String
)