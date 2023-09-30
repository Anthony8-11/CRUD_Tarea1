package edu.umg.conexion;

import java.sql.*;
import java.util.Scanner;

public class clsConexion {
    private Connection conn = null;

    public void conexion() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/dbprogra2";
        String usuario = "root";
        String password = "11111";

        try{
            conn = DriverManager.getConnection(url,usuario,password);
            System.out.println("Conexion realizada exitosamente");

            Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.println("Selecciona una opción:");
                System.out.println("1. Consultar");
                System.out.println("2. Insertar");
                System.out.println("3. Actualizar");
                System.out.println("4. Eliminar");
                System.out.println("5. Salir");
                int opcion = scanner.nextInt();

                switch (opcion) {
                    case 1:
                        consultarDatos(conn);
                        break;
                    case 2:
                        insertarDatos(conn, scanner);
                        break;
                    case 3:
                        actualizarDatos(conn, scanner);
                        break;
                    case 4:
                        eliminarDatos(conn, scanner);
                        break;
                    case 5:
                        conn.close();
                        System.out.println("Programa terminado.");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Opción no válida.");
                        break;
                }
            }
        }catch (SQLException ex){
            System.out.println("Hubo error"+ex.getMessage());
        }
    }

    private static void consultarDatos(Connection conn) throws SQLException {
        System.out.println("Consulta de datos:");
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese el código de la persona a consultar: ");
        int codigo = scanner.nextInt();

        String consultaSQL = "SELECT * FROM tb_datos WHERE codigo_persona = ?";
        try (PreparedStatement statement = conn.prepareStatement(consultaSQL)) {
            statement.setInt(1, codigo);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int codigoPersona = resultSet.getInt("codigo_persona");
                String nombre = resultSet.getString("nombre");
                String apellido = resultSet.getString("apellido");
                // Continúa extrayendo y mostrando otros campos según sea necesario
                System.out.println("Código: " + codigoPersona + ", Nombre: " + nombre + ", Apellido: " + apellido);
            }
        }
    }

    private static void insertarDatos(Connection conn, Scanner scanner) throws SQLException {
        System.out.println("Inserción de datos:");
        System.out.print("Ingrese el nombre: ");
        String nombre = scanner.next();
        System.out.print("Ingrese el apellido: ");
        String apellido = scanner.next();
        System.out.print("Ingrese la fecha de registro (YYYY-MM-DD): ");
        String fechaRegistro = scanner.next();
        System.out.print("Ingrese el sueldo base: ");
        double sueldoBase = scanner.nextDouble();
        System.out.print("Ingrese el sexo (M/F): ");
        String sexo = scanner.next();
        System.out.print("Ingrese la edad: ");
        int edad = scanner.nextInt();
        System.out.print("Ingrese la longitud de la casa: ");
        double longitudCasa = scanner.nextDouble();
        System.out.print("Ingrese la altitud de la casa: ");
        double altitudCasa = scanner.nextDouble();
        scanner.nextLine(); // Consumir la nueva línea
        System.out.print("Ingrese comentarios: ");
        String comentarios = scanner.nextLine();

        String insercionSQL = "INSERT INTO tb_datos (nombre, apellido, fecha_registro, sueldo_base, sexo, edad, longitud_casa, altitud_casa, comentarios) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = conn.prepareStatement(insercionSQL)) {
            statement.setString(1, nombre);
            statement.setString(2, apellido);
            statement.setString(3, fechaRegistro);
            statement.setDouble(4, sueldoBase);
            statement.setString(5, sexo);
            statement.setInt(6, edad);
            statement.setDouble(7, longitudCasa);
            statement.setDouble(8, altitudCasa);
            statement.setString(9, comentarios);

            statement.executeUpdate();
            System.out.println("Registro insertado correctamente.");
        }
    }


    private static void actualizarDatos(Connection conn, Scanner scanner) throws SQLException {
        System.out.println("Actualización de datos:");
        System.out.print("Ingrese el código de la persona a actualizar: ");
        int codigo = scanner.nextInt();
        System.out.print("Ingrese el nuevo sueldo base: ");
        double nuevoSueldo = scanner.nextDouble();

        String actualizacionSQL = "UPDATE tb_datos SET sueldo_base = ? WHERE codigo_persona = ?";
        try (PreparedStatement statement = conn.prepareStatement(actualizacionSQL)) {
            statement.setDouble(1, nuevoSueldo);
            statement.setInt(2, codigo);
            int filasActualizadas = statement.executeUpdate();
            if (filasActualizadas > 0) {
                System.out.println("Registro actualizado correctamente.");
            } else {
                System.out.println("No se encontró ningún registro con ese código.");
            }
        }
    }

    private static void eliminarDatos(Connection conn, Scanner scanner) throws SQLException {
        System.out.println("Eliminación de datos:");
        System.out.print("Ingrese el código de la persona a eliminar: ");
        int codigo = scanner.nextInt();

        String eliminacionSQL = "DELETE FROM tb_datos WHERE codigo_persona = ?";
        try (PreparedStatement statement = conn.prepareStatement(eliminacionSQL)) {
            statement.setInt(1, codigo);
            int filasEliminadas = statement.executeUpdate();
            if (filasEliminadas > 0) {
                System.out.println("Registro eliminado correctamente.");
            } else {
                System.out.println("No se encontró ningún registro con ese código.");
            }
        }
    }





//    public void leerDatos() throws SQLException {
//        conexion();
//        // Leer un registro
//        String sql = "SELECT * FROM bd_nombre WHERE id = ?";
//        PreparedStatement ps = null;
//
//        ps = conn.prepareStatement(sql);
//        ps.setInt(1, 1);
//        ResultSet rs = ps.executeQuery();
//        while (rs.next()) {
//            System.out.println(rs.getString("nombre") + " " + rs.getString("apellido"));
//        }
//
//    }
//
//    public void insertdatos() throws SQLException {
//        conexion();
//        Scanner sc = new Scanner(System.in);
//        System.out.println("Ingrese nombre");
//        String TMPnombre = sc.nextLine();
//        System.out.println("Ingrese apellido");
//        String TMPapellido = sc.nextLine();
//
//        String sql = "insert into tb_nombre (nombre,apellido) values (?.?)";
//        PreparedStatement ps = null;
//
//        ps = conn.prepareStatement(sql);
//        ps.setString(1,TMPnombre);
//        ps.setString(2,TMPapellido);
//        ps.executeUpdate();
//
//
//
//    }

}
