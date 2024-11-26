let convs = document.querySelector('.conv-item');
let conv_list = convs.children;

for (let i = 0; i < conv_list.length; i++) {
    conv_list[i].addEventListener('click', function () {
        let active_conv = convs.querySelector('.active');
        if (active_conv) active_conv.classList.remove('active');
        this.classList.add('active');

        add_chat(this);
    });
    console.log(conv_list[i]);

}



function close_chat(chat_close_btn){
    chat_close_btn.parentElement.parentElement.parentElement.remove();
    

}
