package com.example.endo_ai.features.community.domain.repository

import com.example.endo_ai.core.util.Resource
import com.example.endo_ai.features.community.domain.model.Community
import kotlinx.coroutines.flow.Flow

interface CommunityRepository {
    suspend fun createCommunity(community: Community): Resource<Community>
    suspend fun getAllCommunities(): Flow<Resource<List<Community>>>
    suspend fun getCommunityById(id: String): Flow<Resource<Community>>
    suspend fun joinCommunity(communityId: String, userId: String): Resource<Boolean>
    suspend fun leaveCommunity(communityId: String, userId: String): Resource<Boolean>
}