package com.soft.attendancekt.ui.member

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult
import com.soft.attendancekt.MainActivity
import com.soft.attendancekt.R
import com.soft.attendancekt.databinding.MemberBinding
import com.soft.attendancekt.util.FileUtil
import com.soft.attendancekt.util.PermissionUtil
import kotlinx.android.synthetic.main.layout_add_member.*
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class FragmentAddMember : Fragment() {

    companion object {
        const val KEY_MEMBER_ID = "member_id"
        const val REQUEST_IMAGE_CAPTURE = 1
        const val REQUEST_PICK_IMAGE = 3
    }

    lateinit var currentPhotoFilePath: String
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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val id = arguments?.getInt(KEY_MEMBER_ID) ?: 0
        memberViewModel.member.observe(this, Observer {
            memberViewModel.member
        })
        memberViewModel.memberId.value = id
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        var result: IntentResult =
            IntentIntegrator.parseActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            memberBinding.takePhoto.setImageURI(Uri.parse(currentPhotoFilePath))
            memberViewModel.member.value?.photo = currentPhotoFilePath
        } else if (requestCode == REQUEST_PICK_IMAGE && resultCode == RESULT_OK) {
            var photoUri: Uri? = data?.data

            try {
                val bitmap: Bitmap =
                    FileUtil.writeImage(requireContext(), photoUri, createImageFile())
                memberBinding.takePhoto.setImageBitmap(bitmap)
                memberViewModel.member.value?.photo = currentPhotoFilePath
            } catch (e: Exception) {
                e.printStackTrace();
                Toast.makeText(requireContext(), e.message, Toast.LENGTH_LONG).show();
            }
        } else {
            if (result.contents != null) {
                val member = memberViewModel.member.value
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PermissionUtil.PERMISSION_CAMERA) {
            if (grantResults.size > 0 &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED
            ) {
                takePicture()
            }
        }
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

            val cameraBtn = bsview.findViewById<ImageButton>(R.id.camera)
            cameraBtn.setOnClickListener {
                bottomSheetDialog.dismiss()
                takePicture()
            }
            val fileBtn = bsview.findViewById<ImageButton>(R.id.file)
            fileBtn.setOnClickListener {
                bottomSheetDialog.dismiss()
            }
            bottomSheetDialog.setContentView(bsview);
            bottomSheetDialog.show();
        }

    }


    override fun onDestroy() {
        super.onDestroy()
        val activity: MainActivity = requireActivity() as MainActivity
        activity.hideKeyboard()
    }

    fun takePicture() {
        if (!PermissionUtil.hasCameraPermission(this)) return

        val intent: Intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (intent.resolveActivity(requireContext().packageManager) != null) {
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