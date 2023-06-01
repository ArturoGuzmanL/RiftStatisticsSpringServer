<!DOCTYPE html>
<html>
<head>

    <meta charset="utf-8">
    <meta http-equiv="x-ua-compatible" content="ie=edge">
    <title>Password change</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/animate.css/4.1.1/animate.min.css"/>
    <link rel="stylesheet" href="https://riftstatistics.ddns.net/file/css/accSett">
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    <script src="https://riftstatistics.ddns.net/file/js/Jquery"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/crypto-js/4.1.1/crypto-js.min.js" integrity="sha512-E8QSvWZ0eCLGk4km3hxSsNmGWbLtSCSUcewDQPQWZF6pEU8GlT8a5fF32wOl1i8ftdMhssTrF/OhyGWwonTcXA==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
    <style>
        /**
                 * Google webfonts. Recommended to include the .woff version for cross-client compatibility.
                 */
        @media screen {
            @font-face {
                font-family: 'Source Sans Pro';
                font-style: normal;
                font-weight: 400;
                src: local('Source Sans Pro Regular'), local('SourceSansPro-Regular'), url(https://fonts.gstatic.com/s/sourcesanspro/v10/ODelI1aHBYDBqgeIAH2zlBM0YzuT7MdOe03otPbuUS0.woff) format('woff');
            }

            @font-face {
                font-family: 'Source Sans Pro';
                font-style: normal;
                font-weight: 700;
                src: local('Source Sans Pro Bold'), local('SourceSansPro-Bold'), url(https://fonts.gstatic.com/s/sourcesanspro/v10/toadOcfmlt9b38dHJxOBGFkQc6VGVFSmCnC_l7QZG60.woff) format('woff');
            }
        }

        /**
         * Avoid browser level font resizing.
         * 1. Windows Mobile
         * 2. iOS / OSX
         */
        body,
        table,
        td,
        a {
            -ms-text-size-adjust: 100%; /* 1 */
            -webkit-text-size-adjust: 100%; /* 2 */
        }

        /**
         * Remove extra space added to tables and cells in Outlook.
         */
        table,
        td {
            mso-table-rspace: 0pt;
            mso-table-lspace: 0pt;
        }

        /**
         * Better fluid images in Internet Explorer.
         */
        img {
            -ms-interpolation-mode: bicubic;
        }

        /**
         * Remove blue links for iOS devices.
         */
        a[x-apple-data-detectors] {
            font-family: inherit !important;
            font-size: inherit !important;
            font-weight: inherit !important;
            line-height: inherit !important;
            color: inherit !important;
            text-decoration: none !important;
        }

        /**
         * Fix centering issues in Android 4.4.
         */
        div[style*="margin: 16px 0;"] {
            margin: 0 !important;
        }

        body {
            background: #0a0c23;
            width: 100% !important;
            height: 100% !important;
            padding: 0 !important;
            margin: 0 !important;
        }

        /**
         * Collapse table borders to avoid space between cells.
         */
        table {
            border-collapse: collapse !important;
        }

        a {
            color: #1a82e2;
        }

        img {
            height: auto;
            line-height: 100%;
            text-decoration: none;
            border: 0;
            outline: none;
        }

        #changePasswd2,
        #changePasswd1 {
            margin-top: 1rem;
            position: relative;
            outline: transparent;
            border-top: transparent;
            border-left: transparent;
            border-right: transparent;
            border-bottom: 2px solid #6eb1f8;
            background: transparent;
            padding: 0.25rem 0.5rem 0.25rem 0.5rem;
            color: #bcd0f7;
            font-size: 1rem;
            -webkit-border-radius: 2px;
            -moz-border-radius: 2px;
            border-radius: 2px;
            transition: 0.3s;
            width: 100%;
        }

        #changePasswd2:hover,
        #changePasswd1:hover {
            border-bottom: 2px solid #3594ff;
            transition: 0.3s;
        }

        .form-control ~ .focus-border:before,
        .form-control ~ .focus-border:after{content: ""; position: absolute; top: 0; left: 0; width: 0; height: 2px; background-color: #3399FF; transition: 0.3s;}
        .form-control ~ .focus-border:after{top: auto; bottom: 0; left: auto; right: 0;}
        .form-control ~ .focus-border i:before,
        .form-control ~ .focus-border i:after{content: ""; position: absolute; top: 0; left: 0; width: 2px; height: 0; background-color: #3399FF; transition: 0.4s;}
        .form-control ~ .focus-border i:after{left: auto; right: 0; top: auto; bottom: 0;}
        .form-control:focus ~ .focus-border:before,
        .form-control:focus ~ .focus-border:after{width: 100%; transition: 0.3s;}
        .form-control:focus ~ .focus-border i:before,
        .form-control:focus ~ .focus-border i:after{height: 100%; transition: 0.4s;}

        .effect-8 {
            padding: 7px 14px 9px;
            transition: 0.4s;
        }

        .button {
            background: #007bff;
            border-color: #007bff;
            border-style: none;
            color: #FFF;
            text-align: center;
            min-width: 5rem;
            height: 2rem;
            border-radius: 6px;
            transition: 0.3s;
            cursor: pointer;
            margin-right: 1rem;
        }

        .button:hover {
            background: #0069d9;
            border-color: #0062cc;
            transition: 0.3s;
        }
    </style>
</head>
<body>

<!-- start body -->
<table style="border: 0; width: 100%;">

    <!-- start logo -->
    <tr>
        <td style="display: flex;
    justify-content: center;
    background: #0a0c23;
    height: 100%;">
            <!--[if (gte mso 9)|(IE)]>
            <table style="align-content: center;
    border: 0;
    width: 60px;">
                <tr>
                    <td style="align-content: start;
    justify-content: center;
    width: 600px;">
            <![endif]-->
            <table style="border: 0;
    width: 100%;
    max-width: 600px;">
                <tr>
                    <td style="align-content: start;
    justify-content: center;
    padding: 36px 24px;">
                        <a href="" target="_self">
                            <img style="display: block;
    width: 48px;
    max-width: 96px;
    min-width: 96px;" src="https://riftstatistics.ddns.net/file/assets/logo/RiftStatisticsHorizontal.png" alt="Logo" width="48">
                        </a>
                    </td>
                </tr>
            </table>
            <!--[if (gte mso 9)|(IE)]>
            </td>
            </tr>
            </table>
            <![endif]-->
        </td>
    </tr>
    <!-- end logo -->

    <!-- start hero -->
    <tr>
        <td style="justify-content: center;
    display: flex;
    background: #0a0c23;">
            <!--[if (gte mso 9)|(IE)]>
            <table style="align-items: center;
    display: flex;
    border: 0;
    width: 600px;">
                <tr>
                    <td style="align-content: start;
    justify-content: center;
    width: 600px;">
            <![endif]-->
            <table style="border: 0;
    width: 100%;
    max-width: 600px;">
                <tr>
                    <td style="background: #191c48;
    padding: 15px 0 15px;
    font-family: 'Source Sans Pro', Helvetica, Arial, sans-serif;">
                        <h1 style="margin: 0;
    font-size: 32px;
    font-weight: 700;
    letter-spacing: -1px;
    line-height: 48px;
    text-align: center;
    color: #fff;" class="newPswd" id="${userID}">Write your new password</h1>
                    </td>
                </tr>
            </table>
            <!--[if (gte mso 9)|(IE)]>
            </td>
            </tr>
            </table>
            <![endif]-->
        </td>
    </tr>
    <!-- end hero -->

    <!-- start copy block -->
    <tr>
        <td style="display: flex;
    justify-content: center;
    background: #0a0c23;
    height: 100%;">
            <!--[if (gte mso 9)|(IE)]>
            <table style="align-content: center;
    border: 0;
    width: 60px;">
                <tr>
                    <td style="align-content: start;
    justify-content: center;
    width: 600px;">
            <![endif]-->
            <table style="border: 0;
    width: 100%;
    max-width: 600px;">

                <tr>
                    <td style="align-items: start;
    display: flex;
    color: #fff;
    padding: 24px;
    font-family: 'Source Sans Pro', Helvetica, Arial, sans-serif;
    font-size: 16px;
    line-height: 24px;
    border-top: 2px solid rgba(105, 105, 149, 1);
    border-right: 2px solid rgba(105, 105, 149, 1);
    border-left: 2px solid rgba(105, 105, 149, 1);">
                    </td>
                </tr>

                <tr>
                    <td style="display: flex;
    flex-direction: column;
    justify-content: center;
    border-right: 2px solid rgba(105, 105, 149, 1);
    border-left: 2px solid rgba(105, 105, 149, 1);
    background: #0a0c23;
    padding: 24px;
    font-family: 'Source Sans Pro', Helvetica, Arial, sans-serif;
    font-size: 16px;
    line-height: 24px;">
                        <div>
                            <label style="color: #FFF; font-size: 1.5rem" for="changePasswd1">Password</label>
                            <div class="form_password">
                                <input type="password" id="changePasswd1" class="effect-8" name="password" placeholder="Password" required>
                                <div id="logPassShow" class="logPassShow">
                                    <svg id="LogPassShow" xmlns="http://www.w3.org/2000/svg" width="28" height="28" fill="currentColor" class="bi bi-eye" viewBox="0 0 16 16">
                                        <path d="M16 8s-3-5.5-8-5.5S0 8 0 8s3 5.5 8 5.5S16 8 16 8zM1.173 8a13.133 13.133 0 0 1 1.66-2.043C4.12 4.668 5.88 3.5 8 3.5c2.12 0 3.879 1.168 5.168 2.457A13.133 13.133 0 0 1 14.828 8c-.058.087-.122.183-.195.288-.335.48-.83 1.12-1.465 1.755C11.879 11.332 10.119 12.5 8 12.5c-2.12 0-3.879-1.168-5.168-2.457A13.134 13.134 0 0 1 1.172 8z"/>
                                        <path d="M8 5.5a2.5 2.5 0 1 0 0 5 2.5 2.5 0 0 0 0-5zM4.5 8a3.5 3.5 0 1 1 7 0 3.5 3.5 0 0 1-7 0z"/>
                                        <path id="LogPassShow-line" d="M3.35 5.47c-.18.16-.353.322-.518.487A13.134 13.134 0 0 0 1.172 8l.195.288c.335.48.83 1.12 1.465 1.755C4.121 11.332 5.881 12.5 8 12.5c.716 0 1.39-.133 2.02-.36l.77.772A7.029 7.029 0 0 1 8 13.5C3 13.5 0 8 0 8s.939-1.721 2.641-3.238l.708.709zm10.296 8.884-12-12 .708-.708 12 12-.708.708z"/>
                                    </svg>
                                </div>
                            </div>
                        </div>
                        <div style="margin-top: 1.5rem">
                            <label style="color: #FFF; font-size: 1.5rem" for="changePasswd2">Repeat password</label>
                            <div class="form_password">
                                <input type="password" id="changePasswd2" class="effect-8" name="password" placeholder="Password" required>
                                <div id="logPassShow2" class="logPassShow">
                                    <svg id="LogPassShow2" xmlns="http://www.w3.org/2000/svg" width="28" height="28" fill="currentColor" class="bi bi-eye" viewBox="0 0 16 16">
                                        <path d="M16 8s-3-5.5-8-5.5S0 8 0 8s3 5.5 8 5.5S16 8 16 8zM1.173 8a13.133 13.133 0 0 1 1.66-2.043C4.12 4.668 5.88 3.5 8 3.5c2.12 0 3.879 1.168 5.168 2.457A13.133 13.133 0 0 1 14.828 8c-.058.087-.122.183-.195.288-.335.48-.83 1.12-1.465 1.755C11.879 11.332 10.119 12.5 8 12.5c-2.12 0-3.879-1.168-5.168-2.457A13.134 13.134 0 0 1 1.172 8z"/>
                                        <path d="M8 5.5a2.5 2.5 0 1 0 0 5 2.5 2.5 0 0 0 0-5zM4.5 8a3.5 3.5 0 1 1 7 0 3.5 3.5 0 0 1-7 0z"/>
                                        <path id="LogPassShow-line2" d="M3.35 5.47c-.18.16-.353.322-.518.487A13.134 13.134 0 0 0 1.172 8l.195.288c.335.48.83 1.12 1.465 1.755C4.121 11.332 5.881 12.5 8 12.5c.716 0 1.39-.133 2.02-.36l.77.772A7.029 7.029 0 0 1 8 13.5C3 13.5 0 8 0 8s.939-1.721 2.641-3.238l.708.709zm10.296 8.884-12-12 .708-.708 12 12-.708.708z"/>
                                    </svg>
                                </div>
                            </div>
                        </div>
                    </td>
                </tr>
                <!-- end copy -->

                <!-- start copy -->
                <tr>
                    <td style="display: flex;
    flex-direction: column;
    justify-content: center;
    border-right: 2px solid rgba(105, 105, 149, 1);
    border-left: 2px solid rgba(105, 105, 149, 1);
    border-bottom: 2px solid rgba(105, 105, 149, 1);
    background: #0a0c23;
    padding: 24px;
    font-family: 'Source Sans Pro', Helvetica, Arial, sans-serif;
    font-size: 16px;
    line-height: 24px;">
                        <button id="changePasswdButton" type="submit" style="background: #1a82e2;" class="button">Submit</button>
                    </td>
                </tr>
                <!-- end copy -->

            </table>
            <!--[if (gte mso 9)|(IE)]>
            </td>
            </tr>
            </table>
            <![endif]-->
        </td>
    </tr>
    <!-- end copy block -->

</table>
<!-- end body -->
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
<script src="https://riftstatistics.ddns.net/file/js/PassPage"></script>
</body>
</html>
