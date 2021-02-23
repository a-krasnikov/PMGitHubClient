package krasnikov.project.pmgithubclient.repoinfo.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class IssueModel(
    @SerialName("owner")
    val owner: String
)