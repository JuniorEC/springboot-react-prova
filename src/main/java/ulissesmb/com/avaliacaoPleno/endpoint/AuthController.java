package ulissesmb.com.avaliacaoPleno.endpoint;import org.springframework.beans.factory.annotation.Autowired;import org.springframework.http.HttpStatus;import org.springframework.http.ResponseEntity;import org.springframework.security.authentication.AuthenticationManager;import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;import org.springframework.security.core.Authentication;import org.springframework.security.core.context.SecurityContextHolder;import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;import org.springframework.web.bind.annotation.PostMapping;import org.springframework.web.bind.annotation.RequestBody;import org.springframework.web.bind.annotation.RequestMapping;import org.springframework.web.bind.annotation.RestController;import org.springframework.web.servlet.support.ServletUriComponentsBuilder;import ulissesmb.com.avaliacaoPleno.domain.enuns.RoleEnum;import ulissesmb.com.avaliacaoPleno.domain.modelo.Role;import ulissesmb.com.avaliacaoPleno.domain.modelo.Usuario;import ulissesmb.com.avaliacaoPleno.exception.AppException;import ulissesmb.com.avaliacaoPleno.payload.ApiResponse;import ulissesmb.com.avaliacaoPleno.payload.JwtAuthenticationResponse;import ulissesmb.com.avaliacaoPleno.payload.LoginRequest;import ulissesmb.com.avaliacaoPleno.payload.SignUpRequest;import ulissesmb.com.avaliacaoPleno.reposity.IRoleRepository;import ulissesmb.com.avaliacaoPleno.reposity.IUsuarioRepository;import ulissesmb.com.avaliacaoPleno.security.JwtTokenProvider;import javax.validation.Valid;import java.net.URI;import java.util.Collections;@RestController@RequestMapping("/api/auth")public class AuthController {    @Autowired    IUsuarioRepository usuarioRepository;    @Autowired    IRoleRepository roleRepository;    @Autowired    AuthenticationManager authenticationProvider;    @Autowired    JwtTokenProvider tokenProvider;    @PostMapping("/signin")    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {        Authentication authentication = authenticationProvider.authenticate(                new UsernamePasswordAuthenticationToken(                        loginRequest.getLogin(),                        loginRequest.getSenha()                )        );        SecurityContextHolder.getContext().setAuthentication(authentication);        String jwt = tokenProvider.generateToken(authentication);        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));    }    @PostMapping("/signup")    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {        if(usuarioRepository.existsByLogin(signUpRequest.getLogin())) {            return new ResponseEntity(new ApiResponse(false, "Usuario registrado!"),                    HttpStatus.BAD_REQUEST);        }        // Creating user's account        Usuario user = new Usuario(signUpRequest.getLogin(), signUpRequest.getSenha(), signUpRequest.getNome());        user.setSenha(new BCryptPasswordEncoder().encode(user.getSenha()));        Role userRole = roleRepository.findByName(RoleEnum.ROLE_USER)                .orElseThrow(() -> new AppException("Usuario sem grupo."));        user.setRoles(Collections.singleton(userRole));        Usuario result = usuarioRepository.save(user);        URI location = ServletUriComponentsBuilder                .fromCurrentContextPath().path("/users/{username}")                .buildAndExpand(result.getLogin()).toUri();        return ResponseEntity.created(location).body(new ApiResponse(true, "Usuario registro com sucesso!"));    }}