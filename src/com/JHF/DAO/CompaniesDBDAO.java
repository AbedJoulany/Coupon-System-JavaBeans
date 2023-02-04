package com.JHF.DAO;

import com.JHF.ClientTypes.Company;
import com.JHF.Singletones.ConnectionPool;
import com.JHF.javaBeans.Coupon;

import java.util.ArrayList;
import java.sql.*;


public class CompaniesDBDAO implements CompaniesDAO {

    private ConnectionPool connectionPool = ConnectionPool.getInstance();

    public boolean isCompanyExists(String email, String password) {
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
            throw new RuntimeException(e);
        } finally {
            close(conn);
            close(stmt);
        }
    }

    public void addCompany(Company company) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = connectionPool.getConnection();

            stmt = conn.prepareStatement(INSERT,Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, company.getName());
            stmt.setString(2, company.getEmail());
            stmt.setString(3, company.getPassword());
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();

            if (rs.next()) {
                company.setId(rs.getInt(1));
            }

            CouponsDAO couponsDAO = new CouponsDBDAO();
            for (Coupon coupon: company.getCoupons())
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

    public void updateCompany(Company company) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = connectionPool.getConnection();

            stmt = conn.prepareStatement(UPDATE);
            stmt.setString(1, company.getName());
            stmt.setString(2, company.getEmail());
            stmt.setString(3, company.getPassword());
            stmt.setString(4, String.valueOf(company.getId()));
            stmt.executeUpdate();

            CouponsDAO couponsDAO = new CouponsDBDAO();
            if(company.getCoupons() != null)
                for (Coupon coupon: company.getCoupons())
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

    public void deleteCompany(int companyID) {

        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = connectionPool.getConnection();
            stmt = conn.prepareStatement(DELETE);
            stmt.setString(1, String.valueOf(companyID));

            stmt.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            close(conn);
            close(stmt);
        }
    }

    public ArrayList<Company> getAllCompanies() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ArrayList<Company> list = new ArrayList<Company>();
        CouponsDAO couponsDAO = new CouponsDBDAO();

        try {
            conn = connectionPool.getConnection();
            stmt = conn.prepareStatement(FIND_ALL);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Company company = new Company();
                        company.setId(rs.getInt("id"));
                        company.setName(rs.getString("name"));
                        company.setEmail(rs.getString("email"));
                        company.setPassword(rs.getString("password"));
                        company.setCoupons(couponsDAO.getAllCouponsByCompanyId(rs.getInt("id")));
                list.add(company);
            }
        } catch (SQLException e) {
             e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            close(conn);
            close(stmt);
        }
        return list;
    }

    public Company getOneCompany(int companyID) {
        Connection conn = null;
        PreparedStatement stmt = null;
        CouponsDAO couponsDAO = new CouponsDBDAO();
        try {
            conn = connectionPool.getConnection();
            stmt = conn.prepareStatement(FIND_BY_ID);
            stmt.setString(1, String.valueOf(companyID));
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Company company = new Company();
                company.setId(rs.getInt("id"));
                company.setName(rs.getString("name"));
                company.setEmail(rs.getString("email"));
                company.setPassword(rs.getString("password"));
                company.setCoupons(couponsDAO.getAllCouponsByCompanyId(rs.getInt("id")));
                return company;
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

    public Company getOneCompany(String email) {
        Connection conn = null;
        PreparedStatement stmt = null;
        CouponsDAO couponsDAO = new CouponsDBDAO();

        try {
            conn = connectionPool.getConnection();
            stmt = conn.prepareStatement(FIND_BY_EMAIL);
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Company company = new Company();
                company.setId(rs.getInt("id"));
                company.setName(rs.getString("name"));
                company.setEmail(rs.getString("email"));
                company.setPassword(rs.getString("password"));
                company.setCoupons(couponsDAO.getAllCouponsByCompanyId(rs.getInt("id")));
                return company;
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

    private void close(Connection con) {
        if (con != null) {
                //con.close();
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

    private static final String DELETE = "DELETE FROM systemdb.companies WHERE id=?";
    private static final String FIND_ALL = "SELECT * FROM systemdb.companies ORDER BY id";
    private static final String FIND_BY_EMAIL_PASSWORD = "SELECT * FROM systemdb.companies WHERE email=? and password=?";
    private static final String FIND_BY_ID = "SELECT * FROM systemdb.companies WHERE id=?";
    private static final String FIND_BY_EMAIL = "SELECT * FROM systemdb.companies WHERE email=?";
    private static final String INSERT = "INSERT INTO systemdb.companies( name, email,password) VALUES( ?, ?,?)";
    private static final String UPDATE = "UPDATE systemdb.companies SET name=?, email=?, password=? WHERE id=?";
}

