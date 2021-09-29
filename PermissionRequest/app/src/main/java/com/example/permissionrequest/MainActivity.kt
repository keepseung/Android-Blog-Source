package com.example.permissionrequest

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {

    private val requiredPermissions = arrayOf(Manifest.permission.RECORD_AUDIO)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 사용자에게 오디오 권한 요청하기
        requestAudioPermission()
    }

    private fun requestAudioPermission() {
        requestPermissions(requiredPermissions, REQUEST_RECORD_AUDIO_PERMISSION)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        // 다음 두 가지 조건을 만족해야 오디오 권한 요청을 사용자가 허가했다고 판단한다.
        // 1. 요청 코드가 requestPermissions()의 파라미터로 전달한 요청코드인 경우
        // 2. 허가한 요청 중 첫 번째 권한을 승인한 경우 (권한이 많으면 grantResults안의 권한을 반복해서 확인해야 함)
        val audioRecordPermissionGranted =
            requestCode == REQUEST_RECORD_AUDIO_PERMISSION &&
                    grantResults.firstOrNull() == PackageManager.PERMISSION_GRANTED

        if (!audioRecordPermissionGranted) {
            // 요청에 대한 이유를 안보여주면 권한 설명 다이어로그를 보여줌
            showPermissionExplanationDialog()
        }

    }

    private fun showPermissionExplanationDialog() {
        AlertDialog.Builder(this)
            .setMessage("녹음 권한을 켜주셔야지 앱을 사용할 수 있습니다. 앱 설정 화면으로 진입하셔서 권한을 켜주세요.")
            .setPositiveButton("권한 변경하러 가기") { _, _ -> navigateToAppSetting() }
            .setNegativeButton("앱 종료하기") { _, _ -> finish() }
            .show()
    }

    private fun navigateToAppSetting() {
        // 설정 앱에서 현재 패키지를 가진 앱 설정으로 이동한다.
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri: Uri = Uri.fromParts("package", packageName, null)
        intent.data = uri
        startActivity(intent)
    }

    companion object {
        private const val REQUEST_RECORD_AUDIO_PERMISSION = 201
    }

}