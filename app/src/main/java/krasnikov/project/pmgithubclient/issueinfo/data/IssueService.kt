package krasnikov.project.pmgithubclient.issueinfo.data

import krasnikov.project.pmgithubclient.issueinfo.data.model.Comment
import krasnikov.project.pmgithubclient.issueinfo.data.model.Reaction
import retrofit2.http.*

interface IssueService {

    @GET("/repos/{owner}/{repo}/issues/{issue_number}/comments")
    suspend fun getIssueComments(
            @Path("owner") owner: String,
            @Path("repo") repo: String,
            @Path("issue_number") issueNumber: Int,
            @Query("page") page: Int
    ): List<Comment>

    @GET("/repos/{owner}/{repo}/issues/comments/{comment_id}/reactions")
    suspend fun getIssueCommentReactions(
            @Path("owner") owner: String,
            @Path("repo") repo: String,
            @Path("comment_id") commentId: Int
    ): List<Reaction>

    @POST("/repos/{owner}/{repo}/issues/comments/{comment_id}/reactions")
    @FormUrlEncoded
    suspend fun createIssueCommentReaction(
            @Path("owner") owner: String,
            @Path("repo") repo: String,
            @Path("comment_id") commentId: Int,
            @Field("content") reactionContent: String
    ) : Reaction

}