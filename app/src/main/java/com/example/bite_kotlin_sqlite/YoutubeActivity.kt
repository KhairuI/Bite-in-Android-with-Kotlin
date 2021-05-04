package com.example.bite_kotlin_sqlite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.bite_kotlin_sqlite.databinding.ActivityYoutubeBinding
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer

class YoutubeActivity : YouTubeBaseActivity() {
    private lateinit var binding: ActivityYoutubeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityYoutubeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val youTube= binding.youTubePlayerView
        youTube.initialize("AIzaSyBpCt4YmFGNNECF4D1TZWbrMyNr0ldNvhk",object:YouTubePlayer.OnInitializedListener{
            override fun onInitializationSuccess(
                p0: YouTubePlayer.Provider?,
                p1: YouTubePlayer?,
                p2: Boolean
            ) {
                if(p1==null){
                    return
                }
                if(p2){
                    p1.play()
                }
                else{
                    p1.cueVideo("0KDggnjwafs")
                    p1.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT)
                }
            }

            override fun onInitializationFailure(
                p0: YouTubePlayer.Provider?,
                p1: YouTubeInitializationResult?
            ) {

            }

        })

    }
}