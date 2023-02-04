package com.JHF.DAO;

import com.JHF.Enums.Category;
import com.JHF.Singletones.ConnectionPool;
import com.JHF.javaBeans.Coupon;

import java.sql.*;
import java.util.ArrayList;

public class CouponsDBDAO implements CouponsDAO{
    private ConnectionPool connectionPool = ConnectionPool.getInstance();

    public void addCoupon(Coupon coupon) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = connectionPool.getConnection();

            stmt = conn.prepareStatement(INSERT,Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, String.valueOf(coupon.getCompanyID()));
            stmt.setString(2, String.valueOf(coupon.getCategory().ordinal()));
            stmt.setString(3, coupon.getTitle());
            stmt.setString(4, coupon.getDescription());
            stmt.setString(5, coupon.getStartDate().getYear()+"-"+coupon.getStartDate().getMonth()+"-"+coupon.getStartDate().getDay());
            stmt.setString(6, coupon.getStartDate().getYear()+"-"+coupon.getStartDate().getMonth()+"-"+coupon.getStartDate().getDay());
            stmt.setString(7, String.valueOf(coupon.getAmount()));
            stmt.setString(8, String.valueOf(coupon.getPrice()));
            stmt.setString(9, coupon.getImage());

            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();

            if (rs.next()) {
                coupon.setId(rs.getInt(1));
            }


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            close(conn);
            close(stmt);
        }
    }

    public void updateCoupon(Coupon coupon) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = connectionPool.getConnection();

            stmt = conn.prepareStatement(UPDATE);
            stmt.setString(1, String.valueOf(coupon.getCompanyID()));
            stmt.setString(2, String.valueOf(coupon.getCategory().ordinal()));
            stmt.setString(3, coupon.getTitle());
            stmt.setString(4, coupon.getDescription());
            stmt.setString(5, coupon.getStartDate().getYear()+"-"+coupon.getStartDate().getMonth()+"-"+coupon.getStartDate().getDay());
            stmt.setString(6, coupon.getStartDate().getYear()+"-"+coupon.getStartDate().getMonth()+"-"+coupon.getStartDate().getDay());
            stmt.setString(7, String.valueOf(coupon.getAmount()));
            stmt.setString(8, String.valueOf(coupon.getPrice()));
            stmt.setString(9, coupon.getImage());
            stmt.setString(10, String.valueOf(coupon.getId()));

            stmt.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            close(conn);
            close(stmt);
        }
    }

    public void deleteCoupon(int couponID) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = connectionPool.getConnection();

            stmt = conn.prepareStatement(DELETE);
            stmt.setString(1, String.valueOf(couponID));

            stmt.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            close(conn);
            close(stmt);
        }
    }

    public ArrayList<Coupon> getAllCoupons() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ArrayList<Coupon> list = new ArrayList<Coupon>();
        try {
            conn = connectionPool.getConnection();
            stmt = conn.prepareStatement(FIND_ALL);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Coupon coupon = new Coupon(
                        rs.getInt("company_id"),
                        Category.values()[rs.getInt("category_id")],
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getDate("start_date"),
                        rs.getDate("end_date"),
                        rs.getInt("amount"),
                        rs.getDouble("price"),
                        rs.getString("image"));
                coupon.setId(rs.getInt("id"));
                list.add(coupon);
            }
        } catch (SQLException e) {
            // e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            close(conn);
            close(stmt);
        }
        return list;
    }

    @Override
    public ArrayList<Coupon> getAllCouponsByCompanyId(int companyId) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ArrayList<Coupon> list = new ArrayList<Coupon>();
        try {
            conn = connectionPool.getConnection();
            stmt = conn.prepareStatement(FIND_ALL_BY_COMPANY_ID);
            stmt.setString(1,String.valueOf(companyId));
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Coupon coupon = new Coupon(
                        rs.getInt("company_id"),
                        Category.values()[rs.getInt("category_id")],
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getDate("start_date"),
                        rs.getDate("end_date"),
                        rs.getInt("amount"),
                        rs.getDouble("price"),
                        rs.getString("image"));
                coupon.setId(rs.getInt("id"));
                list.add(coupon);
            }
        } catch (SQLException e) {
            // e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            close(conn);
            close(stmt);
        }
        return list;
    }

    @Override
    public ArrayList<Coupon> getAllCouponsByCustomerId(int customerId) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ArrayList<Coupon> list = new ArrayList<Coupon>();
        try {
            conn = connectionPool.getConnection();
            stmt = conn.prepareStatement(FIND_ALL_BY_CUSTOMER_ID);
            stmt.setString(1,String.valueOf(customerId));
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Coupon coupon = new Coupon(
                        rs.getInt("company_id"),
                        Category.values()[rs.getInt("category_id")],
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getDate("start_date"),
                        rs.getDate("end_date"),
                        rs.getInt("amount"),
                        rs.getDouble("price"),
                        rs.getString("image"));
                coupon.setId(rs.getInt("id"));
                list.add(coupon);
            }
        } catch (SQLException e) {
            // e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            close(conn);
            close(stmt);
        }
        return list;
    }

    @Override
    public ArrayList<Coupon> getAllCouponsByCompanyIdAndCategory(int companyId, Category category) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ArrayList<Coupon> list = new ArrayList<Coupon>();
        try {
            conn = connectionPool.getConnection();
            stmt = conn.prepareStatement(FIND_ALL_BY_CATEGORY_COMPANY_ID);
            stmt.setString(1,String.valueOf(companyId));
            stmt.setString(2,String.valueOf(category.ordinal()));
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Coupon coupon = new Coupon(
                        rs.getInt("company_id"),
                        Category.values()[rs.getInt("category_id")],
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getDate("start_date"),
                        rs.getDate("end_date"),
                        rs.getInt("amount"),
                        rs.getDouble("price"),
                        rs.getString("image"));
                coupon.setId(rs.getInt("id"));
                list.add(coupon);
            }
        } catch (SQLException e) {
            // e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            close(conn);
            close(stmt);
        }
        return list;
    }

    @Override
    public ArrayList<Coupon> getAllCouponsByCustomerIdAndCategory(int customerId, Category category) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ArrayList<Coupon> list = new ArrayList<Coupon>();
        try {
            conn = connectionPool.getConnection();
            stmt = conn.prepareStatement(FIND_ALL_BY_CATEGORY_CUSTOMER_ID);
            stmt.setString(1,String.valueOf(category.ordinal()));
            stmt.setString(2,String.valueOf(customerId));
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Coupon coupon = new Coupon(
                        rs.getInt("company_id"),
                        Category.values()[rs.getInt("category_id")],
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getDate("start_date"),
                        rs.getDate("end_date"),
                        rs.getInt("amount"),
                        rs.getDouble("price"),
                        rs.getString("image"));
                coupon.setId(rs.getInt("id"));
                list.add(coupon);
            }
        } catch (SQLException e) {
            // e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            close(conn);
            close(stmt);
        }
        return list;
    }

    @Override
    public ArrayList<Coupon> getAllCouponsByCompanyIdAndMaxPrice(int companyId, double maxPrice) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ArrayList<Coupon> list = new ArrayList<Coupon>();
        try {
            conn = connectionPool.getConnection();
            stmt = conn.prepareStatement(FIND_ALL_BY_MAX_PRICE_COMPANY_ID);
            stmt.setString(1,String.valueOf(companyId));
            stmt.setString(2,String.valueOf(maxPrice));
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Coupon coupon = new Coupon(
                        rs.getInt("company_id"),
                        Category.values()[rs.getInt("category_id")],
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getDate("start_date"),
                        rs.getDate("end_date"),
                        rs.getInt("amount"),
                        rs.getDouble("price"),
                        rs.getString("image"));
                coupon.setId(rs.getInt("id"));
                list.add(coupon);
            }
        } catch (SQLException e) {
            // e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            close(conn);
            close(stmt);
        }
        return list;
    }

    @Override
    public ArrayList<Coupon> getAllCouponsByCustomerIdAndMaxPrice(int customerId, double maxPrice) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ArrayList<Coupon> list = new ArrayList<Coupon>();
        try {
            conn = connectionPool.getConnection();
            stmt = conn.prepareStatement(FIND_ALL_BY_MAX_PRICE_CUSTOMER_ID);
            stmt.setString(1,String.valueOf(maxPrice));
            stmt.setString(2,String.valueOf(customerId));
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Coupon coupon = new Coupon(
                        rs.getInt("company_id"),
                        Category.values()[rs.getInt("category_id")],
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getDate("start_date"),
                        rs.getDate("end_date"),
                        rs.getInt("amount"),
                        rs.getDouble("price"),
                        rs.getString("image"));
                coupon.setId(rs.getInt("id"));
                list.add(coupon);
            }
        } catch (SQLException e) {
            // e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            close(conn);
            close(stmt);
        }
        return list;
    }

    public Coupon getOneCoupon(int couponID) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = connectionPool.getConnection();
            stmt = conn.prepareStatement(FIND_BY_ID);
            stmt.setString(1, String.valueOf(couponID));
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Coupon coupon = new Coupon();
                coupon.setId(rs.getInt("id"));
                coupon.setCompanyID(rs.getInt("company_id"));
                coupon.setCategory( Category.values()[rs.getInt("category_id")]);
                coupon.setTitle(rs.getString("title"));
                coupon.setDescription(rs.getString("description"));
                coupon.setStartDate(rs.getDate("start_date"));
                coupon.setEndDate(rs.getDate("end_date"));
                coupon.setAmount(rs.getInt("amount"));
                coupon.setPrice(rs.getDouble("price"));
                coupon.setImage(rs.getString("image"));
                return coupon;
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

    public void addCouponPurchase(int customerID, int couponID) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = connectionPool.getConnection();

            stmt = conn.prepareStatement(SELECT_AMOUNT);
            stmt.setString(1, String.valueOf(couponID));
            ResultSet rs = stmt.executeQuery();
            if(rs.next() && rs.getInt("amount") == 0){
                System.out.println("no coupons left");
                return;
            }

            stmt = conn.prepareStatement(INSERT_PURCHASE);
            stmt.setString(1, String.valueOf(customerID));
            stmt.setString(2, String.valueOf(couponID));
            stmt.executeUpdate();

            stmt = conn.prepareStatement(UPDATE_AMOUNT);
            stmt.setString(1, String.valueOf(rs.getInt("amount")-1));
            stmt.setString(2, String.valueOf(couponID));
            stmt.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            close(conn);
            close(stmt);
        }
    }

    @Override
    public void deleteCouponPurchase(int couponID) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = connectionPool.getConnection();

            stmt = conn.prepareStatement(DELETE_PURCHASE);
            stmt.setString(1, String.valueOf(couponID));

            stmt.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
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
    }    private static void close(Statement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static final String DELETE = "DELETE FROM systemdb.coupons WHERE id=?";
    private static final String FIND_ALL = "SELECT * FROM systemdb.coupons ORDER BY id";
    private static final String FIND_ALL_BY_COMPANY_ID = "SELECT * FROM systemdb.coupons WHERE COMPANY_ID=?";
    private static final String FIND_ALL_BY_CUSTOMER_ID = "SELECT * FROM systemdb.coupons where id =" +
                        "(select COUPON_ID from systemdb.customers_vs_coupons where CUSTOMER_ID = ?)";
    private static final String FIND_ALL_BY_CATEGORY_COMPANY_ID = "SELECT * FROM systemdb.coupons WHERE COMPANY_ID=? and CATEGORY_ID=?";
    private static final String FIND_ALL_BY_CATEGORY_CUSTOMER_ID = "SELECT * FROM systemdb.coupons WHERE CATEGORY_ID=? and id="+
            "(select COUPON_ID from systemdb.customers_vs_coupons where CUSTOMER_ID = ?)";
    private static final String FIND_ALL_BY_MAX_PRICE_COMPANY_ID = "SELECT * FROM systemdb.coupons WHERE COMPANY_ID=? and PRICE <=?";
    private static final String FIND_ALL_BY_MAX_PRICE_CUSTOMER_ID = "SELECT * FROM systemdb.coupons WHERE PRICE <=? and id ="+
            "(select COUPON_ID from systemdb.customers_vs_coupons where CUSTOMER_ID = ?)";
    private static final String FIND_BY_ID = "SELECT * FROM systemdb.coupons WHERE id=?";
    private static final String INSERT = "INSERT INTO systemdb.coupons(company_id, category_id,title,description," +
                        "start_date, end_date,amount,price,image) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE systemdb.coupons SET company_id=?, category_id=?, title=?, description=?," +
                        "start_date=?, end_date=?,amount=?,price=?,image=? WHERE id=?";
    private static final String DELETE_PURCHASE = "DELETE FROM systemdb.customers_vs_coupons WHERE coupon_id=?";
    private static final String INSERT_PURCHASE = "INSERT INTO systemdb.customers_vs_coupons(customer_id, coupon_id) VALUES(?, ?)";
    private static final String SELECT_AMOUNT = "SELECT amount FROM systemdb.coupons WHERE id=?";
    private static final String UPDATE_AMOUNT = "UPDATE systemdb.coupons SET amount=? WHERE id=?";

}
