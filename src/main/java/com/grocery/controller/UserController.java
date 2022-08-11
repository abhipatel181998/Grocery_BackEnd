package com.grocery.controller;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.io.IOException;
import java.net.URI;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.grocery.model.Role;
import com.grocery.model.User;
import com.grocery.service.UserService;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/user")
@Log4j2
@RequiredArgsConstructor
public class UserController {

	@Autowired
	private final UserService userService;

	/**
	 * @return all the users.
	 */
	@GetMapping("/user")
	public ResponseEntity<?> getUsers() {
		try {
			log.info("Get All Users Accessed.");
			return ResponseEntity.ok().body(userService.getAllUsers());
		} catch (Exception e) {
			log.error(e);
			return ResponseEntity.internalServerError().body("Error occcured while getting users.");
		}
	}

	/**
	 * @param userId
	 * @return User object found by userId.
	 */
	@GetMapping("/user/{id}")
	public ResponseEntity<?> getUserById(@PathVariable(name = "id", required = true) Long userId) {
		try {
			Optional<User> user = userService.getUserById(userId);

			if (user.isPresent()) {
				log.info("User found for id: " + userId);
				return ResponseEntity.ok().body(user);
			} else {
				log.error("User not found for id: " + userId);
				return new ResponseEntity<>("User not found with id: " + userId, HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			log.error(e);
			return ResponseEntity.internalServerError().body("Error occcured while getting users.");
		}
	}

	/**
	 * Add new user in the database.
	 * 
	 * @param user
	 * @return HttpStatus with with user object or error message.
	 */
	@PostMapping("/user/save")
	public ResponseEntity<?> addUser(@RequestBody User user) {
		try {
			var response = userService.saveUser(user);
			log.info("User added :" + response);
			URI uri = URI
					.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/user/save").toUriString());
			return ResponseEntity.created(uri).body(response);
		} catch (Exception e) {
			log.error(e);
			return ResponseEntity.internalServerError().body("Error occcured while creating user.");
		}
	}

	/**
	 * Update user in the database.
	 * 
	 * @param user
	 * @param userId
	 * @return HttpStatus with with user object or error message.
	 */
	@PutMapping("user/update/{id}")
	public ResponseEntity<?> updateUser(@RequestBody User user,
			@PathVariable(name = "id", required = true) Long userId) {

		try {
			var response = userService.updateUser(user, userId);
			log.info("User updated: " + response);

			return new ResponseEntity<>(response, HttpStatus.OK);

		} catch (NullPointerException ne) {
			log.error("User not found for id: " + userId);
			return new ResponseEntity<>("User not found!", HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			log.error(e);
			return ResponseEntity.internalServerError().body("Error occcured while deleting user.");
		}

	}

	/**
	 * Update user in the database.
	 * 
	 * @param userId
	 * @return HttpStatus with with user id or error message.
	 */
	@DeleteMapping("/user/delete/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable(name = "id", required = true) Long userId) {

		try {
			var response = userService.deleteUser(userId);
			log.info("User deleted with id: " + response);

			if (response != null)
				return new ResponseEntity<>(response, HttpStatus.OK);
			else {
				log.error("User not found for id: " + userId);
				return new ResponseEntity<>("User not found!", HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			log.error(e);
			return ResponseEntity.internalServerError().body("Error occcured while DELETING user.");
		}

	}
	
	@GetMapping("/refreshtoken")
	public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String authorizationHeader = request.getHeader(AUTHORIZATION);
		
		if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
			try {
				String refresh_token = authorizationHeader.substring("Bearer ".length());
				Algorithm algorithm = Algorithm.HMAC256("groceryAppAbhiAndHarshil".getBytes());
				JWTVerifier jwtVerifier = JWT.require(algorithm).build();
				DecodedJWT decodedJWT = jwtVerifier.verify(refresh_token);
				String username = decodedJWT.getSubject();
				
				User user = userService.getUserByEmail(username);
				
				String access_token = JWT.create().withSubject(user.getEmail())
						.withExpiresAt(new Date(System.currentTimeMillis() + 60 * 60 * 1000))
						.withIssuer(request.getRequestURL().toString())
						.withClaim("roles",
								user.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
						.sign(algorithm);
				
				Map<String, String> tokens = new HashMap<>();
				tokens.put("access_tocken", access_token);
				tokens.put("refresh_tocken", refresh_token);

				response.setContentType(APPLICATION_JSON_VALUE);
				new ObjectMapper().writeValue(response.getOutputStream(), tokens);
				
			} catch (Exception e) {
				response.setStatus(FORBIDDEN.value());
				Map<String, String> error = new HashMap<String, String>();
				error.put("error_mesage", e.getMessage());
				response.setContentType(APPLICATION_JSON_VALUE);
				new ObjectMapper().writeValue(response.getOutputStream(), error);
			}
		}else {
			throw new RuntimeException("Refresh token is missing");
		}
	}
}

@Data
class RoleToUser {
	private String email;
	private String name;
}
