package com.trustvault.audit;
import jakarta.servlet.http.HttpServletRequest; import org.springframework.transaction.annotation.Propagation; import org.springframework.stereotype.Service; import org.springframework.transaction.annotation.Transactional; import java.util.UUID;
@Service public class AuditService {
 private final AuditRepository repository; private final HttpServletRequest request;
 public AuditService(AuditRepository repository,HttpServletRequest request){this.repository=repository;this.request=request;}
 @Transactional(propagation=Propagation.REQUIRES_NEW) public void record(String actor,String action,String outcome,UUID documentId,String title,String details){
  String rid=request.getHeader("X-Request-ID"); if(rid==null||rid.isBlank()) rid=UUID.randomUUID().toString(); if(rid.length()>64) rid=rid.substring(0,64);
  repository.save(new AuditEvent(actor==null?"anonymous":actor,action,outcome,documentId==null?null:documentId.toString(),title,details,rid));
 }
}
