


document.getElementById('toggle-senha').addEventListener('click', function () {
    var passwordField = document.getElementById('senha');
    var passwordIcon = document.getElementById('toggle-senha');
    
    if (passwordField.type === 'password') {
        passwordField.type = 'text';
        passwordIcon.src = 'img/eye-open.png'; 
    } else {
        passwordField.type = 'password';
        passwordIcon.src = 'img/eye-closed.png';  
    }
});


document.getElementById('toggle-confirmarsenha').addEventListener('click', function () {
    var passwordField = document.getElementById('confirmarsenha');
    var passwordIcon = document.getElementById('toggle-confirmarsenha');
    
    if (passwordField.type === 'password') {
        passwordField.type = 'text';
        passwordIcon.src = 'img/eye-open.png';  
    } else {
        passwordField.type = 'password';
        passwordIcon.src = 'img/eye-closed.png';  
    }
});
