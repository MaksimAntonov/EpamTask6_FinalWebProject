const openModal = (modalWindowId) => {
  document.querySelector(`#${modalWindowId}`).classList.remove("modal-window_hidden");
}

const modalWindow = (modalWindow) => {
  let closeButton = modalWindow.querySelector(".modal-window__close");
  let closeFunction = (event) => {
    console.log(event);
    let elem = event.target;
    if (elem.classList.contains("modal-window") || elem.classList.contains("modal-window__close")) {
      modalWindow.classList.add("modal-window_hidden");
    }
  }

  modalWindow.addEventListener("click", (event) => { closeFunction(event); })
  closeButton.addEventListener("click", (event) => { closeFunction(event); });
}

window.onload = () => {
  document.querySelector("select[name=locale]").addEventListener("change", (event) => {
    let currentPage = window.location.href;
    window.location.href = PROJECT_ROOT + "controller?command=change_locale&locale="+event.target.value+"&redirect_url="+currentPage;
  });

  document.querySelectorAll(".modal-window").forEach((modal) => modalWindow(modal));
}