package cars.rus.DTO;

import cars.rus.Entities.Car;

public class CarInput {
  private String brand;
  private String model;
  private int pricePerDay;

  public CarInput() {
  }

  public CarInput(String brand, String model, int pricePerDay) {
    this.brand = brand;
    this.model = model;
    this.pricePerDay = pricePerDay;
  }

  public String getBrand() {
    return brand;
  }

  public void setBrand(String brand) {
    this.brand = brand;
  }

  public String getModel() {
    return model;
  }

  public void setModel(String model) {
    this.model = model;
  }

  public int getPricePerDay() {
    return pricePerDay;
  }

  public void setPricePerDay(int pricePerDay) {
    this.pricePerDay = pricePerDay;
  }

  public static Car carFromCarInput(CarInput carInput) {
    return new Car(carInput.getBrand(), carInput.getModel(), carInput.getPricePerDay());
  }
}
