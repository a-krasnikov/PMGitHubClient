package krasnikov.project.pmgithubclient.repoinfo.data

import krasnikov.project.pmgithubclient.repoinfo.data.model.Issue
import krasnikov.project.pmgithubclient.repoinfo.data.model.ReadMe
import krasnikov.project.pmgithubclient.repoinfo.ui.contributors.User
import retrofit2.http.*

interface RepositoryService {
    @GET("/repos/{owner}/{repo}/readme")
    suspend fun getReadMe(
        @Path("owner") owner: String,
        @Path("repo") repo: String,
    ): ReadMe

    @GET("/repos/{owner}/{repo}/contributors")
    suspend fun getContributors(
        @Path("owner") owner: String,
        @Path("repo") repo: String,
        @Query("page") page: Int,
    ): List<User>

    @GET("/repos/{owner}/{repo}/issues")
    suspend fun getIssues(
        @Path("owner") owner: String,
        @Path("repo") repo: String,
        @Query("page") page: Int,
    ): List<Issue>
}