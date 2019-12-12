package com.mobile.android.chameapps.pettalks.demo

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.app.Dialog
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.speech.tts.TextToSpeech
import android.speech.tts.TextToSpeech.OnInitListener
import android.speech.tts.Voice
import android.util.Log
import android.view.*
import android.widget.*
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

    private lateinit var ad_view: RelativeLayout
    private lateinit var ad_view_img: ImageView
    private lateinit var textToSpeech: TextToSpeech

    private var isCC: Boolean = false
    private var isVoice: Boolean = false
    private var isTrainingMode: Boolean = false

    private lateinit var animationDown: ObjectAnimator
    private lateinit var animationUp: ObjectAnimator

    private lateinit var dialog: Dialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val args = getArguments()
        isCC = args!!.getBoolean(IS_CC, false)
        isVoice = args!!.getBoolean(IS_VOICE, false)
        isTrainingMode = args!!.getBoolean(IS_TRAINING_MODE, false)

        val view = inflater.inflate(R.layout.fragment_demo, container, false)
        ad_view_img = view.ad_view_img
        ad_view = view.ad_view
        ad_view.setOnClickListener { hideAds() }

        animationDown = ObjectAnimator.ofFloat (ad_view, "translationY", 0f).setDuration(50)
        animationUp = ObjectAnimator.ofFloat (ad_view, "translationY", -320f).setDuration(50)
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
        simpleExoplayer.volume = 0f
    }

    private fun releaseExoplayer() {
        playbackPosition = simpleExoplayer.currentPosition
        simpleExoplayer.release()
    }

    private fun prepareExoplayer() {
        val playerInfo = Util.getUserAgent(context, "PetTalksDemo")
        val dataSourceFactory = DefaultDataSourceFactory(context, playerInfo)

        val mediaSources = arrayOfNulls<MediaSource>(2)

        mediaSources[0] = buildMediaSource(videoUri, dataSourceFactory)
        mediaSources[1] = buildSubtitlesSource(subtitleUri, dataSourceFactory)

        val mediaSource: MediaSource
        if (isCC) {
            mediaSource = MergingMediaSource(*mediaSources)
        } else {
            mediaSource = buildMediaSource(videoUri, dataSourceFactory)
        }

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

                if (it.toInt() == 13 || it.toInt() == 41 || it.toInt() == 71) {
                    showAds()
                }

                if (it.toInt() == 16 || it.toInt() == 44 || it.toInt() == 74) {
                    hideAds()
                }

                when (it.toInt()) {
                    16 -> ad_view_img.setImageResource(R.drawable.ad_2)
                    44 -> ad_view_img.setImageResource(R.drawable.ad_1)
                }

                if (isVoice) {
                    when (it.toInt()) {
                        2 -> textToSpeech.speak("Can I have this?", TextToSpeech.QUEUE_FLUSH, null)
                        7 -> textToSpeech.speak("Can I have more?", TextToSpeech.QUEUE_FLUSH, null)
                        13 -> textToSpeech.speak("Tasty!", TextToSpeech.QUEUE_FLUSH, null)
                        16 -> textToSpeech.speak("Where is it?", TextToSpeech.QUEUE_FLUSH, null)
                        22 -> textToSpeech.speak("Tasty!", TextToSpeech.QUEUE_FLUSH, null)
                        25 -> textToSpeech.speak("Where did it go?", TextToSpeech.QUEUE_FLUSH, null)
                        30 -> textToSpeech.speak("Hello!", TextToSpeech.QUEUE_FLUSH, null)
                        36 -> textToSpeech.speak("Hello again.", TextToSpeech.QUEUE_FLUSH, null)
                        40 -> textToSpeech.speak(
                            "Where is my treat?",
                            TextToSpeech.QUEUE_FLUSH,
                            null
                        )
                        44 -> textToSpeech.speak(
                            "What are you doing?",
                            TextToSpeech.QUEUE_FLUSH,
                            null
                        )
                        52 -> textToSpeech.speak("I want to play.", TextToSpeech.QUEUE_FLUSH, null)
                        63 -> textToSpeech.speak("This is fun.", TextToSpeech.QUEUE_FLUSH, null)
                        69 -> textToSpeech.speak(
                            "Can I have some more?",
                            TextToSpeech.QUEUE_FLUSH,
                            null
                        )
                    }
                }

                if (isTrainingMode) {
                    when (it.toInt()) {
                        7 -> showCustomDialog(R.string.q1)
                        30 -> showCustomDialog(R.string.q2)
                        52 -> showCustomDialog(R.string.q3)
                    }
                }
            }
    }

    private fun showAds() {
        animationDown.start()
    }

    private fun hideAds() {
        animationUp.start()
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

    private fun showCustomDialog(messageId: Int) {
        if (context == null) {
            return
        }
        if (!::dialog.isInitialized) {
            dialog = Dialog(context!!)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE) // before
            dialog.setContentView(R.layout.dialog_info)
            dialog.setCancelable(true)
            val lp = WindowManager.LayoutParams()
            lp.copyFrom(dialog.window!!.attributes)
            lp.width = WindowManager.LayoutParams.WRAP_CONTENT
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT
            (dialog.findViewById<View>(R.id.bt_yes) as Button).setOnClickListener(
                { v -> dialog.dismiss() })
            (dialog.findViewById<View>(R.id.bt_no) as Button).setOnClickListener(
                { v -> dialog.dismiss() })
            dialog.window!!.attributes = lp
        }
        (dialog.findViewById<View>(R.id.message) as TextView).text =
            resources.getString(messageId)
        dialog.hide()
        dialog.show()
    }

    override fun onPause() {
        super.onPause()
        pausePlayer()
    }

    override fun onResume() {
        super.onResume()
        startPlayer()
    }

    override fun onDestroy() {
        super.onDestroy()
        textToSpeech.stop()
        textToSpeech.shutdown()
    }

    private fun pausePlayer() {
        simpleExoplayer.setPlayWhenReady(false)
        simpleExoplayer.getPlaybackState()
    }

    private fun startPlayer() {
        simpleExoplayer.setPlayWhenReady(true)
        simpleExoplayer.getPlaybackState()
    }

    companion object {

        const val IS_CC = "is_cc"
        const val IS_VOICE = "is_voice"
        const val IS_TRAINING_MODE = "is_training_mode"

        @Singleton
        fun newInstance(isCC: Boolean, isVoice: Boolean, isTrainingMode: Boolean): DemoFragment {
            val fragment = DemoFragment()
            val args = Bundle()
            args.putBoolean(IS_CC, isCC)
            args.putBoolean(IS_VOICE, isVoice)
            args.putBoolean(IS_TRAINING_MODE, isTrainingMode)
            fragment.setArguments(args)
            return fragment
        }
    }
}