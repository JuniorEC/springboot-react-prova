package ulissesmb.com.avaliacaoPleno.reposity;import org.springframework.data.jpa.repository.JpaRepository;import org.springframework.stereotype.Repository;import ulissesmb.com.avaliacaoPleno.domain.enuns.RoleEnum;import ulissesmb.com.avaliacaoPleno.domain.modelo.Role;import java.util.Optional;@Repositorypublic interface IRoleRepository extends JpaRepository<Role, Long> {    Optional<Role> findByName(RoleEnum roleName);}