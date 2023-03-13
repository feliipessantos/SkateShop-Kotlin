package com.feliipessantos.skateshop.data.listeners

import com.feliipessantos.skateshop.domain.model.Brands

interface BrandsListListener {

    fun getBrandsList(brandsList: MutableList<Brands>)
}