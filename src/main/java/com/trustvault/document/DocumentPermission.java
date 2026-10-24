package com.trustvault.document;
import com.trustvault.user.AppUser;
import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;
@Entity @Table(name="document_permissions",uniqueConstraints=@UniqueConstraint(name="uk_doc_permission_user",columnNames={"document_id","user_id"}))
public class DocumentPermission {
 @Id @GeneratedValue(strategy=GenerationType.UUID) private UUID id;
 @ManyToOne(fetch=FetchType.LAZY,optional=false) @JoinColumn(name="document_id",nullable=false) private VaultDocument document;
 @ManyToOne(fetch=FetchType.LAZY,optional=false) @JoinColumn(name="user_id",nullable=false) private AppUser user;
 @Enumerated(EnumType.STRING) @Column(nullable=false,length=10) private AccessLevel accessLevel;
 @ManyToOne(fetch=FetchType.LAZY,optional=false) @JoinColumn(name="granted_by",nullable=false) private AppUser grantedBy;
 @Column(nullable=false,updatable=false) private Instant grantedAt=Instant.now();
 protected DocumentPermission(){}
 public DocumentPermission(VaultDocument d,AppUser u,AccessLevel a,AppUser by){document=d;user=u;accessLevel=a;grantedBy=by;}
 public UUID getId(){return id;} public VaultDocument getDocument(){return document;} public AppUser getUser(){return user;} public AccessLevel getAccessLevel(){return accessLevel;} public void setAccessLevel(AccessLevel v){accessLevel=v;} public AppUser getGrantedBy(){return grantedBy;} public Instant getGrantedAt(){return grantedAt;}
}
