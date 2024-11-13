package com.example.endo_ai.features.community.data.repository

import com.example.endo_ai.core.util.Resource
import com.example.endo_ai.features.community.domain.model.Community
import com.example.endo_ai.features.community.domain.repository.CommunityRepository
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.snapshots
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CommunityRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val storage: FirebaseStorage
) : CommunityRepository {
    private val communitiesCollection = firestore.collection("communities")

    override suspend fun createCommunity(community: Community): Resource<Community> = try {
        val newCommunityRef = communitiesCollection.document()
        val newCommunity = community.copy(id = newCommunityRef.id)

        newCommunityRef.set(newCommunity).await()
        Resource.Success(newCommunity)
    } catch (e: Exception) {
        Resource.Error(e.message ?: "Unknown error occurred")
    }

    override suspend fun getAllCommunities(): Flow<Resource<List<Community>>> = flow {
        emit(Resource.Loading(true))
        try {
            val snapShots = communitiesCollection
                .orderBy("createdAt", Query.Direction.DESCENDING)
                .snapshots()

            snapShots.collect { snapShot ->
                val communities = snapShot.documents.mapNotNull { doc ->
                    doc.toObject(Community::class.java)
                }
                emit(Resource.Success(communities))
            }
            emit(Resource.Loading(false))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Unknown error occured"))
        }
    }

    override suspend fun getCommunityById(id: String): Flow<Resource<Community>> = flow {
        emit(Resource.Loading(true))
        try {
            val communityDoc = communitiesCollection.document(id).get().await()
            val community = communityDoc.toObject(Community::class.java)

            if (community != null) {
                emit(Resource.Success(community))
            } else {
                emit(Resource.Error("community not found"))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Unknown error occurred"))
        }
    }

    override suspend fun joinCommunity(communityId: String, userId: String): Resource<Boolean> =
        try {
            communitiesCollection.document(communityId)
                .update("members", FieldValue.arrayUnion(userId))
                .await()

            Resource.Success(true)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Unknown error occurred")
        }

    override suspend fun leaveCommunity(communityId: String, userId: String): Resource<Boolean> =
        try {
            communitiesCollection.document(communityId)
                .update("members", FieldValue.arrayRemove(userId))
                .await()
            Resource.Success(true)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Unknown error occurred")

        }
}