<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>Login</title>
  <link href="css/bootstrap.min.css" rel="stylesheet" />
  <script src="js/bootstrap.bundle.min.js"></script>
  <link rel="stylesheet" href="css/register.css" />
  <link rel="stylesheet" href="css/fontawesome.css" />
</head>
<body class="d-flex content-center items-center m-0 p-0 overflow-hidden">
<div class="main d-flex position-fixed">
  <div class="left position-relative d-flex">
    <div class="left-content d-flex flex-column h-100">
      <span class="fs-5 text-white logo">Bliq</span>
      <div class="mid-container">
        <div class="blob-design-container d-flex">
          <div class="blob-design">
            <div class="triangle"></div>
            <div class="circle"></div>
            <div class="rectangle"></div>
          </div>
        </div>
        <div class="welcome-head">Welcome to Bliq!</div>
        <div class="welcome-text">
          <p>Join the conversation and connect like never before.</p>
          <ul>
            <li>Chat seamlessly with friends, family, or your team.</li>
            <li>Stay connected in real-time with secure messaging.</li>
            <li>Collaborate effortlessly with groups or one-on-one chats.</li>
          </ul>
          <p>
            <strong>Sign Up now</strong> and take the first step into a world of meaningful connections.
          </p>
          <p>Your Conversations, Your Way - Powered by Bliq.</p>
        </div>
      </div>
      <div class="mob-rectangle"></div>
      <div class="mob-circle"></div>
      <div class="mob-form align-items-center">
        <div
                class="w-100 d-flex flex-column justify-content-center align-items-center"
        >
          <div class="error-msg"></div>
          <!-- <label for="name">Full Name</label>
      <input type="text" id="name" name="name" class="form-control" /> -->
          <label for="email-mob">Email</label>
          <input
                  type="email"
                  id="email-mob"
                  name="email"
                  class="form-control"
          />
          <label for="password-mob">Password</label>
          <div class="position-relative" style="width: 85%">
            <input
                    type="password"
                    id="password-mob"
                    name="password"
                    class="form-control pass-input w-100"
                    onfocus="this.style.marginBottom = '20px'"
            /><i
                  class="fa-solid fa-eye fa-lg pass-toggle position-absolute end-0 translate-middle"
                  style="color: #707070; cursor: pointer; top: 30px"
          ></i>
          </div>
          <div class="login-text text-white" style="width: 85%; margin-bottom: 10px">
            <label for="reset-button">
              <a href="forgot-password.jsp" id="reset-button" class="fw-bolder text-white">Reset Password</a>
            </label>
          </div>
          <!-- <div class="valid-password">
        <div class="pass-items p-2 rounded-3" >
          Set a password with
          <div>
            <div>- Atleast 8 characters</div>
            <div>- Atleast 1 number</div>
            <div>- Atleast 1 alphabet</div>
          </div>
        </div>
      </div> -->
          <button class="submit bg-light">LOGIN</button>
          <div class="mt-3 login-text text-white position-relative text-center">
            Don't have an account? <a href="register.html" class="text-white fw-bolder">Sign Up</a>
          </div>
        </div>
      </div>
      <footer class="text-white">All rights reserved. © 2024 Bliq</footer>
    </div>
    <div class="waves">
      <div class="wave"></div>
      <div class="wave"></div>
      <div class="wave"></div>
      <div class="wave"></div>
      <div class="wave"></div>
      <div class="wave"></div>
      <div class="wave"></div>
      <div class="wave"></div>
      <div class="wave"></div>
      <div class="wave"></div>
      <div class="wave"></div>
      <div class="wave"></div>
      <div class="wave"></div>
      <div class="wave"></div>
      <div class="wave"></div>
      <div class="wave"></div>
      <div class="wave"></div>
      <div class="wave"></div>
      <div class="wave"></div>
      <div class="wave"></div>
      <div class="wave"></div>
      <div class="wave"></div>
      <div class="wave"></div>
      <div class="wave"></div>
      <div class="wave"></div>
      <div class="wave"></div>
      <div class="wave"></div>
      <div class="wave"></div>
      <div class="wave"></div>
      <div class="wave"></div>
      <div class="wave"></div>
      <div class="wave"></div>
      <div class="wave"></div>
      <div class="wave"></div>
      <div class="wave"></div>
      <div class="wave"></div>
      <div class="wave"></div>
      <div class="wave"></div>
      <div class="wave"></div>
      <div class="wave"></div>
    </div>
  </div>
  <div class="right d-flex align-center">
    <div
            class="right-content d-flex flex-column justify-content-center align-items-center h-100 w-100"
    >
      <div
              class="right-content-header position-relative d-flex flex-column justify-content-center align-items-center"
      >
        <span class="sign-up-text fs-1">LOGIN</span>
        <div class="login-text">
          Don't have an account? <a href="register.html">Sign Up</a>
        </div>
      </div>
      <div
              class="right-content-form w-100 d-flex flex-column justify-content-center align-items-center p-4"
      >
        <div class="error-msg"></div>
        <!-- <label for="name">Full Name</label>
        <input type="text" id="name" name="name" class="form-control" /> -->
        <label for="email">Email</label>
        <input type="email" id="email" name="email" class="form-control" />
        <label for="password">Password</label>
        <div class="position-relative" style="width: 70%">
          <input
                  type="password"
                  id="password"
                  name="password"
                  class="form-control pass-input w-100"
                  onfocus="this.style.marginBottom = '20px'"
          /><i
                class="fa-solid fa-eye fa-lg pass-toggle position-absolute end-0 translate-middle"
                style="color: #707070; cursor: pointer; top: 30px"
        ></i>
        </div>
        <!-- <div class="valid-password">
          <div class="pass-items p-2 rounded-3" >
            Set a password with
            <div>
              <div>- Atleast 8 characters</div>
              <div>- Atleast 1 number</div>
              <div>- Atleast 1 alphabet</div>
            </div>
          </div>
        </div> -->
        <button class="submit">LOGIN</button>
        <div class="mt-3 login-text">
          Forgot Password? <a href="forgot-password.jsp">Reset</a>
        </div>
      </div>
    </div>
  </div>
</div>
<script src="js/login.js" type="module"></script>
</body>
</html>
