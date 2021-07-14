window.onload = () => {
  document.querySelector("select[name=locale]").addEventListener("change", (event) => {
    let currentPage = window.location.href;
    window.location.href = "controller?command=change_locale&locale="+event.target.value+"&redirect_url="+currentPage;
  });
}