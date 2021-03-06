package by.antonov.webproject.entity;

import java.time.LocalDateTime;

public class Offer extends EntityBase {

  private final long id;
  private final double price;
  private final LocalDateTime offerDate;
  private final User user;
  private final Status offerStatus;

  protected Offer(long id, double price, LocalDateTime offerDate, User user, Status offerStatus) {
    this.id = id;
    this.price = price;
    this.offerDate = offerDate;
    this.user = user;
    this.offerStatus = offerStatus;
  }

  public long getId() {
    return id;
  }

  public double getPrice() {
    return price;
  }

  public User getUser() {
    return user;
  }

  @Override
  public int hashCode() {
    int result;
    long temp;
    result = (int) (id ^ (id >>> 32));
    temp = Double.doubleToLongBits(price);
    result = 31 * result + (int) (temp ^ (temp >>> 32));
    result = 31 * result + (offerDate != null ? offerDate.hashCode() : 0);
    result = 31 * result + (user != null ? user.hashCode() : 0);
    return result;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (this.getClass() != o.getClass()) {
      return false;
    }

    Offer offer = (Offer) o;

    if (id != offer.id) {
      return false;
    }
    if (Double.compare(offer.price, price) != 0) {
      return false;
    }
    if (offerDate != null ? !offerDate.equals(offer.offerDate) : offer.offerDate != null) {
      return false;
    }
    return user != null ? user.equals(offer.user) : offer.user == null;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("Offer{");
    sb.append("id=").append(id);
    sb.append(", price=").append(price);
    sb.append(", offerDate=").append(offerDate);
    sb.append(", user=").append(user);
    sb.append('}');
    return sb.toString();
  }

  public enum Status {
    OFFERED,
    ACCEPTED,
    DENIED
  }

  public static class Builder {

    private long id;
    private double price;
    private LocalDateTime offerDate;
    private User user;
    private Status offerStatus;

    public Builder setId(long id) {
      this.id = id;
      return this;
    }

    public Builder setPrice(double price) {
      this.price = price;
      return this;
    }

    public Builder setOfferDate(LocalDateTime offerDate) {
      this.offerDate = offerDate;
      return this;
    }

    public Builder setUser(User user) {
      this.user = user;
      return this;
    }

    public Builder setOfferStatus(Status offerStatus) {
      this.offerStatus = offerStatus;
      return this;
    }

    public Offer build() {
      return new Offer(id, price, offerDate, user, offerStatus);
    }
  }
}
