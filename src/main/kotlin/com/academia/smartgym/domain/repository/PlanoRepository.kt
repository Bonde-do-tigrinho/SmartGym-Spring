package com.academia.smartgym.domain.repository

import com.academia.smartgym.domain.model.Plano
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PlanoRepository : JpaRepository<Plano, Long>