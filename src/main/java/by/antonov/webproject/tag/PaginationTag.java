package by.antonov.webproject.tag;

import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.JspWriter;
import jakarta.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class PaginationTag extends TagSupport {

  private int currentPage;
  private int maxPage;

  public void setPage(int currentPage) {
    this.currentPage = currentPage;
  }

  public void setMaxPage(int maxPage) {
    this.maxPage = maxPage;
  }

  @Override
  public int doStartTag() throws JspException {
    try {
      if (currentPage > 0 && maxPage > 0) {
        JspWriter writer = pageContext.getOut();

        StringBuilder stringBuilder = new StringBuilder("<div class='pagination'>");
        for (int i = 1; i <= maxPage; i++) {
          if (i == currentPage) {
            stringBuilder.append("<b class='pagination__item pagination__item_current'>")
                         .append(i)
                         .append("</b>");
          } else {
            if (i == 1 || i == maxPage) {
              stringBuilder.append("<a class='pagination__item'>")
                           .append(i)
                           .append("</a>");
            } else {
              if ((currentPage - 3) < i && (currentPage + 3) > i) {
                stringBuilder.append("<a class='pagination__item'>")
                             .append(i)
                             .append("</a>");
              } else {
                stringBuilder.append(".");
              }
            }
          }
        }
        stringBuilder.append("</div>");

        writer.write(stringBuilder.toString());
      }
      return SKIP_BODY;
    } catch (IOException e) {
      throw new JspException("PaginationTag error: " + e.getMessage(), e);
    }
  }
}
