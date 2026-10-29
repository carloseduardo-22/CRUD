import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

/**
 * Demonstracao das quatro operacoes CRUD da entidade Paciente
 * usando ORMLite + SQLite e o padrao Repository.
 *
 * Pode ser usada de duas formas no BlueJ:
 *  - clicar com o botao direito na classe e executar "void main(String[] args)",
 *    que roda o roteiro completo (Create, Read, Update, Delete);
 *  - criar um objeto TesteCRUD e chamar cada metodo separadamente
 *    (cadastrar, consultarPorId, listarTodos, atualizarTelefone, excluir, mostrarTabela).
 *
 * O metodo mostrarTabela() consulta o arquivo .db com SQL puro (JDBC),
 * sem passar pelo ORM, para provar que os dados estao realmente gravados no banco.
 *
 * @author Carlos
 */
public class TesteCRUD {

    public static final String BANCO = "pacientes.db";

    private Database database;
    private PacienteRepository repositorio;

    public TesteCRUD() {
        database = new Database(BANCO);
        repositorio = new PacienteRepository(database);
    }

    // ---------- CREATE ----------
    public Paciente cadastrar(String nome, String cpf, String telefone, String dataNascimento) {
        Paciente p = new Paciente(nome, cpf, telefone, dataNascimento);
        repositorio.create(p);
        return p;
    }

    // ---------- READ ----------
    public Paciente consultarPorId(int id) {
        return repositorio.loadFromId(id);
    }

    public List<Paciente> listarTodos() {
        return repositorio.loadAll();
    }

    // ---------- UPDATE ----------
    public boolean atualizarTelefone(int id, String novoTelefone) {
        Paciente p = repositorio.loadFromId(id);
        if (p == null) {
            return false;
        }
        p.setTelefone(novoTelefone);
        return repositorio.update(p);
    }

    // ---------- DELETE ----------
    public boolean excluir(int id) {
        Paciente p = repositorio.loadFromId(id);
        if (p == null) {
            return false;
        }
        return repositorio.delete(p);
    }

    /** Mostra o conteudo da tabela "paciente" lendo o arquivo SQLite com SQL puro. */
    public void mostrarTabela() {
        String url = "jdbc:sqlite:" + BANCO;
        try (Connection con = DriverManager.getConnection(url);
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("SELECT id, nome, cpf, telefone, dataNascimento FROM paciente ORDER BY id")) {

            System.out.println("  [SQLite] SELECT * FROM paciente;");
            int linhas = 0;
            while (rs.next()) {
                System.out.println("  [SQLite] " + rs.getInt("id") + " | " + rs.getString("nome")
                    + " | " + rs.getString("cpf") + " | " + rs.getString("telefone")
                    + " | " + rs.getString("dataNascimento"));
                linhas++;
            }
            System.out.println("  [SQLite] " + linhas + " registro(s) na tabela");
        } catch (Exception e) {
            System.out.println("Erro ao ler a tabela: " + e.getMessage());
        }
    }

    public void fechar() {
        database.close();
    }

    /** Roteiro completo de demonstracao das operacoes CRUD. */
    public static void main(String[] args) {
        TesteCRUD teste = new TesteCRUD();

        System.out.println("\n===== CREATE =====");
        Paciente ana = teste.cadastrar("Ana Souza", "111.222.333-44", "(62) 99999-1111", "15/03/1990");
        Paciente joao = teste.cadastrar("Joao Lima", "555.666.777-88", "(62) 98888-2222", "02/11/1985");
        System.out.println("IDs gerados pelo banco: " + ana.getId() + " e " + joao.getId());
        teste.mostrarTabela();

        System.out.println("\n===== READ (por id) =====");
        teste.consultarPorId(ana.getId());

        System.out.println("\n===== READ (listar todos) =====");
        teste.listarTodos();

        System.out.println("\n===== UPDATE =====");
        System.out.println("Alterando o telefone do paciente " + ana.getId() + " para (62) 97777-0000");
        teste.atualizarTelefone(ana.getId(), "(62) 97777-0000");
        teste.mostrarTabela();

        System.out.println("\n===== DELETE =====");
        System.out.println("Excluindo o paciente " + joao.getId());
        teste.excluir(joao.getId());
        System.out.println("Consultando o id " + joao.getId() + " novamente:");
        teste.consultarPorId(joao.getId());
        teste.mostrarTabela();

        teste.fechar();

        System.out.println("\n===== PERSISTENCIA (nova conexao) =====");
        TesteCRUD outraSessao = new TesteCRUD();
        Paciente recarregada = outraSessao.consultarPorId(ana.getId());
        System.out.println("Telefone lido do banco apos reabrir: "
            + (recarregada != null ? recarregada.getTelefone() : "nao encontrado"));
        outraSessao.fechar();
    }
}
