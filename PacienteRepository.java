import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

public class PacienteRepository {

    private static Database database;
    private static Dao<Paciente, Integer> dao;

    private List<Paciente> pacientesCarregados;
    private Paciente pacienteCarregado;

    public PacienteRepository(Database database) {
        setDatabase(database);
        pacientesCarregados = new ArrayList<Paciente>();
    }

    public static void setDatabase(Database database) {
        PacienteRepository.database = database;

        try {
            dao = DaoManager.createDao(
                database.getConnection(), Paciente.class
            );

            TableUtils.createTableIfNotExists(
                database.getConnection(), Paciente.class
            );

        } catch (SQLException e) {
            System.out.println("Erro ao configurar banco: "
                               + e.getMessage());
        }
    }

    public Paciente create(Paciente paciente) {
        try {
            int resultado = dao.create(paciente);

            if (resultado > 0) {
                pacienteCarregado = paciente;
                pacientesCarregados.add(paciente);

                System.out.println("Paciente cadastrado!");
                return paciente;
            }

        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar: "
                               + e.getMessage());
        }

        return null;
    }

    public Paciente loadFromId(int id) {
        try {
            pacienteCarregado = dao.queryForId(id);

            if (pacienteCarregado != null) {
                System.out.println("Paciente encontrado: "
                                   + pacienteCarregado);
            } else {
                System.out.println("Paciente nao encontrado.");
            }

            return pacienteCarregado;

        } catch (SQLException e) {
            System.out.println("Erro ao consultar: "
                               + e.getMessage());
            return null;
        }
    }

    public List<Paciente> loadAll() {
        try {
            pacientesCarregados = dao.queryForAll();

            for (Paciente p : pacientesCarregados) {
                System.out.println(p);
            }

            return pacientesCarregados;

        } catch (SQLException e) {
            System.out.println("Erro ao listar: "
                               + e.getMessage());
            return new ArrayList<Paciente>();
        }
    }

    public boolean update(Paciente paciente) {
        try {
            int resultado = dao.update(paciente);

            if (resultado > 0) {
                pacienteCarregado = paciente;
                System.out.println("Paciente atualizado!");
                return true;
            }

            System.out.println("Nenhum paciente foi atualizado.");
            return false;

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar: "
                               + e.getMessage());
            return false;
        }
    }

    public boolean delete(Paciente paciente) {
        try {
            int resultado = dao.delete(paciente);

            if (resultado > 0) {
                pacientesCarregados.remove(paciente);
                pacienteCarregado = null;

                System.out.println("Paciente excluido!");
                return true;
            }

            System.out.println("Nenhum paciente foi excluido.");
            return false;

        } catch (SQLException e) {
            System.out.println("Erro ao excluir: "
                               + e.getMessage());
            return false;
        }
    }

    public List<Paciente> getPacientesCarregados() {
        return pacientesCarregados;
    }

    public Paciente getPacienteCarregado() {
        return pacienteCarregado;
    }
}