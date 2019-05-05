package com.greenbot.data.mapper

interface DataModelMapper<E, D> {

    fun mapFromDataModel(dataModel: E): D

}