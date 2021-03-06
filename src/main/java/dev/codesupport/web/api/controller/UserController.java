package dev.codesupport.web.api.controller;

import dev.codesupport.web.common.service.service.RestResponse;
import dev.codesupport.web.domain.User;
import dev.codesupport.web.domain.UserRegistration;
import dev.codesupport.web.domain.UserStripped;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Defines endpoints and validations for the associated API Contract for the {@link User} resource.
 */
@RestController
@RequestMapping("api/user/v1")
@Api(value = "User", description = "REST API for User", tags = { "User" })
@Validated
public interface UserController {

    @ApiOperation("Get all Users")
    @GetMapping("/users")
    RestResponse<UserStripped> getAllUsers();

    @ApiOperation("Get User by id")
    @GetMapping("/users/{id}")
    RestResponse<UserStripped> getUserById(@PathVariable Long id);

    @ApiOperation("Register User")
    @PostMapping("/users")
    RestResponse<String> registerUser(@RequestBody @Valid UserRegistration userRegistration);

}
