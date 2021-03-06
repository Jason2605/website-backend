package dev.codesupport.web.common.service.controller;

import dev.codesupport.web.common.exception.InvalidUserException;
import dev.codesupport.web.common.exception.ServiceLayerException;
import dev.codesupport.web.common.security.models.AuthenticationRequest;
import dev.codesupport.web.common.security.AuthorizationService;
import dev.codesupport.web.common.service.service.RestResponse;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

//S2068 - This is not a real password
@SuppressWarnings("squid:S2068")
public class AuthenticationControllerImplTest {

    @Test(expected = InvalidUserException.class)
    public void shouldBubbleExceptionThrownByInvalidCredentials() {
        String email = "user@domain.com";
        String password = "clearpassword";

        AuthenticationRequest request = new AuthenticationRequest();
        request.setEmail(email);
        request.setPassword(password);

        AuthorizationService mockAuthorizationService = mock(AuthorizationService.class);

        doThrow(InvalidUserException.class)
                .when(mockAuthorizationService)
                .createTokenForEmailAndPassword(
                        request.getEmail(),
                        request.getPassword()
                );

        AuthenticationControllerImpl controller = new AuthenticationControllerImpl(mockAuthorizationService);

        controller.authenticate(request);
    }

    @Test
    public void shouldReturnTokenIfAuthenticatedCredentials() {
        String email = "user@domain.com";
        String password = "clearpassword";
        String token = "mytokenstring";

        AuthenticationRequest request = new AuthenticationRequest();
        request.setEmail(email);
        request.setPassword(password);

        AuthorizationService mockAuthorizationService = mock(AuthorizationService.class);

        doReturn(token)
                .when(mockAuthorizationService)
                .createTokenForEmailAndPassword(
                        request.getEmail(),
                        request.getPassword()
                );

        AuthenticationControllerImpl spyController = spy(new AuthenticationControllerImpl(mockAuthorizationService));

        //unchecked - This is fine for creating mocks.
        //noinspection unchecked
        RestResponse<String> mockRestResponse = mock(RestResponse.class);

        doReturn(mockRestResponse)
                .when(spyController)
                .getRestResponse(Collections.singletonList(token));

        ResponseEntity<RestResponse<String>> response = spyController.authenticate(request);

        assertEquals(mockRestResponse, response.getBody());
    }

    @Test(expected = ServiceLayerException.class)
    public void shouldBubbleUpExceptionThrownByAuthorizationService() {
        String code = "code";

        AuthorizationService mockAuthorizationService = mock(AuthorizationService.class);

        doThrow(ServiceLayerException.class)
                .when(mockAuthorizationService)
                .linkDiscord(code);

        AuthenticationControllerImpl controller = new AuthenticationControllerImpl(mockAuthorizationService);

        controller.linkDiscord(code);
    }

    @Test
    public void shouldInvokeLinkDiscord() {
        String code = "code";

        AuthorizationService mockAuthorizationService = mock(AuthorizationService.class);

        AuthenticationControllerImpl controller = new AuthenticationControllerImpl(mockAuthorizationService);

        controller.linkDiscord(code);

        verify(mockAuthorizationService, times(1))
                .linkDiscord(code);
    }

    @Test
    public void shouldReturnOkForLinkDiscord() {
        String code = "code";

        AuthorizationService mockAuthorizationService = mock(AuthorizationService.class);

        AuthenticationControllerImpl controller = new AuthenticationControllerImpl(mockAuthorizationService);

        List<String> expected = Collections.singletonList("Ok");

        ResponseEntity<RestResponse<Serializable>> responseEntity = controller.linkDiscord(code);

        //ConstantConditions - This is fine for the context of this test.
        //noinspection ConstantConditions
        assertEquals(expected, responseEntity.getBody().getResponse());
    }

    @Test
    public void shouldCreateCorrectRestResponseObject() {
        String referenceId = "1234";

        AuthorizationService mockAuthorizationService = mock(AuthorizationService.class);

        AuthenticationControllerImpl controller = new AuthenticationControllerImpl(mockAuthorizationService);

        List<String> token = Collections.singletonList("tokentokentoken");

        RestResponse<String> expected = new RestResponse<>();
        expected.setReferenceId(referenceId);
        expected.setResponse(token);

        RestResponse<String> actual = controller.getRestResponse(token);
        actual.setReferenceId(referenceId);

        assertEquals(expected, actual);
    }

}
