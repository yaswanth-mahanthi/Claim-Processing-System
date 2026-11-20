package com.yaswanth.claimflow.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.yaswanth.claimflow.dto.ClaimStatusUpdateMessage;
import com.yaswanth.claimflow.dto.NotificationDTO;

@Service
public class KafkaNotificationService {
	
    
    private static final String CLAIM_STATUS_TOPIC = "claim-status-updates";

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    public void sendClaimStatusUpdate(Long claimId, String newStatus, String userEmail) {
        ClaimStatusUpdateMessage message = new ClaimStatusUpdateMessage(claimId, newStatus, userEmail);
        kafkaTemplate.send(CLAIM_STATUS_TOPIC, message);
    }
    
}
