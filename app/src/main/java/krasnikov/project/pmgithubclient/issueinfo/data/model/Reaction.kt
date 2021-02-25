package krasnikov.project.pmgithubclient.issueinfo.data.model

import krasnikov.project.pmgithubclient.userinfo.data.model.User

data class Reaction(val id: Int, val user: User, val content: String) {
}