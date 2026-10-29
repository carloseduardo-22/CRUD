import com.j256.ormlite.table.DatabaseTable;
import com.j256.ormlite.field.DatabaseField;

@DatabaseTable(tableName = "paciente")
public class Paciente {

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(canBeNull = false)
    private String nome;

    @DatabaseField
    private String cpf;

    @DatabaseField
    private String telefone;

    @DatabaseField
    private String dataNascimento;

    public Paciente() {
    }

    public Paciente(String nome, String cpf,
                    String telefone, String dataNascimento) {
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.dataNascimento = dataNascimento;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String toString() {
        return "ID: " + id
            + " | Nome: " + nome
            + " | CPF: " + cpf
            + " | Telefone: " + telefone
            + " | Nascimento: " + dataNascimento;
    }
}