package krasnikov.project.pmgithubclient.userinfo.data.model

import krasnikov.project.pmgithubclient.utils.PagedList

data class UserInfoModel(
    val user: User,
    val repos: PagedList<Repo>
)