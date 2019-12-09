package com.mobile.android.chameapps.pettalks.demo

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
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
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.fragment_demo.*
import kotlinx.android.synthetic.main.fragment_demo.view.*
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


/**
 * Created by Natallia Zhabitskaya on 10/26/2019.
 */

class DemoFragment : Fragment() {

    private lateinit var subtitleUri: Uri
    private lateinit var videoUri: Uri

    private lateinit var ad_view: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_demo, container, false)
        ad_view = view.ad_view
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
        createTimer()
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

        val mediaSources =
            arrayOfNulls<MediaSource>(2)

        mediaSources[0] = buildMediaSource(videoUri, dataSourceFactory)
        mediaSources[1] = buildSubtitlesSource(subtitleUri, dataSourceFactory)

        val mediaSource: MediaSource = MergingMediaSource(*mediaSources)

        simpleExoplayer.prepare(mediaSource)
    }

    private fun buildMediaSource(sourseUri: Uri, dataSourceFactory: DefaultDataSourceFactory): MediaSource {
        val mediaSource = ExtractorMediaSource(
            sourseUri,
            dataSourceFactory,
            DefaultExtractorsFactory(),
            Handler(),
            null
        )
        return mediaSource
    }

    private fun buildSubtitlesSource(sourseUri: Uri, dataSourceFactory: DefaultDataSourceFactory): MediaSource {
        val subtitleSource = SingleSampleMediaSource(
            sourseUri, dataSourceFactory,
            Format.createTextSampleFormat(
                null,
                MimeTypes.APPLICATION_SUBRIP,
                NO_VALUE,
                "en",
                null
            ),
            C.TIME_UNSET
        )
        return subtitleSource
    }

    @SuppressLint("CheckResult")
    private fun createTimer() {
        Observable.interval(0, 1, TimeUnit.SECONDS)
            .filter { (it.toInt() == 13) || (it.toInt() == 16) || (it.toInt() == 41) || (it.toInt() == 44) || (it.toInt() == 71) || (it.toInt() == 73) }
            .flatMap {
                return@flatMap Observable.create<String> { emitter ->
                    emitter.onNext(it.toString())
                    emitter.onComplete()
                }
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                if(ad_view.visibility == View.INVISIBLE) {
                    ad_view.visibility = View.VISIBLE
                } else {
                    ad_view.visibility = View.INVISIBLE
                }

                when(it.toInt()) {
                    16 -> ad_view.setImageResource(R.drawable.ad_2)
                    44 -> ad_view.setImageResource(R.drawable.ad_3)
                }
            }
    }

    companion object {

        @Singleton
        fun newInstance(): DemoFragment {
            return DemoFragment()
        }
    }
}