package com.mobile.android.chameapps.pettalks.demo

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.speech.tts.TextToSpeech
import android.speech.tts.TextToSpeech.OnInitListener
import android.speech.tts.Voice
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
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
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


/**
 * Created by Natallia Zhabitskaya on 10/26/2019.
 */

class DemoFragment : Fragment() {

    private lateinit var subtitleUri: Uri
    private lateinit var videoUri: Uri

    private lateinit var ad_view: ImageView
    private lateinit var textToSpeech: TextToSpeech

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
        initTTS()
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

    private fun buildMediaSource(
        sourseUri: Uri,
        dataSourceFactory: DefaultDataSourceFactory
    ): MediaSource {
        val mediaSource = ExtractorMediaSource(
            sourseUri,
            dataSourceFactory,
            DefaultExtractorsFactory(),
            Handler(),
            null
        )
        return mediaSource
    }

    private fun buildSubtitlesSource(
        sourseUri: Uri,
        dataSourceFactory: DefaultDataSourceFactory
    ): MediaSource {
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
            .flatMap {
                return@flatMap Observable.create<String> { emitter ->
                    emitter.onNext(it.toString())
                    emitter.onComplete()
                }
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {

                if (it.toInt() == 13 || it.toInt() == 16  || it.toInt() == 41 || it.toInt() == 44 || it.toInt() == 71 || it.toInt() == 73) {
                    changeVisibility()
                }

                when (it.toInt()) {
                    16 -> ad_view.setImageResource(R.drawable.ad_2)
                    44 -> ad_view.setImageResource(R.drawable.ad_3)
                }

                when (it.toInt()) {
                    2 -> textToSpeech.speak("Can I have this?", TextToSpeech.QUEUE_FLUSH, null)
                    7 -> textToSpeech.speak("Can I have more?", TextToSpeech.QUEUE_FLUSH, null)
                    13 -> textToSpeech.speak("Tasty!", TextToSpeech.QUEUE_FLUSH, null)
                    16 -> textToSpeech.speak("Where is it?", TextToSpeech.QUEUE_FLUSH, null)
                    22 -> textToSpeech.speak("Tasty!", TextToSpeech.QUEUE_FLUSH, null)
                    25 -> textToSpeech.speak("Where did it go?", TextToSpeech.QUEUE_FLUSH, null)
                    30 -> textToSpeech.speak("Hello!", TextToSpeech.QUEUE_FLUSH, null)
                    36 -> textToSpeech.speak("Hello again.", TextToSpeech.QUEUE_FLUSH, null)
                    40 -> textToSpeech.speak("Where is my treat?", TextToSpeech.QUEUE_FLUSH, null)
                    44 -> textToSpeech.speak("What are you doing?", TextToSpeech.QUEUE_FLUSH, null)
                    52 -> textToSpeech.speak("I want to play.", TextToSpeech.QUEUE_FLUSH, null)
                    63 -> textToSpeech.speak("This is fun.", TextToSpeech.QUEUE_FLUSH, null)
                    69 -> textToSpeech.speak(
                        "Can I have some more?",
                        TextToSpeech.QUEUE_FLUSH,
                        null
                    )
                }
            }
    }

    private fun changeVisibility() {
        if (ad_view.visibility == View.INVISIBLE) {
            ad_view.visibility = View.VISIBLE
        } else {
            ad_view.visibility = View.INVISIBLE
        }
    }

    private fun initTTS() {
        textToSpeech = TextToSpeech(context,
            OnInitListener { status ->
                if (status == TextToSpeech.SUCCESS) {
                    val ttsLang = textToSpeech.setLanguage(Locale.UK)

                    //en-US-SMTf00
                    //en-GB-SMTf00
                    val voiceMale = Voice("en-GB-SMTf00", Locale.UK, 1, 1, false, null);
                    textToSpeech.setVoice(voiceMale)

                    textToSpeech.voices

                    if (ttsLang == TextToSpeech.LANG_MISSING_DATA
                        || ttsLang == TextToSpeech.LANG_NOT_SUPPORTED
                    ) {
                        Log.e("TTS", "The Language is not supported!")
                    }
                } else {
                    Toast.makeText(
                        context,
                        "TTS Initialization failed!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }

    override fun onDestroy() {
        super.onDestroy()
        textToSpeech.stop()
        textToSpeech.shutdown()
    }

    companion object {

        @Singleton
        fun newInstance(): DemoFragment {
            return DemoFragment()
        }
    }
}