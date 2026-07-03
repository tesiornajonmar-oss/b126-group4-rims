package com.group4.rims.repository;

import com.group4.rims.model.Supplier;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class SupplierRepoImpl implements SupplierRepo {
    private final Connection connection;

    public SupplierRepoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Supplier findById(int id) {
        String query = "SELECT * FROM suppliers WHERE supplier_id=?";
        try (PreparedStatement stmnt = connection.prepareStatement(query)) {
            stmnt.setInt(1, id);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) return mapRow(rs);
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    @Override
    public List<Supplier> findAll() {
        List<Supplier> suppliers = new ArrayList<>();
        String query = "SELECT * FROM suppliers";
        try (Statement stmnt = connection.createStatement();
             ResultSet rs = stmnt.executeQuery(query)) {
            while (rs.next()) suppliers.add(mapRow(rs));
        } catch (SQLException e) { e.printStackTrace(); }
        return suppliers;
    }

    @Override
    public void save(Supplier supplier) {
        String query = "INSERT INTO suppliers (supplier_name, contact_number, address) VALUES (?, ?, ?)";
        try (PreparedStatement stmnt = connection.prepareStatement(query)) {
            stmnt.setString(1, supplier.getSupplierName());
            stmnt.setString(2, supplier.getContactNumber());
            stmnt.setString(3, supplier.getAddress());
            stmnt.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    @Override
    public void update(Supplier supplier) {
        String query = "UPDATE suppliers SET supplier_name=?, contact_number=?, address=? WHERE supplier_id=?";
        try (PreparedStatement stmnt = connection.prepareStatement(query)) {
            stmnt.setString(1, supplier.getSupplierName());
            stmnt.setString(2, supplier.getContactNumber());
            stmnt.setString(3, supplier.getAddress());
            stmnt.setInt(4, supplier.getSupplierId());
            stmnt.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    @Override
    public void delete(int id) {
        String query = "DELETE FROM suppliers WHERE supplier_id=?";
        try (PreparedStatement stmnt = connection.prepareStatement(query)) {
            stmnt.setInt(1, id);
            stmnt.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    // JOIN: suppliers with their ingredients
    public List<Map<String, Object>> findSuppliersWithIngredients() {
        List<Map<String, Object>> results = new ArrayList<>();
        String query = "SELECT s.supplier_id, s.supplier_name, s.contact_number, s.address, " +
                       "i.ingredient_id, i.ingredient_name, i.stock_quantity " +
                       "FROM suppliers s " +
                       "JOIN ingredients i ON s.supplier_id = i.supplier_id";
        try (Statement stmnt = connection.createStatement();
             ResultSet rs = stmnt.executeQuery(query)) {
            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("supplierId", rs.getInt("supplier_id"));
                row.put("supplierName", rs.getString("supplier_name"));
                row.put("contactNumber", rs.getString("contact_number"));
                row.put("address", rs.getString("address"));
                row.put("ingredientId", rs.getInt("ingredient_id"));
                row.put("ingredientName", rs.getString("ingredient_name"));
                row.put("stockQuantity", rs.getInt("stock_quantity"));
                results.add(row);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return results;
    }

    private Supplier mapRow(ResultSet rs) throws SQLException {
        return new Supplier(
            rs.getInt("supplier_id"),
            rs.getString("supplier_name"),
            rs.getString("contact_number"),
            rs.getString("address")
        );
    }
}
