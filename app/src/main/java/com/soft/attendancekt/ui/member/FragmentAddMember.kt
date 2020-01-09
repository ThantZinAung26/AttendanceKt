package com.soft.attendancekt.ui.member

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.zxing.integration.android.IntentIntegrator
import com.soft.attendancekt.MainActivity
import com.soft.attendancekt.R
import com.soft.attendancekt.databinding.MemberBinding
import kotlinx.android.synthetic.main.layout_add_member.*
import kotlinx.android.synthetic.main.layout_bottom_sheet_view.*
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class FragmentAddMember : Fragment() {

    private val REQUEST_IMAGE_CAPTURE = 1


    private lateinit var currentPhotoFilePath: String
    lateinit var memberViewModel: MemberViewModel
    lateinit var memberBinding: MemberBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        memberViewModel = ViewModelProviders.of(this).get(MemberViewModel::class.java)

        val id = arguments?.getInt(KEY_MEMBER_ID) ?: 0

        memberViewModel.member.observe(this, Observer {
            memberViewModel.member
        })
        memberViewModel.memberId.value = id
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        memberBinding = MemberBinding.inflate(inflater, container, false)
        memberBinding.lifecycleOwner = this
        memberBinding.viewModel = memberViewModel
        return memberBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        save.setOnClickListener {
            memberViewModel.save()
            //Log.d("SAVE","save value")
            findNavController().navigateUp()
        }

        delete.setOnClickListener {
            memberViewModel.delete()
        }

        btnScan.setOnClickListener {
            val i: IntentIntegrator = IntentIntegrator.forSupportFragment(this)
            i.initiateScan()
        }

        take_photo.setOnClickListener {
            val bottomSheetDialog = BottomSheetDialog(requireContext())
            val bsview = layoutInflater.inflate(R.layout.layout_bottom_sheet_view, null)

//            camera.setOnClickListener{
//                bottomSheetDialog.dismiss()
//                takePicture()
//            }
//
//            file.setOnClickListener {
//                bottomSheetDialog.dismiss()
//            }
            bottomSheetDialog.setContentView(bsview);
            bottomSheetDialog.show();

        }

    }

    companion object {
        const val KEY_MEMBER_ID = "member_id"
    }

    override fun onDestroy() {
        super.onDestroy()
        val activity: MainActivity = requireActivity() as MainActivity
        activity.hideKeyboard()
    }

    fun takePicture() {
        val intent: Intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        var photo: File? = null
        try {
            photo = createImageFile()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        if (photo != null) {
            val photoUri: Uri = FileProvider.getUriForFile(
                requireContext(),
                "com.soft.attendancekt.fileprovider",
                photo
            )
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE)
        }

    }

    @Throws(IOException::class)
    private fun createImageFile(): File? {
        val timeStamp =
            SimpleDateFormat("yyyyMMdd_HHmmss", Locale.ENGLISH)
                .format(Date())
        val imageFileName = "JPEG_" + timeStamp + "_"
        val image = File.createTempFile(
            imageFileName, ".jpg",
            requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        )
        currentPhotoFilePath = image.absolutePath
        return image
    }
}