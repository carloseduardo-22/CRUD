import static org.junit.Assert.*;

import java.io.File;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Testes automatizados (JUnit 4, suportado pelo BlueJ) das operacoes CRUD
 * do PacienteRepository. Usa um banco separado (pacientes_teste.db),
 * recriado a cada teste, para nao misturar com os dados da demonstracao.
 *
 * @author Carlos
 */
public class PacienteRepositoryTest {

    private static final String BANCO_TESTE = "pacientes_teste.db";

    private Database database;
    private PacienteRepository repositorio;

    @Before
    public void setUp() {
        new File(BANCO_TESTE).delete();
        database = new Database(BANCO_TESTE);
        repositorio = new PacienteRepository(database);
    }

    @After
    public void tearDown() {
        database.close();
        new File(BANCO_TESTE).delete();
    }

    @Test
    public void testCreate() {
        Paciente p = repositorio.create(new Paciente("Ana Souza", "111.222.333-44", "(62) 99999-1111", "15/03/1990"));
        assertNotNull(p);
        assertTrue("o banco deve gerar um id", p.getId() > 0);
        assertEquals(1, repositorio.loadAll().size());
    }

    @Test
    public void testReadPorIdELista() {
        Paciente ana = repositorio.create(new Paciente("Ana Souza", "111.222.333-44", "(62) 99999-1111", "15/03/1990"));
        repositorio.create(new Paciente("Joao Lima", "555.666.777-88", "(62) 98888-2222", "02/11/1985"));

        Paciente lido = repositorio.loadFromId(ana.getId());
        assertNotNull(lido);
        assertEquals("Ana Souza", lido.getNome());
        assertEquals("111.222.333-44", lido.getCpf());

        List<Paciente> todos = repositorio.loadAll();
        assertEquals(2, todos.size());
    }

    @Test
    public void testUpdate() {
        Paciente ana = repositorio.create(new Paciente("Ana Souza", "111.222.333-44", "(62) 99999-1111", "15/03/1990"));
        ana.setTelefone("(62) 97777-0000");
        assertTrue(repositorio.update(ana));

        // fecha e reabre a conexao para garantir que o valor veio do banco
        database.close();
        database = new Database(BANCO_TESTE);
        repositorio = new PacienteRepository(database);

        assertEquals("(62) 97777-0000", repositorio.loadFromId(ana.getId()).getTelefone());
    }

    @Test
    public void testDelete() {
        Paciente joao = repositorio.create(new Paciente("Joao Lima", "555.666.777-88", "(62) 98888-2222", "02/11/1985"));
        assertTrue(repositorio.delete(joao));
        assertNull(repositorio.loadFromId(joao.getId()));
        assertEquals(0, repositorio.loadAll().size());
    }
}
