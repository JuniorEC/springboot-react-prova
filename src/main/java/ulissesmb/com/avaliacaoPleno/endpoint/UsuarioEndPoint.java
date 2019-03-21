package ulissesmb.com.avaliacaoPleno.endpoint;import org.slf4j.Logger;import org.slf4j.LoggerFactory;import org.springframework.beans.factory.annotation.Autowired;import org.springframework.http.HttpStatus;import org.springframework.http.ResponseEntity;import org.springframework.security.access.prepost.PreAuthorize;import org.springframework.web.bind.annotation.*;import ulissesmb.com.avaliacaoPleno.domain.modelo.Usuario;import ulissesmb.com.avaliacaoPleno.error.CustomErrorResponse;import ulissesmb.com.avaliacaoPleno.payload.UsuarioValido;import ulissesmb.com.avaliacaoPleno.payload.UsuarioResponse;import ulissesmb.com.avaliacaoPleno.reposity.IUsuarioRepository;import ulissesmb.com.avaliacaoPleno.security.CurrentUser;import ulissesmb.com.avaliacaoPleno.security.UserPrincipal;@RestController@RequestMapping("/api")public class UsuarioEndPoint {    private static final Logger logger = LoggerFactory.getLogger(UsuarioEndPoint.class);    @Autowired    private IUsuarioRepository usuarioRepository;    @GetMapping("/user/me")    @PreAuthorize("hasRole('USER')")    public UsuarioResponse getCurrentUser(@CurrentUser UserPrincipal currentUser) {        UsuarioResponse userSummary = new UsuarioResponse(currentUser.getId(), currentUser.getLogin(), currentUser.getNome());        return userSummary;    }    @GetMapping("/user/checkLogin")    public UsuarioValido checkLogin(@RequestParam(value = "login") String login) {        Boolean isAvailable = usuarioRepository.existsByLogin(login);        return new UsuarioValido(isAvailable);    }    @GetMapping("/users/{username}")        public ResponseEntity<?> getUserProfile(@CurrentUser UserPrincipal currentUser, @PathVariable(value = "username") String login) {        Usuario byLogin = usuarioRepository.findByLogin(login);        if(byLogin != null){            return new ResponseEntity<>(new UsuarioResponse(byLogin.getId(), byLogin.getLogin(), byLogin.getNome()), HttpStatus.OK);        }        return new ResponseEntity<>(new CustomErrorResponse("Usuario não localizado") , HttpStatus.NOT_FOUND);    }}