package krasnikov.project.pmgithubclient.repoinfo.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class Issue(
    @SerialName("number")
    val number: Int,
    @SerialName("title")
    val title: String,
    @SerialName("body")
    val body: String
) : Parcelable