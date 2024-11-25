<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <style>
        @import url("https://fonts.googleapis.com/css2?family=Montserrat:wght@400;700;900&display=swap");
    </style>
    <script>
        tailwind.config = {
            theme: {
                extend: {
                    colors: {
                        primary: '#5468ff',
                        secondary: '#378ce7',
                        tertiary: '#D3D9FF',
                    },
                    fontFamily: {
                        montserrat: ['Montserrat', 'sans-serif'],
                    },
                }
            }
        }
    </script>
    <style>
        .flex-container>* {
            flex: 1 1 auto;
            /* Allow children to grow but not shrink */
        }
        .active{
            border-left: 4px solid #5468ff;
        }
    </style>
    <link rel="stylesheet" href="https://site-assets.fontawesome.com/releases/v6.6.0/css/all.css">

</head>

<body class="h-screen w-screen font-montserrat overflow-hidden">
    <nav class="h-[50px] w-full border-b flex items-center px-4 justify-between">
        <div class="flex items-center">
            <img src="assets/logo/logo-light-white.png" alt="logo" class="h-[20px] w-[20px] ">
            <h1 class="text-primary text-2xl ml-2 font-bold">Bliq</h1>
        </div>

        <div class="flex items-center">
            <div class="username mr-2">
                <%= session.getAttribute("name") %>
            </div>
            <div class="prof h-[35px] w-[35px] rounded-full bg-white border border-primary flex items-center justify-center">
                <img src="assets/logo/logo-light-white.png" alt="logo" class="h-[20px] w-[20px]">
            </div>
        </div>
        
    </nav>
    <div class="main flex h-[calc(100vh-50px)]">
        <div class="sidebar w-[300px] h-full border-r">
            <div class="chat-header p-2 border-b">
                <div class="text-2xl font-bold mb-2 pl-2">Chats</div>
                <div class="search flex items-center">
                    <div class="relative w-full">
                        <input type="text"
                            class="w-full p-2 border rounded-full pl-10 focus:outline-primary  focus:outline-primary focus:ring-2 focus:ring-primary transition duration-200"
                            placeholder="Search">
                        <i
                            class="fa-solid fa-magnifying-glass absolute left-3 top-1/2 transform -translate-y-1/2 text-gray-400"></i>
                    </div>
                </div>

            </div>

            <div class="conversations px-2 pt-2">
                <div class="conv-head-text font-thick ml-2 text-lg font-semibold">Conversations</div>
                <div class="conv-list h-[30px]">
                    <div class="conv-item">
                        <div class="flex items-center px-4 py-1 mt-2 cursor-pointer active">
                            <div class="status w-[7px] h-[7px] rounded-full bg-red-600"></div>
                            <div class="name ml-2 text-sm">John Doe</div>
                        </div>
                        <div class="flex items-center px-4 py-1 mt-2 cursor-pointer">
                            <div class="status w-[7px] h-[7px] rounded-full bg-green-600"></div>
                            <div class="name ml-2 text-sm">John Doe</div>
                        </div>
                        <div class="flex items-center px-4 py-1 mt-2 cursor-pointer">
                            <div class="status w-[7px] h-[7px] rounded-full bg-red-600"></div>
                            <div class="name ml-2 text-sm">John Doe</div>
                        </div>
                        <div class="flex items-center px-4 py-1 mt-2 cursor-pointer">
                            <div class="status w-[7px] h-[7px] rounded-full bg-green-600"></div>
                            <div class="name ml-2 text-sm">John Doe</div>
                        </div>
                    </div>
                </div>
            </div>

        </div>
        <div class="chat-section w-full h-[calc(100vh-50px)]">
            <div class="flex-container flex flex-wrap w-full h-full gap-1 p-1" id="chat-container">
               
            </div>



        </div>
    </div>

    <script src="js/index.js"></script>
</body>

</html>
