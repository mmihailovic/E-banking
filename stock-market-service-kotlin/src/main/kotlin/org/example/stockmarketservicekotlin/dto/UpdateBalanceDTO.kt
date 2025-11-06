package org.example.stockmarketservicekotlin.dto

data class UpdateBalanceDTO(val buyerId: Long, val sellerId: Long, val buyPrice: Double, val sellPrice: Double)
