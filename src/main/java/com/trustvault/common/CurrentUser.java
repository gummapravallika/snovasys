package com.trustvault.common;
import com.trustvault.user.*; import org.springframework.security.core.Authentication; import org.springframework.security.core.context.SecurityContextHolder; import org.springframework.stereotype.Component;
@Component public class CurrentUser {
 private final AppUserRepository users; public CurrentUser(AppUserRepository users){this.users=users;}
 public AppUser get(){Authentication a=SecurityContextHolder.getContext().getAuthentication(); if(a==null||!a.isAuthenticated()||"anonymousUser".equals(a.getPrincipal())) throw new IllegalStateException("Authentication required"); return users.findByEmailIgnoreCase(a.getName()).orElseThrow(()->new IllegalStateException("Authenticated user no longer exists"));}
 public boolean isAdmin(){return get().getRole()==Role.ADMIN;}
}
