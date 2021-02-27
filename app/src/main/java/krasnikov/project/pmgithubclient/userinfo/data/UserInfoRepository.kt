package krasnikov.project.pmgithubclient.userinfo.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import krasnikov.project.pmgithubclient.userinfo.data.model.Repo
import krasnikov.project.pmgithubclient.userinfo.data.model.User
import krasnikov.project.pmgithubclient.userinfo.data.model.UserProfile
import krasnikov.project.pmgithubclient.utils.Result
import java.lang.Exception
import javax.inject.Inject

class UserInfoRepository @Inject constructor(private val userService: UserService) {

    suspend fun getUser(userProfile: UserProfile): Result<User> {
        return withContext(Dispatchers.IO) {
            try {
                when (userProfile) {
                    is UserProfile.LoggedUser -> {
                        Result.Success(userService.getLoggedUser())
                    }
                    is UserProfile.User -> {
                        Result.Success(userService.getUser(userProfile.username))
                    }
                }
            } catch (ex: Exception) {
                Result.Error(ex)
            }
        }
    }

    suspend fun getUserRepos(userProfile: UserProfile, page: Int): Result<List<Repo>> {
        return withContext(Dispatchers.IO) {
            try {
                when (userProfile) {
                    is UserProfile.LoggedUser -> {
                        Result.Success(userService.getLoggedUserRepos(page))
                    }
                    is UserProfile.User -> {
                        Result.Success(userService.getUserRepos(userProfile.username, page))
                    }
                }
            } catch (ex: Exception) {
                Result.Error(ex)
            }
        }
    }
}