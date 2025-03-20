


document.getElementById("toggle-password").addEventListener("click", function() {
    
    var passwordField = document.getElementById("senha");
    var eyeIcon = document.getElementById("toggle-password");


    if (passwordField.type === "password") {
        passwordField.type = "text";
        eyeIcon.src = "img/eye-open.png"; 
    } else {
        passwordField.type = "password";
        eyeIcon.src = "img/eye-closed.png"; 
    }
    
});


