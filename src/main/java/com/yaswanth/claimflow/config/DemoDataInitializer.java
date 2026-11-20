package com.yaswanth.claimflow.config;

import com.yaswanth.claimflow.entity.Claim;
import com.yaswanth.claimflow.entity.User;
import com.yaswanth.claimflow.repository.ClaimRepository;
import com.yaswanth.claimflow.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;

@Configuration
public class DemoDataInitializer {

    @Bean
    CommandLineRunner seedDemoData(UserRepository userRepository,
                                   ClaimRepository claimRepository,
                                   PasswordEncoder passwordEncoder) {
        return args -> {
            if (userRepository.count() > 0) {
                return;
            }

            Date now = new Date();
            User demoUser = new User();
            demoUser.setUsername("demo");
            demoUser.setPasswordHash(passwordEncoder.encode("Demo123!"));
            demoUser.setEmail("demo@claimflow.local");
            demoUser.setRole("USER");
            demoUser.setCreatedAt(now);
            demoUser.setUpdatedAt(now);
            demoUser.setStatus("active");
            demoUser = userRepository.save(demoUser);

            seedClaim(claimRepository, demoUser, "Vehicle collision damage", 4850.00, "Submitted", "demo@claimflow.local", now);
            seedClaim(claimRepository, demoUser, "Storm damage to roof", 12750.50, "Under Review", "demo@claimflow.local", now);
            seedClaim(claimRepository, demoUser, "Emergency hospital visit", 3240.25, "Approved", "demo@claimflow.local", now);
            seedClaim(claimRepository, demoUser, "Home water leak", 2180.75, "Pending", "demo@claimflow.local", now);
            seedClaim(claimRepository, demoUser, "Stolen electronic devices", 1650.00, "Rejected", "demo@claimflow.local", now);
        };
    }

    private void seedClaim(ClaimRepository repository, User user, String type,
                           double amount, String status, String email, Date date) {
        Claim claim = new Claim();
        claim.setUser(user);
        claim.setClaimType(type);
        claim.setClaimAmount(amount);
        claim.setClaimStatus(status);
        claim.setEmailId(email);
        claim.setClaimDate(date);
        claim.setLastUpdated(date);
        repository.save(claim);
    }
}
