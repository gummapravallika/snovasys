package com.trustvault.document;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.time.Instant; import java.util.*;
public interface VaultDocumentRepository extends JpaRepository<VaultDocument,UUID> {
 @Query("select distinct d from VaultDocument d left join DocumentPermission p on p.document=d where (:admin=true or d.owner.id=:userId or p.user.id=:userId) and (:q is null or lower(d.title) like lower(concat('%',:q,'%')) or lower(d.originalFilename) like lower(concat('%',:q,'%'))) and (:category is null or lower(d.category)=lower(:category)) and (:ownerName is null or lower(d.owner.fullName) like lower(concat('%',:ownerName,'%'))) and (:fromDate is null or d.createdAt>=:fromDate) and (:toDate is null or d.createdAt<:toDate) order by d.createdAt desc")
 List<VaultDocument> search(@Param("userId") UUID userId,@Param("admin") boolean admin,@Param("q") String q,@Param("category") String category,@Param("ownerName") String ownerName,@Param("fromDate") Instant fromDate,@Param("toDate") Instant toDate);
 Optional<VaultDocument> findByIdAndOwner_Id(UUID id,UUID ownerId);
}
