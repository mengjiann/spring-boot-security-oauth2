package com.mj.auth.demo.config.security.auth;

import com.mj.auth.demo.account.Account;
import lombok.ToString;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

@ToString
public class CustomPrincipal  implements OidcUser, UserDetails,CredentialsContainer {



    private Account account;

    private Map<String, Object> attributes;
    private String name;
    private Map<String, Object> claims;
    private OidcUserInfo userInfo;
    private OidcIdToken idToken;

    public CustomPrincipal(Account account) {
        this.account = account;
    }

    public  Account getAccount(){
        return this.account;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void eraseCredentials() {
        account.setPassword(null);
        attributes = null;
        claims = null;
        userInfo = null;
        idToken = null;
    }

    @Override
    public String getPassword() {
        return account.getPassword();
    }

    @Override
    public String getUsername() {
        return account.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Map<String, Object> getClaims() {
        return claims;
    }

    @Override
    public OidcUserInfo getUserInfo() {
        return userInfo;
    }

    @Override
    public OidcIdToken getIdToken() {
        return idToken;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(new SimpleGrantedAuthority("User"));
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setClaims(Map<String, Object> claims) {
        this.claims = claims;
    }

    public void setUserInfo(OidcUserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public void setIdToken(OidcIdToken idToken) {
        this.idToken = idToken;
    }

}
