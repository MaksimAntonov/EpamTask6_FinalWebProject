package by.antonov.webproject.entity;

import java.time.LocalDateTime;
import java.util.List;

public class Order extends EntityBase {
  private final Long id;
  private final String route;
  private final String details;
  private final LocalDateTime createDate;
  private final LocalDateTime updateDate;
  private final User user;
  private final Status status;
  private Offer bestOffer;
  private List<Offer> offers;

  public enum Status {
    NEW,
    FINISHED,
    CLOSED;

    public int getDBIndex() {
      return this.ordinal() + 1;
    }
  }

  protected Order(Long id,
               String route,
               String details,
               LocalDateTime createDate,
               LocalDateTime updateDate,
               User user,
               Status orderStatus) {
    this.id = id;
    this.route = route;
    this.details = details;
    this.createDate = createDate;
    this.updateDate = updateDate;
    this.user = user;
    this.status = orderStatus;
  }

  public Long getId() {
    return id;
  }

  public String getDetails() {
    return details;
  }

  public String getRoute() {
    return route;
  }

  public LocalDateTime getCreateDate() {
    return createDate;
  }

  public LocalDateTime getUpdateDate() {
    return updateDate;
  }

  public User getUser() {
    return user;
  }

  public Status getStatus() {
    return status;
  }

  public Offer getBestOffer() {
    return bestOffer;
  }

  public void setBestOffer(Offer bestOffer) {
    this.bestOffer = bestOffer;
  }

  public List<Offer> getOffers() {
    return offers;
  }

  public void setOffers(List<Offer> offers) {
    this.offers = offers;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Order)) {
      return false;
    }

    Order order = (Order) o;

    if (id != null ? !id.equals(order.id) : order.id != null) {
      return false;
    }
    if (route != null ? !route.equals(order.route) : order.route != null) {
      return false;
    }
    if (details != null ? !details.equals(order.details) : order.details != null) {
      return false;
    }
    if (createDate != null ? !createDate.equals(order.createDate) : order.createDate != null) {
      return false;
    }
    if (updateDate != null ? !updateDate.equals(order.updateDate) : order.updateDate != null) {
      return false;
    }
    if (user != null ? !user.equals(order.user) : order.user != null) {
      return false;
    }
    if (status != order.status) {
      return false;
    }
    return offers != null ? offers.equals(order.offers) : order.offers == null;
  }

  @Override
  public int hashCode() {
    int result = id != null ? id.hashCode() : 0;
    result = 31 * result + (route != null ? route.hashCode() : 0);
    result = 31 * result + (details != null ? details.hashCode() : 0);
    result = 31 * result + (createDate != null ? createDate.hashCode() : 0);
    result = 31 * result + (updateDate != null ? updateDate.hashCode() : 0);
    result = 31 * result + (user != null ? user.hashCode() : 0);
    result = 31 * result + (status != null ? status.hashCode() : 0);
    result = 31 * result + (offers != null ? offers.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("Order{");
    sb.append("id=").append(id);
    sb.append(", route='").append(route).append('\'');
    sb.append(", details='").append(details).append('\'');
    sb.append(", createDate=").append(createDate);
    sb.append(", updateDate=").append(updateDate);
    sb.append(", user=").append(user);
    sb.append(", orderStatus=").append(status);
    sb.append(", offers=").append(offers);
    sb.append('}');
    return sb.toString();
  }

  public static class Builder {
    private Long id;
    private String route;
    private String details;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
    private User user;
    private Status orderStatus;

    public Builder setId(Long id) {
      this.id = id;
      return this;
    }

    public Builder setRoute(String route) {
      this.route = route;
      return this;
    }

    public Builder setDetails(String details) {
      this.details = details;
      return this;
    }

    public Builder setCreateDate(LocalDateTime createDate) {
      this.createDate = createDate;
      return this;
    }

    public Builder setUpdateDate(LocalDateTime updateDate) {
      this.updateDate = updateDate;
      return this;
    }

    public Builder setUser(User user) {
      this.user = user;
      return this;
    }

    public Builder setOrderStatus(Status orderStatus) {
      this.orderStatus = orderStatus;
      return this;
    }

    public Order build() {
      return new Order(id, route, details, createDate, updateDate, user, orderStatus);
    }
  }
}
