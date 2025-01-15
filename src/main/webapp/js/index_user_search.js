let isProcessing = false;
let debounceTimeout = null;



async function filterUsers(searchBox, userAddList, searchHandler) {
    const searchInput = document.getElementById(searchBox);
    const userList = document.getElementById(userAddList);
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
                    userDiv.addEventListener("click", () => searchHandler(userDiv));
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

async function add_chat(user, need_user_status = true, is_group = false) {
    let chatContainer = document.getElementById('chat-container');

    let name = user.querySelector('.font-medium').innerText;
    let status = 0;
    if (need_user_status) {
        status = user.querySelector('.status').classList[4].split('-')[1];
    }

    for (let i = 0; i < chatContainer.children.length; i++) {
        if (is_group && chatContainer.children[i].getAttribute("data-chat-id") == user.getAttribute("data-chat-id")) {
            make_chat_active(chatContainer.children[i]);
            return;
        }
    }


    let chat = document.createElement('div');

    let chat_id = user.getAttribute("data-chat-id");

    chat.setAttribute("data-chat-id", chat_id);



    chat.classList = 'flex-child overflow-hidden flex flex-col justify-between border rounded-lg h-full sm:w-full md:w-1/3 lg:w-1/4 ';

    let IncomingMessageTemplate = (message, sentTime, sender) => {
        return `<div class="flex items-start gap-2.5 mb-2">
                            <div
                                class="prof h-[30px] w-[30px] border rounded-full bg-white flex items-center justify-center">
                                <img src="assets/logo/logo-light-white.png" alt="logo" class="h-[15px] w-[15px]">
                            </div>
                            
                            <div
                                class="flex flex-col w-full incoming border max-w-[320px] leading-1.5 px-4 py-2 border-gray-200 bg-gray-100 rounded-e-xl rounded-es-xl">
                                ${is_group ?
                                    `<div class="flex justify-end text-primary mb-1">
                                        <span class="text-xs  self-end">${sender}</span>
                                    </div>` : "" }
                                <p class="text-sm font-normal text-gray-900">${message}</p>
                                <div class="flex self-end text-gray-500 mt-1">
                                    <span class="text-xs  self-end">${sentTime}</span>
                                </div>

                            </div>
                        </div>`};

    let OutgoingMessageTemplate = (message, sentTime) => {
        return `<div class="flex items-start gap-2.5 flex-row-reverse mb-2">
                            <div
                                class="prof h-[30px] w-[30px] border rounded-full bg-white flex items-center justify-center">
                                <img src="assets/logo/logo-light-white.png" alt="logo" class="h-[15px] w-[15px]">
                            </div>
                            <div
                                class="flex flex-col w-full max-w-[320px] leading-1.5 p-4 border-gray-200 bg-primary rounded-s-xl rounded-ee-xl">
                                <p class="text-sm font-normal text-white">${message}</p>
                                <div class="flex items-center self-end text-white mt-1">
                                    <span class="text-xs self-end">${sentTime}</span>
                                    <div class="ml-2 dot h-[4px] w-[4px] bg-white rounded-full"></div>
                                    <div class="text-xs self-end ml-2">Sent</div>
                                </div>
                            </div>
                        </div>`};








    let chat_html = `
                    <div class="chat-section-header bg-primary h-[60px] flex items-center px-2 justify-between">
                        <div class="left flex items-center">
                            <div class="prof h-[45px] w-[45px] rounded-full bg-white flex items-center justify-center">
                                <img src="assets/logo/logo-light-white.png" alt="logo" class="h-[25px] w-[25px]">
                            </div>
                            <div class="name ml-2 text-white text-lg font-semibold">${name}</div>
                            ${need_user_status ? `<div class="status w-[8px] h-[8px] ml-2 border border-white rounded-full bg-${status == 0 ? "red" : "green"}-600"></div>` : ""}
                        </div>
                        <div class="right">
                            <button class="w-[30px] h-[30px] flex items-center justify-center text-white mr-1 rounded-lg" onclick="close_chat(this);">
                                <i class="fa-solid fa-times"></i>
                            </button>
                        </div>


                    </div>
                    <div class="chat-msgs flex-1 overflow-y-auto p-2">

                    <!--
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
                    -->

                        

                        <!-- Add more messages as needed -->
                    </div>
                    <div class="msg-box relative p-1 ">
                        <input type="text"
                            class="message-input text-sm w-full p-2 border border-primary outline-primary rounded-lg pl-2 focus:outline-primary focus:ring-2 focus:ring-primary transition duration-200"
                            placeholder="Type a message">
                        <i
                            class="fa-solid fa-paper-plane-top mr-1 cursor-pointer absolute right-3 top-1/2 transform -translate-y-1/2 text-primary message-send-btn"></i>

                    </div>`;

    chat.innerHTML = chat_html;

    let chat_msg = chat.querySelector('.chat-msgs');


    chat.querySelector('.message-send-btn').addEventListener('click', (e) => { sendMessage(e, chat); });

    chat.querySelector('.message-input').addEventListener('keypress', (e) => {
        if (e.key === 'Enter') {
            sendMessage(e, chat);
        }
    });

    chatContainer.insertBefore(chat, chatContainer.firstChild);
    make_chat_active(chat);

    let messages_html = "";

    let messages = await getChat(chat_id);

    let user_id = document.body.getAttribute("data-user-id");

    console.log(messages);

    for (const message of messages) {
        // let message_data = await getMessage(message.message_id);

        if (message.sender_id == user_id) {
            messages_html += OutgoingMessageTemplate(message.content, convertTime(message.created_at));
        } else {
            messages_html += IncomingMessageTemplate(message.content, convertTime(message.created_at), message.sender_name);
        }
    }

    chat_msg.innerHTML = messages_html;

    chat_msg.scrollTop = chat_msg.scrollHeight;



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

function isAlreadyPresent(conv_list, is_group, user_id) {
    for (let i = 0; i < conv_list.length; i++) {
        if (is_group && conv_list[i].getAttribute("data-chat-id") === user_id) {
            conv_list[i].click();
            return true;
        } else if (!is_group && conv_list[i].getAttribute("data-user-id") === user_id) {
            conv_list[i].click();
            return true;
        }
    }
    return false;
}

let chat_sockets = {};

function addSocket(chatId) {

    let protocol = window.location.protocol == "https:" ? "wss" : "ws";

    let socket = new WebSocket(`${protocol}://${window.location.host}/group/${chatId}/`);

    socket.onopen = () => {
        console.log("Connected to chat group: " + chatId);
        socket.send("Hello Group " + chatId);
    };

    socket.onmessage = (event) => {

        let message = JSON.parse(event.data);
        
        if(message.type == "message")
        {
            addIncomingMessage(chatId, message);
        } else if(message.type == "status")
        {
            let chat = document.querySelector(`[data-chat-id="${chatId}"]`);
            chat.querySelector('.status').classList = `status w-[8px] h-[8px] ml-2 rounded-full bg-${message.status == 0 ? "red" : "green"}-600`;
        }
    };

    socket.onclose = () => {
        console.log("Disconnected from chat group: " + chatId);
    };

    socket.onerror = (error) => {
        console.error("WebSocket error: ", error);
    };

    chat_sockets[chatId] = socket;
}


function add_chat_in_sidebar(user, chat_list, user_name, need_user_status, is_group = false, group_id = null) {
    let convs = document.querySelector(chat_list);
    let conv_list = convs.children;

    let user_status = 0;
    if (need_user_status) {
        user_status = user.querySelector('.status').classList[4].split('-')[1];
    }
    let user_conv = document.createElement('div');
    let chatId = user.getAttribute("data-chat-id");
    user_conv.setAttribute("data-chat-id", chatId);    

    addSocket(chatId);


    user_conv.classList = 'flex items-center space-x-3 p-2 hover:bg-gray-100 cursor-pointer border-l-4 border-transparent';
    user_conv.innerHTML = `
                    <div class="prof h-[18px] w-[18px] rounded-full bg-white border border-primary flex items-center justify-center">
                        <img src="assets/logo/logo-light-white.png" alt="logo" class="h-[10px] w-[10px]">
                    </div>
                    <span class="font-medium">${user_name}</span>
                    ${need_user_status ? `<div class="status w-[8px] h-[8px] ml-2 rounded-full bg-${user_status == 0 ? "red" : "green"}-600"></div>` : ""}

                `;

    user_conv.addEventListener('click', function () {
        make_sidebar_active(user_conv);
        add_chat(user_conv, need_user_status, is_group);
    });
    convs.insertBefore(user_conv, conv_list[0]);
    console.log(user_conv);
    user_conv.click();



}

async function openChat(userDiv) {
    closeSearchModal();
    const userId = userDiv.getAttribute("data-user-id");
    console.log("Opening chat with user ID:", userId);

    if (isAlreadyPresent(document.querySelector('.conv-item').children, false, userId)) return;

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

    userDiv.setAttribute("data-chat-id", chat.payLoadList[0]);



    add_chat_in_sidebar(userDiv, '.conv-item', userDiv.querySelector('.font-medium').innerText, true);
    // Add your code to open a chat with the user
}

async function fetch_chats(user_id) {
    let chatContainer = document.querySelector('.conv-item');
    chatContainer.innerHTML = "";
    let chats = await fetch(`/api/get-participations?user_id=${user_id}`);
    if (!chats.ok) {
        console.error("Error fetching chats");
        return;
    }

    let chat_list = await chats.json();
    chat_list.forEach(chat => {
        let chat_div = document.createElement('div');
        chat_div.classList = 'flex items-center space-x-3 p-2 hover:bg-gray-100 cursor-pointer border-l-4 border-transparent';
        chat_div.innerHTML = `
                    <div class="prof h-[18px] w-[18px] rounded-full bg-white border border-primary flex items-center justify-center">
                        <img src="assets/logo/logo-light-white.png" alt="logo" class="h-[10px] w-[10px]">
                    </div>
                    <span class="font-medium">${chat[2]}</span>
                    <div class="status w-[8px] h-[8px] ml-2 rounded-full bg-${chat[3] == 0 ? "red" : "green"}-600"></div>
                `;
        chat_div.setAttribute("data-user-id", chat[0]);
        chat_div.setAttribute("data-chat-id", chat[1]);
        chat_div.addEventListener('click', function () {
            make_sidebar_active(chat_div);
            add_chat(chat_div);
        });
        addSocket(chat[1]);
        chatContainer.appendChild(chat_div);
    });
}

async function fetch_groups(user_id) {

    let chatContainer = document.querySelector('.group-item');
    chatContainer.innerHTML = "";
    let chats = await fetch(`/api/get-groups?user_id=${user_id}`);
    if (!chats.ok) {
        console.error("Error fetching groups");
        return;
    }

    let chat_list = await chats.json();
    chat_list.forEach(chat => {
        let chat_div = document.createElement('div');
        chat_div.classList = 'flex items-center space-x-3 p-2 hover:bg-gray-100 cursor-pointer border-l-4 border-transparent';
        chat_div.innerHTML = `
                    <div class="prof h-[18px] w-[18px] rounded-full bg-white border border-primary flex items-center justify-center">
                        <img src="assets/logo/logo-light-white.png" alt="logo" class="h-[10px] w-[10px]">

                    </div>
                    <span class="font-medium">${chat[1]}</span>
                `;
        chat_div.setAttribute("data-chat-id", chat[0]);
        chat_div.addEventListener('click', function () {
            make_sidebar_active(chat_div);
            add_chat(chat_div, need_user_status = false, is_group = true);
        });
        addSocket(chat[0]);
        chatContainer.appendChild(chat_div);
    });
}




function closeSearchModal() {
    document.getElementById("searchInput").value = "";
    document.getElementById("userList").innerHTML = "";
    document.getElementById("search-modal").classList.remove("fixed");

}



// Attach event listener
document.getElementById("searchInput").addEventListener("input", () => filterUsers("searchInput", "userList", openChat));

document.getElementById("user-search-btn").addEventListener("click", function () {
    document.getElementById("search-modal").classList.add("fixed");
});

window.addEventListener('load', function () {
    fetch_chats(document.body.getAttribute("data-user-id"));
    fetch_groups(document.body.getAttribute("data-user-id"));
}
);


// Group Creation & Search

document.getElementById("create-grp-btn").addEventListener("click", function () {
    document.getElementById("group-modal").classList.add("fixed");
});

function closeGroupModal() {
    document.getElementById("group-modal").classList.remove("fixed");
}

function addUserToSelected(userDiv) {
    document.getElementById("no-members-text").style.display = "none";
    const userId = userDiv.getAttribute("data-user-id");

    console.log("Adding user to group:", userId);
    const selectedUsers = document.getElementById("selectedMembers");

    // Check if the user is already selected
    for (let i = 0; i < selectedUsers.children.length; i++) {
        if (selectedUsers.children[i].getAttribute("data-user-id") === userId) {
            return;
        }
    }
    const selectedUser = document.createElement("div");
    selectedUser.className =
        "flex items-center space-x-3 p-2 hover:bg-gray-100 rounded-lg cursor-pointer";
    selectedUser.innerHTML = `
        <div class="prof h-[35px] w-[35px] rounded-full bg-white border border-primary flex items-center justify-center">
            <img src="assets/logo/logo-light-white.png" alt="logo" class="h-[20px] w-[20px]">
        </div>
        <span class="font-medium">${userDiv.querySelector(".font-medium").innerText}</span>
    `;
    selectedUser.setAttribute("data-user-id", userId);
    selectedUser.addEventListener("click", () => {
        selectedUser.remove();
        if (selectedUsers.children.length === 1) {
            document.getElementById("no-members-text").style.display = "block";
        }

    });
    selectedUsers.appendChild(selectedUser);


}

document.getElementById("searchGrpInput").addEventListener("input", () => filterUsers("searchGrpInput", "groupList", addUserToSelected));

async function createGroup() {
    let groupName = document.getElementById("group-name").value.trim();
    if (groupName === "") {
        alert("Please enter a group name");
        return;
    }

    const selectedUsers = document.getElementById("selectedMembers");
    if (selectedUsers.children.length === 1) {
        alert("Please select at least one user to create a group");
        return;
    }

    const groupMembers = [];
    for (let i = 1; i < selectedUsers.children.length; i++) {
        groupMembers.push(selectedUsers.children[i].getAttribute("data-user-id"));
    }

    const params = new URLSearchParams();
    let user_id = document.body.getAttribute("data-user-id");
    params.append("user_id", user_id);
    params.append("members", groupMembers.join(","));
    params.append("group_name", groupName);
    const checkedValue = document.querySelector('input[name="privacy"]:checked')?.value;
    params.append("is_private", checkedValue == "private" ? true : false);

    console.log("Creating group with members:", groupMembers);

    const response = await fetch("/api/create-group", {
        method: "POST",
        body: params,
    });

    if (!response.ok) {
        console.error("Error creating group");
        return;
    }

    const group = await response.json();
    console.log("Group created:", group);

    let groupDatadiv = document.createElement('div');
    groupDatadiv.setAttribute("data-chat-id", group.payload);



    add_chat_in_sidebar(groupDatadiv, '.group-item', groupName, need_user_status = false, is_group = true);

    // Add your code to open the group chat
    closeGroupModal();
}


document.getElementById("create-btn").addEventListener("click", createGroup);


