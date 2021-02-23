package krasnikov.project.pmgithubclient.userinfo.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

sealed class UserProfile : Parcelable {
    @Parcelize
    object LoggedUser : UserProfile()

    @Parcelize
    data class User(val username: String) : UserProfile()
}