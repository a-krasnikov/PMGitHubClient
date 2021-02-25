package krasnikov.project.pmgithubclient.issueinfo.data.model

import krasnikov.project.pmgithubclient.userinfo.data.model.User

data class Comment(val id: Int, val body: String, val user: User) {
}