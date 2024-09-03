<script setup>

import {ref} from "vue";
import Utils from '@/scripts/Utils.js'

const username = ref("")
const rawPassword = ref("")

const wrongCredsErr = ref(false)

async function authorize() {
  const userDto = {
    username: username.value,
    rawPassword: rawPassword.value
  }

  fetch("http://localhost:8765/auth-service/api/v1/auth",
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

        const responseText = await response.text()

        wrongCredsErr.value = false

        switch (responseText) {
          case "Имя пользователя или пароль неверные": {
            wrongCredsErr.value = true
          }
        }

        throw Error("Credentials are wrong")
      })
      .then((responseJson) => {
        document.cookie = "_at=" + responseJson.token + "; max-age=1800"
        document.location.href = '/?i=auth'
      })
      .catch((error) => {
        console.log(error)
      });
}

function test() {
  fetch("http://localhost:8765/auth-service/api/v1/test",
      {
        headers: {
          Authorization: "Bearer " + Utils.getCookie("_at")
        }
      })
      .then(async (response) => {

        console.log(await response.text())
      })
}
</script>

<template>
  <a class="block-center m-t-3em" href="/"><img src="@/assets/images/logo.png"></a>
  <div class="block-center">На главную</div>
  <div class="w-100 p-tb-3em m-t-5em block-center">
    <h1 class="block-center">GoCow Авторизация</h1>
    <div class="err-field" v-if="wrongCredsErr">Логин и (или) пароль неверные</div>
    <div class="block-center">
      <div class="lbl-field">Имя пользователя</div>
      <input name="username" v-model="username">
    </div>
    <div class="block-center">
      <div class="lbl-field">Пароль</div>
      <input name="rawPassword" type="password" v-model="rawPassword">
    </div>
    <div>
      <button @click="authorize">Авторизоваться</button>
    </div>
    <hr>
    <div class="block-center" style="color: white">Еще нет аккаунта?
      <a style="color: white" href="/reg">
        <b>Зарегистрироваться</b>
      </a>
    </div>
  </div>

  <button @click="test">тест</button>
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

.err-field {
  font-family: 'codropsicons';
  color: #a10000;
  font-size: 1.3em;
}
</style>