package com.yaswanth.claimflow.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yaswanth.claimflow.dto.ClaimDTO;
import com.yaswanth.claimflow.entity.Claim;

public interface ClaimRepository extends JpaRepository<Claim, Long> {
    List<Claim> findByUser_UserId(Long userId);

	Claim save(Claim claim);
	
    List<Claim> findByClaimStatusAndLastUpdatedBefore(String status, Date lastUpdated);

}