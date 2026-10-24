package com.trustvault.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.time.Instant;
import java.util.UUID;
import java.util.ArrayList;
import java.util.List;
import com.trustvault.document.VaultDocument;
import com.trustvault.document.DocumentPermission;

@Entity
@Table(name="app_users", uniqueConstraints=@UniqueConstraint(name="uk_app_user_email", columnNames="email"))
public class AppUser {
    @Id @GeneratedValue(strategy=GenerationType.UUID) private UUID id;
    @NotBlank @Column(nullable=false, length=120) private String fullName;
    @Email @NotBlank @Column(nullable=false, length=190) private String email;
    @Column(nullable=false) private String passwordHash;
    @Enumerated(EnumType.STRING) @Column(nullable=false, length=20) private Role role;
    @Column(nullable=false, length=120) private String department;
    @Column(nullable=false) private boolean enabled=true;
    @Column(nullable=false, updatable=false) private Instant createdAt=Instant.now();

    // One employee can own many documents.
    @OneToMany(mappedBy="owner", fetch=FetchType.LAZY)
    private List<VaultDocument> ownedDocuments = new ArrayList<>();

    // One employee can receive access to many documents.
    @OneToMany(mappedBy="user", fetch=FetchType.LAZY)
    private List<DocumentPermission> documentPermissions = new ArrayList<>();

    // One employee can grant permissions to many people/documents.
    @OneToMany(mappedBy="grantedBy", fetch=FetchType.LAZY)
    private List<DocumentPermission> permissionsGranted = new ArrayList<>();

    protected AppUser() {}
    public AppUser(String fullName, String email, String passwordHash, Role role, String department) {
        this.fullName=fullName; this.email=email.toLowerCase().trim(); this.passwordHash=passwordHash; this.role=role; this.department=department;
    }
    public UUID getId(){return id;} public String getFullName(){return fullName;} public void setFullName(String v){fullName=v;}
    public String getEmail(){return email;} public void setEmail(String v){email=v.toLowerCase().trim();}
    public String getPasswordHash(){return passwordHash;} public void setPasswordHash(String v){passwordHash=v;}
    public Role getRole(){return role;} public void setRole(Role v){role=v;} public String getDepartment(){return department;} public void setDepartment(String v){department=v;}
    public boolean isEnabled(){return enabled;} public void setEnabled(boolean v){enabled=v;} public Instant getCreatedAt(){return createdAt;}
    public List<VaultDocument> getOwnedDocuments(){return ownedDocuments;}
    public List<DocumentPermission> getDocumentPermissions(){return documentPermissions;}
    public List<DocumentPermission> getPermissionsGranted(){return permissionsGranted;}
}
