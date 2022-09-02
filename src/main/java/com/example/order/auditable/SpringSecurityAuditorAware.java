
package com.example.order.auditable;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class SpringSecurityAuditorAware implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {

        // Just return a string representing the username
        String currentUser= SecurityContextHolder.getContext().getAuthentication().getName();
        return Optional.ofNullable(currentUser);
    }

}