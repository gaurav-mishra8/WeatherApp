package com.greenbot.data.mapper

interface DataModelMapper<in E, out D> {

    fun mapFromDataModel(dataModel: E): D

}