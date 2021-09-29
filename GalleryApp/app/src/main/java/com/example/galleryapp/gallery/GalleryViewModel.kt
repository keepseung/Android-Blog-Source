package com.example.galleryapp.gallery

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.galleryapp.GalleryApp.Companion.appContext
import kotlinx.coroutines.launch

class GalleryViewModel : ViewModel() {

    private val galleryPhotoRepository by lazy { GalleryPhotoRepository(appContext!!) }

    private lateinit var photoList: MutableList<GalleryPhoto>

    val galleryStateLiveData = MutableLiveData<GalleryState>(GalleryState.Uninitialized)

    fun fetchData() = viewModelScope.launch {
        setState(
            GalleryState.Loading
        )
        photoList = galleryPhotoRepository.getAllPhotos()
        setState(
            GalleryState.Success(
                photoList = photoList
            )
        )
    }

    fun selectMultiPhoto(galleryPhoto: GalleryPhoto) {
        val newFindGalleryPhoto = photoList.find { it.id == galleryPhoto.id }

        // 이전에 선택한 이미지가 없는 경우
        // 새로 선택한 이미지를 체크하게 함
        newFindGalleryPhoto?.let { photo ->
            photoList[photoList.indexOf(photo)] =
                photo.copy(
                    isSelected = photo.isSelected.not()
                )
            setState(
                GalleryState.Success(
                    photoList = photoList
                )
            )
        }
    }

    fun selectSinglePhoto(galleryPhoto: GalleryPhoto) {
        val beforeSelectedPhoto = photoList.filter { it.isSelected }
        val newFindGalleryPhoto = photoList.find { it.id == galleryPhoto.id }

        if (beforeSelectedPhoto.isEmpty()) {
            // 이전에 선택한 이미지가 없는 경우
            // 새로 선택한 이미지를 체크하게 함
            newFindGalleryPhoto?.let { photo ->
                photoList[photoList.indexOf(photo)] =
                    photo.copy(
                        isSelected = photo.isSelected.not()
                    )
                setState(
                    GalleryState.Success(
                        photoList = photoList
                    )
                )
            }

        } else {

            // 1. 기존에 체크되있는 이미지 체크 안됨 상태로 변경
            val beforePhoto = beforeSelectedPhoto.first()
            beforePhoto.let { photo ->
                photoList[photoList.indexOf(photo)] = photo.copy(isSelected = false)
            }


            // 2. 새로 선택한 이미지를 체크하게 함
            newFindGalleryPhoto?.let { photo ->

                // 이전, 새로 선택한 이미지가 다른 경우만 새로 이미지 체크한다.
                if (beforePhoto.id != photo.id){
                    photoList[photoList.indexOf(photo)] =
                        photo.copy(isSelected = photo.isSelected.not())
                }

            }

            setState(
                GalleryState.Success(
                    photoList = photoList
                )
            )

        }
    }

    private fun setState(state: GalleryState) {
        galleryStateLiveData.postValue(state)
    }

    fun confirmCheckedPhotos() {
        setState(
            GalleryState.Loading
        )
        setState(
            GalleryState.Confirm(
                photoList = photoList.filter { it.isSelected }
            )
        )
    }

}
