let isProcessing = false;
let debounceTimeout = null;



async function filterUsers() {
    const searchInput = document.getElementById("searchInput");
    const userList = document.getElementById("userList");
    const searchQuery = searchInput.value.toLowerCase();

    // Clear any existing debounce timeout
    if (debounceTimeout) {
        clearTimeout(debounceTimeout);
    }

    // Clear existing users
    userList.innerHTML = "";

    // Skip processing for empty query
    if (searchQuery.trim() === "") {
        return;
    }

    // Prevent multiple simultaneous API calls
    if (isProcessing) {
        return;
    }

    // Debounce the API call
    debounceTimeout = setTimeout(async () => {
        isProcessing = true;

        try {
            // Fetch matching users from the API
            const response = await fetch(`/api/search-users?query=${encodeURIComponent(searchQuery)}`);
            if (!response.ok) {
                throw new Error("Error fetching users");
            }
            let users = await response.json();

            // Populate the user list
            if (users.length === 0) {
                userList.innerHTML = `<div class="text-gray-500 text-center">No users found</div>`;
            } else {
                users.forEach((user) => {
                    let this_user = document.body.getAttribute("data-user-id");
                    if (this_user == user[0]) return;
                    const userDiv = document.createElement("div");
                    userDiv.className =
                        "flex items-center space-x-3 p-2 hover:bg-gray-100 rounded-lg cursor-pointer";
                    userDiv.innerHTML = `
                        <div class="prof h-[35px] w-[35px] rounded-full bg-white border border-primary flex items-center justify-center">
                            <img src="assets/logo/logo-light-white.png" alt="logo" class="h-[20px] w-[20px]">
                        </div>
                        <span class="font-medium">${user[1]}</span>
                        <div class="status w-[8px] h-[8px] ml-2 rounded-full bg-${user[2] == 0 ? "red" : "green"}-600"></div>
                    `;
                    userDiv.setAttribute("data-user-id", user[0]);
                    userDiv.addEventListener("click", () => openChat(userDiv));
                    userList.appendChild(userDiv);
                });
            }
        } catch (error) {
            console.error("Error:", error);
            userList.innerHTML = `<div class="text-red-500 text-center">Failed to fetch users</div>`;
        } finally {
            isProcessing = false;
        }
    }, 300); // 300ms debounce delay
}

function add_chat(user) {
    let chatContainer = document.getElementById('chat-container');

    let name = user.querySelector('.font-medium').innerText;
    let status = user.querySelector('.status').classList[4].split('-')[1];

    for (let i = 0; i < chatContainer.children.length; i++) {
        if (chatContainer.children[i].getAttribute("data-user-id") == user.getAttribute("data-user-id")) {
            make_chat_active(chatContainer.children[i]);
            return;
        }
    }


    let chat = document.createElement('div');

    chat.setAttribute("data-user-id", user.getAttribute("data-user-id"));

    chat.classList = 'flex-child overflow-hidden flex flex-col justify-between border rounded-lg h-full sm:w-full md:w-1/3 lg:w-1/4 ';



    let chat_html = `
                    <div class="chat-section-header bg-primary h-[60px] flex items-center px-2 justify-between">
                        <div class="left flex items-center">
                            <div class="prof h-[45px] w-[45px] rounded-full bg-white flex items-center justify-center">
                                <img src="assets/logo/logo-light-white.png" alt="logo" class="h-[25px] w-[25px]">
                            </div>
                            <div class="name ml-2 text-white text-lg font-semibold">${name}</div>
                            <div class="status w-[8px] h-[8px] ml-2 border border-white rounded-full bg-${status == 0 ? "red" : "green"}-600"></div>
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
                                class="flex flex-col w-full incoming border max-w-[320px] leading-1.5 p-4 border-gray-200 bg-gray-100 rounded-e-xl rounded-es-xl">
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
    make_chat_active(chat);

    chat.addEventListener('click', function () {
        make_chat_active(chat);
        make_sidebar_active(user);
    });


}

function make_sidebar_active(chat) {
    let active_chat = document.querySelector('.active');
    if (active_chat) active_chat.classList.remove('active');
    chat.classList.add('active');
}

function make_chat_active(chat) {
    let chatContainer = document.getElementById('chat-container');
    let active_chat = chatContainer.querySelector('.active-chat');
    if (active_chat) active_chat.classList.remove('active-chat');
    chat.classList.add('active-chat');
}

function add_chat_in_sidebar(user) {
    let convs = document.querySelector('.conv-item');
    let conv_list = convs.children;
    let user_id = user.getAttribute("data-user-id");
    let user_name = user.querySelector('.font-medium').innerText;
    let user_status = user.querySelector('.status').classList[4].split('-')[1];
    let user_conv = document.createElement('div');
    user_conv.classList = 'flex items-center space-x-3 p-2 hover:bg-gray-100 cursor-pointer border-l-4 border-transparent';
    user_conv.innerHTML = `
                    <div class="prof h-[18px] w-[18px] rounded-full bg-white border border-primary flex items-center justify-center">
                        <img src="assets/logo/logo-light-white.png" alt="logo" class="h-[10px] w-[10px]">
                    </div>
                    <span class="font-medium">${user_name}</span>
                    <div class="status w-[8px] h-[8px] ml-2 rounded-full bg-${user_status == 0 ? "red" : "green"}-600"></div>
                `;
    user_conv.setAttribute("data-user-id", user_id);
    user_conv.addEventListener('click', function () {
        make_sidebar_active(user_conv);
        add_chat(user_conv);
    });
    for (let i = 0; i < conv_list.length; i++) {
        if (conv_list[i].getAttribute("data-user-id") == user_id) {
            conv_list[i].click();
            return;
        }
    }
    convs.insertBefore(user_conv, conv_list[0]);
    console.log(user_conv);
    user_conv.click();


}

async function openChat(userDiv) {
    closeModal();
    const userId = userDiv.getAttribute("data-user-id");
    console.log("Opening chat with user ID:", userId);

    add_chat_in_sidebar(userDiv);

    // Create a new chat with the user

    let params = new URLSearchParams();
    params.append('user_id', document.body.getAttribute("data-user-id"));
    params.append('receiver_id', userId);

    let create_chat = await fetch(`/api/create-chat`,
        {
            method: 'POST',
            body: params
        });
    
    if (!create_chat.ok) {
        console.error("Error creating chat");
        return;
    }

    let chat = await create_chat.json();

    console.log("Chat created:", chat);

    


    // Add your code to open a chat with the user
}

function closeModal() {
    document.getElementById("searchInput").value = "";
    document.getElementById("userList").innerHTML = "";
    document.getElementById("search-modal").classList.remove("fixed");

}

// Attach event listener
document.getElementById("searchInput").addEventListener("input", filterUsers);

document.getElementById("user-search-btn").addEventListener("click", function () {
    document.getElementById("search-modal").classList.add("fixed");
});
