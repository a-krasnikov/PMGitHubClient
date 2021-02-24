package krasnikov.project.pmgithubclient.repoinfo.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Issue(
    @SerialName("number")
    val number: Int,
    @SerialName("title")
    val title: String,
    @SerialName("body")
    val body: String
)