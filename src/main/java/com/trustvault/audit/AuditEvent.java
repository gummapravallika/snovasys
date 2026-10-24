package com.trustvault.audit;
import jakarta.persistence.*; import java.time.Instant; import java.time.LocalDateTime; import java.time.ZoneId; import java.util.UUID;
@Entity @Table(name="audit_events",indexes={@Index(name="idx_audit_time",columnList="occurred_at"),@Index(name="idx_audit_actor",columnList="actor_email")})
public class AuditEvent {
 @Id @GeneratedValue(strategy=GenerationType.UUID) private UUID id;
 @Column(nullable=false,length=190) private String actorEmail;
 @Column(nullable=false,length=50) private String action;
 @Column(nullable=false,length=20) private String outcome;
 @Column(length=36) private String documentId;
 @Column(length=255) private String documentTitle;
 @Column(length=1000) private String details;
 @Column(length=64) private String requestId;
 @Column(nullable=false,updatable=false) private Instant occurredAt=Instant.now();
 protected AuditEvent(){}
 public AuditEvent(String actorEmail,String action,String outcome,String documentId,String documentTitle,String details,String requestId){this.actorEmail=actorEmail;this.action=action;this.outcome=outcome;this.documentId=documentId;this.documentTitle=documentTitle;this.details=details;this.requestId=requestId;}
 public UUID getId(){return id;} public String getActorEmail(){return actorEmail;} public String getAction(){return action;} public String getOutcome(){return outcome;} public String getDocumentId(){return documentId;} public String getDocumentTitle(){return documentTitle;} public String getDetails(){return details;} public String getRequestId(){return requestId;} public Instant getOccurredAt(){return occurredAt;} public LocalDateTime getOccurredAtDisplay(){return LocalDateTime.ofInstant(occurredAt, ZoneId.systemDefault());}
}
