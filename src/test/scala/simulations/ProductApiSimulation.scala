package simulations

import io.gatling.core.scenario.Simulation
import io.gatling.core.Predef._
import io.gatling.http.Predef._
class ProductApiSimulation extends Simulation {

  //conf
  val value_conf = http.baseUrl("http://localhost:8081")
    .header("Accept",value="application/json")
    .header(name="content-type", value ="application/json")


  //scenario
  val scn = scenario("Product Management Api operations")

    .exec(http("Get all Product details")
      .get("/rvy/api/pis/v1/products")
      .check(status is 200))

    .exec(http("Insert product")
      .post("/rvy/api/pis/v1/product")
      .body(RawFileBody(filePath = "./src/test/resources/bodies/addCustomer.json")).asJson
      .header(name="content-type",value = "application/json")
      .check(status is 200))

    .exec(http("Get by id")
      .get("/rvy/api/pis/v1/product/13")
      .check(status is 200))

    .exec(http("Update product")
      .put("/rvy/api/pis/v1/product")
      .body(RawFileBody(filePath = "./src/test/resources/bodies/putCustomer.json")).asJson
      .header(name="content-type",value = "application/json")
      .check(status is 200))



  //setup
  setUp(scn.inject(atOnceUsers(users=30))).protocols(value_conf)



}
