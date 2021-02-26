package krasnikov.project.pmgithubclient.search.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import krasnikov.project.pmgithubclient.userinfo.data.model.User

@Serializable
data class SearchResult(
    @SerialName("incomplete_results")
    val incompleteResults: Boolean,
    @SerialName("items")
    val items: List<User>
)