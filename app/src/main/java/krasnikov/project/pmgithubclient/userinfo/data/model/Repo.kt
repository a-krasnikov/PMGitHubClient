package krasnikov.project.pmgithubclient.userinfo.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Repo(
    @SerialName("name")
    val name: String
)