package krasnikov.project.pmgithubclient.repo.info.data

import krasnikov.project.pmgithubclient.repo.info.data.model.Contributor
import krasnikov.project.pmgithubclient.repo.info.data.model.Issue
import krasnikov.project.pmgithubclient.repo.info.data.model.ReadMe
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
    ): List<Contributor>

    @GET("/repos/{owner}/{repo}/issues")
    suspend fun getIssues(
        @Path("owner") owner: String,
        @Path("repo") repo: String,
        @Query("page") page: Int,
    ): List<Issue>
}