// let convs = document.querySelector('.conv-item');
// let conv_list = convs.children;

// for (let i = 0; i < conv_list.length; i++) {
//     conv_list[i].addEventListener('click', function () {
//         let active_conv = convs.querySelector('.active');
//         if (active_conv) active_conv.classList.remove('active');
//         this.classList.add('active');

//         add_chat(this);
//     });
//     console.log(conv_list[i]);

// }



function close_chat(chat_close_btn, is_group = false) {
    

    let side_bar_conv = document.querySelector('.active');
    let this_chat_user_id, side_bar_conv_user_id
    if(!is_group){
        this_chat_user_id = chat_close_btn.parentElement.parentElement.parentElement.getAttribute('data-user-id');
        side_bar_conv_user_id = side_bar_conv.getAttribute('data-user-id');
    } else {
        this_chat_user_id = chat_close_btn.parentElement.parentElement.parentElement.getAttribute('data-group-id');
        side_bar_conv_user_id = side_bar_conv.getAttribute('data-group-id');
    }

    console.log(this_chat_user_id, side_bar_conv_user_id);

    if(this_chat_user_id == side_bar_conv_user_id){
        console.log(side_bar_conv);
        
        side_bar_conv.classList.remove('active');
    }
    

    chat_close_btn.parentElement.parentElement.parentElement.remove();

}
