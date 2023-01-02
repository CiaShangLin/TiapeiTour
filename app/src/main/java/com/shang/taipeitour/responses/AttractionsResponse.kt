package com.shang.taipeitour.responses

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AttractionsResponse(
    val `data`: List<Data?>?,
    val total: Int?
):java.io.Serializable {
    @JsonClass(generateAdapter = true)
    data class Data(
        val id: Int?,
        val name: String?,
        val introduction: String?,
        val address: String?,
        val tel: String?,
        val fax: String?,
        val email: String?,
        val official_site: String?,
        val url: String?,
        val facebook: String?,
        val category: List<Category?>?,
        val images: List<Image?>?,
        val target: List<Target?>?,
        val nlat: Double?,
        val elong: Double?,
    ):java.io.Serializable {
        @JsonClass(generateAdapter = true)
        data class Category(
            val id: Int?,
            val name: String?
        ):java.io.Serializable

        @JsonClass(generateAdapter = true)
        data class Image(
            val ext: String?,
            val src: String?,
            val subject: String?
        ):java.io.Serializable

        @JsonClass(generateAdapter = true)
        data class Target(
            val id: Int?,
            val name: String?
        ):java.io.Serializable
    }

}
