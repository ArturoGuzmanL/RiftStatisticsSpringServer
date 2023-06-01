<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>RiftStatistics</title>
  <link rel="stylesheet" href="https://riftstatistics.ddns.net/file/css/style">
  <link rel="stylesheet" href="https://riftstatistics.ddns.net/file/css/loader">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/animate.css/4.1.1/animate.min.css"/>
  <script src="https://riftstatistics.ddns.net/file/js/Jquery"></script>
</head>
<body>

<div id="loader-content">

  <div id="loader-wrapper">
    <div id="" class="entry-header">
      <h1 id="entry-title" class="entry-title">Loading</h1>
    </div>
    <div id="pageLoader"></div>

    <div class="loader-section section-left"></div>
    <div class="loader-section section-right"></div>

  </div>

</div>

<div id="page" class="page">
  <div class="popup">
    <div class="close-btn noselection" id="close-btn-log">&times;</div>
    <div class="form">
      <h2>LogIn</h2>
      <div class="form_element">
        <label for="logUsername">Username</label>
        <input type="text" id="logUsername" placeholder="Username...">
      </div>
      <div class="form_element">
        <label for="logPassword">Password</label>
        <div class="form_password">
          <input type="password" id="logPassword" placeholder="password...">
          <div id="logPassShow">
            <svg id="LogPassShow" xmlns="http://www.w3.org/2000/svg" width="32" height="32" fill="currentColor" class="bi bi-eye" viewBox="0 0 16 16">
              <path d="M16 8s-3-5.5-8-5.5S0 8 0 8s3 5.5 8 5.5S16 8 16 8zM1.173 8a13.133 13.133 0 0 1 1.66-2.043C4.12 4.668 5.88 3.5 8 3.5c2.12 0 3.879 1.168 5.168 2.457A13.133 13.133 0 0 1 14.828 8c-.058.087-.122.183-.195.288-.335.48-.83 1.12-1.465 1.755C11.879 11.332 10.119 12.5 8 12.5c-2.12 0-3.879-1.168-5.168-2.457A13.134 13.134 0 0 1 1.172 8z"/>
              <path d="M8 5.5a2.5 2.5 0 1 0 0 5 2.5 2.5 0 0 0 0-5zM4.5 8a3.5 3.5 0 1 1 7 0 3.5 3.5 0 0 1-7 0z"/>
              <path id="LogPassShow-activeShow" d="M3.35 5.47c-.18.16-.353.322-.518.487A13.134 13.134 0 0 0 1.172 8l.195.288c.335.48.83 1.12 1.465 1.755C4.121 11.332 5.881 12.5 8 12.5c.716 0 1.39-.133 2.02-.36l.77.772A7.029 7.029 0 0 1 8 13.5C3 13.5 0 8 0 8s.939-1.721 2.641-3.238l.708.709zm10.296 8.884-12-12 .708-.708 12 12-.708.708z"/>
            </svg>
          </div>
        </div>
      </div>
      <div class="form_element">
        <label for="remember">Remember me</label>
        <input type="checkbox" id="remember">
      </div>
      <div class="form_element">
        <p id="logError">...</p>
      </div>
      <div class="form_element">
        <button type="button" id="Login-button">Sign in</button>
      </div>
    </div>
  </div>

  <div class="popupSign">
    <div class="close-btn noselection" id="close-btn-sign">&times;</div>
    <div class="form">
      <h2>SignUp</h2>
      <div class="form_element">
        <label for="sigUsername">Username</label>
        <input type="text" id="sigUsername" placeholder="Username...">
      </div>
      <div class="form_element">
        <label for="sigEmail">Email</label>
        <input type="email" id="sigEmail" placeholder="email...">
      </div>
      <div class="form_element">
        <label for="sigPassword">Password</label>
        <div class="form_password">
          <input type="password" id="sigPassword" placeholder="password...">
          <div id="signPassShow">
            <svg id="SignPassShow" xmlns="http://www.w3.org/2000/svg" width="32" height="32" fill="currentColor" class="bi bi-eye" viewBox="0 0 16 16">
              <path d="M16 8s-3-5.5-8-5.5S0 8 0 8s3 5.5 8 5.5S16 8 16 8zM1.173 8a13.133 13.133 0 0 1 1.66-2.043C4.12 4.668 5.88 3.5 8 3.5c2.12 0 3.879 1.168 5.168 2.457A13.133 13.133 0 0 1 14.828 8c-.058.087-.122.183-.195.288-.335.48-.83 1.12-1.465 1.755C11.879 11.332 10.119 12.5 8 12.5c-2.12 0-3.879-1.168-5.168-2.457A13.134 13.134 0 0 1 1.172 8z"/>
              <path d="M8 5.5a2.5 2.5 0 1 0 0 5 2.5 2.5 0 0 0 0-5zM4.5 8a3.5 3.5 0 1 1 7 0 3.5 3.5 0 0 1-7 0z"/>
              <path id="SignPassShow-activeShow" d="M3.35 5.47c-.18.16-.353.322-.518.487A13.134 13.134 0 0 0 1.172 8l.195.288c.335.48.83 1.12 1.465 1.755C4.121 11.332 5.881 12.5 8 12.5c.716 0 1.39-.133 2.02-.36l.77.772A7.029 7.029 0 0 1 8 13.5C3 13.5 0 8 0 8s.939-1.721 2.641-3.238l.708.709zm10.296 8.884-12-12 .708-.708 12 12-.708.708z"/>
            </svg>
          </div>
        </div>
      </div>
      <div class="form_element">
        <button type="button" id="Signup-button">Sign up</button>
      </div>
    </div>
  </div>

  <!-- Comienzo del header -->
  <div class="blur-toggle" id="blurrDiv">
    <header class="header_noBrow">

      <!-- Botones de cuenta y ventanas -->
      <div class="accountArea  noselection">
        <button type="button" class="accountButton" id="show-login">LogIn</button>
        <button type="button" class="accountButton" id="show-signup" >SignUp</button>
      </div>
    </header>
    <!-- Fin del header-->
  </div>

  <!-- Comienzo del sidebar -->
  <div class="sidebar noselection" id="sidebar">
    <div class="sidebar-top">
      <div class="sidebar-logo">
        <img src="https://riftstatistics.ddns.net/file/assets/logo/RiftStatisticsHorizontal.png" width="200" height="54.09" alt="RiftStatistics">
      </div>
      <svg id="sidebar-button" xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-list" viewBox="0 0 16 16">
        <path fill-rule="evenodd" d="M2.5 12a.5.5 0 0 1 .5-.5h10a.5.5 0 0 1 0 1H3a.5.5 0 0 1-.5-.5zm0-4a.5.5 0 0 1 .5-.5h10a.5.5 0 0 1 0 1H3a.5.5 0 0 1-.5-.5zm0-4a.5.5 0 0 1 .5-.5h10a.5.5 0 0 1 0 1H3a.5.5 0 0 1-.5-.5z"/>
      </svg>
    </div>
    <div id="interactiveSidebar">
      <div class="sidebar-user unloggedPhoto">
        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="white" class="bi bi-person-circle" viewBox="0 0 16 16">
          <path d="M11 6a3 3 0 1 1-6 0 3 3 0 0 1 6 0z"/>
          <path fill-rule="evenodd" d="M0 8a8 8 0 1 1 16 0A8 8 0 0 1 0 8zm8-7a7 7 0 0 0-5.468 11.37C3.242 11.226 4.805 10 8 10s4.757 1.225 5.468 2.37A7 7 0 0 0 8 1z"/>
        </svg>
        <div>
          <p class="bold">Unlogged</p>
        </div>
      </div>
      <ul class="sidebarList">
        <li>
          <a href="#" id="homePageButton">
            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="white" class="bi bi-house-fill" viewBox="0 0 16 16">
              <path d="M8.707 1.5a1 1 0 0 0-1.414 0L.646 8.146a.5.5 0 0 0 .708.708L8 2.207l6.646 6.647a.5.5 0 0 0 .708-.708L13 5.793V2.5a.5.5 0 0 0-.5-.5h-1a.5.5 0 0 0-.5.5v1.293L8.707 1.5Z"/>
              <path d="m8 3.293 6 6V13.5a1.5 1.5 0 0 1-1.5 1.5h-9A1.5 1.5 0 0 1 2 13.5V9.293l6-6Z"/>
            </svg>
            <span class="sidebar-nav-item">Home</span>
          </a>
        </li>
        <li>
          <a href="#" id="championsPageButton">
            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="white" class="bi bi-grid-fill" viewBox="0 0 16 16">
              <path d="M1 2.5A1.5 1.5 0 0 1 2.5 1h3A1.5 1.5 0 0 1 7 2.5v3A1.5 1.5 0 0 1 5.5 7h-3A1.5 1.5 0 0 1 1 5.5v-3zm8 0A1.5 1.5 0 0 1 10.5 1h3A1.5 1.5 0 0 1 15 2.5v3A1.5 1.5 0 0 1 13.5 7h-3A1.5 1.5 0 0 1 9 5.5v-3zm-8 8A1.5 1.5 0 0 1 2.5 9h3A1.5 1.5 0 0 1 7 10.5v3A1.5 1.5 0 0 1 5.5 15h-3A1.5 1.5 0 0 1 1 13.5v-3zm8 0A1.5 1.5 0 0 1 10.5 9h3a1.5 1.5 0 0 1 1.5 1.5v3a1.5 1.5 0 0 1-1.5 1.5h-3A1.5 1.5 0 0 1 9 13.5v-3z"/>
            </svg>
            <span class="sidebar-nav-item">Champions</span>
          </a>
        </li>
        <li>
          <a href="#" id="itemsPageButton">
            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="white" class="bi bi-box" viewBox="0 0 16 16">
              <path d="M8.186 1.113a.5.5 0 0 0-.372 0L1.846 3.5 8 5.961 14.154 3.5 8.186 1.113zM15 4.239l-6.5 2.6v7.922l6.5-2.6V4.24zM7.5 14.762V6.838L1 4.239v7.923l6.5 2.6zM7.443.184a1.5 1.5 0 0 1 1.114 0l7.129 2.852A.5.5 0 0 1 16 3.5v8.662a1 1 0 0 1-.629.928l-7.185 2.874a.5.5 0 0 1-.372 0L.63 13.09a1 1 0 0 1-.63-.928V3.5a.5.5 0 0 1 .314-.464L7.443.184z"/>
            </svg>
            <span class="sidebar-nav-item">Items</span>
          </a>
        </li>
      </ul>
    </div>
  </div>

  <!-- Fin del sidebar -->
  <div class="blur-toggle" id="blurrDiv2">
    <div class="main-content">
      <div class="main-browser noselection">
        <div class="empty-col empty-row"></div>
        <div class="empty-col empty-row"></div>
        <div class="main-logo">
          <img src="https://riftstatistics.ddns.net/file/assets/logo/RiftStatisticsVertical.png" width="275" height="248.31" alt="RiftStatistics" >
        </div>
        <div class="empty-col empty-row"></div>
        <div class="empty-col empty-row"></div>
        <div class="empty-col empty-row"></div>
        <div class="empty-col empty-row"></div>
        <div>
          <textarea id="mainSearchField" class="mainSearchField" placeholder="Search summoners..." readonly></textarea>
        </div>
      </div>
    </div>
  </div>

  <div class="popupBrowserWindow" id="popupBrowserWindow">
    <div class="popupBrowserInnerBrow">
      <label class="popupSearchLabel">
        <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="white" class="bi bi-search" viewBox="0 0 16 16">
          <path d="M11.742 10.344a6.5 6.5 0 1 0-1.397 1.398h-.001c.03.04.062.078.098.115l3.85 3.85a1 1 0 0 0 1.415-1.414l-3.85-3.85a1.007 1.007 0 0 0-.115-.1zM12 6.5a5.5 5.5 0 1 1-11 0 5.5 5.5 0 0 1 11 0z"/>
        </svg>
        <textarea id="BrowserInput" class="popupSearchField" placeholder="Search summoners..."></textarea>
        <svg id="clearSearch" xmlns="http://www.w3.org/2000/svg" width="32" height="32" fill="#C0C2CB" class="bi bi-x" viewBox="0 0 16 16">
          <path d="M4.646 4.646a.5.5 0 0 1 .708 0L8 7.293l2.646-2.647a.5.5 0 0 1 .708.708L8.707 8l2.647 2.646a.5.5 0 0 1-.708.708L8 8.707l-2.646 2.647a.5.5 0 0 1-.708-.708L7.293 8 4.646 5.354a.5.5 0 0 1 0-.708z"/>
        </svg>
      </label>
      <div class="closeBrowserBtn" id="closeBrowserBtn">
        <svg width="70" height="70" viewBox="-2.4 -2.4 28.80 28.80" fill="white" xmlns="http://www.w3.org/2000/svg" stroke="white" stroke-width="0.00024000000000000003" transform="rotate(0)">
          <g id="SVGRepo_bgCarrier" stroke-width="0" transform="translate(0,0), scale(1)"></g>
          <g id="SVGRepo_tracerCarrier" stroke-linecap="round" stroke-linejoin="round" stroke="white" stroke-width="1.104"></g>
          <g id="SVGRepo_iconCarrier">
            <path id="rectangle" opacity="0.4" d="M16.19 2H7.81C4.17 2 2 4.17 2 7.81V16.18C2 19.83 4.17 22 7.81 22H16.18C19.82 22 21.99 19.83 21.99 16.19V7.81C22 4.17 19.83 2 16.19 2Z" fill="#34373f"></path>
            <path d="M13.0594 12.0001L15.3594 9.70011C15.6494 9.41011 15.6494 8.93011 15.3594 8.64011C15.0694 8.35011 14.5894 8.35011 14.2994 8.64011L11.9994 10.9401L9.69937 8.64011C9.40937 8.35011 8.92937 8.35011 8.63938 8.64011C8.34938 8.93011 8.34938 9.41011 8.63938 9.70011L10.9394 12.0001L8.63938 14.3001C8.34938 14.5901 8.34938 15.0701 8.63938 15.3601C8.78938 15.5101 8.97937 15.5801 9.16937 15.5801C9.35937 15.5801 9.54937 15.5101 9.69937 15.3601L11.9994 13.0601L14.2994 15.3601C14.4494 15.5101 14.6394 15.5801 14.8294 15.5801C15.0194 15.5801 15.2094 15.5101 15.3594 15.3601C15.6494 15.0701 15.6494 14.5901 15.3594 14.3001L13.0594 12.0001Z" fill="#C0C2CB"></path>
          </g>
        </svg>
      </div>
    </div>
    <div class="popupBrowserContent" id="popupBrowserContent">
      <ul class="browserList" id="browserListContainer">
        <div class="loader disabled" id="loader"></div>
      </ul>
    </div>
  </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
<script type="module" src="https://riftstatistics.ddns.net/file/js/Utilities"></script>
</body>
</html>