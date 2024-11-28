function convertTime(date_string){
    // 2024-11-28 14:29:54.0

    let timePart = date_string.split(' ')[1];

    let timeParts = timePart.split(':');

    let hour = timeParts[0];
    let minute = timeParts[1];
    let second = timeParts[2].split('.')[0];

    // Convert Time to 12 hour format
    let suffix = 'AM';
    if(hour > 12){
        hour = hour - 12;
        suffix = 'PM';
    }

    return `${hour}:${minute} ${suffix}`;
}


async function getMessage(messageId){
    let msgRequest = fetch('/api/get-message?message_id='+messageId);
    let msg = await msgRequest;
    let msgData = await msg.json();     

    return msgData;
}


async function sendMessage(event, chat){

    let chatId = chat.getAttribute('data-chat-id');
    let message = chat.querySelector('.message-input').value;
    chat.querySelector('.message-input').value = '';
    if (message.trim() === '') {
        return;
    }
    let data = new URLSearchParams();
    data.append('chat_id', chatId);
    data.append('message', message);
    data.append('user_id', document.body.getAttribute('data-user-id'));

    event.preventDefault();

    console.log('Sending message: ' + message + ' to chat: ' + chatId+ ' from user: ' + data.get('user_id'));

    let msgRequest = fetch('/api/create-message', {
        method: 'POST',
        body: data
    });

    let msgData = await (await msgRequest).json();

    console.log(msgData);
    

    let msgId = msgData.payload;

    let messageData = await getMessage(msgId);

    let OutgoingMessageTemplate =   `<div
                                class="prof h-[30px] w-[30px] border rounded-full bg-white flex items-center justify-center">
                                <img src="assets/logo/logo-light-white.png" alt="logo" class="h-[15px] w-[15px]">
                            </div>
                            <div
                                class="flex flex-col w-full max-w-[320px] leading-1.5 p-4 border-gray-200 bg-primary rounded-s-xl rounded-ee-xl">
                                <p class="text-sm font-normal text-white">${message}</p>
                                <div class="flex items-center self-end text-white mt-1">
                                    <span class="text-xs self-end">${convertTime(messageData.created_at)}</span>
                                    <div class="ml-2 dot h-[4px] w-[4px] bg-white rounded-full"></div>
                                    <div class="text-xs self-end ml-2">Sent</div>
                                </div>
                            </div>`;


    let OutgoingMessage = document.createElement('div');
    OutgoingMessage.className = 'flex items-start gap-2.5 flex-row-reverse mb-2 transition-all duration-300 ease-in-out';
    OutgoingMessage.innerHTML = OutgoingMessageTemplate;

    OutgoingMessage.setAttribute('data-message-id', msgId);

    let messageList = chat.querySelector('.chat-msgs');
    messageList.appendChild(OutgoingMessage);
    messageList.scrollTop = messageList.scrollHeight;

}

async function getChat(chatId){
    let chatRequest = fetch('/api/get-messages?chat_id='+chatId);
    let chat = await chatRequest;
    let chatData = await chat.json();     

    return chatData;
}

