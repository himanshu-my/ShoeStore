package com.udacity.shoestore.view_models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.udacity.shoestore.models.Shoe
import timber.log.Timber


class ShoeListViewModel : ViewModel() {

    val shoeList =  MutableLiveData<MutableList<Shoe>>()
    val shoeItem: MutableLiveData<Shoe> =
        MutableLiveData<Shoe>()

    fun selectedItem(item: Shoe) {
        shoeItem.value = item
    }

    fun getList(): MutableLiveData<MutableList<Shoe>>{
        return shoeList
    }

    fun setList(list: MutableList<Shoe>){
        shoeList.value = list
    }

    override fun onCleared() {
        super.onCleared()
        Timber.d("on cleared called")
    }
}