import java.util.HashMap;
import java.util.Map;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;

public class App {
  public static void main(String[] args) {
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";

    get("/", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("customers", Customer.all());
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("products/new", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/product-form.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/products", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("products", Product.all());
      model.put("template", "templates/products.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/products", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Customer customer = Customer.find(Integer.parseInt(request.queryParams("customerId")));
      String name = request.queryParams("name");
      int cost = Integer.parseInt(request.queryParams("cost"));
      Product newProduct = new Product(name, cost, customer.getId());
      newProduct.save();
      model.put("customer", customer);
      model.put("template", "templates/customer-product-success.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/products/:id", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Product product = Product.find(Integer.parseInt(request.params(":id")));
      Customer customer = Customer.find(product.getCustomerId());
      model.put("customer", customer);
      model.put("product", product);
      model.put("template", "templates/product.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/customers/new", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/customer-form.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/customers", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      String name = request.queryParams("name");
      Customer newCustomer = new Customer(name);
      newCustomer.save(); // Saves to the customer database table
      model.put("template", "templates/customer-success.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/customers", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("customers", Customer.all());
      model.put("template", "templates/customers.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("customers/:id/products/new", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Customer customer = Customer.find(Integer.parseInt(request.params(":id")));
      model.put("customer", customer);
      model.put("template", "templates/customer-product-form.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/customers/:id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Customer customer = Customer.find(Integer.parseInt(request.params(":id")));
      model.put("customer", customer);
      model.put("template", "templates/customer.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/customers/:customer_id/products/:id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Customer customer = Customer.find(Integer.parseInt(request.params(":customer_id")));
      Product product = Product.find(Integer.parseInt(request.params(":id")));
      model.put("customer", customer);
      model.put("product", product);
      model.put("template", "templates/product.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/customers/:customer_id/products/:id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Product product = Product.find(Integer.parseInt(request.params("id")));
      int cost = Integer.parseInt(request.queryParams("cost"));
      Customer customer = Customer.find(product.getCustomerId());
      product.update(cost);
      String url = String.format("/customers/%d/products/%d", customer.getId(), product.getId());
      response.redirect(url);
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/customers/:customer_id/products/:id/delete", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Product product = Product.find(Integer.parseInt(request.params("id")));
      Customer customer = Customer.find(product.getCustomerId());
      product.delete();
      model.put("customer", customer);
      model.put("template", "templates/customer.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/customers/:id/delete", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Customer customer = Customer.find(Integer.parseInt(request.params(":id")));
      String deletedCustomerName = customer.getName();
      customer.delete();
      response.redirect("/");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());
  }
}
