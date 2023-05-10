package com.example.testretrofit.models

data class Product(
    val id:Int,
    val title: String,
    val description: String,
    val price: Int,
    val discountPercentage: Float,
    val rating: Float,
    val stock: Int,
    val brand: String,
    val category: String,
    val thumbnail: String,
    val images: List<String>
) {
    override fun toString(): String {
        return "id=$id,\n" +
                "title='$title',\n" +
                "description='$description',\n" +
                "price=$price,\n" +
                "discountPercentage=$discountPercentage,\n" +
                "rating=$rating,\n" +
                "stock=$stock,\n" +
                "brand='$brand',\n" +
                "category='$category',\n" +
                "thumbnail='$thumbnail',\n" +
                "images=$images"
    }
}