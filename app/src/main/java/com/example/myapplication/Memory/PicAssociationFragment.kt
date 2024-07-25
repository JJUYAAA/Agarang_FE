package com.example.myapplication.Memory

import android.Manifest
import android.app.VoiceInteractor
import android.content.pm.PackageManager
import android.media.MediaRecorder
import android.os.Bundle
import android.os.Environment
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.myapplication.R
import com.example.myapplication.Retrofit.RetrofitInstance
import com.example.myapplication.databinding.FragmentPicAssociationBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import org.json.JSONObject
import java.io.File
import java.io.IOException

class PicAssociationFragment : Fragment() {
    private lateinit var binding:FragmentPicAssociationBinding
    private var mediaRecorder: MediaRecorder? = null
    private var audioFile: File? = null

    private val clientId = "nlpxphm34l"
    private val clientSecret = "B4F7SeFMWV7UpjzOcuu6Kb0nsEuz8EUaF6HYOL44"
    /*private val apiUrl = "https://naveropenapi.apigw.ntruss.com/recog/v1/stt"*/

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentPicAssociationBinding.inflate(inflater,container,false)

        // 녹음 권한 요청
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE), 1)
        }

        binding.ivRecordBtn.setOnClickListener {
            startRecording()
            binding.ivRecordBtn.visibility=View.GONE
            binding.tvRecordNotice.visibility=View.VISIBLE
            binding.ivRecordCancleBtn.visibility=View.VISIBLE
            binding.ivRecordArrowBtn.visibility=View.VISIBLE
            binding.ivRecordIng.visibility=View.VISIBLE
            binding.ivRecordingNextBtn.visibility=View.GONE

        }
        binding.ivRecordingNextBtn.setOnClickListener {
            val fragment=DeepQuestionFragment()
            parentFragmentManager.beginTransaction()
                .replace(R.id.memory_frm,fragment)
                .commit()
        }

        binding.ivRecordCancleBtn.setOnClickListener {
            stopRecording()
            binding.ivRecordBtn.visibility=View.VISIBLE
            binding.tvRecordNotice.visibility=View.GONE
            binding.ivRecordCancleBtn.visibility=View.GONE
            binding.ivRecordArrowBtn.visibility=View.GONE
            binding.ivRecordIng.visibility=View.GONE
            binding.ivRecordingNextBtn.visibility=View.GONE
        }

        binding.ivRecordArrowBtn.setOnClickListener {
            stopRecording()
            binding.ivRecordBtn.visibility=View.GONE
            binding.tvRecordNotice.visibility=View.GONE
            binding.ivRecordCancleBtn.visibility=View.GONE
            binding.ivRecordArrowBtn.visibility=View.GONE
            binding.ivRecordIng.visibility=View.GONE
            binding.ivRecordingNextBtn.visibility=View.VISIBLE

            uploadAudioFileAndGetText()
        }

        return binding.root
    }
    private fun startRecording() {
        audioFile = File(requireContext().getExternalFilesDir(Environment.DIRECTORY_MUSIC), "recorded_audio.wav")
        mediaRecorder = MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
            setOutputFile(audioFile?.absolutePath)
            try {
                prepare()
                start()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
    private fun stopRecording() {
        mediaRecorder?.apply {
            stop()
            release()
        }
        mediaRecorder = null
    }
    private fun uploadAudioFileAndGetText() {
        audioFile?.let { file ->
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val requestFile = RequestBody.create("application/octet-stream".toMediaTypeOrNull(), file)
                    val body = MultipartBody.Part.createFormData("audio", file.name, requestFile)
                    val clientIdBody = RequestBody.create("text/plain".toMediaTypeOrNull(), clientId)
                    val clientSecretBody = RequestBody.create("text/plain".toMediaTypeOrNull(), clientSecret)

                    val response = RetrofitInstance.api.uploadAudio(body, clientIdBody, clientSecretBody)
                    val text = response.text

                    CoroutineScope(Dispatchers.Main).launch {
                        binding.tvRecordNotice.text = text
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    CoroutineScope(Dispatchers.Main).launch {
                        binding.tvRecordNotice.text = "녹음 오류가 발생했습니다. 다시 시도해주세요."
                    }
                }
            }
        }
    }
}