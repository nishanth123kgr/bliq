import { toggle } from './utils.js';

toggle();



document.querySelectorAll('.pass-input').forEach((passwordInput, index) => {
    const passwordValidation = document.querySelectorAll('.valid-password')[index];

    passwordInput.addEventListener('focus', () => {
        passwordValidation.style.display = 'block';
    });

    passwordInput.addEventListener('blur', () => {
        passwordValidation.style.display = 'none';
    });

    passwordInput.addEventListener('input', () => validatePassword(passwordInput, passwordValidation));
});

function isPasswordValid(password) {
    const passwordPattern = /^(?=.*\d)(?=.*[a-zA-Z]).{8,}$/;
    return passwordPattern.test(password);
}

function validatePassword(passwordInput, passwordValidation) {
    console.log('Validating password');

    function updateErrors(passwordErrorItems, errors = [0, 0, 0]) {
        errors.forEach((error, index) => {
            const item = passwordErrorItems[index];
            if (error) {
                if (item.classList.contains('valid')) {
                    item.classList.replace('valid', 'invalid');
                } else {
                    item.classList.add('invalid');
                }
            } else {
                if (item.classList.contains('invalid')) {
                    item.classList.replace('invalid', 'valid');
                } else {
                    item.classList.add('valid');
                }
            }
        });
    }
    const passwordErrorItems = passwordValidation.children[0].children[0].children;
    
    const password = passwordInput.value;
    const passwordPattern = /^(?=.*\d)(?=.*[a-zA-Z]).{8,}$/;

    let errors = [0, 0, 0];

    if (passwordPattern.test(password)) {
        updateErrors(passwordErrorItems);
    } else {
        if (password.length < 8) errors[0] = 1;
        if (!/(?=.*\d)/.test(password)) errors[1] = 1;
        if (!/(?=.*[a-zA-Z])/.test(password)) errors[2] = 1;
        

        updateErrors(passwordErrorItems, errors);
    }

    return errors.every(error => error === 0);
}

document.querySelectorAll('.conf').forEach(item => {
    item.addEventListener('input', () => {
        let form = item.parentElement.parentElement;
        const password = form.querySelector('.pass-input').value;
        const confirm = item.value;
        const confirmValidation = form.querySelector('.conf-label');
        if(confirmValidation.classList.contains('d-none')){
            confirmValidation.classList.remove('d-none');
        }
        if (confirm.length === 0) {
            confirmValidation.classList.remove('valid');
            confirmValidation.classList.remove('invalid');
            return;
        }
        if (confirm === password) {
            if(!confirmValidation.classList.contains('mob')) {

                confirmValidation.classList.add('valid');
                confirmValidation.classList.remove('invalid');
            }

            confirmValidation.innerHTML = "Passwords Match!"
        } else {
            if(!confirmValidation.classList.contains('mob')) {
                confirmValidation.classList.add('invalid');
                confirmValidation.classList.remove('valid');
            }
            confirmValidation.innerHTML = "Passwords do not match"
        }
    });
})

document.querySelectorAll('.submit').forEach(submitButton => {
    submitButton.addEventListener('click', (event) => {
        const form = submitButton.parentElement;
        console.log(form)
        const passwordInputs = form.querySelectorAll('.pass-input');
        const passwordValidations = form.querySelectorAll('.valid-password');

        let valid = true;
        passwordInputs.forEach((passwordInput, index) => {
            const passwordValidation = passwordValidations[index];
            if (!validatePassword(passwordInput, passwordValidation)) {
                valid = false;
            }
        });

        if (!valid) {
            alert('Password should be at least 8 characters long and contain at least one letter and one number');
            event.preventDefault();
        }
        const urlEncodedData = new URLSearchParams();
        form.querySelectorAll('.form-control').forEach(input => {
            if(input.name === 'password' && !isPasswordValid(input.value)) {
                return;

            }
            if(input.name === 'email' && !/^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/.test(input.value)) {
                alert('Invalid email');

                return
            }

            console.log(input)
            console.log(input.value)
            if (!input.value) {
                alert('Please fill out all fields');
                valid = false;
            } else {
                urlEncodedData.append(input.name, input.value);
            }

        })
        if(!valid){
            return;
        }
        if(form.querySelector('.conf').value !== form.querySelector('.pass-input').value) {
            alert('Passwords do not match');
            return;
        }


        fetch('/api/signup', {
            method: 'POST',
            body: urlEncodedData,
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded', // Specify form data type
            }
        }).then(response => response.json().then(data => {
                if (data.status === 'success') {
                    const toastBootstrap = bootstrap.Toast.getOrCreateInstance(document.getElementById('success-toast'));
                    toastBootstrap.show();
                    setTimeout(() => {
                            window.location.href = '/login.jsp';
                        }
                        , 1000);


                } else {
                    const toastBootstrap = bootstrap.Toast.getOrCreateInstance(document.getElementById('error-toast'));
                    toastBootstrap.show();
                }
            })
        );
    });
})

