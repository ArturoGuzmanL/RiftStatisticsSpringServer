
$('#LogPassShow').off('click').on('click', function () {
    let line = $('#LogPassShow-line');
    let input = $('#changePasswd1');
    if (input.attr('type') === 'password') {
        input.attr('type', 'text');
        line.css('visibility', 'hidden');
    }else {
        input.attr('type', 'password');
        line.css('visibility', 'visible');
    }
});

$('#LogPassShow2').off('click').on('click', function () {
    let line = $('#LogPassShow-line2');
    let input = $('#changePasswd2');
    if (input.attr('type') === 'password') {
        input.attr('type', 'text');
        line.css('visibility', 'hidden');
    }else {
        input.attr('type', 'password');
        line.css('visibility', 'visible');
    }
});

$('#changePasswdButton').off('click').on('click', function () {
    let pass1 = $('#changePasswd1').val();
    let pass2 = $('#changePasswd2').val();
    let pass1Enr = CryptoJS.SHA256(pass1).toString(CryptoJS.enc.Hex);
    let pass2Enr = CryptoJS.SHA256(pass2).toString(CryptoJS.enc.Hex);
    let userid = $('.newPswd').attr('id');
    let reply = passValidator(pass1, pass2);

    if (reply !== "OK") {
        const Toast = Swal.mixin({
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
                toast.addEventListener('mouseenter', Swal.stopTimer)
                toast.addEventListener('mouseleave', Swal.resumeTimer)
            }
        })

        Toast.fire({
            icon: 'error',
            title: reply
        })
        $('#changePasswd1').val("");
        $('#changePasswd2').val("");
    }else {
        let xhr = new XMLHttpRequest();
        let message = pass1Enr + "@&@"
        xhr.open("PUT", `https://riftstatistics.ddns.net/page/users/actions/update/${userid}/8`, false);
        xhr.setRequestHeader("Content-Type", "application/json");
        xhr.onreadystatechange = function () {
            if (xhr.readyState === 4 && xhr.status === 200) {
                const Toast = Swal.mixin({
                    toast: false,
                    position: 'top',
                    color: '#FFFFFF',
                    background: 'rgba(11, 11, 35, 1)',
                    showConfirmButton: true,
                    confirmButtonText: 'OK',
                    timer: 5000,
                    timerProgressBar: true,
                    showClass: {
                        popup: 'animate__animated animate__headShake'
                    },
                    hideClass: {
                        popup: 'animate__animated animate__fadeOutUpBig'
                    },
                    didOpen: (toast) => {
                        toast.addEventListener('mouseenter', Swal.stopTimer)
                        toast.addEventListener('mouseleave', Swal.resumeTimer)
                    }
                })

                Toast.fire({
                    icon: 'success',
                    title: 'Password changed successfully. You can close this window now.'
                })
                $('#changePasswd1').val("");
                $('#changePasswd2').val("");
            }else {
                const Toast = Swal.mixin({
                    toast: false,
                    position: 'top',
                    color: '#FFFFFF',
                    background: 'rgba(11, 11, 35, 1)',
                    showConfirmButton: true,
                    confirmButtonText: 'OK',
                    timer: 5000,
                    timerProgressBar: true,
                    showClass: {
                        popup: 'animate__animated animate__headShake'
                    },
                    hideClass: {
                        popup: 'animate__animated animate__fadeOutUpBig'
                    },
                    didOpen: (toast) => {
                        toast.addEventListener('mouseenter', Swal.stopTimer)
                        toast.addEventListener('mouseleave', Swal.resumeTimer)
                    }
                })

                Toast.fire({
                    icon: 'error',
                    title: 'Something went wrong. Please try again.'
                })
                $('#changePasswd1').val("");
                $('#changePasswd2').val("");
            }
        }
        xhr.send(message);
    }

});


function passValidator(password1, password2) {
    if (password1 !== password2) {
        return "Passwords do not match";
    }
    if (isWithinLengthLimits(password1, 8, 20)) {
        return "Password must be between 8 and 20 characters";
    }
    if (containstInvalidCharactersP(password1)) {
        return "Password cannot contain special characters such as spaces or these symbols: ^*¨'";
    }
    return "OK";
}

function isWithinLengthLimits(string, minLength, maxLength) {
    return string.length < minLength || string.length > maxLength;
}

function containstInvalidCharactersP(string) {
    return !string.match(/[^ ^*¨']+/);
}