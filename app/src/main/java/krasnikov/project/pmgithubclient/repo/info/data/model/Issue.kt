package krasnikov.project.pmgithubclient.repo.info.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import krasnikov.project.pmgithubclient.userinfo.data.model.User

@Serializable
@Parcelize
data class Issue(
    @SerialName("number")
    val number: Int,
    @SerialName("title")
    val title: String,
    @SerialName("body")
    val body: String,
    @SerialName("user")
    val user: @RawValue User,
) : Parcelable