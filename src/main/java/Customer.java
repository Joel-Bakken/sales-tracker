import org.sql2o.*;
import java.util.ArrayList;
import java.util.List;
import java.util.*;

public class Customer {
  private String name;
  private int id;

  public Customer(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public int getId() {
    return id;
  }

  @Override
  public boolean equals(Object otherCustomer){
    if (!(otherCustomer instanceof Customer)) {
      return false;
    } else {
    Customer newCustomer = (Customer) otherCustomer;
    return this.getName().equals(newCustomer.getName());
   }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO customers (name) VALUES (:name)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", this.name)
        .executeUpdate()
        .getKey();
    }
  }

  public static Customer find(int id) {
      try(Connection con = DB.sql2o.open()) {
        String sql = "SELECT * FROM customers WHERE id=:id;";
        Customer customer = con.createQuery(sql)
          .addParameter("id", id)
          .executeAndFetchFirst(Customer.class);
        return customer;
     }
   }

  public static List<Customer> all() {
    String sql = "SELECT * FROM customers";
    try(Connection con = DB.sql2o.open()) {
    return con.createQuery(sql).executeAndFetch(Customer.class);
    }
  }

  public void delete() {
    try(Connection con = DB.sql2o.open()) {
    String sql = "DELETE FROM customers WHERE id = :id;";
    con.createQuery(sql)
      .addParameter("id", id)
      .executeUpdate();
    }
  }

  public List<Product> getProducts(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM products WHERE customerId = :id";
      return con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetch(Product.class);
    }
  }

}
