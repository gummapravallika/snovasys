package com.trustvault.audit;
import org.springframework.data.jpa.repository.JpaRepository; import java.util.*;
public interface AuditRepository extends JpaRepository<AuditEvent,UUID> { List<AuditEvent> findTop500ByOrderByOccurredAtDesc(); List<AuditEvent> findTop500ByActorEmailContainingIgnoreCaseOrderByOccurredAtDesc(String email); }
