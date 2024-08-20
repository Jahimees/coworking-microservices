<script setup>

import {ref} from "vue";

const username = ref("")
const rawPassword = ref("")
const confirmPassword = ref("")
const email = ref("")

const isUsernameErr = ref(false)
const isEmailErr = ref(false)
const isServiceUnavailable = ref(false)

async function register() {
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
  ).then(async (response) => {
    if (response.ok) {
      return response.json();
    }

    throw new Error(await response.text());
  })
      .then((responseJson) => {
        // Do something with the response
      })
      .catch((error) => {
        console.log(error)
      });

  // if (response.ok) { // если HTTP-статус в диапазоне 200-299
  //                    // получаем тело ответа (см. про этот метод ниже)
  //   let json = await response.json();
  // } else {
  //   console.log("Ошибка HTTP: " + response.status);
  // }
  //
  // if (response.statusText === "Internal Server Error" || response.statusText === "Service Unavailable") {
  //   isServiceUnavailable.value = true;
  //   return
  // } else {
  //   isServiceUnavailable.value = false;
  // }
  //
  // switch (response.body) {
  //   case "User object is empty. Check username, email and password": {
  //     console.log("ERROR")
  //   }
  // }
  // console.log(response)
}

</script>

<template>
  <div class="w-75 p-tb-3em m-t-5em block-center">
    <div class="err-field" v-if="isServiceUnavailable">Сервис недоступен, попробуйте позже</div>
    <div class="block-center">
      <div class="lbl-field">Имя пользователя</div>
      <div class="err-field" v-if="isUsernameErr">Имя пользователя уже занято</div>
      <input name="username" v-model="username">
    </div>
    <div class="block-center">
      <div class="lbl-field">Электронная почта</div>
      <div class="err-field" v-if="isEmailErr">Электронная почта уже занята</div>
      <input name="email" v-model="email">
    </div>
    <div class="block-center">
      <div class="lbl-field">Пароль</div>
      <div class="err-field" v-if="rawPassword !== confirmPassword">Пароли не совпадают</div>
      <input name="rawPassword" type="password" v-model="rawPassword">
    </div>
    <div class="block-center">
      <div class="lbl-field">Подтверждение пароля</div>
      <input name="confirmPassword" type="password" v-model="confirmPassword">
    </div>
    <div>
      <button @click="register">Зарегистрироваться</button>
    </div>
  </div>
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

.w-75 {
  background: linear-gradient(90deg, rgba(132,82,231,0.6979166666666667) 0%, rgba(78,78,233,0.5130427170868348) 48%, rgba(77,81,233,0.5382528011204482) 52%, rgba(0,212,255,0.48783263305322133) 100%);
}
</style>