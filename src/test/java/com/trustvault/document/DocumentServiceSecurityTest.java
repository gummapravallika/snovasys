package com.trustvault.document;

import com.trustvault.audit.AuditService;
import com.trustvault.common.CurrentUser;
import com.trustvault.user.AppUser;
import com.trustvault.user.AppUserRepository;
import com.trustvault.user.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.security.access.AccessDeniedException;

import java.lang.reflect.Field;
import java.nio.file.Path;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class DocumentServiceSecurityTest {
    @TempDir Path tempDir;
    VaultDocumentRepository documents;
    DocumentPermissionRepository permissions;
    AppUserRepository users;
    CurrentUser current;
    AuditService audit;
    DocumentService service;

    @BeforeEach void setUp() {
        documents = mock(VaultDocumentRepository.class);
        permissions = mock(DocumentPermissionRepository.class);
        users = mock(AppUserRepository.class);
        current = mock(CurrentUser.class);
        audit = mock(AuditService.class);
        service = new DocumentService(documents, permissions, users, current, audit, tempDir.toString());
    }

    @Test void viewerCannotReadAnotherOwnersDocumentWithoutPermission() throws Exception {
        UUID ownerId = UUID.randomUUID();
        UUID viewerId = UUID.randomUUID();
        UUID documentId = UUID.randomUUID();
        AppUser owner = new AppUser("Owner", "owner@example.test", "hash", Role.OWNER, "HR");
        AppUser viewer = new AppUser("Viewer", "viewer@example.test", "hash", Role.VIEWER, "Finance");
        setId(owner, ownerId);
        setId(viewer, viewerId);
        VaultDocument document = new VaultDocument("Salary letter", "Confidential", "Salary", "salary.pdf", "random.pdf", "application/pdf", 100, "abc", owner);
        setId(document, documentId);
        when(current.get()).thenReturn(viewer);
        when(documents.findById(documentId)).thenReturn(Optional.of(document));
        when(permissions.findByDocument_IdAndUser_Id(documentId, viewerId)).thenReturn(Optional.empty());

        assertThrows(AccessDeniedException.class, () -> service.getReadable(documentId));
        verify(audit).record(eq("viewer@example.test"), eq("DOCUMENT_VIEW"), eq("DENIED"), eq(documentId), eq("Salary letter"), anyString());
    }

    private static void setId(Object entity, UUID id) throws Exception {
        Field f = entity.getClass().getDeclaredField("id");
        f.setAccessible(true);
        f.set(entity, id);
    }
}
