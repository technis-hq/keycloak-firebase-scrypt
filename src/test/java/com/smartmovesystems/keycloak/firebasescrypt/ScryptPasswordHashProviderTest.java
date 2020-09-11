package com.smartmovesystems.keycloak.firebasescrypt;


import com.smartmovesystems.keycloak.firebasescrypt.model.ScryptHashParametersEntity;
import com.smartmovesystems.keycloak.firebasescrypt.model.ScryptHashParametersCredentialEntity;
import org.apache.commons.codec.binary.Base64;
import org.junit.Before;
import org.junit.Test;
import org.keycloak.models.PasswordPolicy;
import org.keycloak.models.credential.PasswordCredentialModel;

import static org.junit.Assert.*;

public class ScryptPasswordHashProviderTest {

    private ScryptParametersMockProvider mockProvider;
    private ScryptPasswordHashProvider hashProvider;

    @Before
    public void setUp() {
        mockProvider = new ScryptParametersMockProvider();

        ScryptHashParametersEntity parametersEntityOne = new ScryptHashParametersEntity();
        parametersEntityOne.setId("1");
        parametersEntityOne.setBaser64Signer("jxspr8Ki0RYycVU8zykbdLGjFQ3McFUH0uiiTvC8pVMXAn210wjLNmdZJzxUECKbm0QsEmYUSDzZvpjeJ9WmXA==");
        parametersEntityOne.setMemCost(14);
        parametersEntityOne.setRounds(8);
        parametersEntityOne.setSaltSeparator("Bw==");
        parametersEntityOne.setDefault(true);

        // Map user 1 -> hash parameters entity 1
        ScryptHashParametersCredentialEntity userOneMapping = new ScryptHashParametersCredentialEntity();
        userOneMapping.setCredentialId("1");
        userOneMapping.setHashParametersEntity(parametersEntityOne);
        userOneMapping.setId("1");
        parametersEntityOne.getCredentialMappings().add(userOneMapping);

        // Map user 2 -> hash parameters entity 1
        ScryptHashParametersCredentialEntity userTwoMapping = new ScryptHashParametersCredentialEntity();
        userTwoMapping.setCredentialId("2");
        userTwoMapping.setHashParametersEntity(parametersEntityOne);
        userTwoMapping.setId("2");
        parametersEntityOne.getCredentialMappings().add(userTwoMapping);

        ScryptHashParametersEntity parametersEntityTwo = new ScryptHashParametersEntity();
        parametersEntityTwo.setBaser64Signer("jxspr8Ki0ABCDVU8zykbdLGjFQ3McFUH0uiiTvC8pVMX1234567LNmdZJzxUECKbm0QsEmYUSDzZvpjeJ9WmXA==");
        parametersEntityTwo.setMemCost(14);
        parametersEntityTwo.setRounds(8);
        parametersEntityTwo.setSaltSeparator("Bw==");
        parametersEntityTwo.setDefault(false);

        // Map user 3 -> hash parameters entity 2
        ScryptHashParametersCredentialEntity userThreeMapping = new ScryptHashParametersCredentialEntity();
        userThreeMapping.setCredentialId("3");
        userThreeMapping.setHashParametersEntity(parametersEntityTwo);
        userThreeMapping.setId("3");
        parametersEntityTwo.getCredentialMappings().add(userThreeMapping);

        // Map user 4 -> hash parameters entity 2
        ScryptHashParametersCredentialEntity userFourMapping = new ScryptHashParametersCredentialEntity();
        userFourMapping.setCredentialId("4");
        userFourMapping.setHashParametersEntity(parametersEntityTwo);
        userFourMapping.setId("4");
        parametersEntityTwo.getCredentialMappings().add(userFourMapping);

        mockProvider.add(parametersEntityOne);
        mockProvider.add(parametersEntityTwo);

        hashProvider = new ScryptPasswordHashProvider(ScryptPasswordHashProviderFactory.ID, mockProvider, new SaltMockProvider());
    }

    @Test
    public void policyCheck() {
        PasswordCredentialModel model = PasswordCredentialModel.createFromValues(
                ScryptPasswordHashProviderFactory.ID,
                Base64.decodeBase64("42xEC+ixf3L2lw=="),
                0,
                "lSrfV15cpx95/sZS2W9c9Kp6i/LVgQNDNC/qzrCnh1SAyZvqmZqAjTdn3aoItz+VHjoZilo78198JAdRuid5lQ=="
        );
        PasswordPolicy policy = PasswordPolicy.build()
                .put(PasswordPolicy.HASH_ALGORITHM_ID, ScryptPasswordHashProviderFactory.ID)
                .build(new KeycloakSessionMock());
        boolean valid = hashProvider.policyCheck(policy, model);
        assertTrue(valid);
    }

    @Test
    public void policyCheckWrongPolicyAlgorithm() {
        PasswordCredentialModel model = PasswordCredentialModel.createFromValues(
                ScryptPasswordHashProviderFactory.ID,
                Base64.decodeBase64("42xEC+ixf3L2lw=="),
                0,
                "lSrfV15cpx95/sZS2W9c9Kp6i/LVgQNDNC/qzrCnh1SAyZvqmZqAjTdn3aoItz+VHjoZilo78198JAdRuid5lQ=="
        );
        PasswordPolicy policy = PasswordPolicy.build()
                .put(PasswordPolicy.HASH_ALGORITHM_ID, PasswordPolicy.HASH_ALGORITHM_DEFAULT)
                .build(new KeycloakSessionMock());
        boolean valid = hashProvider.policyCheck(policy, model);
        assertFalse(valid);
    }

    @Test
    public void policyCheckWrongCredentialAlgorithm() {
        PasswordCredentialModel model = PasswordCredentialModel.createFromValues(
                PasswordPolicy.HASH_ALGORITHM_DEFAULT,
                Base64.decodeBase64("42xEC+ixf3L2lw=="),
                0,
                "lSrfV15cpx95/sZS2W9c9Kp6i/LVgQNDNC/qzrCnh1SAyZvqmZqAjTdn3aoItz+VHjoZilo78198JAdRuid5lQ=="
        );
        PasswordPolicy policy = PasswordPolicy.build()
                .put(PasswordPolicy.HASH_ALGORITHM_ID, ScryptPasswordHashProviderFactory.ID)
                .build(new KeycloakSessionMock());
        boolean valid = hashProvider.policyCheck(policy, model);
        assertFalse(valid);
    }

    @Test
    public void encodedCredential() {
        // Expect to use default hash parameters
        String expected = "{\"value\":\"lSrfV15cpx95/sZS2W9c9Kp6i/LVgQNDNC/qzrCnh1SAyZvqmZqAjTdn3aoItz+VHjoZilo78198JAdRuid5lQ==\",\"salt\":\"42xEC+ixf3L2lw==\"}";
        PasswordCredentialModel encoded = hashProvider.encodedCredential("user1password", 0);
        assertEquals(expected, encoded.getSecretData());
    }

    @Test
    public void verifyUserOne() {
        PasswordCredentialModel model = PasswordCredentialModel.createFromValues(
                ScryptPasswordHashProviderFactory.ID,
                Base64.decodeBase64("42xEC+ixf3L2lw=="),
                0,
                "lSrfV15cpx95/sZS2W9c9Kp6i/LVgQNDNC/qzrCnh1SAyZvqmZqAjTdn3aoItz+VHjoZilo78198JAdRuid5lQ=="
        );

        model.setId("1");

        boolean verified = hashProvider.verify("user1password", model);
        assertTrue(verified);
    }

    @Test
    public void verifyUserOneBadPassword() {
        PasswordCredentialModel model = PasswordCredentialModel.createFromValues(
                ScryptPasswordHashProviderFactory.ID,
                Base64.decodeBase64("42xEC+ixf3L2lw=="),
                0,
                "lSrfV15cpx95/sZS2W9c9Kp6i/LVgQNDNC/qzrCnh1SAyZvqmZqAjTdn3aoItz+VHjoZilo78198JAdRuid5lQ=="
        );

        model.setId("1");

        boolean verified = hashProvider.verify("user1password_", model);
        assertFalse(verified);
    }

    @Test
    public void verifyUserTwo() {
        PasswordCredentialModel model = PasswordCredentialModel.createFromValues(
                ScryptPasswordHashProviderFactory.ID,
                Base64.decodeBase64("42xEC+ixf3L2lw=="),
                0,
                "lSrfV15cpx95/sZS2W9c9Kp6i/LVgQNDNC/qzrCnh1SAyZvqmZqAjTdn3aoItz+VHjoZilo78198JAdRuid5lQ=="
        );

        model.setId("2");

        boolean verified = hashProvider.verify("user1password", model);
        assertTrue(verified);
    }

    @Test
    public void verifyUserTwoBadPassword() {
        PasswordCredentialModel model = PasswordCredentialModel.createFromValues(
                ScryptPasswordHashProviderFactory.ID,
                Base64.decodeBase64("42xEC+ixf3L2lw=="),
                0,
                "lSrfV15cpx95/sZS2W9c9Kp6i/LVgQNDNC/qzrCnh1SAyZvqmZqAjTdn3aoItz+VHjoZilo78198JAdRuid5lQ=="
        );

        model.setId("2");

        boolean verified = hashProvider.verify("_user1password", model);
        assertFalse(verified);
    }
}