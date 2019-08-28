package com.deadshot.android.projectneostore.ui.products

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deadshot.android.projectneostore.repository.ProductsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ProductsViewModel(private val productValue: Int) : ViewModel(){

    /**
     * Create an instance of ProductsRepository
     */
    private val productsRepository = ProductsRepository(productValue)

    /**
     * Status for Error checking
     */
    val status = productsRepository.status

    val properties = productsRepository.properties

    private var viewModelJob = Job()

    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    /**
     * Call productsRepository.loadProducts() on init so we can display status immediately.
     */
    init {
        viewModelScope.launch {
            productsRepository.loadProducts()
        }
    }

    /**
     * When the [ViewModel] is finished, we cancel our coroutine [viewModelJob], which tells the
     * Retrofit service to stop.
     */
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}