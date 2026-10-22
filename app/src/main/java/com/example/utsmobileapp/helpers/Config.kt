package com.example.utsmobileapp.helpers

class Config {

    companion object {
        const val BASE_URL = "https://dummyjson.com/"
        const val PRODUCT_LIMIT = 25
        const val PRODUCT_SELECT =
            "id,title,description,category,price,stock,availabilityStatus," +
                    "tags,dimensions,reviews,rating,images,thumbnail"
        const val EXTRA_PRODUCT = "extra_product"
    }
}
