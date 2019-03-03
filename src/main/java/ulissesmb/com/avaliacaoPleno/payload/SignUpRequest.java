package ulissesmb.com.avaliacaoPleno.payload;

import javax.validation.constraints.*;

public class SignUpRequest {
    @NotBlank
    @Size(min = 4, max = 40)
    private String nome;

    @NotBlank
    @Size(min = 3, max = 15)
    private String login;

    @NotBlank
    @Size(min = 6, max = 20)
    private String senha;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
