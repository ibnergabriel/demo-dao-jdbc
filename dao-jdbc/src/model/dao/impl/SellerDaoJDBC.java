package model.dao.impl;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SellerDaoJDBC implements SellerDao {

    private Connection connection;

    // Injeção de depedências
    public SellerDaoJDBC(Connection connection){
        this.connection = connection;
    }

    @Override
    public void insert(Seller obj) {

    }

    @Override
    public void update(Seller obj) {

    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public Seller findById(Integer id) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = connection.prepareStatement(
                    "SELECT seller.*,department.Name as DepName " +
                            "FROM seller INNER JOIN  department " +
                            "ON seller.DepartmentId = department.Id " +
                            "WHERE seller.Id = ?");

            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {

                Department department = instantieteDepartment(resultSet);

                Seller obj = instantieteSeller(resultSet, department);
                return obj;
            }
            return null;
        }
        catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(preparedStatement);
            DB.closeResultSet(resultSet);
        }
    }

    private Seller instantieteSeller(ResultSet resultSet, Department department) throws SQLException {
        Seller obj = new Seller();
        obj.setId(resultSet.getInt("Id"));
        obj.setName(resultSet.getString("Name"));
        obj.setEmail(resultSet.getString("Email"));
        obj.setBaseSalary(resultSet.getDouble("BaseSalary"));
        obj.setBirthDate(resultSet.getDate("BirthDate"));
        obj.setDepartment(department);
        return obj;
    }

    private Department instantieteDepartment(ResultSet resultSet) throws SQLException {
        Department department = new Department();
        department.setId(resultSet.getInt("DepartmentId"));
        department.setName(resultSet.getString("DepName"));
        return department;
    }

    @Override
    public List<Seller> findAll() {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = connection.prepareStatement(
                    "SELECT seller.*,department.Name as DepName "
                            + "FROM seller INNER JOIN  department "
                            + "ON seller.DepartmentId = department.Id "
                            + "ORDER BY Name");

            resultSet = preparedStatement.executeQuery();

            List<Seller> list = new ArrayList<>();
            Map<Integer,Department> map = new HashMap<>();

            while(resultSet.next()) {

                Department departmentMap = map.get(resultSet.getInt("DepartmentId"));

                if(departmentMap == null) {
                    departmentMap = instantieteDepartment(resultSet);
                    map.put(resultSet.getInt("DepartmentId"), departmentMap);
                }

                Seller obj = instantieteSeller(resultSet, departmentMap);
                list.add(obj);
            }
            return list;
        }
        catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(preparedStatement);
            DB.closeResultSet(resultSet);
        }
    }

    @Override
    public List<Seller> findByDepartment(Department department) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = connection.prepareStatement(
                    "SELECT seller.*,department.Name as DepName "
                            + "FROM seller INNER JOIN  department "
                            + "ON seller.DepartmentId = department.Id "
                            + "WHERE DepartmentId = ? "
                            + "ORDER BY Name");

            preparedStatement.setInt(1, department.getId());
            resultSet = preparedStatement.executeQuery();

            List<Seller> list = new ArrayList<>();
            Map<Integer,Department> map = new HashMap<>();

            while(resultSet.next()) {

                /*
                Iniciado o while, haverá um teste para verificar se o departamento já existe através do map, onde
                o map.get verificará se há igualdade do "DepartmentId"
                */
                Department departmentMap = map.get(resultSet.getInt("DepartmentId"));

                if(departmentMap == null) {
                    departmentMap = instantieteDepartment(resultSet);
                    map.put(resultSet.getInt("DepartmentId"), departmentMap);
                }

                Seller obj = instantieteSeller(resultSet, departmentMap);
                list.add(obj);
            }
            return list;
        }
        catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(preparedStatement);
            DB.closeResultSet(resultSet);
        }
    }
}
