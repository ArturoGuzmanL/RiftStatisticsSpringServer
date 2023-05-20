const $ = require( "jquery" );
const Swal = require("sweetalert2");
const Swaldef = Swal.default;
const {ipcRenderer} = require("electron");
const SHA256 = require("crypto-js/sha256");
let originalUsername;
let originalEmail;
let originalBirthday;

$(document).ready(function() {
  originalUsername = $("#usernameInput").val();
  originalEmail = $("#emailInput").val();
  originalBirthday = $("#birthdayInput").val();
});


$('#pass1ShowS').off('click').on('click', function(event) {
  let signPassField = $("#pass1Input");
  let lineSign = $("#pass1ShowS-activeShow");
  if (signPassField.attr("type") === "password") {
    signPassField.attr("type", "text");
    lineSign.css("visibility", "hidden");
    console.log("show")
  } else {
    signPassField.attr("type", "password");
    lineSign.css("visibility", "visible");
    console.log("hide")
  }
});


$('#pass2ShowS').off('click').on('click', function(event) {
  let signPassField = $("#pass2Input");
  let lineSign = $("#pass2ShowS-activeShow");
  if (signPassField.attr("type") === "password") {
    signPassField.attr("type", "text");
    lineSign.css("visibility", "hidden");
    console.log("show")
  } else {
    signPassField.attr("type", "password");
    lineSign.css("visibility", "visible");
    console.log("hide")
  }
});

$('#user-photo').off('click').on('click', async function (event) {
  const {value: file} = await Swaldef.fire({
    title: 'Select a new image',
    input: 'file',
    inputAttributes: {
      'accept': 'image/*',
      'aria-label': 'Upload your profile picture'
    },
    color: '#FFFFFF',
    background: 'rgba(11, 11, 35, 1)',
    showCancelButton: true,
    confirmButtonText: 'Upload',
    cancelButtonText: 'Cancel',
    width: '35%',
  })

  if (file) {
    let xhr = new XMLHttpRequest();
    ipcRenderer.send('get-uid');
    ipcRenderer.on('get-uid-reply', (event, uid) => {
      xhr.open("PUT", `https://riftstatistics.ddns.net/page/users/actions/profileimgupdt/${uid}`, true);
      xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
          ipcRenderer.send('lol-account-change', 'your profile picture has been updated');
        } else {
          Swaldef.fire({
            title: 'Something went wrong',
            icon: 'error',
            color: '#FFFFFF',
            background: 'rgba(11, 11, 35, 1)',
            confirmButtonText: 'Ok',
          });
        }
      }
      xhr.send(file);
    });
  }
});

$('#cancelButton').off('click').on('click', function(event) {
  $("#usernameInput").val(originalUsername);
  $("#emailInput").val(originalEmail);
  $("#birthdayInput").val(originalBirthday);
});

$('#deleteAcc').off('click').on('click', function(event) {
  Swaldef.fire({
    title: 'Are you sure you want to delete your account?',
    text: "You won't be able to revert this!",
    icon: 'warning',
    color: '#FFFFFF',
    background: 'rgba(11, 11, 35, 1)',
    showCancelButton: true,
    confirmButtonText: 'Yes',
    cancelButtonText: 'No',
  }).then(async (result) => {
    if (result.isConfirmed) {
      const {value: password} = await Swaldef.fire({
        title: 'Enter your password',
        color: '#FFFFFF',
        background: 'rgba(11, 11, 35, 1)',
        input: 'password',
        inputLabel: 'Password',
        inputPlaceholder: 'Enter your password',
        inputAttributes: {
          autocapitalize: 'off',
          autocorrect: 'off'
        }
      })
      let hashedPassword = SHA256(password).toString();
      let xhr = new XMLHttpRequest();
      let correctPassword = false;
      ipcRenderer.send('get-uid');
      ipcRenderer.on('get-uid-reply', (event, uid) => {
        xhr.open("GET", `https://riftstatistics.ddns.net/page/users/actions/change/correctpsw/${uid}@&@${hashedPassword}`, false);
        xhr.onload = function () {
          if (xhr.readyState === 4 && xhr.status === 200) {
            let xhr2 = new XMLHttpRequest();
            xhr2.open("GET", `https://riftstatistics.ddns.net/page/users/actions/delete/${uid}`, false);
            xhr2.onload = function () {
              if (xhr2.readyState === 4 && xhr2.status === 200) {
                ipcRenderer.send('unlogged-event', 'your account has been deleted');
              }else {
                Swaldef.fire({
                  title: 'Something went wrong',
                  icon: 'error',
                  color: '#FFFFFF',
                  background: 'rgba(11, 11, 35, 1)',
                  confirmButtonText: 'Ok',
                });
              }
            }
            xhr2.send();
          }else {
            Swaldef.fire({
              title: 'Password is incorrect',
              icon: 'error',
              color: '#FFFFFF',
              background: 'rgba(11, 11, 35, 1)',
              confirmButtonText: 'Ok',
            });
          }
        }
        xhr.send();
      });
    }
  });
});


$('#submitButton').off('click').on('click', function(event) {
  let nameInput = $("#usernameInput");
  let mailInput = $("#emailInput");
  let birthdayInput = $("#birthdayInput");
  let psswd = $("#pass1Input").val();
  let psswd2 = $("#pass2Input").val();
  let hashedPassword = SHA256(psswd).toString();

  if (nameInput.val() !== originalUsername || mailInput.val() !== originalEmail || birthdayInput.val() !== originalBirthday) {

    if (psswd === "" && psswd2 === "") {
      Swaldef.fire({
        toast: true,
        position: 'top',
        width: '80%',
        title: 'You need to fill the password fields to change your information',
        icon: 'error',
        color: '#FFFFFF',
        background: 'rgba(11, 11, 35, 1)',
        timer: 3000,
        confirmButtonText: 'Ok',
      });
    }else if (psswd !== psswd2) {
      Swaldef.fire({
        toast: true,
        position: 'top',
        width: '80%',
        title: 'The passwords do not match',
        icon: 'error',
        color: '#FFFFFF',
        background: 'rgba(11, 11, 35, 1)',
        timer: 3000,
        confirmButtonText: 'Ok',
      });
      $("#pass1Input").val("");
      $("#pass2Input").val("");
    }else {
      let xhr = new XMLHttpRequest();
      ipcRenderer.send('get-uid');
      ipcRenderer.on('get-uid-reply', (event, uid) => {
        xhr.open("GET", `https://riftstatistics.ddns.net/page/users/actions/change/correctpsw/${uid}@&@${hashedPassword}`, false);
        xhr.onload = function () {
          if (xhr.readyState === 4 && xhr.status === 200) {
            Swaldef.fire({
              title: 'Are you sure you want to save the changes?',
              icon: 'question',
              color: '#FFFFFF',
              background: 'rgba(11, 11, 35, 1)',
              showCancelButton: true,
              confirmButtonText: 'Yes',
              cancelButtonText: 'No',
            }).then((result) => {
              if (result.isConfirmed) {
                let xhr = new XMLHttpRequest();
                ipcRenderer.send('get-uid');
                ipcRenderer.on('get-uid-reply', (event, uid) => {

                  let messagge = "";
                  let type = "";
                  let nombreT = false;
                  let correoT = false;
                  let cumpleT = false;
                  if (nameInput.val() !== originalUsername) {
                    messagge += nameInput.val() + "@&@";
                    nombreT = true;
                  }
                  if (mailInput.val() !== originalEmail) {
                    messagge += mailInput.val() + "@&@";
                    correoT = true;
                  }
                  if (birthdayInput.val() !== originalBirthday) {
                    messagge += birthdayInput.val() + "@&@";
                    cumpleT = true;
                  }

                  switch (true) {
                    case nombreT && !correoT && !cumpleT:
                      type = "1";
                      break;
                    case !nombreT && correoT && !cumpleT:
                      type = "2";
                      break;
                    case !nombreT && !correoT && cumpleT:
                      type = "3";
                      break;
                    case nombreT && correoT && !cumpleT:
                      type = "4";
                      break;
                    case nombreT && !correoT && cumpleT:
                      type = "5";
                      break;
                    case !nombreT && correoT && cumpleT:
                      type = "6";
                      break;
                    case nombreT && correoT && cumpleT:
                      type = "7";
                      break;
                  }

                  xhr.open("PUT", `https://riftstatistics.ddns.net/page/users/actions/update/${uid}/${type}`, false);
                  xhr.setRequestHeader("Content-Type", "text/plain");
                  xhr.onload = function () {
                    if (xhr.readyState === 4 && xhr.status === 200) {
                      Swaldef.fire({
                        title: 'Changes saved!',
                        icon: 'success',
                        color: '#FFFFFF',
                        background: 'rgba(11, 11, 35, 1)',
                        confirmButtonText: 'Ok',
                      }).then((result) => {
                        if (result.isConfirmed) {
                          ipcRenderer.send('reload');
                        }
                      });
                    }else if (xhr.readyState === 4 && xhr.status === 409) {
                      Swaldef.fire({
                        toast: true,
                        position: 'top',
                        width: '80%',
                        title: xhr.responseText,
                        icon: 'error',
                        color: '#FFFFFF',
                        background: 'rgba(11, 11, 35, 1)',
                        timer: 3000,
                        confirmButtonText: 'Ok',
                      });
                      $("#pass1Input").val("");
                      $("#pass2Input").val("");
                    }else if (xhr.readyState === 4 && xhr.status === 304) {
                      Swaldef.fire({
                        toast: true,
                        position: 'top',
                        width: '80%',
                        title: 'No changes made',
                        icon: 'info',
                        color: '#FFFFFF',
                        background: 'rgba(11, 11, 35, 1)',
                        timer: 3000,
                        confirmButtonText: 'Ok',
                      });
                      $("#pass1Input").val("");
                      $("#pass2Input").val("");
                    }else {
                      Swaldef.fire({
                        toast: true,
                        position: 'top',
                        width: '80%',
                        title: 'An error has occurred',
                        icon: 'error',
                        color: '#FFFFFF',
                        background: 'rgba(11, 11, 35, 1)',
                        timer: 3000,
                        confirmButtonText: 'Ok',
                      });
                      $("#pass1Input").val("");
                      $("#pass2Input").val("");
                    }
                  }

                  xhr.send(messagge);
                });
              }else {
                $("#pass1Input").val("");
                $("#pass2Input").val("");
              }
            });
          }else {
            Swaldef.fire({
              toast: true,
              position: 'top',
              width: '80%',
              title: 'The password is incorrect',
              icon: 'error',
              color: '#FFFFFF',
              background: 'rgba(11, 11, 35, 1)',
              timer: 3000,
              confirmButtonText: 'Ok',
            });
          }
        }
        xhr.send();
      });
    }
  }
});

$('#changePasswdButton').off('click').on('click', function () {
  let pass1 = $('#changePasswd1').val()
  let pass2 = $('#changePasswd2').val()
  let uid = $('.newPswd').attr('id')

  if (pass1 === "" || pass2 === "") {
    Swaldef.fire({
      toast: true,
      position: 'top',
      width: '80%',
      title: 'Fill in all the fields',
      icon: 'error',
      color: '#FFFFFF',
      background: 'rgba(11, 11, 35, 1)',
      timer: 3000,
      confirmButtonText: 'Ok',
    });
    $('#changePasswd1').val("")
    $('#changePasswd2').val("")
  }else if (pass1 !== pass2) {
    Swaldef.fire({
      toast: true,
      position: 'top',
      width: '80%',
      title: 'The passwords do not match',
      icon: 'error',
      color: '#FFFFFF',
      background: 'rgba(11, 11, 35, 1)',
      timer: 3000,
      confirmButtonText: 'Ok',
    });
    $('#changePasswd1').val("")
    $('#changePasswd2').val("")
  }else {
    let xhr = new XMLHttpRequest();
    xhr.open("PUT", `https://riftstatistics.ddns.net/page/users/actions/update/${uid}/8`, false);
    xhr.setRequestHeader("Content-Type", "text/plain");
    xhr.onload = function () {
      if (xhr.readyState === 4 && xhr.status === 200) {
        Swaldef.fire({
          title: 'Changes saved!',
          icon: 'success',
          color: '#FFFFFF',
          background: 'rgba(11, 11, 35, 1)',
          confirmButtonText: 'Ok',
        });
      }else {
        Swaldef.fire({
          toast: true,
          position: 'top',
          width: '80%',
          title: 'An error has occurred',
          icon: 'error',
          color: '#FFFFFF',
          background: 'rgba(11, 11, 35, 1)',
          timer: 3000,
          confirmButtonText: 'Ok',
        });
      }
    }
  }
});

