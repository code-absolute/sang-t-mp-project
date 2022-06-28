package tech.codeabsolute.repository

import kotlinx.coroutines.flow.Flow
import tech.codeabsolute.model.Requisition
import tech.codeabsolute.util.Resource

interface RequisitionsRepository {
    fun addRequisition(requisition: Requisition, clientId: Int): Flow<Resource<Requisition>>
    fun getRequisitions(clientId: Int): Flow<Resource<List<Requisition>>>
}