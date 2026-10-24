package com.trustvault.document;
import org.springframework.data.jpa.repository.JpaRepository; import java.util.*;
public interface DocumentPermissionRepository extends JpaRepository<DocumentPermission,UUID> {
 Optional<DocumentPermission> findByDocument_IdAndUser_Id(UUID documentId,UUID userId);
 List<DocumentPermission> findByDocument_IdOrderByGrantedAtDesc(UUID documentId);
 boolean existsByDocument_IdAndUser_Id(UUID documentId,UUID userId);
 void deleteByDocument_IdAndUser_Id(UUID documentId,UUID userId);
}
