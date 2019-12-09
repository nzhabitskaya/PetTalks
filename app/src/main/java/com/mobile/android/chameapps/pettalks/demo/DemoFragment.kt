package com.mobile.android.chameapps.pettalks.demo

import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.Format.NO_VALUE
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.MergingMediaSource
import com.google.android.exoplayer2.source.SingleSampleMediaSource
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.MimeTypes
import com.google.android.exoplayer2.util.Util
import com.mobile.android.chameapps.pettalks.R
import kotlinx.android.synthetic.main.fragment_demo.*
import javax.inject.Singleton


/**
 * Created by Natallia Zhabitskaya on 10/26/2019.
 */

class DemoFragment : Fragment() {

    private lateinit var subtitleUri: Uri
    private lateinit var videoUri: Uri

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_demo, container, false)
        return view
    }

    private lateinit var simpleExoplayer: SimpleExoPlayer
    private var playbackPosition = 0L
    private val bandwidthMeter by lazy {
        DefaultBandwidthMeter()
    }

    private val adaptiveTrackSelectionFactory by lazy {
        AdaptiveTrackSelection.Factory(bandwidthMeter)
    }

    override fun onStart() {
        subtitleUri = Uri.parse("file:///android_asset/sub.srt")
        videoUri = Uri.parse("file:///android_asset/demo.mp4")
        initializeExoplayer()
        super.onStart()
    }

    override fun onStop() {
        releaseExoplayer()
        super.onStop()
    }

    private fun initializeExoplayer() {
        simpleExoplayer = ExoPlayerFactory.newSimpleInstance(
            DefaultRenderersFactory(context),
            DefaultTrackSelector(adaptiveTrackSelectionFactory),
            DefaultLoadControl()
        )

        prepareExoplayer()
        simpleExoPlayerView.player = simpleExoplayer
        simpleExoplayer.seekTo(playbackPosition)
        simpleExoplayer.playWhenReady = true
    }

    private fun releaseExoplayer() {
        playbackPosition = simpleExoplayer.currentPosition
        simpleExoplayer.release()
    }

    private fun prepareExoplayer() {
        val playerInfo = Util.getUserAgent(context, "PetTalksDemo")
        val dataSourceFactory = DefaultDataSourceFactory(context, playerInfo)

        val contentMediaSource: MediaSource = buildMediaSource(videoUri)
        val mediaSources =
            arrayOfNulls<MediaSource>(2)

        mediaSources[0] = contentMediaSource

        val subtitleSource = SingleSampleMediaSource(
            subtitleUri, dataSourceFactory,
            Format.createTextSampleFormat(
                null,
                MimeTypes.APPLICATION_SUBRIP,
                NO_VALUE,
                "en",
                null
            ),
            C.TIME_UNSET
        )

        mediaSources[1] = subtitleSource

        val mediaSource: MediaSource = MergingMediaSource(*mediaSources)

        simpleExoplayer.prepare(mediaSource)
    }

    private fun buildMediaSource(parse: Uri): MediaSource {
        val dataSourceFactory = DefaultDataSourceFactory(
            context,
            Util.getUserAgent(context, "PetTalksDemo")
        )
        val mediaSource = ExtractorMediaSource(
            parse,
            dataSourceFactory,
            DefaultExtractorsFactory(),
            Handler(),
            null
        )
        return mediaSource
    }

    companion object {

        @Singleton
        fun newInstance(): DemoFragment {
            return DemoFragment()
        }
    }
}