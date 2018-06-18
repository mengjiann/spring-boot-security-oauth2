# spring-boot-security-oauth2
This Spring Boot Application demonstrates 2 different ways of authenticating users:
- Self managed account authentication using dbms
- Integration with 3rd party OAuth2 authentication server such as Faceebook and Google


# Things to note
- Remember to get client-id and client secret for both Facebook and Google and fill up in the application properties.
- A self-signed certificate is generated to serve the site on HTTPS because Facebook enforces OAuth2 client to be on HTTPS before authenticating against Facebook server.
- If you decided to terminate your SSL cert on a load balancer, you should enable `server.use-forward-headers` under the properties file. Refer to https://stackoverflow.com/questions/49765687/redirect-uri-using-http-instead-of-https/50808675#50808675. Please also disable the SSL key configuration in the application.properties file.
- Enable `server.tomcat.accesslog.enabled` in the application properties for troubleshooting on the oauth2 access log.

# Additional Library 
- Webjars on Bootstrap and JQuery
- H2 in memory database
- thymeleaf-extras-springsecurity4 for usage of Spring Security authentication library on thymeleaf. 


# References
- Good tutorial on Spring security OAuth2 -  https://spring.io/blog/2018/03/06/using-spring-security-5-to-integrate-with-oauth-2-secured-services-such-as-facebook-and-github
- Special thanks to Spring Lemon project - https://github.com/naturalprogrammer/spring-lemon
- Official Documentation - https://docs.spring.io/spring-security/site/docs/5.0.5.RELEASE/reference/htmlsingle/#jc-oauth2login
- Official Documentation - https://docs.spring.io/spring-security/site/docs/current/reference/html/oauth2login-advanced.html
- Facebook Graph Explorer - https://developers.facebook.com/tools/explorer/