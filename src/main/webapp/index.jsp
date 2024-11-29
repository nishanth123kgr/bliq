<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Bliq</title>
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
                        dark_primary: '#3F57FF',
                        primary_light: '#5468ff1a'
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

        .active {
            border-left: 4px solid #5468ff !important;
        }

        .active-chat {
            border: 1px solid #5468ff;
            background-color: #5468ff1a;

        }

        .active-chat .incoming {
            background-color: #fff !important;
        }

        #create-grp-btn:hover, #user-search-btn:hover, button:hover {
            background: #3F57FF;
        }


    </style>
    <link rel="stylesheet" href="https://site-assets.fontawesome.com/releases/v6.6.0/css/all.css">
    <link rel="icon" href="assets/logo/favicon.ico" type="image/x-icon">
</head>

<body class="h-screen w-screen font-montserrat overflow-hidden" data-user-id=<%=session.getAttribute("userId")%>>

    <nav class="h-[50px] w-full border-b flex items-center px-4 justify-between">
        <div class="flex items-center">
            <img src="assets/logo/logo-light-white.png" alt="logo" class="h-[20px] w-[20px] ">
            <h1 class="text-primary text-2xl ml-2 font-bold">Bliq</h1>
        </div>

        <div class="flex items-center">
            <div class="username mr-2">
                <%= session.getAttribute("name") %>
            </div>
            <div
                class="prof h-[35px] w-[35px] rounded-full bg-white border border-primary flex items-center justify-center">
                <img src="assets/logo/logo-light-white.png" alt="logo" class="h-[20px] w-[20px]">
            </div>
        </div>

    </nav>
    <div class="main flex h-[calc(100vh-50px)]">
        <div class="sidebar w-[300px] h-full border-r">
            <div class="chat-header p-2 border-b flex justify-between">
                <div class="text-2xl font-bold pl-2">Chats</div>
                <div class="flex items-center">
                    <div id="user-search-btn"
                        class="search flex items-center mr-2 h-[30px] w-[30px] rounded-full bg-primary text-white justify-center cursor-pointer">
                        <i class="fa-solid fa-magnifying-glass"></i>

                    </div>
                    <div id="create-grp-btn" data-modal-target="group-modal" data-modal-toggle="group-modal"
                        class="create-grp flex items-center mr-2 h-[30px] w-[30px] rounded-full bg-primary text-white justify-center cursor-pointer">
                        <i class="fa-solid fa-user-group fa-sm"></i>

                    </div>
                </div>


            </div>

            <div class="conversations px-2 pt-2">
                <div class="conv-head-text font-thick ml-2 text-lg font-semibold">Conversations</div>
                <div class="conv-list">
                    <div class="conv-item">
                        <!-- <div class="flex items-center px-4 py-1 mt-2 cursor-pointer active">
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
                        </div> -->
                    </div>
                </div>
            </div>

            <div class="groups px-2 pt-2">
                <div class="group-head-text font-thick ml-2 text-lg font-semibold">Groups</div>
                <div class="group-list">
                    <div class="group-item">
                        <!-- <div class="flex items-center px-4 py-1 mt-2 cursor-pointer active">
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
                        </div> -->
                    </div>
                </div>
            </div>

        </div>
        <div class="chat-section w-full h-[calc(100vh-50px)]">
            <div class="flex-container flex flex-wrap w-full h-full gap-1 p-1" id="chat-container">

            </div>



        </div>
    </div>

    <div class="inset-0 bg-black bg-opacity-50 flex items-center justify-center p-4" id="search-modal">
        <div class="bg-white rounded-lg w-full max-w-md p-6 space-y-4" style="min-height: 500px;">
            <!-- Header -->
            <div class="flex justify-between items-center">
                <h2 class="text-xl font-semibold">Search Users</h2>
                <button class="text-gray-500 hover:text-gray-700" onclick="closeSearchModal()">
                    <i class="fa-solid fa-times"></i>
                </button>
            </div>

            <!-- Search Bar -->
            <div class="relative">
                <input type="text" id="searchInput" placeholder="Search users..."
                    class="w-full pl-4 pr-10 py-2 border border-primary rounded-lg focus:outline-primary focus:ring-2 focus:ring-primary transition duration-200" />
                <i
                    class="fa-solid fa-magnifying-glass absolute right-3 top-1/2 transform -translate-y-1/2 text-primary"></i>
            </div>

            <!-- User List -->
            <div class="space-y-2 overflow-y-auto border-t border-gray-200 pt-4" style="max-height: 300px;"
                id="userList">
                <!-- Example User -->
                <div class="text-gray-500 text-center">Start Typing to Search</div>
            </div>
        </div>
    </div>

    <div class="inset-0 bg-black bg-opacity-50 flex items-center justify-center p-4" id="group-modal">
        <div class="bg-white rounded-lg w-full max-w-4xl p-6 space-y-4" style="min-height: 500px;">
            <!-- Header -->
            <div class="flex justify-between items-center">
                <h2 class="text-xl font-semibold">Create Group</h2>
                <div class="text-gray-500 hover:text-gray-700 cursor-pointer" onclick="closeGroupModal()">
                    <i class="fa-solid fa-times"></i>
                </div>
            </div>
            <div class="add-group-container flex">


                <div class="left w-1/2 h-full">
                    <div class="col-span-2 mb-2">
                        <div class="flex items-center justify-between">
                            <label for="name" class="block mb-2 text-sm font-medium text-gray-900">Group Name</label>
                            <div class="flex items-center gap-1 mb-2">
                                <input type="radio" name="privacy" id="public" value="public" checked
                                    class="mr-1 cursor-pointer text-primary bg-gray-100 border-gray-300 focus:ring-primary">
                                <label for="public" class="block text-sm font-medium text-gray-900 mr-1 cursor-pointer">Public</label>
                                <input type="radio" name="privacy" id="private" value="private" 
                                class="mr-1 cursor-pointer text-primary bg-gray-100 border-gray-300 focus:ring-primary">
                                <label for="private" class="block text-sm font-medium text-gray-900 cursor-pointer">Private</label>
                            </div>
                        </div>
                        <input type="text" name="name" id="group-name"
                            class="border border-primary text-sm rounded-lg transition duration-200 focus:outline-primary focus:ring-primary focus:ring-2  block w-full p-2.5 "
                            placeholder="Type Group name" required="true">
                    </div>

                    <!-- Search Bar -->
                    <div class="relative mb-2">
                        <input type="text" id="searchGrpInput" placeholder="Add users..."
                            class="border border-primary text-sm rounded-lg transition duration-200 focus:outline-primary focus:ring-primary focus:ring-2  block w-full p-2.5 pr-10" />
                        <i
                            class="fa-solid fa-magnifying-glass absolute right-3 top-1/2 transform -translate-y-1/2 text-primary"></i>
                    </div>

                    <!-- User List -->
                    <div class="space-y-2 overflow-y-auto border-t border-gray-200 pt-4" style="max-height: 300px;"
                        id="groupList">
                        <!-- Example User -->
                        <div class="text-gray-500 text-sm text-center">Start Typing to Search</div>
                    </div>
                </div>

                <div class="separator border-l mx-3" style="min-height: 500px;">

                </div>

                <div class="right w-1/2">
                    <div class="flex w-full h-[500px]">

                        <div class="group-members flex flex-col w-full">
                            <div class="text-lg font-semibold mb-2 w-full">Selected Members</div>
                            <div class="members-list space-y-2 overflow-y-auto border-t w-full border-gray-200 "
                                style="max-height: 500px;" id="selectedMembers">
                                <!-- Example User -->
                                <div class="text-gray-500 text-sm text-center pt-4" id="no-members-text">No Members Selected</div>
                            </div>
                        </div>



                    </div>
                    <div class="flex w-full flex-col">
                        <button id="create-btn"
                            class="self-end create-group-btn bg-primary text-white rounded-lg px-4 py-2 mt-4 transition duration-200">
                            Create Group
                        </button>
                    </div>

                </div>
            </div>
        </div>
    </div>


    <script>
        const urlParams = new URLSearchParams();
        urlParams.append("userId", document.body.getAttribute("data-user-id"));
        urlParams.append("status", "1");

        async function updateStatus() {
            const update = await fetch("/api/set-user-status", {
                method: "POST",
                headers: {
                    "Content-Type": "application/x-www-form-urlencoded",
                },
                body: urlParams,
            });

            let data = await update.json()

            console.log(data)
        }

        updateStatus()

    </script>

    <script>
        window.addEventListener("beforeunload", (event) => {
            event.returnValue = "Are you sure you want to leave?";
            urlParams.set("status", "0");
            updateStatus();

        });
    </script>

    <script src="js/index_user_search.js"></script>
    <script src="js/message_utils.js"></script>
    <script src="js/index.js"></script>
</body>

</html>