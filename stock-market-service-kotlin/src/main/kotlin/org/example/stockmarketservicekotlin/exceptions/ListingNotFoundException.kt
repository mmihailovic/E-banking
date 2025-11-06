package org.example.stockmarketservicekotlin.exceptions

import org.springframework.http.HttpStatus

data class ListingNotFoundException(val id: Long) :
    CustomException("Listing with ID $id doesn't exist!", HttpStatus.NOT_FOUND)
