const createFormErrorElement = (id, classes, parentNote, insertBeforeNode) => {
  let elem = document.createElement("i");
  elem.classList.add(...classes.split(' '));
  elem.setAttribute("id", id);
  parentNote.insertBefore(elem, insertBeforeNode)

  return elem;
}

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

const clearInputError = (event) => {
  let elem = event.target;
  let errorElem = elem.parentNode.querySelector("#" + elem.id + "-error");
  if (errorElem !== null) {
    errorElem.remove();
  }
}

const invalidInputHandler = (event) => {
  event.preventDefault();
  let elem = event.target;
  if (elem.dataset.invalidText !== undefined) {
    let errElem = document.querySelector("#"+elem.getAttribute("id")+"-error")
        || createFormErrorElement(elem.id + "-error", "forms__element forms__error", event.target.parentNode, elem.nextSibling);
    errElem.innerText = elem.dataset.invalidText;
  }
}

const changeLocale = (event) => {
  let currentPage = encodeURIComponent(window.location.pathname + window.location.search);
  window.location.href = PROJECT_ROOT + "controller?command=change_locale&locale="+event.target.value+"&redirect_url="+currentPage;
}

window.onload = () => {
  document.querySelector("#change-locale").addEventListener("change", changeLocale);

  document.querySelectorAll(".modal-window").forEach((modal) => modalWindow(modal));

  document.querySelectorAll(".forms__input").forEach((inputEl) => {
    inputEl.addEventListener('invalid', invalidInputHandler);
    inputEl.addEventListener('input', clearInputError);
  });

  document.querySelectorAll("a.pagination__item").forEach((link) => {
    let currentPage = window.location.href.replace(/&page=\d/ig, "");
    let page = link.innerText;
    link.href = currentPage + "&page=" + page;
  })
}