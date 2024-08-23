<script setup>

import {ref} from "vue";

const username = ref("sadf")
const rawPassword = ref("sdf")

async function authorize() {
  const userDto = {
    username: username.value,
    rawPassword: rawPassword.value
  }

  console.log(userDto)
  console.log(JSON.stringify(userDto))
  const response = await (await fetch("http://localhost:8765/auth-service/api/v1/auth",
      {
        method: "POST",
        body: JSON.stringify(userDto),
        headers: {
          "Content-Type": "application/json"
        },
        crossDomain: true
      }
  )).json();

}

</script>

<template>
  <div class="w-75 p-tb-3em m-t-5em block-center">
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
  background-image: url('@/assets/images/header-back.jpg');
  background-repeat: no-repeat;
  background-size: cover;
  background-position: center;
}
</style>