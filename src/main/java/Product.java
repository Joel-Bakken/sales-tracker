import java.util.ArrayList;
import java.util.List;
import org.sql2o.*;

public class Product {
  private String name;
  private int cost;
  private int customerId;
  private int id;

  public Product(String name, int cost) {
    this.name = name;
    this.cost = cost;
  }

  public String getName() {
    return name;
  }

  public int getCost() {
    return cost;
  }

  public int getId() {
    return id;
  }

  public int getCustomerId(){
    return customerId;
  }


  @Override
  public boolean equals(Object otherProduct){
    if (!(otherProduct instanceof Product)) {
      return false;
    } else {
      Product newProduct = (Product) otherProduct;
      return this.getName().equals(newProduct.getName()) &&
             this.getCost()==(newProduct.getCost());
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO products (name, cost) VALUES (:name, :cost)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", this.name)
        .addParameter("cost", this.cost)
        .executeUpdate()
        .getKey();
    }
  }

  public static List<Product> all() {
    String sql = "SELECT id, name, cost FROM products";
    try(Connection con = DB.sql2o.open()) {
     return con.createQuery(sql).executeAndFetch(Product.class);
    }
  }

  public List<Object> getProducts() {
    List<Object> allProducts = new ArrayList<Object>();

    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM products WHERE productId=:id;";
      List<Product> products = con.createQuery(sql)
        .addParameter("id", this.id)
        .throwOnMappingFailure(false)
        .executeAndFetch(Product.class);
      allProducts.addAll(products);
    }
    return allProducts;
  }

  public List<Customer> getCustomers() {
    try(Connection con = DB.sql2o.open()){
      String joinQuery = "SELECT customer_id FROM customers_products WHERE product_id = :product_id";
      List<Integer> customerIds = con.createQuery(joinQuery)
        .addParameter("product_id", this.getId())
        .executeAndFetch(Integer.class);

      List<Customer> customers = new ArrayList<Customer>();

      for (Integer customerId : customerIds) {
        String customerQuery = "SELECT * FROM customers WHERE id = :customerId";
        Customer customer = con.createQuery(customerQuery)
          .addParameter("customerId", customerId)
          .executeAndFetchFirst(Customer.class);
        customers.add(customer);
      }
      return customers;
    }
  }

  public void leaveCustomer(Customer customer){
    try(Connection con = DB.sql2o.open()){
      String joinRemovalQuery = "DELETE FROM customers_products WHERE customer_id = :customerId AND product_id = :productId;";
      con.createQuery(joinRemovalQuery)
        .addParameter("customerId", customer.getId())
        .addParameter("productId", this.getId())
        .executeUpdate();
    }
  }

  public static Product find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM products where id=:id";
      Product product = con.createQuery(sql)
        .addParameter("id", id)
        .throwOnMappingFailure(false)
        .executeAndFetchFirst(Product.class);
    return product;
    }
  }

}
