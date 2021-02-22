package krasnikov.project.pmgithubclient.app.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import krasnikov.project.pmgithubclient.R
import krasnikov.project.pmgithubclient.app.navigation.Navigator

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Navigator.navigateToLogin(supportFragmentManager)
    }
}