window.onload = () => {
  document.querySelector("select[name=locale]").addEventListener("change", (event) => {
    let currentPage = window.location.href;
    window.location.href = PROJECT_ROOT + "controller?command=change_locale&locale="+event.target.value+"&redirect_url="+currentPage;
  });
}