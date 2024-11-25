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

function add_chat(conv) {
    let chatContainer = document.getElementById('chat-container');

    let chat = document.createElement('div');

    chat.classList = 'flex-child overflow-hidden flex flex-col justify-between border rounded-lg h-full sm:w-full md:w-1/3 lg:w-1/4';



    let chat_html = `
                    <div class="chat-section-header bg-primary h-[60px] flex items-center px-2 justify-between">
                        <div class="left flex items-center">
                            <div class="prof h-[45px] w-[45px] rounded-full bg-white flex items-center justify-center">
                                <img src="assets/logo/logo-light-white.png" alt="logo" class="h-[25px] w-[25px]">
                            </div>
                            <div class="name ml-2 text-white text-lg font-semibold">John Doe</div>
                        </div>
                        <div class="right">
                            <button class="w-[30px] h-[30px] flex items-center justify-center text-white mr-1" onclick="close_chat(this);">
                                <i class="fa-solid fa-times"></i>
                            </button>
                        </div>


                    </div>
                    <div class="chat-msgs flex-1 overflow-y-auto p-2">
                        <!-- Incoming Message -->
                        <div class="flex items-start gap-2.5 mb-2">
                            <div
                                class="prof h-[30px] w-[30px] border rounded-full bg-white flex items-center justify-center">
                                <img src="assets/logo/logo-light-white.png" alt="logo" class="h-[15px] w-[15px]">
                            </div>
                            <div
                                class="flex flex-col w-full border max-w-[320px] leading-1.5 p-4 border-gray-200 bg-gray-100 rounded-e-xl rounded-es-xl">
                                <p class="text-sm font-normal text-gray-900">Hello! How are you today?</p>
                                <div class="flex self-end text-gray-500 mt-1">
                                    <span class="text-xs  self-end">12:45 PM</span>
                                </div>

                            </div>
                        </div>

                        <!-- Outgoing Message -->
                        <div class="flex items-start gap-2.5 flex-row-reverse mb-2">
                            <div
                                class="prof h-[30px] w-[30px] border rounded-full bg-white flex items-center justify-center">
                                <img src="assets/logo/logo-light-white.png" alt="logo" class="h-[15px] w-[15px]">
                            </div>
                            <div
                                class="flex flex-col w-full max-w-[320px] leading-1.5 p-4 border-gray-200 bg-primary rounded-s-xl rounded-ee-xl">
                                <p class="text-sm font-normal text-white">I'm doing great! Thanks for asking 😊</p>
                                <div class="flex items-center self-end text-white mt-1">
                                    <span class="text-xs self-end">12:45 PM</span>
                                    <div class="ml-2 dot h-[4px] w-[4px] bg-white rounded-full"></div>
                                    <div class="text-xs self-end ml-2">Sent</div>
                                </div>
                            </div>
                        </div>

                        <!-- Add more messages as needed -->
                    </div>
                    <div class="msg-box relative p-1 ">
                        <input type="text"
                            class="w-full p-2 border border-primary outline-primary rounded-lg pl-2 focus:outline-primary focus:ring-2 focus:ring-primary transition duration-200"
                            placeholder="Type a message">
                        <i
                            class="fa-solid fa-paper-plane-top mr-1 cursor-pointer absolute right-3 top-1/2 transform -translate-y-1/2 text-primary"></i>

                    </div>`;

    chat.innerHTML = chat_html;

    chatContainer.insertBefore(chat, chatContainer.firstChild);


}

function close_chat(chat_close_btn){
    chat_close_btn.parentElement.parentElement.parentElement.remove();
    

}
