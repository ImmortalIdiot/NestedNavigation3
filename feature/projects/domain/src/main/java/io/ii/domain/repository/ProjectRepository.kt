package io.ii.domain.repository

import io.ii.domain.model.Project

interface ProjectRepository {

    suspend fun getAll(): List<Project>

    suspend fun get(id: String): Project?
}
