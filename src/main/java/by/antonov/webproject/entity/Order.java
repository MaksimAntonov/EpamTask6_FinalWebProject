package by.antonov.webproject.entity;

import java.time.LocalDateTime;
import java.util.List;

public class Order extends EntityBase {
  private Long id;
  private String details;
  private LocalDateTime readyDate;
  private LocalDateTime endDate;
  private User user;
  private List<Offer> offers;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getDetails() {
    return details;
  }

  public void setDetails(String details) {
    this.details = details;
  }

  public LocalDateTime getReadyDate() {
    return readyDate;
  }

  public void setReadyDate(LocalDateTime readyDate) {
    this.readyDate = readyDate;
  }

  public LocalDateTime getEndDate() {
    return endDate;
  }

  public void setEndDate(LocalDateTime endDate) {
    this.endDate = endDate;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
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
    if (getClass() != o.getClass()) {
      return false;
    }

    Order order = (Order) o;

    if (id != null ? !id.equals(order.id) : order.id != null) {
      return false;
    }
    if (details != null ? !details.equals(order.details) : order.details != null) {
      return false;
    }
    if (readyDate != null ? !readyDate.equals(order.readyDate) : order.readyDate != null) {
      return false;
    }
    if (endDate != null ? !endDate.equals(order.endDate) : order.endDate != null) {
      return false;
    }
    if (user != null ? !user.equals(order.user) : order.user != null) {
      return false;
    }
    return offers != null ? offers.equals(order.offers) : order.offers == null;
  }

  @Override
  public int hashCode() {
    int result = id != null ? id.hashCode() : 0;
    result = 31 * result + (details != null ? details.hashCode() : 0);
    result = 31 * result + (readyDate != null ? readyDate.hashCode() : 0);
    result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
    result = 31 * result + (user != null ? user.hashCode() : 0);
    result = 31 * result + (offers != null ? offers.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("Order{");
    sb.append("id=").append(id);
    sb.append(", details='").append(details).append("'");
    sb.append(", readyDate=").append(readyDate);
    sb.append(", endDate=").append(endDate);
    sb.append(", user='").append(user).append("'");
    sb.append(", offers=").append(offers);
    sb.append('}');
    return sb.toString();
  }
}
