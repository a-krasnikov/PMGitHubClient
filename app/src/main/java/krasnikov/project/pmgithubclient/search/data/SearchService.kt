package krasnikov.project.pmgithubclient.search.data

import retrofit2.http.GET
import retrofit2.http.Query

interface SearchService {

    @GET("search/users")
    suspend fun searchUsers(
        @Query("q") query: String,
        @Query("page") page: Int,
    ): SearchResult
}