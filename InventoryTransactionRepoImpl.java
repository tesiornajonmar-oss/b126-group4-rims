package com.group4.rims.repository;

import com.group4.rims.model.InventoryTransaction;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class InventoryTransactionRepoImpl implements InventoryTransactionRepo {
    private final Connection connection;

    public InventoryTransactionRepoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public InventoryTransaction findById(int id) {
        String query = "SELECT * FROM inventory_transactions WHERE transaction_id=?";
        try (PreparedStatement stmnt = connection.prepareStatement(query)) {
            stmnt.setInt(1, id);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) return mapRow(rs);
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    @Override
    public List<InventoryTransaction> findAll() {
        List<InventoryTransaction> transactions = new ArrayList<>();
        String query = "SELECT * FROM inventory_transactions";
        try (Statement stmnt = connection.createStatement();
             ResultSet rs = stmnt.executeQuery(query)) {
            while (rs.next()) transactions.add(mapRow(rs));
        } catch (SQLException e) { e.printStackTrace(); }
        return transactions;
    }

    @Override
    public void save(InventoryTransaction transaction) {
        String query = "INSERT INTO inventory_transactions (ingredient_id, staff_id, transaction_type, quantity, transaction_date, remarks) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmnt = connection.prepareStatement(query)) {
        	stmnt.setInt(1, transaction.getIngredientId());
        	stmnt.setInt(2, transaction.getStaffId());
        	stmnt.setString(3, transaction.getTransactionType());
        	stmnt.setInt(4, transaction.getQuantity());
        	stmnt.setInt(5, transaction.getTransactionDate());
            stmnt.setString(6, transaction.getRemarks());
            stmnt.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    @Override
    public void update(InventoryTransaction transaction) {
        String query = "UPDATE inventory_transactions SET ingredient_id=?, staff_id=?, transaction_type=?, quantity=?, transaction_date=?, remarks=? WHERE transaction_id=?";
        try (PreparedStatement stmnt = connection.prepareStatement(query)) {
        	stmnt.setInt(1, transaction.getIngredientId());
        	stmnt.setInt(2, transaction.getStaffId());
        	stmnt.setString(3, transaction.getTransactionType());
        	stmnt.setInt(4, transaction.getQuantity());
        	stmnt.setInt(5, transaction.getTransactionDate());
        	stmnt.setString(6, transaction.getRemarks());
        	stmnt.setInt(7, transaction.getTransactionId());
        	stmnt.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    @Override
    public void delete(int id) {
        String query = "DELETE FROM inventory_transactions WHERE transaction_id=?";
        try (PreparedStatement stmnt = connection.prepareStatement(query)) {
        	stmnt.setInt(1, id);
        	stmnt.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    @Override
    public List<InventoryTransaction> findByIngredientId(int ingredientId) {
        List<InventoryTransaction> transactions = new ArrayList<>();
        String query = "SELECT * FROM inventory_transactions WHERE ingredient_id=?";
        try (PreparedStatement stmnt = connection.prepareStatement(query)) {
        	stmnt.setInt(1, ingredientId);
            ResultSet rs = stmnt.executeQuery();
            while (rs.next()) transactions.add(mapRow(rs));
        } catch (SQLException e) { e.printStackTrace(); }
        return transactions;
    }

    @Override
    public List<InventoryTransaction> findByStaffId(int staffId) {
        List<InventoryTransaction> transactions = new ArrayList<>();
        String query = "SELECT * FROM inventory_transactions WHERE staff_id=?";
        try (PreparedStatement stmnt = connection.prepareStatement(query)) {
        	stmnt.setInt(1, staffId);
            ResultSet rs = stmnt.executeQuery();
            while (rs.next()) transactions.add(mapRow(rs));
        } catch (SQLException e) { e.printStackTrace(); }
        return transactions;
    }

    private InventoryTransaction mapRow(ResultSet rs) throws SQLException {
        return new InventoryTransaction(
            rs.getInt("transaction_id"),
            rs.getInt("ingredient_id"),
            rs.getInt("staff_id"),
            rs.getString("transaction_type"),
            rs.getInt("quantity"),
            rs.getInt("transaction_date"),
            rs.getString("remarks")
        );
    }
}
