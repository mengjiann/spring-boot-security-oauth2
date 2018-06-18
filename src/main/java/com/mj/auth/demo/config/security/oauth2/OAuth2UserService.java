package com.mj.auth.demo.config.security.oauth2;

import com.mj.auth.demo.account.Account;
import com.mj.auth.demo.account.exception.AccountCreationException;
import com.mj.auth.demo.account.service.AccountService;
import com.mj.auth.demo.config.security.auth.CustomPrincipal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.oidc.StandardClaimNames;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
public class OAuth2UserService  extends DefaultOAuth2UserService {

    @Autowired
    private AccountService accountService;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        try{
            log.debug("User Request: {} Client token: {}",userRequest.getClientRegistration(), userRequest.getAccessToken().getTokenValue());

            OAuth2User oAuth2User = super.loadUser(userRequest);
            return buildPrincipal(oAuth2User, userRequest.getClientRegistration().getRegistrationId());
        }catch (Exception e){
            log.error("{}",e.getMessage(),e);
            throw new OAuth2AuthenticationException(
                    new OAuth2Error("invalid_oauth_user_request"), "Unable to laod user from OAuth2UserRequest.");
        }
    }


    private CustomPrincipal buildPrincipal(OAuth2User oAuth2User, String registrationId) throws OAuth2AuthenticationException {

        Map<String, Object> attributes = oAuth2User.getAttributes();

        log.debug("oAuth2User: {} with attributes: {}",oAuth2User.toString(),attributes.entrySet().toString());

        String email = (String) attributes.get(StandardClaimNames.EMAIL);

        if(email == null ||  email.isEmpty()){
            throw new OAuth2AuthenticationException(
                    new OAuth2Error("invalid_oauth_user_email"),
                    "Unable to retrieve email from attribute for user: "+oAuth2User.getName());
        }

        Account validAccount = accountService.findByEmail(email).orElseGet(()->{
            try{
                log.debug("Creating new account for email: {}",email);
                Account account = accountService.registerNewOAuthUser(email,
                        (String)attributes.get(StandardClaimNames.NAME));
                log.info("Registered account: {}",account);
                return account;
            }catch (AccountCreationException e) {
                log.error("{]",e.getMessage(),e);
                throw new OAuth2AuthenticationException(
                        new OAuth2Error("oauth_user_creation"),
                        "Unable to register account with email: "+email);
            }
        });

        CustomPrincipal principal = new CustomPrincipal(validAccount);

        principal.setAttributes(attributes);
        principal.setName(oAuth2User.getName());

        log.info("Principal from OAuth2 user: {}",principal);

        return principal;
    }
}
