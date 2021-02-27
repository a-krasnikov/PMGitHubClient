package krasnikov.project.pmgithubclient.userinfo.data

import krasnikov.project.pmgithubclient.userinfo.data.model.Repo
import krasnikov.project.pmgithubclient.userinfo.data.model.User
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface UserService {
    @GET("/user")
    suspend fun getLoggedUser(): User

    @GET("users/{username}")
    suspend fun getUser(
        @Path("username") username: String
    ): User

    @GET("/user/repos?affiliation=owner")
    suspend fun getLoggedUserRepos(
        @Query("page") page: Int
    ): List<Repo>

    @GET("/users/{username}/repos")
    suspend fun getUserRepos(
        @Path("username") username: String,
        @Query("page") page: Int,
    ): List<Repo>
}