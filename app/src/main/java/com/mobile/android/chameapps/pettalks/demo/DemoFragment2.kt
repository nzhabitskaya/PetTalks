package com.mobile.android.chameapps.pettalks.demo

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.Format
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.MergingMediaSource
import com.google.android.exoplayer2.source.SingleSampleMediaSource
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import com.google.android.exoplayer2.util.MimeTypes
import com.mobile.android.chameapps.pettalks.R
import kotlinx.android.synthetic.main.fragment_demo.*
import javax.inject.Singleton


class DemoFragment2 : Fragment() {

    private lateinit var playerView: PlayerView
    private lateinit var exoPlayer: SimpleExoPlayer
    private lateinit var subtitleUri: Uri
    private lateinit var videoUri: Uri

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_demo, container, false)
        return view
    }

    override fun onStart() {
        super.onStart()
        subtitleUri = Uri.parse("file:///android_asset/sub.srt")
        videoUri = Uri.parse("file:///android_asset/demo.mp4")
        playWithCaption()
    }

    override fun onStop() {
        super.onStop()
        playerView.player = null
        exoPlayer.release()
    }

    private fun playWithCaption() {
        val bandwidthMeter = DefaultBandwidthMeter()
        val trackSelector = DefaultTrackSelector(AdaptiveTrackSelection.Factory(bandwidthMeter))
        exoPlayer = ExoPlayerFactory.newSimpleInstance(context, trackSelector)

        val dataSourceFactory = DefaultHttpDataSourceFactory("exoplayer_video")

        val extractorsFactory = DefaultExtractorsFactory()
        val mediaSource =
            ExtractorMediaSource(videoUri, dataSourceFactory, extractorsFactory, null, null)

        // Build the subtitle MediaSource.
        val subtitleFormat = Format.createTextSampleFormat(
            null, // An identifier for the track. May be null.
            MimeTypes.APPLICATION_SUBRIP, // The mime type. Must be set correctly.
            null,
            Format.NO_VALUE,
            0,
            "en",
            Format.NO_VALUE,
            null,
            Format.OFFSET_SAMPLE_RELATIVE,
            null
        ) // The subtitle language. May be null.

        val subtitleSource =
            SingleSampleMediaSource(subtitleUri, dataSourceFactory, subtitleFormat, C.TIME_UNSET)

        val mergedSource = MergingMediaSource(mediaSource, subtitleSource)

        simpleExoPlayerView.setPlayer(exoPlayer)
        exoPlayer.prepare(mergedSource)
        exoPlayer.setPlayWhenReady(true)
    }

    companion object {

        @Singleton
        fun newInstance(): DemoFragment2 {
            return DemoFragment2()
        }
    }
}