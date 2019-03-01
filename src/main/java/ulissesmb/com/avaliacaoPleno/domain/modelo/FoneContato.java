package ulissesmb.com.avaliacaoPleno.domain.modelo;import com.fasterxml.jackson.annotation.JsonIgnore;import javax.persistence.*;import java.io.Serializable;import java.util.Objects;@Entity@Table(name = "TP_FONE_CONTATO")@SequenceGenerator(name = "SEQ_FONE_CONTATO", sequenceName = "SEQ_FONE_CONTATO", initialValue = 1, allocationSize = 1)public class FoneContato implements Serializable {    private static final long serialVersionUID = -350070059332882670L;    @Id    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_FONE_CONTATO")    private Long id;    @Column(name = "tipo_contato")    private String tipoContato;    private String numero;    @ManyToOne(fetch =  FetchType.LAZY, optional = false)    @JoinColumn(name = "ID_CLIENTE", nullable = false, foreignKey=@ForeignKey(name="FK_FONE_CONT_TO_CLIENTE"))    @JsonIgnore    private Cliente cliente;    public FoneContato(){    }    public FoneContato(String tipoContato, String numero) {        this.tipoContato = tipoContato;        this.numero = numero;    }    public Long getId() {        return id;    }    public void setId(Long id) {        this.id = id;    }    public String getTipoContato() {        return tipoContato;    }    public void setTipoContato(String tipoContato) {        this.tipoContato = tipoContato;    }    public String getNumero() {        return numero;    }    public void setNumero(String numero) {        this.numero = numero;    }    public Cliente getCliente() {        return cliente;    }    public void setCliente(Cliente cliente) {        this.cliente = cliente;    }    @Override    public boolean equals(Object o) {        if (this == o) return true;        if (o == null || getClass() != o.getClass()) return false;        FoneContato that = (FoneContato) o;        return id.equals(that.id) &&                tipoContato.equals(that.tipoContato) &&                numero.equals(that.numero);    }    @Override    public int hashCode() {        return Objects.hash(id, tipoContato, numero);    }    @Override    public String toString() {        final StringBuilder sb = new StringBuilder("FoneContato{");        sb.append("id=").append(id);        sb.append(", tipoContato='").append(tipoContato).append('\'');        sb.append(", numero='").append(numero).append('\'');        sb.append('}');        return sb.toString();    }}