(() => {
  // prevent resubmission form when we refrsh the page
  // = submitssion only onclick submit button
  if (window.history.replaceState) {
    window.history.replaceState(null, null, window.location.href);
  }
})();

const logout = () => {
  window.location.replace("/logout");
};

const closeAlertModal = () => {
  document.querySelector(".alert-modal").style.display = "none";
};

const openModalEmail = () => {
  document.getElementById("edit-email").classList.remove("hidden");
};

const closeModalEmail = () => {
  document.getElementById("edit-email").classList.add("hidden");
  closeAlertModal();
};

const openModalPwd = () => {
  document.getElementById("edit-pwd").classList.remove("hidden");
};

const closeModalPwd = () => {
  document.getElementById("edit-pwd").classList.add("hidden");
  closeAlertModal();
};
