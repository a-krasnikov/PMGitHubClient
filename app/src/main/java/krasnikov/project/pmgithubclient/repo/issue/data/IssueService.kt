package krasnikov.project.pmgithubclient.repo.issue.data

import krasnikov.project.pmgithubclient.repo.issue.data.model.Comment
import krasnikov.project.pmgithubclient.repo.issue.data.model.Reaction
import retrofit2.http.*

interface IssueService {

    @GET("/repos/{owner}/{repo}/issues/{issue_number}/comments")
    suspend fun getIssueComments(
            @Path("owner") owner: String,
            @Path("repo") repo: String,
            @Path("issue_number") issueNumber: Int,
            @Query("page") page: Int
    ): List<Comment>

    @Headers("Accept: application/vnd.github.squirrel-girl-preview+json")
    @GET("/repos/{owner}/{repo}/issues/comments/{comment_id}/reactions")
    suspend fun getIssueCommentReactions(
            @Path("owner") owner: String,
            @Path("repo") repo: String,
            @Path("comment_id") commentId: Int
    ): List<Reaction>

    @Headers("Accept: application/vnd.github.squirrel-girl-preview+json")
    @POST("/repos/{owner}/{repo}/issues/comments/{comment_id}/reactions")
    @FormUrlEncoded
    suspend fun createIssueCommentReaction(
            @Path("owner") owner: String,
            @Path("repo") repo: String,
            @Path("comment_id") commentId: Int,
            @Field("content") reactionContent: String
    ) : Reaction
}