package com.trustvault.document;

import com.trustvault.user.AppUser;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.time.Instant; import java.time.LocalDateTime; import java.time.ZoneId;
import java.util.UUID; import java.util.ArrayList; import java.util.List;

@Entity @Table(name="vault_documents", indexes={@Index(name="idx_doc_owner",columnList="owner_id"),@Index(name="idx_doc_category",columnList="category"),@Index(name="idx_doc_created",columnList="created_at")})
public class VaultDocument {
    @Id @GeneratedValue(strategy=GenerationType.UUID) private UUID id;
    @NotBlank @Column(nullable=false,length=180) private String title;
    @Column(length=1000) private String description;
    @Column(nullable=false,length=80) private String category;
    @Column(nullable=false,length=255) private String originalFilename;
    @Column(nullable=false,unique=true,length=80) private String storageKey;
    @Column(nullable=false,length=100) private String contentType;
    @Column(nullable=false) private long sizeBytes;
    @Column(nullable=false,length=64) private String sha256;
    @ManyToOne(fetch=FetchType.LAZY,optional=false) @JoinColumn(name="owner_id",nullable=false) private AppUser owner;
    @Column(nullable=false,updatable=false) private Instant createdAt=Instant.now();
    @Column(nullable=false) private Instant updatedAt=Instant.now();

    // One document can have many named-user permission grants.
    @OneToMany(mappedBy="document", fetch=FetchType.LAZY)
    private List<DocumentPermission> permissions = new ArrayList<>();

    protected VaultDocument() {}
    public VaultDocument(String title,String description,String category,String originalFilename,String storageKey,String contentType,long sizeBytes,String sha256,AppUser owner){
        this.title=title;this.description=description;this.category=category;this.originalFilename=originalFilename;this.storageKey=storageKey;this.contentType=contentType;this.sizeBytes=sizeBytes;this.sha256=sha256;this.owner=owner;
    }
    @PreUpdate void touch(){updatedAt=Instant.now();}
    public UUID getId(){return id;} public String getTitle(){return title;} public void setTitle(String v){title=v;} public String getDescription(){return description;} public void setDescription(String v){description=v;}
    public String getCategory(){return category;} public void setCategory(String v){category=v;} public String getOriginalFilename(){return originalFilename;} public String getStorageKey(){return storageKey;}
    public String getContentType(){return contentType;} public long getSizeBytes(){return sizeBytes;} public String getSha256(){return sha256;} public AppUser getOwner(){return owner;} public Instant getCreatedAt(){return createdAt;} public Instant getUpdatedAt(){return updatedAt;} public List<DocumentPermission> getPermissions(){return permissions;} public LocalDateTime getCreatedAtDisplay(){return LocalDateTime.ofInstant(createdAt, ZoneId.systemDefault());} public LocalDateTime getUpdatedAtDisplay(){return LocalDateTime.ofInstant(updatedAt, ZoneId.systemDefault());}
}
