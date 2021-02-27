package krasnikov.project.pmgithubclient.repo.info.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CommentUser(@SerialName("login") val login: String)