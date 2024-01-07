
/**
 * 등록 버튼 클릭 후, 유효성 검사 실패 시 - 기존 값을 토대로 안내문구 재출력
 */
document.addEventListener("DOMContentLoaded", function() {
    initializePage();
});

function initializePage() {
    checkEmail();
}

/**
 * onkeyup - 비밀번호, 비밀번호 동일 체크
 */
function validatePassword() {
    let password = document.getElementById("password").value;
    let confirmPassword = document.getElementById("passwordConfirmation").value;
    let passwordCheck = document.getElementById("passwordCheck");

    if (password === "" || confirmPassword === "") {
        passwordCheck.innerHTML = "";
        return;
    }

    if (password === confirmPassword) {
        passwordCheck.style.color = "green";
        passwordCheck.innerHTML = "비밀번호 일치";
    } else {
        passwordCheck.style.color = "red";
        passwordCheck.innerHTML = "비밀번호가 일치하지 않습니다.";
    }
}

/**
 * onkeyup - DB 이메일 중복 체크
 */
function checkEmail() {
    let email = document.getElementById("email").value;
    let emailCheckMessage = document.getElementById("emailCheckMessage");

    if (email === ""){
        emailCheckMessage.innerHTML = "";
        return;
    }

    $.ajax({
        url: '/emailCheck?email=' + email,
        success: function(response) {
            if (response) {
                emailCheckMessage.style.color = "green";
                emailCheckMessage.innerHTML = "사용 가능한 이메일입니다.";
            } else {
                emailCheckMessage.style.color = "red";
                emailCheckMessage.innerHTML = "이미 사용 중인 이메일입니다.";
            }
        },
        error: function(error) {
            alert(error.responseJSON.data);
        }
    })
}

/**
 * 등록 버튼 - Server 전송 전, 유효성 체크
 */
function validateForm() {
    let password = document.getElementById("password").value;
    let confirmPassword = document.getElementById("passwordConfirmation").value;
    let emailCheckMessage = document.getElementById("emailCheckMessage").innerHTML;

    if (password !== confirmPassword || emailCheckMessage !== "사용 가능한 이메일입니다.") {
        alert("입력 정보를 확인해주세요.");
        return false;
    } else {
        return true;
    }
}


