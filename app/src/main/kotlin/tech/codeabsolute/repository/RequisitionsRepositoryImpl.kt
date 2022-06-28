package tech.codeabsolute.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import tech.codeabsolute.model.Requisition
import tech.codeabsolute.util.Resource

class RequisitionsRepositoryImpl : RequisitionsRepository {

    override fun addRequisition(requisition: Requisition, clientId: Int): Flow<Resource<Requisition>> = flow {
        emit(Resource.Loading())
    }

    override fun getRequisitions(clientId: Int): Flow<Resource<List<Requisition>>> = flow {
        emit(Resource.Loading())
    }

}