package com.JHF.DAO;

import com.JHF.Singletones.ConnectionPool;
import com.JHF.javaBeans.Coupon;
import com.JHF.ClientTypes.Customer;

import java.sql.*;
import java.util.ArrayList;

public class CustomerDBAO implements CustomerDAO {

    private ConnectionPool connectionPool = ConnectionPool.getInstance();

    public boolean isCustomerExists(String email, String password) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = connectionPool.getConnection();
            stmt = conn.prepareStatement(FIND_BY_EMAIL_PASSWORD);
            stmt.setString(1, email);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            // e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            close(conn);
            close(stmt);
        }
    }

    public void addCustomer(Customer customer) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = connectionPool.getConnection();

            stmt = conn.prepareStatement(INSERT,Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, customer.getFirstName());
            stmt.setString(2, customer.getLastName());
            stmt.setString(3, customer.getEmail());
            stmt.setString(4, customer.getPassword());
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();

            if (rs.next()) {
                customer.setId(rs.getInt(1));
            }

            CouponsDAO couponsDAO = new CouponsDBDAO();
            for (Coupon coupon: customer.getCoupons())
            {
                couponsDAO.addCoupon(coupon);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            close(conn);
            close(stmt);
        }
    }

    public void updateCustomer(Customer customer) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = connectionPool.getConnection();

            stmt = conn.prepareStatement(UPDATE);
            stmt.setString(1, customer.getFirstName());
            stmt.setString(2, customer.getLastName());
            stmt.setString(3, customer.getEmail());
            stmt.setString(4, customer.getPassword());
            stmt.setString(5, String.valueOf(customer.getId()));
            stmt.executeUpdate();

            CouponsDAO couponsDAO = new CouponsDBDAO();
            if(customer.getCoupons() != null)
            for (Coupon coupon: customer.getCoupons())
            {
                couponsDAO.addCoupon(coupon);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            close(conn);
            close(stmt);
        }
    }

    public void deleteCustomer(int customerID) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = connectionPool.getConnection();

            stmt = conn.prepareStatement(DELETE);
            stmt.setString(1, String.valueOf(customerID));

            stmt.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            close(conn);
            close(stmt);
        }
    }

    public ArrayList<Customer> getAllCustomers() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ArrayList<Customer> list = new ArrayList<Customer>();
        CouponsDAO couponsDAO = new CouponsDBDAO();
        try {
            conn = connectionPool.getConnection();
            stmt = conn.prepareStatement(FIND_ALL);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Customer customer = new Customer();
                customer.setId(rs.getInt("id"));
                customer.setFirstName(rs.getString("first_name"));
                customer.setLastName(rs.getString("last_name"));
                customer.setEmail(rs.getString("email"));
                customer.setPassword(rs.getString("password"));
                customer.setCoupons(couponsDAO.getAllCouponsByCustomerId(rs.getInt("id")));
                list.add(customer);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(conn);
            close(stmt);
        }
        return list;
    }

    public Customer getOneCustomer(int customerID) {
        Connection conn = null;
        PreparedStatement stmt = null;
        CouponsDAO couponsDAO = new CouponsDBDAO();
        try {
            conn = connectionPool.getConnection();
            stmt = conn.prepareStatement(FIND_BY_ID);
            stmt.setString(1, String.valueOf(customerID));
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Customer customer = new Customer();
                customer.setId(rs.getInt("id"));
                customer.setFirstName(rs.getString("first_name"));
                customer.setLastName(rs.getString("last_name"));
                customer.setEmail(rs.getString("email"));
                customer.setPassword(rs.getString("password"));
                customer.setCoupons(couponsDAO.getAllCouponsByCustomerId(rs.getInt("id")));
                return customer;
            } else {
                return null;
            }
        } catch (SQLException e) {
            // e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            close(conn);
            close(stmt);
        }
    }

    @Override
    public Customer getOneCustomer(String email) {
        Connection conn = null;
        PreparedStatement stmt = null;
        CouponsDAO couponsDAO = new CouponsDBDAO();
        try {
            conn = connectionPool.getConnection();
            stmt = conn.prepareStatement(FIND_BY_EMAIL);
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Customer customer = new Customer();
                customer.setId(rs.getInt("id"));
                customer.setFirstName(rs.getString("first_name"));
                customer.setLastName(rs.getString("last_name"));
                customer.setEmail(rs.getString("email"));
                customer.setPassword(rs.getString("password"));
                customer.setCoupons(couponsDAO.getAllCouponsByCustomerId(rs.getInt("id")));
                return customer;
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(conn);
            close(stmt);
        }
    }

    private void close(Connection con) {
        if (con != null) {
            connectionPool.restoreConnection(con);
        }
    }
    private static void close(Statement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static final String DELETE = "DELETE FROM systemdb.customers WHERE id=?";
    private static final String FIND_ALL = "SELECT * FROM systemdb.customers ORDER BY id";
    private static final String FIND_BY_EMAIL_PASSWORD = "SELECT * FROM systemdb.customers WHERE email=? and password=?";
    private static final String FIND_BY_ID = "SELECT * FROM systemdb.customers WHERE id=?";
    private static final String FIND_BY_EMAIL = "SELECT * FROM systemdb.customers WHERE email=?";
    private static final String INSERT = "INSERT INTO systemdb.customers(first_name, last_name, email,password) VALUES( ?, ?,?,?)";
    private static final String UPDATE = "UPDATE systemdb.customers SET first_name=?, last_name=?, email=?, password=? WHERE id=?";
}
