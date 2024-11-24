import { toggle } from './utils.js';

toggle();

document.querySelectorAll('.submit').forEach(submitButton => {
    submitButton.addEventListener('click', (event) => {
        const form = submitButton.parentElement;
        console.log(form)
        const passwordInputs = form.querySelectorAll('.pass-input');

        let valid = true;

        const urlEncodedData = new URLSearchParams();
        form.querySelectorAll('.form-control').forEach(input => {
            if(input.name === 'email' && !/^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/.test(input.value)) {
                alert('Invalid email');

                return
            }
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



        fetch('/api/login', {
            method: 'POST',
            body: urlEncodedData,
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded', // Specify form data type
            }
        }).then(response => response.json().then(data => {
                if(data.status === 'success') {
                    window.location.href = '/index.jsp';
                } else {
                    alert(data.message);
                }
            })
        );
    });
})
