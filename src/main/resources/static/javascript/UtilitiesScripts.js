const fs = require("fs");
const $ = require( "jquery" );
const SHA256 = require("crypto-js/sha256");
const {ipcRenderer} = require("electron");
const repl = require("repl");
const {getFirstChild} = require("jsdom/lib/jsdom/living/domparsing/parse5-adapter-serialization");
const Swal = require("sweetalert2");
const Swaldef = Swal.default;
const emailValidator = require("email-validator");
const mousetrap = require("mousetraps");
let openedOnButton = false;

mousetrap.bind('f5', () => {
  $('body').removeClass('loaded').css('overflow', 'hidden');
  $('#page').html("");
  $('#entry-title').removeClass("disabled");
  ipcRenderer.send('reload');
});

mousetrap.bind(['command+r', 'ctrl+r'], () => {
  $('body').removeClass('loaded').css('overflow', 'hidden');
  $('#page').html("");
  $('#entry-title').removeClass("disabled");
  ipcRenderer.send('force-reload');
});

$(document).ready(function() {
  $('body').addClass('loaded').css('overflow', 'auto');
  $('#entry-title').addClass("disabled");
  ipcRenderer.on('toast-message-lol', (event, Mssg) => {
    const Toast = Swaldef.mixin({
      toast: true,
      position: 'top',
      icon: 'success',
      confirmButtonText: 'Ok',
      showCancelButton: false,
      color: '#FFFFFF',
      timer: 3000,
      width: '80%',
      background: 'rgba(51, 51, 106, 1)',
      showClass: {
        popup: 'animate__animated animate__headShake'
      },
      hideClass: {
        popup: 'animate__animated animate__fadeOutUpBig'
      },
    })
    setTimeout(() => {
      Toast.fire({
        text: Mssg
      });
    }, 750);
  });
});

$('#sidebar-button').off('click').on('click', function(event) {
  let sidebar = $('.sidebar');
  sidebar.toggleClass('active');
  if (sidebar.hasClass('active')) {
    openedOnButton = true;
    $('.header_Browser').css('margin-left', '275px');
  } else {
    openedOnButton = false;
    $('.header_Browser').css('margin-left', '95px');
  }

});

$('#interactiveSidebar').off('mouseenter').on('mouseenter', function(event) {
  let sidebar = $('.sidebar');
  sidebar.addClass('active');
  $('.header_Browser').css('margin-left', '275px');
});

$('#interactiveSidebar').off('mouseleave').on('mouseleave', function(event) {
  let sidebar = $('.sidebar');

  if (!openedOnButton) {
    sidebar.removeClass('active');
    $('.header_Browser').css('margin-left', '95px');
  }
});


$('#show-login').off('click').on('click', function(event) {
  let line = $("#LogPassShow-activeShow");
  let logPassField = $("#logPassword");
  let logUsername = $("#logUsername");
  let logrememberMe = $("#remember");

  if (!$(".popupSign").hasClass("active")) {
    if (logPassField.attr("type") === "text") {
      logPassField.attr("type", "password");
      line.css("visibility", "visible");
    }
    $(".popup").toggleClass("active");
    logUsername.val("");
    logPassField.val("");
    logrememberMe.prop("checked", false);
  }
});



$('#show-signup').off('click').on('click', function(event) {
  let lineSign = $("#SignPassShow-activeShow");
  let signPassField = $("#sigPassword");
  if (!$(".popup").hasClass("active")) {
    if (signPassField.attr("type") === "text") {
      signPassField.attr("type", "password");
      lineSign.css("visibility", "visible");
    }
    $(".popupSign").toggleClass("active");
    $("#sigUsername").val("");
    $("#sigPassword").val("");
    $("#sigEmail").val("");
  }
});

$('#close-btn-log').off('click').on('click', function(event) {
  let logPassField = $('#logPassword');
  let line = $('#LogPassShow-activeShow');
  $('.popup').removeClass('active');
  logPassField.attr('type', 'password');
  line.css('visibility', 'visible');
});

$('#close-btn-sign').off('click').on('click', function(event) {
  let lineSign = $("#SignPassShow-activeShow");
  let signPassField = $("#sigPassword");
  $(".popupSign").removeClass("active");
  signPassField.attr("type", "password");
  lineSign.css("visibility", "visible");
});

$('#logPassShow').off('click').on('click', function(event) {
  let logPassField = $('#logPassword');
  let line = $('#LogPassShow-activeShow');
  if (logPassField.attr('type') === 'password') {
    logPassField.attr('type', 'text');
    line.css('visibility', 'hidden');
  } else {
    logPassField.attr('type', 'password');
    line.css('visibility', 'visible');
  }
});

$('#signPassShow').off('click').on('click', function(event) {
  let signPassField = $("#sigPassword");
  let lineSign = $("#SignPassShow-activeShow");
  if (signPassField.attr("type") === "password") {
    signPassField.attr("type", "text");
    lineSign.css("visibility", "hidden");
  } else {
    signPassField.attr("type", "password");
    lineSign.css("visibility", "visible");
  }
});

$('#clearSearch').off('click').on('click', function(event) {
  $('#BrowserInput').val("");
  $('#browserListContainer').html("<div class=\"loader disabled\" id=\"loader\"></div>");
});

$('#mainSearchField').off('click').on('click', function(event) {
  $('#popupBrowserWindow').toggleClass("active");
  $('#blurrDiv').toggleClass("active");
  $('#blurrDiv2').toggleClass("active");
  $('body').addClass("filtered");
  $('#sidebar').toggleClass("filtered");
  $(".header_object").addClass("disabled");
  $(".sidebar").addClass("disabled");
  $('#loader').addClass("disabled");
  let container = $('#browserListContainer');
  if ($("body").css("overflow") === "hidden") {
    $("body").css("overflow", "visible");
  }else {
    $("body").css("overflow", "hidden");
  }
  if (container.html() === "") {
    container.html("<div class=\"loader disabled\" id=\"loader\"></div>");
  }
  container.removeClass("loader");
  setTimeout(function() {
    $('#BrowserInput').focus();
  }, 100);
});

$("#header_Browser_ta").off('click').on("click", function(event) {
  $('#popupBrowserWindow').toggleClass("active");
  $('#blurrDiv').toggleClass("active");
  $('#blurrDiv2').toggleClass("active");
  $('body').addClass("filtered");
  $('#sidebar').toggleClass("filtered");
  $(".header_object").addClass("disabled");
  $(".sidebar").addClass("disabled");
  $('#loader').addClass("disabled");
  let container = $('#browserListContainer');
  if ($("body").css("overflow") === "hidden") {
    $("body").css("overflow", "visible");
  }else {
    $("body").css("overflow", "hidden");
    window.scrollTo(0, 0);
  }
  if (container.html() === "") {
    container.html("<div class=\"loader disabled\" id=\"loader\"></div>");
  }
  container.removeClass("loader");
  setTimeout(function() {
    $('#BrowserInput').focus();
  }, 100);
});

$('#closeBrowserBtn').off('click').on('click', function(event) {
  $('#popupBrowserWindow').toggleClass("active");
  $('#blurrDiv').toggleClass("active");
  $('#blurrDiv2').toggleClass("active");
  $('body').removeClass("filtered");
  $('#sidebar').toggleClass("filtered");
  $("#BrowserInput").val("");
  $('#browserListContainer').html("").removeClass("loader");
  $(".header_object").removeClass("disabled");
  $(".sidebar").removeClass("disabled");
  $('#loader').addClass("disabled");
  if ($("body").css("overflow") === "hidden") {
    $("body").css("overflow", "visible");
  }
});

$('#homePageButton').off('click').on('click', function(event) {
  ipcRenderer.send("is-logged");
  ipcRenderer.on("is-logged-reply", (event, reply) => {
    console.log(reply)
    if (reply) {
      ipcRenderer.send("get-uid");
      ipcRenderer.on("get-uid-reply", (event, uid) => {
        $('body').removeClass('loaded').css('overflow', 'hidden');
        $('#page').html("");
        $('#entry-title').removeClass("disabled");
        ipcRenderer.send("change-html", "https://riftstatistics.ddns.net/page/htmlRequests/home/" + reply + "/" + uid);
      });
    }else {
      $('body').removeClass('loaded').css('overflow', 'hidden');
      $('#page').html("");
      $('#entry-title').removeClass("disabled");
      ipcRenderer.send("change-html", "https://riftstatistics.ddns.net/page/htmlRequests/home/" + reply + "/null");
    }
  });
});

$('#championsPageButton').off('click').on('click', function(event) {
  ipcRenderer.send("is-logged");
  ipcRenderer.on("is-logged-reply", (event, reply) => {
    if (reply) {
      ipcRenderer.send("get-uid");
      ipcRenderer.on("get-uid-reply", (event, uid) => {
        $('body').removeClass('loaded').css('overflow', 'hidden');
        $('#page').html("");
        $('#entry-title').removeClass("disabled");
        ipcRenderer.send("change-html", "https://riftstatistics.ddns.net/page/htmlRequests/championlist/" + reply + "/" + uid);
      });
    }else {
      $('body').removeClass('loaded').css('overflow', 'hidden');
      $('#page').html("");
      $('#entry-title').removeClass("disabled");
      ipcRenderer.send("change-html", "https://riftstatistics.ddns.net/page/htmlRequests/championlist/" + reply + "/null");
    }
  });
});

$('#itemsPageButton').off('click').on('click', function(event) {
  ipcRenderer.send("is-logged");
  ipcRenderer.on("is-logged-reply", (event, reply) => {
    if (reply) {
      ipcRenderer.send("get-uid");
      ipcRenderer.on("get-uid-reply", (event, uid) => {
        $('body').removeClass('loaded').css('overflow', 'hidden');
        $('#page').html("");
        $('#entry-title').removeClass("disabled");
        ipcRenderer.send("change-html", "https://riftstatistics.ddns.net/page/htmlRequests/itemlist/" + reply + "/" + uid);
      });
    }else {
      $('body').removeClass('loaded').css('overflow', 'hidden');
      $('#page').html("");
      $('#entry-title').removeClass("disabled");
      ipcRenderer.send("change-html", "https://riftstatistics.ddns.net/page/htmlRequests/itemlist/" + reply + "/null");
    }
  });
});

$('#settingsPageButton').off('click').on('click', function(event) {
  ipcRenderer.send("get-uid");
  ipcRenderer.on("get-uid-reply", (event, uid) => {
    $('body').removeClass('loaded').css('overflow', 'hidden');
    $('#page').html("");
    $('#entry-title').removeClass("disabled");
    ipcRenderer.send("change-html", "https://riftstatistics.ddns.net/page/htmlRequests/accSettings/" + uid);
  });
});

$('#browserListContainer').off('click', 'li.browserItem').on('click', 'li.browserItem', function() {
  getSummoner.call(this, true);
});

$('#recentlyPlayedContent').off('click', 'div.recentlyPlayedSummoner').on('click', 'div.recentlyPlayedSummoner', function() {
  getSummoner.call(this, false);
});

$('.lolAccountButton').off('click').on('click', function() {
  getSummoner.call(this, false);
});

function getSummoner(isBrowser) {
  let summID = $(this).attr("id");
  let region;
  if (isBrowser) {
    region = $('#summoner'+summID+'Region').text();
  }else {
    let divided = summID.split("@&@");
    summID = divided[0];
    region = divided[1];
  }
  ipcRenderer.send("is-logged");
  ipcRenderer.on("is-logged-reply", (event, reply) => {
    if (reply) {
      ipcRenderer.send("get-uid");
      ipcRenderer.on("get-uid-reply", (event, uid) => {
        $('body').removeClass('loaded').css('overflow', 'hidden');
        $('#page').html("");
        $('#entry-title').removeClass("disabled");
        ipcRenderer.send("change-html", "https://riftstatistics.ddns.net/page/htmlRequests/summonerPage/" + summID + "/" + region + "/" + reply + "/" + uid);
      });
    } else {
      $('body').removeClass('loaded').css('overflow', 'hidden');
      $('#page').html("");
      $('#entry-title').removeClass("disabled");
      ipcRenderer.send("change-html", "https://riftstatistics.ddns.net/page/htmlRequests/summonerPage/" + summID + "/" + region + "/" + reply + "/null");
    }
  });
}

$("#soloqButton").off('click').on("click", function(event) {
  $(".eloInfoFlex").removeClass("active");
  $(".eloInfoSolo").removeClass("disabled");
  $(".flexqButton").removeClass("active");
  $(".soloqButton").removeClass("disabled");
});

$("#flexqButton").off('click').on("click", function(event) {
  $(".eloInfoFlex").addClass("active");
  $(".eloInfoSolo").addClass("disabled");
  $(".flexqButton").addClass("active");
  $(".soloqButton").addClass("disabled");
});

$('#accountLink').off('click').on('click', function(event) {
  console.log("clicked")
  let accountLink = $('#accountLink');
  let toastmssg;
  let toasttype = "question";
  let confirmText = "Link";
  let toastconfirm = "link";
  let lolData = $('.profileInfo').attr("id");
  let lolDataId = lolData.split("@&@")[0];

  ipcRenderer.send("is-logged");
  ipcRenderer.on("is-logged-reply", (event, logged) => {
    if (logged) {
      if (accountLink.children().last().text() === "this account is not linked yet") {
        if(logged) {
          toastmssg = "This LoL account is not linked to a RiftStatistics account yet. Would you like to link it to your account?"
        }
      } else if (accountLink.children().last().text() === "this account is linked to your account") {
        toastmssg = "This LoL account is already linked to your RiftStatistics account. Would you like to unlink it from your account?"
        toastconfirm = "unlink";
        confirmText = "Unlink";
      } else if (accountLink.children().last().text() === "you already have a LoL account linked to your account") {
        toastmssg = "You already have a LoL account linked to your RiftStatistics account. You need to unlink it first before linking a new one."
        toasttype = "error";
      }else if (accountLink.children().last().text() === "this account is already linked to an account") {
        toastmssg = "This LoL account is already linked to a RiftStatistics account. You cannot link it to your account."
        toasttype = "error";
      }

    }else if (accountLink.children().last().text() === "this account is already linked to an account") {
      confirmText = "Unlink";
      toastmssg = "You must be logged in to link a LoL account to your RiftStatistics account."
      toasttype = "error";
    }else if (accountLink.children().last().text() === "this account is not linked yet") {
      toastmssg = "You must be logged in to link a LoL account to your RiftStatistics account."
      toasttype = "error";
    }

    const Toast = Swaldef.mixin({
      position: 'top',
      confirmButtonText: confirmText,
      allowOutsideClick: false,
      showCancelButton: true,
      cancelButtonText: 'Cancel',
      color: '#FFFFFF',
      width: '80%',
      background: 'rgba(51, 51, 106, 1)',
      showClass: {
        popup: 'animate__animated animate__headShake'
      },
      hideClass: {
        popup: 'animate__animated animate__fadeOutUpBig'
      },
    })

    if (toasttype === "question") {
      Toast.fire({
        icon: 'info',
        text: toastmssg,
      }).then((result) => {
        if (result.isConfirmed) {
          if (toastconfirm === "link") {
            let user = $('#username').text();
            let xhr = new XMLHttpRequest();
            xhr.open('GET', `https://riftstatistics.ddns.net/page/linkAccount/${user}/${lolData}`, false);
            xhr.onload = function () {
              if (xhr.readyState === 4 && xhr.status === 200) {
                ipcRenderer.send("lol-account-change", "This LoL account has been linked to your RiftStatistics account");
              }else {
                Toast.fire({
                  icon: 'error',
                  text: "There was an error.",
                  showCancelButton: false,
                  confirmButtonText: 'Ok',
                })
              }
            }
            xhr.send();
          }else {
            let user = $('#username').text();
            let xhr = new XMLHttpRequest();
            xhr.open('GET', `https://riftstatistics.ddns.net/page/unlinkAccount/${user}/${lolDataId}`, false);
            xhr.onload = function () {
              if (xhr.readyState === 4 && xhr.status === 200) {
                ipcRenderer.send("lol-account-change", "This LoL account has been unlinked from your RiftStatistics account");
              }else {
                Toast.fire({
                  icon: 'error',
                  text: "There was an error.",
                  showCancelButton: false,
                  confirmButtonText: 'Ok',
                })
              }
            }
            xhr.send();
          }
        }
      });
    } else {
      Toast.fire({
        icon: 'error',
        text: toastmssg,
        showCancelButton: false,
        confirmButtonText: 'Ok',
      })
    }
  });
});

$(document).ready(function() {
  const itemListContainer = $('#itemListContainer');
  const itemImages = $('.ItemObject');
  const itemTooltips = $('.itemTooltip');

  itemImages.off('mouseleave');
  itemImages.mouseleave(function() {
    const tooltip = $(this).next();
    tooltip.css('display', 'none');
  });
  itemImages.off('mouseenter');
  itemImages.mouseenter(function() {
    const tooltip = $(this).next();
    tooltip.css('display', 'flex');
  });

  itemImages.off('hover');
  itemImages.hover(function(e) {
    const itemImage = $(this);
    const itemImageRect = itemImage[0].getBoundingClientRect();
    const tooltip = $(itemImage.next());
    const tooltipRect = tooltip[0].getBoundingClientRect();
    const containerRect = itemListContainer[0].getBoundingClientRect();
    const itemImageXl = itemImage.offset().left - containerRect.left + itemImageRect.width;
    const itemImageYt = itemImage.offset().top - containerRect.top - itemImageRect.height + 10 - window.scrollY;
    const tooltipWidth = tooltip.outerWidth();
    const tooltipHeight = tooltip.outerHeight();

    let tooltipLeft = itemImageXl;
    let tooltipTop = itemImageYt;

    const spaceBelow = window.innerHeight - (itemImageYt - scrollY + 40);
    if (spaceBelow < tooltipRect.height+297) {
      tooltipTop = itemImageYt - tooltipRect.height - 15;
    }

    if (tooltipLeft + tooltipWidth > window.innerWidth - 240) {
      tooltipLeft = itemImage.offset().left - containerRect.left - tooltipWidth - 10;
    }

    tooltip.css({
      left: tooltipLeft + 'px',
      top: tooltipTop + 'px'
    });
  });
});

// ******************************************************
// Button actions scripts
// ******************************************************

$('#Login-button').off('click').on('click', function(event) {
  const username = $('#logUsername').val();
  const password = $('#logPassword').val();
  const remember = $('#remember').is(':checked');
  let hashedPassword = SHA256(password).toString();
  getLoginPetition(username, hashedPassword, remember);
});

function getLoginPetition(username, password, remember) {
  const Toast = Swaldef.mixin({
    toast: true,
    position: 'top',
    color: '#FFFFFF',
    background: 'rgba(11, 11, 35, 1)',
    showConfirmButton: false,
    timer: 3000,
    showClass: {
      popup: 'animate__animated animate__headShake'
    },
    hideClass: {
      popup: 'animate__animated animate__fadeOutUpBig'
    },
    timerProgressBar: true,
    didOpen: (toast) => {
      toast.addEventListener('mouseenter', Swaldef.stopTimer)
      toast.addEventListener('mouseleave', Swaldef.resumeTimer)
    }
  })


  const { ipcRenderer } = require('electron');
  const fs = require("fs");
  const xhr = new XMLHttpRequest();
  xhr.open('GET', `https://riftstatistics.ddns.net/page/users/actions/login/${username}@&@${password}`, false);
  xhr.send();
  let id = "";
  var ip;
  let logCorrect = false;
  if (xhr.readyState === 4 && xhr.status === 200) {
    logCorrect = true;
    id = xhr.responseText;
    ipcRenderer.send('create-account-info', id, remember);
    if (remember) {
      ipcRenderer.send('get-ip');
      ipcRenderer.on('get-ip-reply', (event, ipreply) => {
        ip = ipreply;
      });
    }
  } else if (xhr.readyState === 4 && xhr.status === 404) {
    console.log("Error");

    Toast.fire({
      icon: 'error',
      title: "Incorrect username or password"
    })
  }else if (xhr.readyState === 4 && xhr.status === 403) {

    Toast.fire({
      timer: 8000,
      showConfirmButton: true,
      confirmButtonText: 'Ok',
      icon: 'error',
      title: "Please verify your email first",
      html: `Click <a href="https://riftstatistics.ddns.net/file/mail/verify/${username}" class="mailLink">here</a> to resend the verification email`
    })
  }
  if (logCorrect) {
    ipcRenderer.send('log-action-toast', 'Logged in successfully', `https://riftstatistics.ddns.net/page/htmlRequests/home/true/${id}`);
  }else {
    Toast.fire({
      icon: 'error',
      title: "Something went wrong."
    });
  }

}

$('#Signup-button').off('click').on('click', async function (event) {
  let username = $('#sigUsername').val();
  let password = $('#sigPassword').val();
  let email = $('#sigEmail').val();
  let created = false;
  let publicIP = await getIP();
  console.log(publicIP);
  let reply = SignUpActionValidator(username, password, email);
  let xhr;
  if (reply !== "") {
    const Toast = Swaldef.mixin({
      toast: true,
      position: 'top',
      color: '#FFFFFF',
      background: 'rgba(11, 11, 35, 1)',
      showConfirmButton: false,
      timer: 3000,
      timerProgressBar: true,
      showClass: {
        popup: 'animate__animated animate__headShake'
      },
      hideClass: {
        popup: 'animate__animated animate__fadeOutUpBig'
      },
      didOpen: (toast) => {
        toast.addEventListener('mouseenter', Swaldef.stopTimer)
        toast.addEventListener('mouseleave', Swaldef.resumeTimer)
      }
    })

    Toast.fire({
      icon: 'error',
      title: reply
    })
  } else {
    xhr = new XMLHttpRequest();
    let data = "";
    let hashedPassword = SHA256(password).toString();
    data = username + "@&@" + email + "@&@" + hashedPassword + "@&@" + publicIP;
    xhr.open('POST', `https://riftstatistics.ddns.net/page/users/actions/signup/${data}`, false);
    xhr.onload = function () {
      if (xhr.readyState === 4 && xhr.status === 201) {
        console.log("User created");
        const Toast = Swaldef.mixin({
          toast: true,
          color: '#FFFFFF',
          background: 'rgba(11, 11, 35, 1)',
          position: 'top',
          showConfirmButton: false,
          timer: 3000,
          timerProgressBar: true,
          showClass: {
            popup: 'animate__animated animate__fadeInDownBig'
          },
          hideClass: {
            popup: 'animate__animated animate__fadeOutUpBig'
          },
          didOpen: (toast) => {
            toast.addEventListener('mouseenter', Swaldef.stopTimer)
            toast.addEventListener('mouseleave', Swaldef.resumeTimer)
          }
        })

        let lineSign = $("#SignPassShow-activeShow");
        let signPassField = $("#sigPassword");
        $(".popupSign").removeClass("active");
        signPassField.attr("type", "password");
        lineSign.css("visibility", "visible");
        Toast.fire({
          tast: false,
          showClass: {
            popup: 'animate__animated animate__zoomIn'
          },
          hideClass: {
            popup: 'animate__animated animate__zoomOut'
          },
          width: '80%',
          position: 'center',
          showConfirmButton: true,
          confirmButtonText: 'Ok',
          timer: false,
          icon: 'success',
          title: 'Account created successfully',
          html: `Your account has been created successfully<br>The next step is to verify your email address, remember to check your spam folder. The mail can take up to 5 minutes to arrive, if you do not receive it <br>Click <a href="https://riftstatistics.ddns.net/file/mail/verify/${username}" class="mailLink">here</a> to resend the verification email.`
        })
        created = true;
      } else {
        let lineSign = $("#SignPassShow-activeShow");
        let signPassField = $("#sigPassword");
        signPassField.attr("type", "password");
        lineSign.css("visibility", "visible");
        signPassField.val("");
        const Toast = Swaldef.mixin({
          toast: true,
          position: 'top',
          color: '#FFFFFF',
          background: 'rgba(11, 11, 35, 1)',
          showConfirmButton: false,
          showClass: {
            popup: 'animate__animated animate__headShake'
          },
          hideClass: {
            popup: 'animate__animated animate__fadeOutUpBig'
          },
          timer: 3000,
          timerProgressBar: true,
          didOpen: (toast) => {
            toast.addEventListener('mouseenter', Swaldef.stopTimer)
            toast.addEventListener('mouseleave', Swaldef.resumeTimer)
          }
        })

        Toast.fire({
          icon: 'error',
          title: 'Error: ' + xhr.responseText
        })
      }
    }
    xhr.send();
    if (created) {
      xhr = new XMLHttpRequest();
      xhr.open('GET', `https://riftstatistics.ddns.net/file/mail/verify/${username}`, true);
      xhr.send();
    }
  }
});


function SignUpActionValidator(username, password, email) {
  const emailValidator = require('email-validator');

  if (isWithinLengthLimits(username, 4, 20)) {
    return "Username must be between 4 and 20 characters";
  }
  if (isWithinLengthLimits(password, 8, 20)) {
    return "Password must be between 8 and 20 characters";
  }
  if (containstInvalidCharactersP(password)) {
    return "Password cannot contain special characters such as spaces or these symbols: ^*¨'";
  }
  if (isWithinLengthLimits(email, 1, 50)) {
    return "Email cannot have more than 50 characters";
  }
  if (!emailValidator.validate(email)) {
    return "Invalid email address";
  }
  if (containsInvalidCharacters(username)) {
    return "Username can oly contain letters and numbers";
  }

  return "";
}

function isWithinLengthLimits(string, minLength, maxLength) {
  return string.length < minLength || string.length > maxLength;
}

function containstInvalidCharactersP(string) {
  return !string.match(/[^ ^*¨']+/);
}

function containsInvalidCharacters(string) {
  return string.match(/[^a-zA-Z0-9]+/);
}

$('#BrowserInput').off('click').on('input', function(event) {
  let xhr;
  let timeoutId;
  let loading;

  let username = $('#BrowserInput').val();

  if (username !== "") {
    let container = $('#browserListContainer');
    if (container.html() === "") {
      container.html("<div class=\"loader disabled\" id=\"loader\"></div>");
    }

    if (xhr) {
      xhr.abort();
    }
    if (timeoutId) {
      clearTimeout(timeoutId);
    }

    $('#loader').removeClass("disabled");
    $('#browserListContainer').addClass("loader");

    timeoutId = setTimeout(function () {
      if (username === $('#BrowserInput').val()) {
        xhr = new XMLHttpRequest();
        xhr.open('GET', `https://riftstatistics.ddns.net/page/browse/${username}`, true);
        xhr.onload = function () {
          if (xhr.readyState === 4 && xhr.status === 200) {
            if (username === $('#BrowserInput').val()) {
              $('#loader').addClass("disabled");
              $('#browserListContainer').removeClass("loader").html(xhr.responseText);
              loading = false;
            } else {
              console.log("Error");
              $('#loader').addClass("disabled");
              $('#browserListContainer').removeClass("loader").html("<div class=\"loader disabled\" id=\"loader\"></div>");
              loading = false;
            }
          }
        };
        xhr.send();
      }
    }, 1150);
  }else {
    $('#browserListContainer').html("");
  }
});

$('#BrowserInput').off('click').on('keydown', function(event) {
  let browserListContainer = $('#browserListContainer');
  let browserInput = $('#BrowserInput').val();

  if (event.which === 8) { // El usuario ha pulsado la tecla borrar
    if (browserListContainer.html() !== "<div class=\"loader disabled\" id=\"loader\"></div>") {
      browserListContainer.html(""); // Borramos el contenido
    }

    if (browserInput !== "") {
      if (!browserListContainer.hasClass("loader")) {
        browserListContainer.addClass("loader").html("<div class=\"loader disabled\" id=\"loader\"></div>");
        $('#loader').removeClass("disabled");
      }
    }else {
      $('#browserListContainer').html("");
    }
  }
});

// ******************************************************
// Logged utilities scripts
// ******************************************************

$('#show-logout').on('click', function(event) {
  $("#outPopup").toggleClass("active");
});

$('#close-btn').on('click', function(event) {
  $("#outPopup").removeClass("active");
});

$('#logout-cancel').on('click', function(event) {
  $("#outPopup").removeClass("active");
});

$('#logout-accept').on('click', function(event) {
  $("#outPopup").removeClass("active");
  ipcRenderer.send("logout-from-account");
  ipcRenderer.on("logout-from-account-reply", (event, arg) => {
    if(arg === true) {
      ipcRenderer.send('log-action-toast', 'logged out from your account' ,`https://riftstatistics.ddns.net/page/htmlRequests/home/false/null`);
    } else {
      console.log("Error");
    }
  });
});

$('#profileImage').on('click', function(event) {
  $("#file-input").click();
});

function previewFile(){
  var file = $('#file-input').prop('files')[0];
  var img = $('#profileImage').get(0);

  if(file){
    var reader = new FileReader();
    reader.onload = function(){
      img.src = reader.result;
      console.log(reader.result);
    }

    reader.readAsDataURL(file);

    const reader2 = new FileReader();
    reader2.addEventListener("load", function () {
      const base64Image = btoa(reader2.result);
      const imgNormal = Buffer.from(base64Image, "base64");

      const xhr = new XMLHttpRequest();
      ipcRenderer.send("get-uid");
      ipcRenderer.on("get-uid-reply", (event, uid) => {
        xhr.open("POST", `https://riftstatistics.ddns.net/page/users/actions/profileimgupdt/${uid}`);
        xhr.send(imgNormal);
      });
    });

    reader2.readAsBinaryString(file)
  }
}

function getIP() {
  return new Promise((resolve, reject) => {
    ipcRenderer.send('get-ip');

    ipcRenderer.once('get-ip-reply', (event, ipreply) => {
      resolve(ipreply);
    });

    ipcRenderer.once('get-ip-error', (event, error) => {
      reject(error);
    });
  });
}

