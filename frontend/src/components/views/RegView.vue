<script setup>

import {ref} from "vue";

const username = ref("")
const rawPassword = ref("")
const confirmPassword = ref("")
const email = ref("")

const isUsernameErr = ref(false)
const isEmailErr = ref(false)
const isServiceUnavailable = ref(false)
const isFieldsEmptyErr = ref(false)

function register() {

  isFieldsEmptyErr.value = !!isFieldsEmpty()

  if (isFieldsEmptyErr.value || !isSamePasswords()) return;

  const userDto = {
    username: username.value,
    rawPassword: rawPassword.value,
    email: email.value
  }

  fetch("http://localhost:8765/auth-service/api/v1/reg",
      {
        method: "POST",
        body: JSON.stringify(userDto),
        headers: {
          "Content-Type": "application/json"
        },
        crossDomain: true
      }
  )
      .then(async (response) => {
        if (response.ok) {
          return response.json();
        }

        let text = await response.text()

        clearErrors()

        switch (text) {
          case "User with this username already exists": {
            isUsernameErr.value = true
            break
          }
          case "User with this email already exists": {
            isEmailErr.value = true
            break
          }
          default: {
            isServiceUnavailable.value = true
          }
        }

        throw new Error(text)
      })
      .then((responseJson) => {
        window.location.href = "/?i=reg"
      })
      .catch((error) => {
        console.log(error)
      });
}

function isFieldsEmpty() {
  return username.value === undefined || username.value === ""
      || rawPassword.value === undefined || rawPassword.value === ""
      || email.value === undefined || email.value === ""
}

function isSamePasswords() {
  return rawPassword.value === confirmPassword.value
}

function clearErrors() {
  isUsernameErr.value = false
  isEmailErr.value = false
  isServiceUnavailable.value = false
}

</script>

<template>
  <a class="block-center m-t-3em" href="/"><img src="@/assets/images/logo.png"></a>
  <div class="block-center">На главную</div>
  <form class="w-100 p-tb-3em m-t-3em block-center">
    <h1 class="block-center">GoCow Регистрация</h1>
    <div class="err-field" v-if="isServiceUnavailable">Сервис недоступен, попробуйте позже</div>
    <div class="err-field" v-if="isFieldsEmptyErr">Все поля должны быть заполнены!</div>
    <div class="block-center">
      <div class="lbl-field">Имя пользователя</div>
      <div class="err-field" v-if="isUsernameErr">Имя пользователя уже занято</div>
      <input name="username" v-model="username" required>
    </div>
    <div class="block-center">
      <div class="lbl-field">Электронная почта</div>
      <div class="err-field" v-if="isEmailErr">Электронная почта уже занята</div>
      <input name="email" v-model="email" required>
    </div>
    <div class="block-center">
      <div class="lbl-field">Пароль</div>
      <div class="err-field" v-if="rawPassword !== confirmPassword">Пароли не совпадают</div>
      <input name="rawPassword" type="password" v-model="rawPassword" required>
    </div>
    <div class="block-center">
      <div class="lbl-field">Подтверждение пароля</div>
      <input name="confirmPassword" type="password" v-model="confirmPassword" required>
    </div>
    <div>
      <button type="submit" @click.prevent="register">Зарегистрироваться</button>
    </div>
    <hr>
    <div class="block-center" style="color: white">Уже зарегистрированы?
      <a style="color: white" href="/auth">
        <b>Авторизоваться</b>
      </a>
    </div>
  </form>
</template>

<style scoped>

@font-face {
  font-weight: normal;
  font-style: normal;
  font-family: 'codropsicons';
  src: url('../fonts/codropsicons/codropsicons.eot');
  src: url('../fonts/codropsicons/codropsicons.eot?#iefix') format('embedded-opentype'),
  url('../fonts/codropsicons/codropsicons.woff') format('woff'),
  url('../fonts/codropsicons/codropsicons.ttf') format('truetype'),
  url('../fonts/codropsicons/codropsicons.svg#codropsicons') format('svg');
}

.err-field {
  font-family: 'codropsicons';
  color: #a10000;
  font-size: 1.3em;
}

.lbl-field {
  font-family: 'codropsicons';
  font-size: 2em;
  color: white;
}

input {
  font-size: 2em;
  font-family: 'codropsicons';
}

button {
  font-size: 2em;
  font-family: 'codropsicons';
  margin-top: 0.5em;
  border-radius: 0.3em;
  padding: 0.4em 0.5em;
}

button:hover {
  background-color: hsl(243, 94%, 87%);
  color: white;
}

.w-100 {
  background: linear-gradient(90deg, rgba(168, 162, 255, 0.6811099439775911) 0%, rgba(42, 42, 255, 0.4682247899159664) 49%, rgba(0, 212, 255, 0.5886729691876751) 100%);
}

h1 {
  font-size: 4em;
  margin: 0.3em 0;
  color: white;
  font-family: 'Raleway', Calibri, Arial, sans-serif;
}

img {
  width: 100px;
  cursor: pointer;
}

img:hover {
  transform: rotate(360deg);
  transition: all 1s;
}

a.block-center:hover {
  background: none;
}

</style>