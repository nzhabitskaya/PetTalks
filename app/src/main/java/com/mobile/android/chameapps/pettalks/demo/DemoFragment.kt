package com.mobile.android.chameapps.pettalks.demo

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.TrackGroupArray
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.trackselection.TrackSelectionArray
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.mobile.android.chameapps.pettalks.R
import kotlinx.android.synthetic.main.fragment_demo.*
import javax.inject.Singleton

/**
 * Created by Natallia Zhabitskaya on 10/26/2019.
 */

class DemoFragment : Fragment(), Player.EventListener {

    private lateinit var subtitleUri: Uri

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_demo, container, false)
        return view
    }

    override fun onPlaybackParametersChanged(playbackParameters: PlaybackParameters?) {
    }

    override fun onTracksChanged(
        trackGroups: TrackGroupArray?,
        trackSelections: TrackSelectionArray?
    ) {
    }

    override fun onPlayerError(error: ExoPlaybackException?) {
    }

    override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
        if (playbackState == Player.STATE_BUFFERING)
            progressBar.visibility = View.VISIBLE
        else if (playbackState == Player.STATE_READY)
            progressBar.visibility = View.INVISIBLE
    }

    override fun onLoadingChanged(isLoading: Boolean) {
    }

    override fun onRepeatModeChanged(repeatMode: Int) {
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
        initializeExoplayer()
        subtitleUri = Uri.parse("file:///android_asset/sub.srt");
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
        simpleExoplayer.addListener(this)
    }

    private fun releaseExoplayer() {
        playbackPosition = simpleExoplayer.currentPosition
        simpleExoplayer.release()
    }

    private fun prepareExoplayer() {
        val playerInfo = Util.getUserAgent(context, "PetTalksDemo");
        val dataSourceFactory = DefaultDataSourceFactory(context, playerInfo)
        val mediaSource = ExtractorMediaSource.Factory(dataSourceFactory)
            .setExtractorsFactory(DefaultExtractorsFactory())
            .createMediaSource(Uri.parse("file:///android_asset/demo.mp4"));

        simpleExoplayer.prepare(mediaSource)
    }

    override fun onSeekProcessed() {

    }

    override fun onPositionDiscontinuity(reason: Int) {

    }

    override fun onShuffleModeEnabledChanged(shuffleModeEnabled: Boolean) {

    }

    override fun onTimelineChanged(timeline: Timeline?, manifest: Any?, reason: Int) {

    }

    companion object {

        @Singleton
        fun newInstance(): DemoFragment {
            return DemoFragment()
        }
    }
}