package krasnikov.project.pmgithubclient.app.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import krasnikov.project.pmgithubclient.R
import krasnikov.project.pmgithubclient.app.data.pref.SharedPref
import krasnikov.project.pmgithubclient.app.navigation.Navigator
import krasnikov.project.pmgithubclient.userinfo.data.model.UserProfile

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(SharedPref(this).token == "") {
            Navigator.navigateToLogin(supportFragmentManager)
        } else {
            Navigator.navigateToUserInfo(supportFragmentManager, UserProfile.LoggedUser)
        }

    }
}