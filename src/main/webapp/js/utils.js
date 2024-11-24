function toggle(){
    document.querySelectorAll('.pass-toggle').forEach(toggler => {

        toggler.addEventListener('click', () => {
            console.log("clicked")

            if (toggler.classList.contains('fa-eye-slash')) {
                toggler.classList.replace('fa-eye-slash', 'fa-eye')
                toggler.previousSibling.type = "password"
            } else {
                toggler.classList.replace('fa-eye', 'fa-eye-slash')
                toggler.previousSibling.type = "text"

            }
        })

        console.log(toggler)
    })
}

export { toggle };