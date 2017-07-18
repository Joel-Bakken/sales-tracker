import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void customer_instantiatesCorrectly_true() {
    Customer testCustomer = new Customer("Kelle", "kelle@kelle.com");
    assertEquals(true, testCustomer instanceof Customer);
  }

  @Test
  public void getName_customerInstantiatesWithName_Kelle() {
    Customer testCustomer = new Customer("Kelle", "kelle@kelle.com");
    assertEquals("Kelle", testCustomer.getName());
  }

  @Test
  public void getEmail_CustomerInstantiatesWithEmail_String() {
    Customer testCustomer = new Customer("Kelle", "kelle@kelle.com");
    assertEquals("kelle@kelle.com", testCustomer.getEmail());
  }

  @Test
  public void save_insertsObjectIntoDatabase_Customer() {
    Customer testCustomer = new Customer("Kelle", "kelle@kelle.com");
    testCustomer.save();
    assertEquals(true, Customer.all().get(0).equals(testCustomer));
  }

  @Test
  public void save_assignsIdToObject() {
    Customer testCustomer = new Customer("Kelle", "kelle@kelle.com");
    testCustomer.save();
    Customer savedCustomer = Customer.all().get(0);
    assertEquals(testCustomer.getId(), savedCustomer.getId());
  }

  @Test
  public void equals_returnsTrueIfNameAndEmailAreSame_true() {
    Customer testCustomer = new Customer("Kelle", "kelle@kelle.com");
    Customer anotherCustomer = new Customer("Kelle", "kelle@kelle.com");
    assertTrue(testCustomer.equals(anotherCustomer));
  }

  @Test
  public void all_returnsAllInstancesOfCustomer_true() {
    Customer firstCustomer = new Customer("Kelle", "kelle@kelle.com");
    firstCustomer.save();
    Customer secondCustomer = new Customer("Scuba Steve", "Steve@scuba.com");
    secondCustomer.save();
    assertEquals(true, Customer.all().get(0).equals(firstCustomer));
    assertEquals(true, Customer.all().get(1).equals(secondCustomer));
  }

  @Test
  public void getProducts_retrievesAllProductsFromDatabase_productsList() {
    Customer testCustomer = new Customer("Henry", "henry@henry.com");
    testCustomer.save();
    Product firstProduct = new product("smoke detector", testCustomer.getId());
    firstProduct.save();
    Object[] products = new Object[] { firstProduct };
    assertTrue(testCustomer.getProducts().containsAll(Arrays.asList(products)));
  }

}
