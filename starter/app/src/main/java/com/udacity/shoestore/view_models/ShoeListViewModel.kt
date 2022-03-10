package com.udacity.shoestore.view_models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.udacity.shoestore.models.Shoe
import timber.log.Timber


class ShoeListViewModel : ViewModel() {

    private val shoeList: MutableLiveData<MutableList<Shoe>> =
        MutableLiveData<MutableList<Shoe>>()

    fun getList(): MutableLiveData<MutableList<Shoe>> {
        return shoeList
    }

    fun setList(shoes: MutableList<Shoe>) {
        shoeList.value = shoes
    }

    override fun onCleared() {
        super.onCleared()
        Timber.d("on cleared called")
    }
}