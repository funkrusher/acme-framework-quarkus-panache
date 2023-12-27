package org.acme.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Default;
import jakarta.inject.Inject;
import org.acme.entity.UserEntity;
import org.acme.entity.UserRoleEntity;
import org.acme.util.cognito.AcmeClaim;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;
import software.amazon.awssdk.services.cognitoidentityprovider.model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * CognitoLocalService
 */
@ApplicationScoped
public class CognitoLocalService {
    private static final Logger LOGGER = Logger.getLogger(CognitoLocalService.class);

    @ConfigProperty(name = "cognitolocal.userpoolid")
    String userPoolId;

    @ConfigProperty(name = "cognitolocal.userpoolclientid")
    String userPoolClientId;

    @Inject
    @Default
    private CognitoIdentityProviderClient cognitoIpc;

    @Inject
    ObjectMapper objectMapper;

    /**
     * Signup a new user into the pool
     *
     * @param clientId clientId
     * @param email    email
     * @param password password
     * @param firstname firstname
     * @param lastname lastname
     * @param roleId roleId
     * @return userSub
     */
    public String signup(
            Integer clientId,
            String email,
            String password,
            String firstname,
            String lastname,
            String roleId) {

        UserEntity user = new UserEntity();
        user.clientId = clientId;
        user.email = email;
        user.firstname = firstname;
        user.lastname = lastname;

        UserRoleEntity userRole = new UserRoleEntity();

        String userSub = null;
        try {
            user.persistAndFlush();

            userRole.userId = user.userId;
            userRole.roleId = roleId;
            userRole.persistAndFlush();

            List<String> roles = new ArrayList<>();
            roles.add(roleId);

            AcmeClaim acmeClaim = new AcmeClaim(clientId, user.userId, roles);
            String acmeClaimStr = objectMapper.writeValueAsString(acmeClaim);

            SignUpRequest request = SignUpRequest.builder()
                    .clientId(userPoolClientId)
                    .username(email)
                    .password(password)
                    .userAttributes(
                            AttributeType.builder().name("email").value(email).build(),
                            AttributeType.builder().name("custom:acme").value(acmeClaimStr).build()
                    )
                    .build();
            SignUpResponse response = cognitoIpc.signUp(request);
            userSub = response.userSub();

            AdminConfirmSignUpRequest confirmRequest = AdminConfirmSignUpRequest.builder()
                    .userPoolId(userPoolId)
                    .username(email)
                    .build();
            AdminConfirmSignUpResponse confirmResponse = cognitoIpc.adminConfirmSignUp(confirmRequest);

        } catch (Exception e) {
            // delete database entries in case of error (manual rollback)
            if (userRole.isPersistent()) {
                userRole.delete();
            }
            if (user.isPersistent()) {
                user.delete();
            }
        }
        return userSub;
    }

    /**
     * Signin with an existing user.
     *
     * @param email    email
     * @param password password
     * @return jwtTokens
     * @throws NotAuthorizedException
     */
    public Map<String, String> signin(String email, String password) throws NotAuthorizedException {
        InitiateAuthResponse response = cognitoIpc.initiateAuth(
                InitiateAuthRequest.builder()
                        .clientId(userPoolClientId)
                        .authFlow(AuthFlowType.USER_PASSWORD_AUTH)
                        .authParameters(
                                Map.of(
                                        "USERNAME", email,
                                        "PASSWORD", password
                                )
                        )
                        .build()
        );
        AuthenticationResultType result = response.authenticationResult();

        Map<String, String> jwtTokens = new HashMap<>();
        jwtTokens.put("access_token", result.accessToken());
        jwtTokens.put("refresh_token", result.refreshToken());
        jwtTokens.put("id_token", result.idToken());
        return jwtTokens;
    }

    /**
     * Find and return an existing user.
     *
     * @param email email
     * @return user-attributes
     * @throws UserNotFoundException
     */
    public Map<String, String> find(String email) throws UserNotFoundException {
        AdminGetUserRequest request = AdminGetUserRequest.builder()
                .userPoolId(userPoolId)
                .username(email)
                .build();
        AdminGetUserResponse response = cognitoIpc.adminGetUser(request);

        List<AttributeType> userAttributes = response.userAttributes();
        Map<String, String> attributesMap = new HashMap<>();
        for (AttributeType attribute : userAttributes) {
            attributesMap.put(attribute.name(), attribute.value());
        }
        // The attributes of the user can be accessed through the attributesMap object.
        return attributesMap;
    }


}
