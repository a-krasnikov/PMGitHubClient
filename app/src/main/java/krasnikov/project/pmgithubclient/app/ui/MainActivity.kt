package krasnikov.project.pmgithubclient.app.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint
import krasnikov.project.pmgithubclient.R
import krasnikov.project.pmgithubclient.app.data.pref.SharedPref
import krasnikov.project.pmgithubclient.app.navigation.Navigator
import krasnikov.project.pmgithubclient.userinfo.data.model.UserProfile

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (SharedPref(this).token.isEmpty()) {
            Navigator.navigateToLogin(supportFragmentManager)
        } else {
            Navigator.startScreen(supportFragmentManager)
        }
    }
}